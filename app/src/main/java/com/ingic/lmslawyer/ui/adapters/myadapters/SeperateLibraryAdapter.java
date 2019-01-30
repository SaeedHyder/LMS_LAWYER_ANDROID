package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.DockActivity;
import com.ingic.lmslawyer.entities.library_entities.SortLibrary;
import com.ingic.lmslawyer.fragments.Case.LibraryDetailFragment;
import com.ingic.lmslawyer.helpers.ImageLoaderHelper;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.interfaces.OnViewHolderClick;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;


public class SeperateLibraryAdapter extends RecyclerViewListAdapter<SortLibrary> {
    private Context context;

    public SeperateLibraryAdapter(Context context,
                                  OnViewHolderClick listener) {
        super(context, listener);
        this.context = context;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_seperatelibrary, viewGroup, false);
        return view;
    }


    @Override
    protected void bindView(final SortLibrary item, final RecyclerviewViewHolder viewHolder) {
        if (item != null) {
            final int pos = viewHolder.getAdapterPosition();

            TextView tvDocType = (TextView) viewHolder.getView(R.id.tvDocType);
            TextView tvQuantity = (TextView) viewHolder.getView(R.id.tvQuantity);
            ImageView imgview = (ImageView) viewHolder.getView(R.id.imgview);

            if (viewHolder.getAdapterPosition() == 0) {
                ImageLoaderHelper.loadImage( item.getPlaceHolder(), imgview);
            } else if (viewHolder.getAdapterPosition() == 1) {
                imgview.setImageDrawable(context.getResources().getDrawable(R.drawable.play_video_placeholder));
            } else if (viewHolder.getAdapterPosition() == 2) {
                imgview.setImageDrawable(context.getResources().getDrawable(R.drawable.document_sample));
            }
            TextViewHelper.setText(tvDocType, item.getTitle());
            TextViewHelper.setText(tvQuantity, item.getCount() + "");
          /*  if (viewHolder.getAdapterPosition() == 0) {
            } else if (viewHolder.getAdapterPosition() == 1) {
                imgview.setImageDrawable(context.getResources().getDrawable(R.drawable.p6));
                TextViewHelper.setText(tvDocType, "All Videos");
                TextViewHelper.setText(tvQuantity, "15");
            } else if (viewHolder.getAdapterPosition() == 2) {
                imgview.setImageDrawable(context.getResources().getDrawable(R.drawable.p6));
                TextViewHelper.setText(tvDocType, "All Documents");
                TextViewHelper.setText(tvQuantity, "115");

            }*/
            viewHolder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((DockActivity) context).replaceDockableFragment(LibraryDetailFragment.newInstance(item.getDocList(), pos));
                }
            });
        }
    }

    @Override
    protected int bindItemViewType(int position) {
        return position;
    }

    @Override
    protected int bindItemId(int position) {
        return 0;
    }

}
