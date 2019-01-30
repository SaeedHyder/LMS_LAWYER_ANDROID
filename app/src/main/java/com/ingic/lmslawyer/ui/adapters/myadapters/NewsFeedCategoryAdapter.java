package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.newsfeed.Categories;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;


public class NewsFeedCategoryAdapter extends RecyclerViewListAdapter<Categories> {
    private Context context;
    private Double categoryID = 0.0;
    private RadioButton lastCheckedRadioButton;

    public NewsFeedCategoryAdapter(Context context) {
        super(context);
        this.context = context;

    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.row_item_categories_feedback, viewGroup, false);
    }

    @Override
    protected void bindView(final Categories item, final RecyclerviewViewHolder viewHolder) {
        final int position = viewHolder.getAdapterPosition();
        int id = (position + 1) * 100;
        if (item != null) {
            final RadioButton radioButton = (RadioButton) viewHolder.getView(R.id.rbRadioButton);
            radioButton.setId(id);
            radioButton.setText(item.getTitle());
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if (lastCheckedRadioButton != null) {
                        lastCheckedRadioButton.setChecked(false);
                    }
                    if (b) {
                        lastCheckedRadioButton = radioButton;
                        categoryID = item.getId();
                    }

                }
            });
        }
    }

    public Double getCategoryID() {
        return categoryID;
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
