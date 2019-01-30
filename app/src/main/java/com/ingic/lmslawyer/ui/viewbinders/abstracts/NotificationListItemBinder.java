package com.ingic.lmslawyer.ui.viewbinders.abstracts;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.NotificationEnt;
import com.ingic.lmslawyer.helpers.DateHelper;
import com.ingic.lmslawyer.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationListItemBinder extends ViewBinder<NotificationEnt> {

    private Context context;

    public NotificationListItemBinder(Context context) {
        super(R.layout.notofication_list_item);

        this.context = context;
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(NotificationEnt entity, int position, int grpPosition, View view, Activity activity) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.txtNotification.setText(entity.getMessage());

        viewHolder.txtDate.setText(DateHelper.getLocalDate(entity.getCreatedAt(),DateHelper.DATE_FORMAT2));



    }


    static class ViewHolder extends BaseViewHolder{
        @BindView(R.id.imgLogo)
        ImageView imgLogo;
        @BindView(R.id.txtNotification)
        AnyTextView txtNotification;
        @BindView(R.id.textLayout)
        LinearLayout textLayout;
        @BindView(R.id.txtDate)
        AnyTextView txtDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
