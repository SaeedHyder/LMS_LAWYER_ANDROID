package com.ingic.lmslawyer.fragments.Case;


import android.os.Bundle;
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
import com.ingic.lmslawyer.entities.my_cases_entities.MyCases;
import com.ingic.lmslawyer.entities.my_cases_entities.PendingOrActiveCaseDetails;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.SimpleDividerItemDecoration;
import com.ingic.lmslawyer.interfaces.OnViewHolderClick;
import com.ingic.lmslawyer.sectionedRecyclerView.HeaderRecyclerViewSection;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.MyCasesListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCasesListFragment extends BaseFragment implements OnViewHolderClick {


    @BindView(R.id.rvMyCaseList)
    RecyclerView rvMyCaseList;
    Unbinder unbinder;
    RecyclerViewListAdapter adapter;
    ArrayList list;
    private MyCases myCases;
    private ArrayList<PendingOrActiveCaseDetails> recentCases;
    @BindView(R.id.alternateText)
    TextView alternateText;

    private boolean mIsNewCase;

    private SectionedRecyclerViewAdapter sectionAdapter;

    public MyCasesListFragment() {
        // Required empty public constructor
    }

    public static MyCasesListFragment newInstance(boolean isNewCases) {
        try {
            Bundle args = new Bundle();
            args.putBoolean(AppConstant.IS_NEW_CASE, isNewCases);
            MyCasesListFragment fragment = new MyCasesListFragment();
            fragment.setArguments(args);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
            return new MyCasesListFragment();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cases_list, container, false);
        if (getArguments() != null)
            mIsNewCase = getArguments().getBoolean(AppConstant.IS_NEW_CASE);

        unbinder = ButterKnife.bind(this, view);

        callService();


        // initAdapter();
        return view;
    }


    private void callService() {
        if (mIsNewCase)
            serviceHelper.enqueueCall(webService.getLawyerNewCase(prefHelper.getUser().getToken()),
                    WebServiceConstant.getLawyerNewCase);
        else
            serviceHelper.enqueueCall(webService.getLawyerRecentCase(prefHelper.getUser().getToken()),
                    WebServiceConstant.getLawyerRecentCase);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        if (MyCasesListFragment.this.isDetached()
                || MyCasesListFragment.this.isRemoving() || !MyCasesListFragment.this.isVisible())
            return;
        switch (Tag) {
            case WebServiceConstant.getLawyerNewCase: //For new cases
                if (result != null) {
                    myCases = (MyCases) result;
                    if (myCases != null && !(myCases.getPending() != null && myCases.getPending().size() == 0
                            && myCases.getActive() != null && myCases.getActive().size() == 0
                            && myCases.getWaiting() != null && myCases.getWaiting().size() == 0
                    )) {
                        if (alternateText != null)
                            alternateText.setVisibility(View.GONE);
                        initNewCasesAdapter();
                    } else if (alternateText != null)
                        alternateText.setVisibility(View.VISIBLE);
                }
                break;

            case WebServiceConstant.getLawyerRecentCase: //for recent Cases
                if (result != null) {
                    recentCases = (ArrayList<PendingOrActiveCaseDetails>) result;
                    if (recentCases.size() > 0) {
                        alternateText.setVisibility(View.GONE);
                        initRecentCasesAdapter();
                    } else
                        alternateText.setVisibility(View.VISIBLE);
                }
                break;

        }


    }


    private void initNewCasesAdapter() {
//        adapter = new MyCasesListAdapter(getDockActivity(), this, mIsNewCase);

        if (MyCasesListFragment.this.isDetached() ||
                MyCasesListFragment.this.isRemoving() ||
                !MyCasesListFragment.this.isVisible())
            return;

        rvMyCaseList.setLayoutManager(new LinearLayoutManager(getDockActivity(),
                LinearLayoutManager.VERTICAL, false));
        rvMyCaseList.addItemDecoration(new SimpleDividerItemDecoration(getDockActivity()));

        HeaderRecyclerViewSection firstSection = new HeaderRecyclerViewSection(getDockActivity(),
                AppConstant.PENDING_FOR_APPROVAL, getPendingCases());
        HeaderRecyclerViewSection secondSection = new HeaderRecyclerViewSection(getDockActivity(),
                AppConstant.ACTIVE_CASES, getRecentCases());
        HeaderRecyclerViewSection thirdSection = new HeaderRecyclerViewSection(getDockActivity(),
                AppConstant.WAITING_CASES, getWaitingCases());

        sectionAdapter = new SectionedRecyclerViewAdapter();

        if (getPendingCases().size() > 0)
            sectionAdapter.addSection(firstSection);
        if (getRecentCases().size() > 0)
            sectionAdapter.addSection(secondSection);
        if (getWaitingCases().size() > 0)
            sectionAdapter.addSection(thirdSection);

        rvMyCaseList.setAdapter(sectionAdapter);
    }

    private ArrayList<PendingOrActiveCaseDetails> getPendingCases() {
        if (myCases != null)
            return myCases.getPending();
        return new ArrayList<>();
    }

    private ArrayList<PendingOrActiveCaseDetails> getRecentCases() {
        if (myCases != null)
            return myCases.getActive();
        return new ArrayList<>();
    }

    private ArrayList<PendingOrActiveCaseDetails> getWaitingCases() {
        if (myCases != null)
            return myCases.getWaiting();
        return new ArrayList<>();
    }


    private void initRecentCasesAdapter() {
        adapter = new MyCasesListAdapter(getDockActivity(), this, mIsNewCase);
        rvMyCaseList.setLayoutManager(new LinearLayoutManager(getDockActivity(),
                LinearLayoutManager.VERTICAL, false));
        rvMyCaseList.addItemDecoration(new SimpleDividerItemDecoration(getDockActivity()));
        rvMyCaseList.setAdapter(adapter);
        adapter.addAll(recentCases);
    }


    @Override
    public void onItemClick(View view, int position) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
