package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.event_entities.Event;
import com.ingic.lmslawyer.helpers.DateHelper;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.interfaces.OnViewDetailsClick;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;

import java.util.Date;


public class PerDayCasesAdapter extends RecyclerViewListAdapter<Event> {
    private Context context;
    OnViewDetailsClick listener;
    String lawyerTitle = "<b>User:</b>";
    String caseTitle = "<b>Case:</b>";
    String eventTitle = "<b>Event:</b>";

    public PerDayCasesAdapter(Context context,
                              OnViewDetailsClick listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.item_perday_case_appointment, viewGroup, false);
    }

    @Override
    protected void bindView(final Event item, final RecyclerviewViewHolder viewHolder) {
        if (item != null) {
            Date meetingTime = DateHelper.stringToDate(item.getDate() + " " + item.getTime(), DateHelper.DATE_TIME_FORMAT);
            TextView txtCaseName = (TextView) viewHolder.getView(R.id.tvCaseName);
            TextView txtLawyerName = (TextView) viewHolder.getView(R.id.tvLawyerName);
            TextView txtCaseDetails = (TextView) viewHolder.getView(R.id.txtCaseDetails);
            TextView tvFromTime = (TextView) viewHolder.getView(R.id.tvFromTime);
            TextView tvToTime = (TextView) viewHolder.getView(R.id.tvToTime);
            TextView tvEvent = (TextView) viewHolder.getView(R.id.tvEvent);
            TextView tvViewDetails = (TextView) viewHolder.getView(R.id.tvViewDetails);

            if (item.getRegisterCase() != null) {
                TextViewHelper.setText(txtCaseName, item.getRegisterCase().getSubject());
                TextViewHelper.setHtmlText(txtLawyerName, lawyerTitle + " " + item.getRegisterCase().getUser().getFullName());
                TextViewHelper.setHtmlText(txtCaseDetails, caseTitle + " " + item.getRegisterCase().getDetail());
            }
            TextViewHelper.setHtmlText(tvEvent, eventTitle + " " + item.getTitle());
            TextViewHelper.setHtmlText(tvFromTime, DateHelper.getTime(item.getDate() + " " + item.getTime()));


            tvViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.viewDetails(item);
                }
            });
        }
    }

    @Override
    protected int bindItemViewType(int position) {
        return 0;
    }

    @Override
    protected int bindItemId(int position) {
        return 0;
    }
}
