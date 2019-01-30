package com.ingic.lmslawyer.fragments.calender;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.CaseDetail;
import com.ingic.lmslawyer.entities.NewCasesEnt;
import com.ingic.lmslawyer.entities.User;
import com.ingic.lmslawyer.entities.event_entities.Event;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.DateHelper;
import com.ingic.lmslawyer.helpers.DatePickerHelper;
import com.ingic.lmslawyer.helpers.SimpleDividerItemDecorationWithoutLastItem;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.ui.adapters.SpinnerAdapter;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.SearchMembersAdapter;
import com.ingic.lmslawyer.ui.views.AnyEditTextView;
import com.ingic.lmslawyer.ui.views.AnyTextView;
import com.ingic.lmslawyer.ui.views.TitleBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.lmslawyer.constants.WebServiceConstant.CaseUsers;
import static com.ingic.lmslawyer.constants.WebServiceConstant.CreateEvent;
import static com.ingic.lmslawyer.constants.WebServiceConstant.LawyerNewCases;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends BaseFragment {

    private final String TAG = CreateEventFragment.class.getSimpleName();

    @BindView(R.id.ietCaseName)
    TextInputEditText ietCaseName;
    @BindView(R.id.ietTitleAgenda)
    TextInputEditText ietTitleAgenda;
    @BindView(R.id.toggleReminder)
    ToggleButton toggleReminder;
    @BindView(R.id.etSearch)
    AnyEditTextView etSearch;
    @BindView(R.id.rvSearchList)
    RecyclerView rvSearchList;
    Unbinder unbinder;
    @BindView(R.id.ietDate)
    TextInputEditText ietDate;
    @BindView(R.id.ietTime)
    TextInputEditText ietTime;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.btnCreateEvent)
    Button btnCreateEvent;
    private ArrayList<User> list = new ArrayList<>();
    private RecyclerViewListAdapter<User> adapter;
    private int startYear;
    private int startMonth;
    private int startDay;
    DatePickerDialog datePickerDialog;
    Calendar mCalendar = Calendar.getInstance();
    @BindView(R.id.spn_cases)
    Spinner spnCases;

    private ArrayList<User> usersCollection = new ArrayList<>();
    private Date DateSelected;
    private Date TimeSelected;
    private ArrayList<CaseDetail> recentCases = new ArrayList<>();
    private String caseId;
    private String caseTitle;
    private String membersIds;

    private Event eventDetail;
    private boolean isEditEvent = false;
    private ArrayList<String> members;


    public CreateEventFragment() {
        // Required empty public constructor
    }

    public static CreateEventFragment newInstance(boolean isEditEvent, Event eventDetail) {
        Bundle args = new Bundle();
        args.putSerializable(AppConstant.EVENT_DETAIL, eventDetail);
        args.putBoolean(AppConstant.IS_EDIT_EVENT, isEditEvent);
        CreateEventFragment fragment = new CreateEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            eventDetail = (Event) getArguments().getSerializable(AppConstant.EVENT_DETAIL);
            isEditEvent = getArguments().getBoolean(AppConstant.IS_EDIT_EVENT);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTextWatecher();
        if (eventDetail == null) {
            serviceHelper.enqueueCall(webService.getLawyerNewCases(prefHelper.getUser().getToken()), LawyerNewCases);
            serviceHelper.enqueueCall(webService.getEventUsers(prefHelper.getUser().getToken(), 0), CaseUsers);
        } else {
            setEventDetail();
            serviceHelper.enqueueCall(webService.getEventUsers(prefHelper.getUser().getToken(), eventDetail.getId()), CaseUsers);
        }
        spinnerListner();
    }

    private void setEventDetail() {

        CaseDetail selectedCase = new CaseDetail();
        selectedCase.setSubject(eventDetail.getRegisterCase().getSubject());
        selectedCase.setId(eventDetail.getRegisterCase().getId());
        selectedCase.setUserId(eventDetail.getRegisterCase().getUserId());
        selectedCase.setLawyerId(eventDetail.getRegisterCase().getLawyerId());
        recentCases.add(selectedCase);

        SpinnerAdapter adapter = new SpinnerAdapter(getDockActivity(),
                R.layout.spinner_item_activities, R.id.txt, recentCases, prefHelper, getMainActivity());
        spnCases.setAdapter(adapter);


        ietTitleAgenda.setText(eventDetail.getTitle());
        ietTime.setText(eventDetail.getTime());
        ietDate.setText(eventDetail.getDate());
        DateSelected = DateHelper.stringToDate(eventDetail.getDate()
                , DateHelper.DATE_FORMAT);

        if (eventDetail.getIsReminder() == 1) {
            toggleReminder.setChecked(true);
        } else {
            toggleReminder.setChecked(false);
        }

        btnCreateEvent.setText(getDockActivity().getResources().getString(R.string.edit_event));

        if (getTitleBar() != null) {
            if (isEditEvent) {
                getTitleBar().setSubHeading(getDockActivity().getResources().getString(R.string.edit_event));
            } else {
                getTitleBar().setSubHeading(getDockActivity().getResources().getString(R.string.create_event));
            }
        }

    }


    private void spinnerListner() {

        spnCases.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (recentCases.get(position).getId() != null) {
                    caseId = String.valueOf(recentCases.get(position).getId());
//                    caseTitle = String.valueOf(recentCases.get(position).getSubject());
                } else {
                    caseId = null;
//                    caseTitle = null;
                }
                members = new ArrayList<>();
                members.add(String.valueOf(recentCases.get(position).getUserId()));
                members.add(String.valueOf(recentCases.get(position).getLawyerId()));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initFromPickerValidated(final TextInputEditText textView) {

        Calendar calendar = Calendar.getInstance();
        final DatePickerHelper datePickerHelper = new DatePickerHelper();
        datePickerHelper.initDateDialog(
                getDockActivity(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
                , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Date date = new Date();
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        Date dateSpecified = c.getTime();
                        if (dateSpecified.before(date)) {
                            UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.date_after_error));
                        } else {
                            DateSelected = dateSpecified;
                            String predate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

                            textView.setText(predate);
                            textView.setPaintFlags(Typeface.BOLD);
                        }
                        /*Date dateSpecified = c.getTime();
                        DateSelected = dateSpecified;
                        String predate = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
                        textView.setText(predate);
                        textView.setPaintFlags(Typeface.BOLD);*/


                    }
                }, "PreferredDate", new Date());

        datePickerHelper.showDate();
    }

    private void initTimePicker(final TextView textView) {
        if (DateSelected != null) {
//        if (!ietDate.getText().toString().isEmpty()) {
            TimePickerDialog dialog = new TimePickerDialog(getDockActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Date date = new Date();
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    c.set(year, month, day, hourOfDay, minute);
                    Calendar calcurrent = Calendar.getInstance();
                    calcurrent.setTime(date);
                    int currentHour = calcurrent.get(Calendar.HOUR_OF_DAY);
                    int currentMinute = calcurrent.get(Calendar.MINUTE);
                    calcurrent.set(Calendar.MINUTE, currentMinute + 30);
                    int addedHalfhour = calcurrent.get(Calendar.HOUR_OF_DAY);
                    int addedHalfhourMinute = calcurrent.get(Calendar.MINUTE);


                    if (DateHelper.isSameDay(DateSelected, date) && !DateHelper.isTimeAfter(currentHour, currentMinute, hourOfDay, minute)) {
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.less_time_error));

                    } /*else if (DateHelper.isSameDay(DateSelected, date) && !DateHelper.isTimeAfter(addedHalfhour, addedHalfhourMinute, hourOfDay, minute)) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Select Time 30 min after current time");

                    }*/ else {

                        TimeSelected = c.getTime();
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, day, hourOfDay, minute + 15);
                        String preTime = new SimpleDateFormat("HH:mm:ss").format(c.getTime());
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(preTime);
                        textView.setPaintFlags(Typeface.BOLD);
                    }
                }
            }, DateSelected.getHours(), DateSelected.getMinutes(), true);

            dialog.show();

        } else {
            UIHelper.showShortToastInCenter(getDockActivity(), getResources().getString(R.string.select_pickup_date_first));
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        switch (Tag) {
            case WebServiceConstant.LawyerNewCases:

                NewCasesEnt newCases = (NewCasesEnt) result;

                recentCases = newCases.getRecent();

                if (recentCases.size() <= 0) {
//                    CaseDetail noitem = new CaseDetail();
//                    noitem.setSubject("No Case");
                    recentCases.add(new CaseDetail("No Case"));

                    SpinnerAdapter adapter = new SpinnerAdapter(getDockActivity(),
                            R.layout.spinner_item_activities, R.id.txt, recentCases, prefHelper, getMainActivity());
                    spnCases.setAdapter(adapter);

                } else {
                    recentCases.add(0, new CaseDetail("Please Select"));
                    SpinnerAdapter adapter = new SpinnerAdapter(getDockActivity(),
                            R.layout.spinner_item_activities, R.id.txt, recentCases, prefHelper, getMainActivity());
                    spnCases.setAdapter(adapter);

                }

                break;

            case WebServiceConstant.CaseUsers:

                ArrayList<User> caseUsers = (ArrayList<User>) result;
                initAdapter(caseUsers);


                break;

            case WebServiceConstant.CreateEvent:
                getDockActivity().popFragment();
                if (isEditEvent) {
                    getDockActivity().popFragment();
                }

                UIHelper.showShortToastInCenter(getDockActivity(), message);


                break;
        }
    }

    private void setTextWatecher() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bindData(getSearchedArray(s.toString()));
            }
        });
    }

    public ArrayList<User> getSearchedArray(String keyword) {
        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<User> arrayList = new ArrayList<>();

        String UserName = "";
        for (User item : list) {
            UserName = item.getFullName();

            if (Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(UserName).find()) {
                arrayList.add(item);
            }
        }
        return arrayList;

    }


    private void initAdapter(ArrayList<User> caseUsers) {


        adapter = new SearchMembersAdapter(getDockActivity());
        rvSearchList.setLayoutManager(new LinearLayoutManager(getDockActivity(),
                LinearLayoutManager.VERTICAL, false));
        rvSearchList.addItemDecoration(new SimpleDividerItemDecorationWithoutLastItem(getDockActivity()));

        for (int i = 0; i < recentCases.size(); i++) {
            for (int j = 0; j < caseUsers.size(); j++) {
                if (recentCases.get(i).getLawyerId() == caseUsers.get(j).getId() || recentCases.get(i).getUserId() == caseUsers.get(j).getId()) {
                    Log.d(TAG, "initAdapter: " + caseUsers.get(j).getId());
                    caseUsers.remove(j);
                }
            }
        }

        list.addAll(caseUsers);

        bindData(list);

    }

    private void bindData(ArrayList<User> collection) {

        if (CreateEventFragment.this.isDetached() || CreateEventFragment.this.isRemoving()
                || !CreateEventFragment.this.isVisible())
            return;


        if (collection.size() > 0) {
            rvSearchList.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
        } else {
            rvSearchList.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }


        rvSearchList.setAdapter(adapter);
        adapter.addAll(collection);


    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.showWhiteBackButton();
        if (!isEditEvent) {
            titleBar.setSubHeading(getResources().getString(R.string.create_event));
        } else {
            titleBar.setSubHeading(getDockActivity().getResources().getString(R.string.edit_event));
        }

    }


    @OnClick({R.id.ietDate, R.id.ietTime, R.id.imgSearch, R.id.btnCreateEvent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ietDate:
                initFromPickerValidated(ietDate);
                break;
            case R.id.ietTime:
                initTimePicker(ietTime);
                break;
            case R.id.imgSearch:
                break;
            case R.id.btnCreateEvent:
                createEvent();
                break;
        }
    }

    private void createEvent() {


        for (User item : list) {
            if (item.isAdded()) {
                members.add(item.getId().toString());
            }
        }

        membersIds = TextUtils.join(",", members);

        caseTitle = ietTitleAgenda.getText().toString();
        if (isValidate()) {
            if (!isEditEvent) {
                serviceHelper.enqueueCall(webService.createEvent(prefHelper.getUser().getToken(), caseId, caseTitle,
                        ietDate.getText().toString(), ietTime.getText().toString(),
                        membersIds, toggleReminder.isChecked() ? 1 : 0), CreateEvent);
            } else {
                serviceHelper.enqueueCall(webService.updateEvent(prefHelper.getUser().getToken(),
                        eventDetail.getId(), caseId, caseTitle, ietDate.getText().toString(), ietTime.getText().toString(),
                        membersIds, toggleReminder.isChecked() ? 1 : 0), CreateEvent);
            }
        }

    }

    private boolean isValidate() {

        if (caseId == null || (caseId.isEmpty())) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Please Select Case");
            return false;
        }
//        if (caseTitle == null || (caseTitle.isEmpty()) ) {
//            UIHelper.showShortToastInCenter(getDockActivity(), "No Case Found");
//            return false;
//        }
        else if (ietTitleAgenda.getText() == null || (ietTitleAgenda.getText().toString().isEmpty())) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Enter Title Agenda");
            return false;
        } else if (ietDate.getText().toString() == null || (ietDate.getText().toString().isEmpty()) || ietDate.getText().toString().equals("Date")) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Select Date");
            return false;
        } else if (ietTime.getText().toString() == null || (ietTime.getText().toString().isEmpty()) || ietTime.getText().toString().equals("Time")) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Select Time");
            return false;
        } else if (membersIds == null || (membersIds.equals(""))) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Select members");
            return false;
        } else {
            return true;
        }

    }


}
