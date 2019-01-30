package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.net.Uri;
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


public class MyCaseLibraryAdapter extends RecyclerViewListAdapter<SortLibrary> {
    private Context context;

    public MyCaseLibraryAdapter(Context context,
                                OnViewHolderClick listener) {
        super(context, listener);
        this.context = context;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
       /* if (viewType == 0)
            view = inflater.inflate(R.layout.item_mycaseslibrary_photos, viewGroup, false);
        else
            view = inflater.inflate(R.layout.item_mycaseslibrary_video_doc, viewGroup, false);*/

        view = inflater.inflate(R.layout.item_mycaseslibrary_video_doc, viewGroup, false);

        return view;
    }

    @Override
    protected void bindView(final SortLibrary item, final RecyclerviewViewHolder viewHolder) {
       /* if (item != null) {
            TextView tvDocType = (TextView) viewHolder.getView(R.id.tvDocType);
            TextView tvDocNumbers = (TextView) viewHolder.getView(R.id.tvDocNumbers);

            if (viewHolder.getItemViewType() == 0) {
                ImageView imgview1 = (ImageView) viewHolder.getView(R.id.imgview1);
                ImageView imgview2 = (ImageView) viewHolder.getView(R.id.imgview2);
                ImageView imgview3 = (ImageView) viewHolder.getView(R.id.imgview3);
                ImageView imgview4 = (ImageView) viewHolder.getView(R.id.imgview4);

            } else {
                ImageView imgview = (ImageView) viewHolder.getView(R.id.imgview);
                if (viewHolder.getAdapterPosition() == 1) {
                    imgview.setImageDrawable(context.getResources().getDrawable(R.drawable.p1));
                    TextViewHelper.setText(tvDocType, "Videos");
                    TextViewHelper.setText(tvDocNumbers, "50");
                } else if (viewHolder.getAdapterPosition() == 2) {
                    imgview.setImageDrawable(context.getResources().getDrawable(R.drawable.p6));
                    TextViewHelper.setText(tvDocType, "Documents");
                    TextViewHelper.setText(tvDocNumbers, "15");
                } else if (viewHolder.getAdapterPosition() == 0) {
                    TextViewHelper.setText(tvDocType, "Photos");
                    TextViewHelper.setText(tvDocNumbers, "115");
                }
            }
        }*/
        if (item != null) {
            String videoUrl = "https://www.youtube.com/watch?v=9xwazD5SyVg";
            final Uri uri = Uri.parse(videoUrl);


            final int pos = viewHolder.getAdapterPosition();
            TextView tvDocType = (TextView) viewHolder.getView(R.id.tvDocType);
            TextView tvQuantity = (TextView) viewHolder.getView(R.id.tvDocNumbers);

            ImageView imgview = (ImageView) viewHolder.getView(R.id.imgview);

            if (pos == 0) {
                ImageLoaderHelper.loadImage(item.getPlaceHolder(), imgview);
            } else if (pos == 1) {
                imgview.setImageDrawable(context.getResources().getDrawable(R.drawable.play_video_placeholder));
            } else if (pos == 2) {
                imgview.setImageDrawable(context.getResources().getDrawable(R.drawable.document_sample));
            }
            TextViewHelper.setText(tvDocType, item.getTitle());
            TextViewHelper.setText(tvQuantity, item.getCount() + "");

            viewHolder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.getDocList() != null && item.getDocList().size() > 0)
                        ((DockActivity) context).addDockableFragment(LibraryDetailFragment.newInstance(item.getDocList(), pos)
                                , LibraryDetailFragment.class.getSimpleName());
//                    GiraffePlayer.play(context, new VideoInfo(uri));

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
