package com.ingic.lmslawyer.ui.viewbinders.abstracts;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.Specialization_Experience_CategoryItem;
import com.ingic.lmslawyer.ui.views.AnyTextView;

public class NewsFeedFilterListItemBinder extends ViewBinder<Specialization_Experience_CategoryItem> {

    private Context context;

    public NewsFeedFilterListItemBinder(Context context) {
        super(R.layout.newsfeed_filter_item);

        this.context = context;
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new NewsFeedFilterListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(Specialization_Experience_CategoryItem entity, int position, int grpPosition, View view, Activity activity) {


        NewsFeedFilterListItemBinder.ViewHolder viewHolder = (NewsFeedFilterListItemBinder.ViewHolder) view.getTag();

        viewHolder.txtCategory.setText(entity.getTitle());

        if (entity.isChecked()) {
            viewHolder.icnCheck.setImageResource(R.drawable.circle1);
        } else {
            viewHolder.icnCheck.setImageResource(R.drawable.circle);
        }

    }

    public static class ViewHolder extends BaseViewHolder {

        private AnyTextView txtCategory;
        private ImageView icnCheck;


        public ViewHolder(View view) {

            txtCategory = (AnyTextView) view.findViewById(R.id.txtCategory);
            icnCheck = (ImageView) view.findViewById(R.id.icnCheck);


        }
    }
}