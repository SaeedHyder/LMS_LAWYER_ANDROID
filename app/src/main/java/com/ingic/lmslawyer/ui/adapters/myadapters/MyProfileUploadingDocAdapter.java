package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;


public class MyProfileUploadingDocAdapter extends RecyclerViewListAdapter<String> {
    private Context context;

    public MyProfileUploadingDocAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.item_upload_files, viewGroup, false);
    }

    @Override
    protected void bindView(final String item, final RecyclerviewViewHolder viewHolder) {
        if (item != null) {
            TextView tvName = (TextView) viewHolder.getView(R.id.tvText);
            ImageView imgDelete = (ImageView) viewHolder.getView(R.id.imgDelete);
            TextViewHelper.setText(tvName, item);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UIHelper.showLongToastInCenter(context, context.getResources().getString(R.string.will_be_beta));
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
