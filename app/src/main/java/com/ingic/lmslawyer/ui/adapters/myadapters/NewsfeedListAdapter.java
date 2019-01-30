package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Function;
import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.DockActivity;
import com.ingic.lmslawyer.entities.newsfeed.NewsfeedEnt;
import com.ingic.lmslawyer.fragments.NewsFeed.NewsfeedDetailsFragment;
import com.ingic.lmslawyer.helpers.DateHelper;
import com.ingic.lmslawyer.helpers.ImageLoaderHelper;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.interfaces.CategoryFavShareListner;
import com.ingic.lmslawyer.interfaces.OnDataChange;
import com.ingic.lmslawyer.interfaces.OnViewHolderClick;
import com.ingic.lmslawyer.interfaces.RecyclerItemListener;
import com.ingic.lmslawyer.ui.adapters.abstracts.MatchableRVArrayAdapter;


public class NewsfeedListAdapter extends MatchableRVArrayAdapter<NewsfeedEnt>
        implements Filterable {
    private Context context;
    private CategoryFavShareListner itemListener;


    public NewsfeedListAdapter(Context context, OnViewHolderClick listener, Function<NewsfeedEnt, String> converter,
                               OnDataChange onDataChange, CategoryFavShareListner itemListener) {
        super(context, listener, converter, onDataChange);
        this.context = context;
        this.itemListener = itemListener;

    }


    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        return inflater.inflate(R.layout.item_newsfeed, viewGroup, false);
    }

    @Override
    protected void bindView(final NewsfeedEnt item, final RecyclerviewViewHolder viewHolder) {
        if (item != null) {

            ImageView imgview = (ImageView) viewHolder.getView(R.id.imgview);
            TextView tvTitle = (TextView) viewHolder.getView(R.id.tvTitle);
            TextView tvDateTime = (TextView) viewHolder.getView(R.id.tvDateTime);

            ImageView btnShare = (ImageView) viewHolder.getView(R.id.btnShare);
            ImageView btnFav = (ImageView) viewHolder.getView(R.id.btnFav);

            TextViewHelper.setText(tvTitle, item.getTitle());

            TextViewHelper.setText(tvDateTime, DateHelper.getTime(item.getCreatedAt())
                    + " | " + DateHelper.getLocalDateTime2(item.getCreatedAt()));

            ImageLoaderHelper.loadImageWithPicasso(context, item.getImageUrl(), imgview);
            viewHolder.getView().setOnClickListener(view -> {
                if (context == null) return;
                ((DockActivity) context).replaceDockableFragment(NewsfeedDetailsFragment.newInstance(item.getLink())
                        , NewsfeedDetailsFragment.class.getSimpleName());
            });
            btnFav.setImageResource(item.isFavorite() ? R.drawable.fav2 : R.drawable.fav);

            btnShare.setOnClickListener(view -> {
                if (itemListener != null)
                    itemListener.share(item, viewHolder.getAdapterPosition());
            });
            btnFav.setOnClickListener(view -> {
                if (itemListener != null) {
                    item.setFavorite(!item.isFavorite());
                    btnFav.setImageResource(item.isFavorite() ? R.drawable.fav2 : R.drawable.fav);
                    itemListener.favorite(item, viewHolder.getAdapterPosition());
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
