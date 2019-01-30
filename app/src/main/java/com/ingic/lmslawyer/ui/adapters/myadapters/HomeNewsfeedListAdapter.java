package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.DockActivity;
import com.ingic.lmslawyer.entities.newsfeed.NewsfeedEnt;
import com.ingic.lmslawyer.fragments.NewsFeed.NewsfeedDetailsFragment;
import com.ingic.lmslawyer.helpers.DateHelper;
import com.ingic.lmslawyer.helpers.ImageLoaderHelper;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.interfaces.OnViewHolderClick;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;


public class HomeNewsfeedListAdapter extends RecyclerViewListAdapter<NewsfeedEnt> {
    private Context context;

    public HomeNewsfeedListAdapter(Context context,
                                   OnViewHolderClick listener) {
        super(context, listener);
        this.context = context;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.item_home_newsfeed, viewGroup, false);
    }

    @Override
    protected void bindView(final NewsfeedEnt item, final RecyclerviewViewHolder viewHolder) {
        if (item != null) {

            ImageView imgview = (ImageView) viewHolder.getView(R.id.imgview);
            TextView tvName = (TextView) viewHolder.getView(R.id.tvName);
            TextView tvTime = (TextView) viewHolder.getView(R.id.tvTime);
            TextView tvDesc = (TextView) viewHolder.getView(R.id.tvDesc);

            TextViewHelper.setText(tvName, item.getTitle());
            TextViewHelper.setText(tvDesc, item.getDescription());


            TextViewHelper.setText(tvTime, DateHelper.getTime(item.getCreatedAt())
                    + " | " + DateHelper.getLocalDateTime2(item.getCreatedAt()));

            ImageLoaderHelper.loadImageWithPicasso(context, item.getImageUrl(), imgview);

            viewHolder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((DockActivity) context).addDockableFragment(NewsfeedDetailsFragment.newInstance(item.getLink())
                            , NewsfeedDetailsFragment.class.getSimpleName());
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

    @Override
    public int getItemCount() {
        return 5;
    }
}
