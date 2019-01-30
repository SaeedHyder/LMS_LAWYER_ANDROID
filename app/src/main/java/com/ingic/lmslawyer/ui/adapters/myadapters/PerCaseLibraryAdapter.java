package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.DockActivity;
import com.ingic.lmslawyer.entities.library_entities.Case;
import com.ingic.lmslawyer.fragments.Case.LibraryFragment;
import com.ingic.lmslawyer.helpers.ImageLoaderHelper;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.interfaces.OnViewHolderClick;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;


public class PerCaseLibraryAdapter extends RecyclerViewListAdapter<Case> {
    private Context context;

    public PerCaseLibraryAdapter(Context context,
                                 OnViewHolderClick listener) {
        super(context, listener);
        this.context = context;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_caselibrary, viewGroup, false);
        return view;
    }

    @Override
    protected void bindView(final Case item, final RecyclerviewViewHolder viewHolder) {
        if (item != null) {
            ImageView imgview = (ImageView) viewHolder.getView(R.id.imgview);
            TextView tvCaseName = (TextView) viewHolder.getView(R.id.tvCaseName);
            TextView tvPhotosNumbers = (TextView) viewHolder.getView(R.id.tvPhotosNumbers);
            TextView tvDocNumbers = (TextView) viewHolder.getView(R.id.tvDocNumbers);
            TextView tvVideoNumbers = (TextView) viewHolder.getView(R.id.tvVideoNumbers);

            String thumbnail = "";
            if (item.getPhotos() != null && item.getPhotos().size() > 0)
                thumbnail = item.getPhotos().get(0).getDocUrl();
            else if (item.getVideos() != null && item.getVideos().size() > 0)
                thumbnail = item.getVideos().get(0).getThumbUrl();
            else if (item.getFiles() != null && item.getFiles().size() > 0)
                thumbnail = item.getFiles().get(0).getThumbUrl();

            ImageLoaderHelper.loadImage(thumbnail, imgview);
            TextViewHelper.setText(tvCaseName, item.getSubject());
            TextViewHelper.setText(tvPhotosNumbers, String.valueOf(item.getPhotosCount()));
            TextViewHelper.setText(tvDocNumbers, String.valueOf(item.getFilesCount()));
            TextViewHelper.setText(tvVideoNumbers, String.valueOf(item.getVideosCount()));

            viewHolder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((DockActivity) context).replaceDockableFragment(LibraryFragment.newInstance(false, item, ""));
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
