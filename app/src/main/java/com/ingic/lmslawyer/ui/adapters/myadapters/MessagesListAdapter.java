package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.DockActivity;
import com.ingic.lmslawyer.fragments.messages.MessagesListFragment;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.interfaces.OnViewHolderClick;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;

import de.hdodenhof.circleimageview.CircleImageView;


public class MessagesListAdapter extends RecyclerViewListAdapter<MessagesListFragment.Messages> {
    private DockActivity context;
    private long lastClickTime;
//    OnSwipeDelete onSwipeDelete;

    public MessagesListAdapter(DockActivity context,
                               OnViewHolderClick listener/*, OnSwipeDelete onSwipeDelete*/) {
        super(context, listener);
        this.context = context;
//        this.onSwipeDelete = onSwipeDelete;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.item_messages_list, viewGroup, false);
    }

    @Override
    protected void bindView(final MessagesListFragment.Messages item, final RecyclerviewViewHolder viewHolder) {
        if (item != null) {
            /*Swipe delete */
           /* ImageView imgDelete = (ImageView) viewHolder.getView(R.id.imgDelete);
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSwipeDelete.deleteItem(item.getId(), viewHolder.getAdapterPosition());

                }
            });*/

            CircleImageView imgview = (CircleImageView) viewHolder.getView(R.id.imgview);

            TextView tvName = (TextView) viewHolder.getView(R.id.tvName);
            TextView tvMsgDesc = (TextView) viewHolder.getView(R.id.tvMsgDesc);
            TextView tvDateTime = (TextView) viewHolder.getView(R.id.tvDateTime);

            imgview.setImageResource(item.getImg());
            TextViewHelper.setText(tvName, item.getName());
            TextViewHelper.setText(tvMsgDesc, item.getMsgDesc());
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
