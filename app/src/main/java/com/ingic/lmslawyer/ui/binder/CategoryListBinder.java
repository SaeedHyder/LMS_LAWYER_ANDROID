package com.ingic.lmslawyer.ui.binder;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.DockActivity;
import com.ingic.lmslawyer.entities.newsfeed.AllCategoriesEnt;
import com.ingic.lmslawyer.helpers.BasePreferenceHelper;
import com.ingic.lmslawyer.interfaces.CaterogyClickListner;
import com.ingic.lmslawyer.ui.viewbinders.abstracts.CustomRecyclerViewBinder;
import com.ingic.lmslawyer.ui.views.AnyTextView;
import com.ingic.lmslawyer.ui.views.CustomRecyclerView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryListBinder extends CustomRecyclerViewBinder<AllCategoriesEnt> implements CaterogyClickListner {
    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    private CaterogyClickListner clickListner;
    private ArrayList<String> childCollection;


    public CategoryListBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, CaterogyClickListner clickListner) {
        super(R.layout.row_item_categories_list);
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
    public void bindView(AllCategoriesEnt entity, int position, Object viewHolder, Context context) {

        final ViewHolder holder = (ViewHolder) viewHolder;

        holder.heading.setText(entity.getTitle());

        if(entity.getSubCategories().size()>0) {
            holder.rvCategoryItems.setVisibility(View.VISIBLE);
            holder.rvCategoryItems.BindRecyclerView(new CategoryBinder(dockActivity, prefHelper, this), entity.getSubCategories(),
                    new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    , new DefaultItemAnimator());
        }else{
            holder.rvCategoryItems.setVisibility(View.GONE);
        }
    }


    @Override
    public void categoryClick(Object entity, int position) {
        clickListner.categoryClick(entity,position);
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.view)
        View view;
        @BindView(R.id.heading)
        AnyTextView heading;
        @BindView(R.id.rv_category_items)
        CustomRecyclerView rvCategoryItems;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
