package com.ingic.lmslawyer.ui.binder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.DockActivity;
import com.ingic.lmslawyer.helpers.BasePreferenceHelper;
import com.ingic.lmslawyer.interfaces.RecyclerClickListner;
import com.ingic.lmslawyer.ui.viewbinders.abstracts.CustomRecyclerViewBinder;
import com.ingic.lmslawyer.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SuggestionBinder extends CustomRecyclerViewBinder<String> {
    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    private RecyclerClickListner clickListner;



    public SuggestionBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, RecyclerClickListner clickListner) {
        super(R.layout.row_item_suggestion);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.imageLoader = ImageLoader.getInstance();
        this.clickListner = clickListner;
    }


    @Override
    public CustomRecyclerViewBinder.BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(String entity, int position, Object viewHolder, Context context) {

        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.tag.setText(entity);

        holder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.onClick(entity,position);
            }
        });

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tag)
        AnyTextView tag;
        @BindView(R.id.cross)
        ImageView cross;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
