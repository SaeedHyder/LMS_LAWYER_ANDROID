package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;


public class RecentCasesPaymentAdapter extends RecyclerViewListAdapter<String> {
    private Context context;

    public RecentCasesPaymentAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.item_recent_detail_payment, viewGroup, false);
    }

    @Override
    protected void bindView(final String item, final RecyclerviewViewHolder viewHolder) {
        if (item != null) {
            TextView tvCasePaymentList = (TextView) viewHolder.getView(R.id.tvCasePaymentList);
            TextView tvDate = (TextView) viewHolder.getView(R.id.tvDate);
            TextView tvPayment = (TextView) viewHolder.getView(R.id.tvPayment);
            TextViewHelper.setText(tvCasePaymentList, item);
            TextViewHelper.setText(tvDate, item);
            TextViewHelper.setText(tvPayment, item);
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
