package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.event_entities.EventMember;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;


public class AddMoreMembersAdapter extends RecyclerViewListAdapter<EventMember> {
    private Context context;

    public AddMoreMembersAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.item_case_member_add_more, viewGroup, false);
    }

    @Override
    protected void bindView(final EventMember item, final RecyclerviewViewHolder viewHolder) {
        TextView tvName = (TextView) viewHolder.getView(R.id.tvName);
        if (item != null && item.getMemberDetail() != null) {
            TextViewHelper.setText(tvName, item.getMemberDetail().getFullName());
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
