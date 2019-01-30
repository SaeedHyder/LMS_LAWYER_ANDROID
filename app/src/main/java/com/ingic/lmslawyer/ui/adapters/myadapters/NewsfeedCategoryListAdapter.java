package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.newsfeed.Categories;
import com.ingic.lmslawyer.interfaces.OnCategoryClick;
import com.ingic.lmslawyer.interfaces.OnViewHolderClick;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;


public class NewsfeedCategoryListAdapter extends RecyclerViewListAdapter<Categories> {
    double selectedCategoryID = 0.0;
    OnCategoryClick onCategoryClick;
    private Context context;
    private int selectedItemPosition = 0;

    public NewsfeedCategoryListAdapter(Context context, OnViewHolderClick listener, OnCategoryClick onCategoryClick) {
        super(context, listener);
        this.context = context;
        this.onCategoryClick = onCategoryClick;

    }

    public void setSelectedCategoryID(double selectedCategoryID) {
        this.selectedCategoryID = selectedCategoryID;
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.item_category, viewGroup, false);
    }

    @Override
    protected void bindView(final Categories item, final RecyclerviewViewHolder viewHolder) {
        if (item != null) {
            final int position = viewHolder.getAdapterPosition();
            RadioButton rbCategory = (RadioButton) viewHolder.getView(R.id.rbCategory);

            rbCategory.setText(item.getTitle());
            rbCategory.setOnCheckedChangeListener(null);
            rbCategory.setChecked(item.getId() == selectedCategoryID);
            if (item.getId() == selectedCategoryID) {
                selectedItemPosition = position;
            }
            rbCategory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    selectedCategoryID = item.getId();
                    notifyDataSetChanged();
                    onCategoryClick.onCategoryClick(item.getId());
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
