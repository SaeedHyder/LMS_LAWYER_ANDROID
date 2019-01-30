package com.ingic.lmslawyer.sectionedRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ingic.lmslawyer.R;


public class HeaderViewHolder extends RecyclerView.ViewHolder{
    public TextView headerTitle;
    public LinearLayout headerLayout;
    public HeaderViewHolder(View itemView) {
        super(itemView);
        headerTitle = (TextView)itemView.findViewById(R.id.header_id);
        headerLayout = (LinearLayout) itemView.findViewById(R.id.header_layout);
    }
}