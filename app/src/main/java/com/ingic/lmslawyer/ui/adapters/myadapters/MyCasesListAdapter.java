package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.DockActivity;
import com.ingic.lmslawyer.entities.my_cases_entities.PendingOrActiveCaseDetails;
import com.ingic.lmslawyer.fragments.Case.MainMyCaseDetailsFragment;
import com.ingic.lmslawyer.fragments.Case.RecentCaseDetailsFragment;
import com.ingic.lmslawyer.helpers.DateHelper;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.interfaces.OnViewHolderClick;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;

import java.util.Date;


public class MyCasesListAdapter extends RecyclerViewListAdapter<PendingOrActiveCaseDetails> {
    private boolean isNewCase;
    private DockActivity context;
    private long lastClickTime;
//    OnSwipeDelete onSwipeDelete;

    public MyCasesListAdapter(DockActivity context,
                              OnViewHolderClick listener,/*, OnSwipeDelete onSwipeDelete*/boolean isNewCase) {
        super(context, listener);
        this.context = context;
        this.isNewCase = isNewCase;
//        this.onSwipeDelete = onSwipeDelete;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.item_case, viewGroup, false);
    }

    @Override
    protected void bindView(final PendingOrActiveCaseDetails item, final RecyclerviewViewHolder viewHolder) {
        if (item != null) {
            /*Swipe delete */
           /* ImageView imgDelete = (ImageView) viewHolder.getView(R.id.imgDelete);
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSwipeDelete.deleteItem(item.getId(), viewHolder.getAdapterPosition());

                }
            });*/


            TextView tvCaseName = (TextView) viewHolder.getView(R.id.tvCaseName);
            TextView tvLawyerName = (TextView) viewHolder.getView(R.id.tvLawyerName);
            TextView tvCaseDetails = (TextView) viewHolder.getView(R.id.txtCaseDetails);
            TextView tvDate = (TextView) viewHolder.getView(R.id.tvDate);
            TextView tvTotalPayment = (TextView) viewHolder.getView(R.id.tvTotalPayment);
            TextView tvEvents = (TextView) viewHolder.getView(R.id.tvEvents);
            TextView tvViewDetails = (TextView) viewHolder.getView(R.id.tvViewDetails);

            /*Set Data*/
            String lawyerTitle = "<b>User:</b>";
            TextViewHelper.setText(tvCaseName, item.getSubject().trim());
            TextViewHelper.setHtmlText(tvLawyerName, lawyerTitle + " " + item.getUser().getFullName().trim());
            TextViewHelper.setText(tvCaseDetails, item.getDetail().trim());
            Date mDate = DateHelper.stringToDate(item.getCreatedAt(), DateHelper.DATE_TIME_FORMAT);
            TextViewHelper.setText(tvDate, DateHelper.getFormattedTime(mDate)
                    + " | " + DateHelper.getFormattedDate(mDate, DateHelper.dateFormat));

            String paymentText = "0.0";
            if (item.getPayments() != null && item.getPayments().size() > 0) {
                paymentText = String.valueOf(item.getPayments().get(0).getCharges());
            }
            TextViewHelper.setText(tvTotalPayment, context.getString(R.string.total_payment) +
                    paymentText);

            /*========*/
            if (isNewCase) {
                tvEvents.setVisibility(View.VISIBLE);
                tvTotalPayment.setVisibility(View.INVISIBLE);
            } else {
                tvEvents.setVisibility(View.INVISIBLE);
                tvTotalPayment.setVisibility(View.VISIBLE);
            }
            tvViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isNewCase)
                        context.replaceDockableFragment(MainMyCaseDetailsFragment.newInstance(true,
                                false));
                    else {
                        RecentCaseDetailsFragment recentCaseDetailsFragment = RecentCaseDetailsFragment.newInstance(false);
                        recentCaseDetailsFragment.setActiveCaseDetail(item);
                        context.replaceDockableFragment(recentCaseDetailsFragment);
                    }
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
