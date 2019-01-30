package com.ingic.lmslawyer.fragments.Case;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.event_entities.Event;
import com.ingic.lmslawyer.entities.event_entities.EventEnt;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.fragments.calender.CreateEventFragment;
import com.ingic.lmslawyer.helpers.DateHelper;
import com.ingic.lmslawyer.helpers.InternetHelper;
import com.ingic.lmslawyer.helpers.SimpleDividerItemDecoration;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.interfaces.OnViewDetailsClick;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.PerDayCasesAdapter;
import com.ingic.lmslawyer.ui.views.TitleBar;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalenderFragment extends BaseFragment implements OnViewDetailsClick {


    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;

    @BindView(R.id.rvPerDayAppointments)
    RecyclerView rvPerDayAppointments;
    Unbinder unbinder;
    @BindView(R.id.tvTotalCalenderEvent)
    TextView tvTotalCalenderEvent;
    @BindView(R.id.btnCreateNewEvent)
    Button btnCreateNewEvent;
    @BindView(R.id.parent)
    ScrollView parent;
    MainMyCaseDetailsFragment mainMyCaseDetailsFragment;
    private RecyclerViewListAdapter adapter;
    private ArrayList list;
    private boolean isCaseDesc;
    private String caseId = "";
    Date mSelectedDate;

    Calendar calendar = Calendar.getInstance();
    private ArrayList<Event> eventsList;
    Collection<CalendarDay> dates;

    public CalenderFragment() {
        // Required empty public constructor
    }

    public static CalenderFragment newInstance() {

        return new CalenderFragment();
    }

    public static CalenderFragment newInstance(boolean isCaseDesc, String case_id) {
        Bundle args = new Bundle();
        args.putBoolean(AppConstant.IS_CASE_DESC, isCaseDesc);
        args.putString(AppConstant.CASE_ID, case_id);
        CalenderFragment fragment = new CalenderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calender, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            isCaseDesc = getArguments().getBoolean(AppConstant.IS_CASE_DESC);
            caseId = getArguments().getString(AppConstant.CASE_ID);
        }
        if (!isCaseDesc) {
            parent.setBackground(getResources().getDrawable(R.drawable.bg));
            tvTotalCalenderEvent.setVisibility(View.VISIBLE);
            btnCreateNewEvent.setVisibility(View.VISIBLE);
        } else {
            tvTotalCalenderEvent.setVisibility(View.GONE);
            btnCreateNewEvent.setVisibility(View.GONE);
        }


        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (InternetHelper
                        .CheckInternetConectivityandShowToast(getDockActivity())) {
                    if (dates != null && dates.size() > 0) {
                        calendarView.setSelectionColor(Color.parseColor("#670708"));

                        mSelectedDate = date.getDate();
                        widget.getSelectedDate();

                        //Set the privious decorator
                        calendarView.addDecorators(new EventDecorator(dates, getResources().getColor(R.color.app_maroon)));

                        //then new current selector
                        Collection<CalendarDay> dayList = new ArrayList<>();
                        for (CalendarDay day : dates) {
                            if (DateHelper.isSameDay(mSelectedDate, day.getDate())) {
                                dayList.add(day);
                                calendarView.addDecorators(new EventDecorator(dayList, getResources().getColor(R.color.white)));
                            }
                        }
                        getSelectedDateList();
                    }

                }
            }
        });

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                calendarView.setSelectionColor(Color.parseColor("#670708"));
//                mSelectedDate = date.getDate();
                mSelectedDate = date.getDate();

                serviceHelper.enqueueCall(webService.getEvents(prefHelper.getUser().getToken(), date.getYear(),
                        date.getMonth() + 1, caseId),
                        WebServiceConstant.getEvents);
            }
        });
        initAdapter();

        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.calendar)); /*todo Add Search icon */
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void getInitialData() {
        if (adapter != null) {
            adapter.clearAll();
        }


        calendarView.clearSelection();

        serviceHelper.enqueueCall(webService.getEvents(prefHelper.getUser().getToken(), calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, caseId),
                WebServiceConstant.getEvents);

    }

    private void initAdapter() {
        adapter = new PerDayCasesAdapter(getDockActivity(), this);
        rvPerDayAppointments.setLayoutManager(new LinearLayoutManager(getDockActivity(),
                LinearLayoutManager.VERTICAL, false));
        rvPerDayAppointments.addItemDecoration(new SimpleDividerItemDecoration(getDockActivity()));

        rvPerDayAppointments.setAdapter(adapter);

    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        switch (Tag) {
            case WebServiceConstant.getEvents: //For new cases
                if (CalenderFragment.this.isDetached() || CalenderFragment.this.isRemoving() || !CalenderFragment.this.isVisible())
                    return;


                if (result != null) {
                    EventEnt eventResult = (EventEnt) result;
                    eventsList = eventResult.getEvents();
                    if (eventsList != null && eventsList != null && eventsList.size() > 0) {
                        calendarView.clearSelection();
                        highlightDates();
                        setTotalEvents();

                    } else {
                        adapter.clearAll();
                        tvTotalCalenderEvent.setText("You have no events");

                        Collection<CalendarDay> dayList = new ArrayList<>();
                        dayList.add(CalendarDay.from(mSelectedDate));
                        calendarView.addDecorators(new EventDecorator(dayList, getResources().getColor(R.color.black)));

                    }

                }
                break;
        }
    }

    private void setTotalEvents() {
        if (isCaseDesc) {
            tvTotalCalenderEvent.setVisibility(View.GONE);
        } else {
            tvTotalCalenderEvent.setVisibility(View.VISIBLE);
        }
        String eventText = "";
        if (eventsList != null && eventsList.size() > 0) {
            eventText = "You Have Total " + eventsList.size() + " Events";
        } else
            eventText = "You have no events";

        TextViewHelper.setText(tvTotalCalenderEvent, eventText);
    }

    private void highlightDates() {

        Collection<CalendarDay> dayList = new ArrayList<>();
        dayList.add(CalendarDay.from(mSelectedDate));
        calendarView.addDecorators(new EventDecorator(dayList, getResources()
                .getColor(R.color.black)));

        if (eventsList == null || eventsList.size() < 1) return;
        dates = new ArrayList<>();
        for (int i = 0; i < eventsList.size(); i++) {
            dates.add(CalendarDay.from(DateHelper.stringToDate(eventsList.get(i).getDate(), DateHelper.DATE_FORMAT)));
        }

        calendarView.addDecorators(new EventDecorator(dates, getResources()
                .getColor(R.color.app_maroon)));
    }

    private void getSelectedDateList() {
        if (eventsList != null && mSelectedDate != null) {
            ArrayList<Event> sortedList = new ArrayList<>();
            for (int i = 0; i < eventsList.size(); i++) {
                if (DateHelper.isSameDay(mSelectedDate, DateHelper.stringToDate(eventsList.get(i).getDate()
                        , DateHelper.DATE_FORMAT))) {
                    sortedList.add(eventsList.get(i));
                }
            }
            adapter.addAll(sortedList);
        }
    }


    @OnClick(R.id.btnCreateNewEvent)
    public void onViewClicked() {
        getDockActivity().replaceDockableFragment(new CreateEventFragment());
    }


    public class EventDecorator implements DayViewDecorator {


        int color;
        private final HashSet<CalendarDay> dates;
        ForegroundColorSpan maroonColor;
        final StyleSpan bold = new StyleSpan(Typeface.BOLD);

        public EventDecorator(Collection<CalendarDay> dates, int color) {
            this.dates = new HashSet<>(dates);
            this.color = color;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            maroonColor = new ForegroundColorSpan(color);
            view.addSpan(maroonColor);
            view.addSpan(bold);
        }
    }

    @Override
    public void viewDetails(Event eventItem) {
        getDockActivity().addDockableFragment(EventDetailsFragment.newInstance(eventItem),
                EventDetailsFragment.class.getSimpleName());
    }

    @Override
    public void fragmentResume() {
        super.fragmentResume();
        getInitialData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getInitialData();
    }


}


