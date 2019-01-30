package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.fragments.myprofile.MyProfileUploadDocFragment;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.interfaces.OnItemCancelClick;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;


public class AttachedDocsAdapter extends RecyclerViewListAdapter<MyProfileUploadDocFragment.FileType> {
    private Context context;
    OnItemCancelClick onItemCancelClick;

    public AttachedDocsAdapter(Context context, OnItemCancelClick onItemCancelClick) {
        super(context);
        this.context = context;
        this.onItemCancelClick = onItemCancelClick;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.item_upload_files, viewGroup, false);
    }


    @Override
    protected void bindView(final MyProfileUploadDocFragment.FileType item, final RecyclerviewViewHolder viewHolder) {
        if (item != null) {
           /* ImageView imageView = (ImageView) viewHolder.getView(R.id.imgview);
            ImageView imgPlaceHolder = (ImageView) viewHolder.getView(R.id.imgPlaceHolder);
            ImageView imgCross = (ImageView) viewHolder.getView(R.id.imgCross);

            if (item.getFileThumbnail() != null && !item.getFileThumbnail().isEmpty())
                ImageLoader.getInstance().displayImage("file:///" + item.getFileThumbnail(), imageView);

            if (item.getExtension().equals("mp4")) {
                imgPlaceHolder.setVisibility(View.VISIBLE);
            } else
                imgPlaceHolder.setVisibility(View.INVISIBLE);

*/
           /* imgCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemCancelClick.itemCrossClick(item);
                }
            });*/

            if (item != null) {
                TextView tvName = (TextView) viewHolder.getView(R.id.tvText);
                ImageView imgDelete = (ImageView) viewHolder.getView(R.id.imgDelete);
                TextViewHelper.setText(tvName, item.getFile().getName());

                imgDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemCancelClick.itemCrossClick(item);
                    }
                });
            }

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
