package com.ingic.lmslawyer.sectionedRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ingic.lmslawyer.R;


public class ItemViewHolder extends RecyclerView.ViewHolder {
    TextView tvCaseName, tvLawyerName, tvCaseDetails, tvDate, tvTotalPayment, tvEvents, tvViewDetails;
    LinearLayout llCaseItem ;

    public ItemViewHolder(View itemView) {
        super(itemView);
        llCaseItem = (LinearLayout) itemView.findViewById(R.id.llCaseItem);
        tvCaseName = (TextView) itemView.findViewById(R.id.tvCaseName);
        tvLawyerName = (TextView) itemView.findViewById(R.id.tvLawyerName);
        tvCaseDetails = (TextView) itemView.findViewById(R.id.txtCaseDetails);
        tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        tvTotalPayment = (TextView) itemView.findViewById(R.id.tvTotalPayment);
        tvEvents = (TextView) itemView.findViewById(R.id.tvEvents);
        tvViewDetails = (TextView) itemView.findViewById(R.id.tvViewDetails);
    }
}