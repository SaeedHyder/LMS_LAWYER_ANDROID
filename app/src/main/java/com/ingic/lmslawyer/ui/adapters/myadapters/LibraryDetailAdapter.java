package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dmallcott.dismissibleimageview.DismissibleImageView;
import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.library_entities.Photo;
import com.ingic.lmslawyer.fragments.Case.LibraryDetailFragment;
import com.ingic.lmslawyer.helpers.ImageLoaderHelper;
import com.ingic.lmslawyer.helpers.PostVideoBitmapWorkerTask;
import com.ingic.lmslawyer.interfaces.OnViewHolderClick;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;

import java.util.List;


public class LibraryDetailAdapter extends RecyclerViewListAdapter<Photo> {
    private final String TAG = LibraryDetailFragment.class.getSimpleName();
    private Context context;
    List<Photo> docList;
    int docType;
    String docUrl = "";
    private Bitmap bitmap;

    public LibraryDetailAdapter(Context context,
                                OnViewHolderClick listener, List<Photo> docList, int docType) {
        super(context, listener);
        this.context = context;
        this.docList = docList;
        this.docType = docType;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if (docType == 0)
            view = inflater.inflate(R.layout.item_library_photos_details, viewGroup, false);
        else
            view = inflater.inflate(R.layout.item_library_details, viewGroup, false);

        return view;
    }


    @Override
    protected void bindView(final Photo item, final RecyclerviewViewHolder viewHolder) {
        if (item != null) {
            final int pos = viewHolder.getAdapterPosition();
            final DismissibleImageView simpDismissibleImageView = (DismissibleImageView) viewHolder.getView(R.id.dissImgview);

            ImageView imgview = (ImageView) viewHolder.getView(R.id.imgview);
            ImageView imgPlaceHolder = (ImageView) viewHolder.getView(R.id.imgPlaceHolder);

            if (docType == 1) {
                imgPlaceHolder.setVisibility(View.VISIBLE);
                if (item.getThumbUrl() == null || item.getThumbUrl().isEmpty()) {
                    PostVideoBitmapWorkerTask task = new PostVideoBitmapWorkerTask(imgview);
                    task.execute(item.getDocUrl());

                } else
                    ImageLoaderHelper.loadImage(item.getThumbUrl(), imgview);

            } else {
                if (docType == 2) {
                    imgPlaceHolder.setVisibility(View.GONE);
//                    if (item.getThumbUrl() != null && !item.getThumbUrl().isEmpty())
//                        ImageLoaderHelper.loadImage(item.getThumbUrl(), imgview);
//                    else
                        imgview.setImageDrawable(context.getResources().getDrawable(R.drawable.document_sample));


                } else if (item.getDocUrl() != null && !item.getDocUrl().isEmpty()) {
                    ImageLoaderHelper.loadImage(item.getDocUrl(), simpDismissibleImageView);
                }
            }

            if (imgview != null) {
                imgview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (docType != 0) {
                            docUrl = item.getDocUrl();
                            if (!item.getDocUrl().startsWith("http://") && !item.getDocUrl().startsWith("https://")) {
                                docUrl = "http://" + item.getDocUrl();
                            }
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(docUrl));
                            context.startActivity(browserIntent);
                        }
                    }
                });
            }
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
