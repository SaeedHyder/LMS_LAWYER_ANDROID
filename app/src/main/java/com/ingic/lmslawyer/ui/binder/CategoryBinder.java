package com.ingic.lmslawyer.ui.binder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.DockActivity;
import com.ingic.lmslawyer.entities.newsfeed.SubCategory;
import com.ingic.lmslawyer.helpers.BasePreferenceHelper;
import com.ingic.lmslawyer.interfaces.CaterogyClickListner;
import com.ingic.lmslawyer.ui.viewbinders.abstracts.CustomRecyclerViewBinder;
import com.ingic.lmslawyer.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryBinder extends CustomRecyclerViewBinder<SubCategory> {
    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    private CaterogyClickListner clickListner;

    public CategoryBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, CaterogyClickListner clickListner) {
        super(R.layout.row_item_category);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.imageLoader = ImageLoader.getInstance();
        this.clickListner = clickListner;

    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(SubCategory entity, int position, Object viewHolder, Context context) {

        final ViewHolder holder = (ViewHolder) viewHolder;

        if (entity.getIsSelected() == 1) {
            holder.llTag.setBackground(dockActivity.getResources().getDrawable(R.drawable.yellow_rounded_rectangle));
        } else {
            holder.llTag.setBackground(dockActivity.getResources().getDrawable(R.drawable.gray_rounded_tag));
        }

        holder.tag.setText(entity.getTitle());



        holder.tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entity.getIsSelected() == 0) {
                    clickListner.categoryClick(entity, position);
                }
            }
        });

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tag)
        AnyTextView tag;
        @BindView(R.id.ll_tag)
        LinearLayout llTag;



        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
