package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.User;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;


public class SearchMembersAdapter extends RecyclerViewListAdapter<User> {
    private Context context;
    private ImageLoader imageLoader;

    public SearchMembersAdapter(Context context) {
        super(context);
        this.context = context;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.item_search, viewGroup, false);
    }

    @Override
    protected void bindView(final User item, final RecyclerviewViewHolder viewHolder) {
        if (item != null) {
            ImageView imgPerson = (ImageView) viewHolder.getView(R.id.imgPerson);
            TextView tvName = (TextView) viewHolder.getView(R.id.tvName);
            final ImageView imgDltAdd = (ImageView) viewHolder.getView(R.id.imgDltAdd);

            TextViewHelper.setText(tvName, item.getFullName());

            if (item.getImageUrl() != null && !item.getImageUrl().equals("")) {
                imageLoader.displayImage(item.getImageUrl(), imgPerson);
            }

            if (item.getIsSelected() == 1) {
                item.setAdded(true);
                item.setIsSelected(1);
            } else {
                item.setAdded(false);
                item.setIsSelected(0);
            }

            if (item.isAdded()) {
                imgDltAdd.setBackgroundResource(R.drawable.ic_delete);

            } else {
                imgDltAdd.setBackgroundResource(R.drawable.addmembers);
            }


            imgDltAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.isAdded()) {
                        item.setAdded(false);
                        item.setIsSelected(0);
                        imgDltAdd.setBackgroundResource(R.drawable.addmembers);
                    } else {
                        item.setAdded(true);
                        item.setIsSelected(1);
                        imgDltAdd.setBackgroundResource(R.drawable.ic_delete);
                    }

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
