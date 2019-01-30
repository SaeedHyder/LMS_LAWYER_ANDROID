package com.ingic.lmslawyer.fragments.Case;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.event_entities.Event;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.fragments.calender.CreateEventFragment;
import com.ingic.lmslawyer.helpers.DateHelper;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.AddMoreMembersAdapter;
import com.ingic.lmslawyer.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.lmslawyer.constants.WebServiceConstant.deleteEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsFragment extends BaseFragment {


    @BindView(R.id.tvLawyerName)
    TextView tvLawyerName;
    @BindView(R.id.tvDateTime)
    TextView tvDateTime;
    @BindView(R.id.tvSubject)
    TextView tvSubject;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.rvCaseMembers)
    RecyclerView rvCaseMembers;
    Unbinder unbinder;

    private RecyclerViewListAdapter adapter;
    private ArrayList list;
    Event eventDetail;

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    public static EventDetailsFragment newInstance(Event eventDetail) {
        try {
            Bundle args = new Bundle();
            args.putSerializable(AppConstant.EVENT_DETAIL, eventDetail);
            EventDetailsFragment fragment = new EventDetailsFragment();
            fragment.setArguments(args);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
            return new EventDetailsFragment();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            eventDetail = (Event) getArguments().getSerializable(AppConstant.EVENT_DETAIL);
//            setData();
        }
        initAdapter();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();
    }


    private void setData() {
        if (EventDetailsFragment.this.isDetached() || EventDetailsFragment.this.isRemoving()
                || !EventDetailsFragment.this.isVisible() || eventDetail == null)
            return;
//        Date meetingTime = DateHelper.stringToDate(eventDetail.getDate() + " " +
//                eventDetail.getTime(), DateHelper.DATE_TIME_FORMAT);

        if (eventDetail.getRegisterCase() != null)
            TextViewHelper.setHtmlText(tvLawyerName, eventDetail.getRegisterCase().getUser().getFullName());

        TextViewHelper.setHtmlText(tvSubject, eventDetail.getTitle());
//        TextViewHelper.setHtmlText(tvDate, DateHelper.getFormattedDate(meetingTime, DateHelper.dateFormat));
        TextViewHelper.setHtmlText(tvDate, DateHelper.getLocalDateTime2(eventDetail.getDate() + " " + eventDetail.getTime()));
    }


    private void initAdapter() {
        if (eventDetail == null) return;

        adapter = new AddMoreMembersAdapter(getDockActivity());
        rvCaseMembers.setLayoutManager(new LinearLayoutManager(getDockActivity(),
                LinearLayoutManager.VERTICAL, false));

        rvCaseMembers.setAdapter(adapter);
        if (eventDetail.getEventMember() != null && eventDetail.getEventMember().size() > 0)
            adapter.addAll(eventDetail.getEventMember());
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.event_details)); /*todo Add Search icon */
    }


    @OnClick({R.id.btnEditEvent, R.id.btnDeleteEvent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnEditEvent:
//                getDockActivity().popFragment();
//                getDockActivity().popBackStackTillEntry(1);
                getDockActivity().replaceDockableFragment(CreateEventFragment.newInstance(true, eventDetail));
                break;
            case R.id.btnDeleteEvent:
                serviceHelper.enqueueCall(webService.deleteEvent(prefHelper.getUser().getToken(), eventDetail.getId()), deleteEvent);
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        switch (Tag) {
            case WebServiceConstant.deleteEvent: //For new cases
                getDockActivity().popFragment();
//                getDockActivity().popFragment();
//                getDockActivity().popBackStackTillEntry(1);
//                getDockActivity().replaceDockableFragment(CalenderFragment.newInstance());
                UIHelper.showShortToastInCenter(getDockActivity(), message);

                break;
        }
    }
}
