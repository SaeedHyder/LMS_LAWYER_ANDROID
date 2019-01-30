package com.ingic.lmslawyer.sectionedRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.DockActivity;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.entities.my_cases_entities.PendingOrActiveCaseDetails;
import com.ingic.lmslawyer.fragments.Case.MainMyCaseDetailsFragment;
import com.ingic.lmslawyer.fragments.Case.RecentCaseDetailsFragment;
import com.ingic.lmslawyer.helpers.DateHelper;
import com.ingic.lmslawyer.helpers.TextViewHelper;

import java.util.ArrayList;
import java.util.Date;

import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by syedatafseer on 6/11/2018.
 */

public class HeaderRecyclerViewSection extends StatelessSection {
    private static final String TAG = HeaderRecyclerViewSection.class.getSimpleName();
    DockActivity context;
    private String title;
    private ArrayList<PendingOrActiveCaseDetails> list;


    public HeaderRecyclerViewSection(DockActivity context, String title, ArrayList<PendingOrActiveCaseDetails> list) {
        super(R.layout.header_layout, R.layout.item_case);
        this.context = context;
        this.title = title;
        this.list = list;
    }


    @Override
    public int getContentItemsTotal() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new com.ingic.lmslawyer.sectionedRecyclerView.ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindItemViewHolder: Title" + getTitle());
        final int pos = position;
        String strEvent = "";


        com.ingic.lmslawyer.sectionedRecyclerView.ItemViewHolder viewHolder = (com.ingic.lmslawyer.sectionedRecyclerView.ItemViewHolder) holder;

        if (getTitle().equals(AppConstant.PENDING_FOR_APPROVAL)) {
            viewHolder.llCaseItem.setBackgroundColor(context.getResources().getColor(R.color.grey_transparent));
            viewHolder.tvEvents.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.tvEvents.setVisibility(View.VISIBLE);
        }
        if (list.get(position).getEventsCount() != null && (list.get(position).getEventsCount() == 1 || list.get(position).getEventsCount() == 0)) {
            strEvent = " Event";
        } else
            strEvent = " Events";


        String lawyerTitle = "<b>User:</b>";
        TextViewHelper.setText(viewHolder.tvCaseName, list.get(position).getSubject().trim());
        TextViewHelper.setHtmlText(viewHolder.tvLawyerName, lawyerTitle + " " + list.get(position).getUser().getFullName().trim());
        TextViewHelper.setText(viewHolder.tvCaseDetails, list.get(position).getDetail().trim());
        Date mDate = DateHelper.stringToDate(list.get(position).getCreatedAt(), DateHelper.DATE_TIME_FORMAT);
        TextViewHelper.setText(viewHolder.tvDate, DateHelper.getFormattedTime(mDate)
                + " | " + DateHelper.getFormattedDate(mDate, DateHelper.dateFormat));
        TextViewHelper.setText(viewHolder.tvEvents, String.valueOf(list.get(position).getEventsCount()).trim() + strEvent);

        viewHolder.tvViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: position = " + pos);
                if (getTitle().equals(AppConstant.PENDING_FOR_APPROVAL)) {

                    RecentCaseDetailsFragment recentCaseDetailsFragment = RecentCaseDetailsFragment.newInstance(true, null);
                    recentCaseDetailsFragment.setActiveCaseDetail(list.get(pos));

                    context.replaceDockableFragment(recentCaseDetailsFragment);
                } else {
                    MainMyCaseDetailsFragment myCaseDetailsFragment ;

                    if (getTitle().equals(AppConstant.WAITING_CASES)){
                        myCaseDetailsFragment = MainMyCaseDetailsFragment.newInstance(true ,
                                true);
                    }
                    else
                        myCaseDetailsFragment = MainMyCaseDetailsFragment.newInstance(true ,
                                false);

//                    myCaseDetailsFragment = MainMyCaseDetailsFragment.newInstance(true, false);
                    myCaseDetailsFragment.setActiveCaseDetail(list.get(pos));
                    context.replaceDockableFragment(myCaseDetailsFragment);
                }
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new com.ingic.lmslawyer.sectionedRecyclerView.HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        com.ingic.lmslawyer.sectionedRecyclerView.HeaderViewHolder hHolder = (com.ingic.lmslawyer.sectionedRecyclerView.HeaderViewHolder) holder;
        hHolder.headerTitle.setText(title);
    }

    public String getTitle() {
        return title;
    }
}
