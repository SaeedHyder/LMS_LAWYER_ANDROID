package com.ingic.lmslawyer.ui.viewbinders.abstracts;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.LawyerType_CaseItem;
import com.ingic.lmslawyer.interfaces.ItemClickListener;
import com.ingic.lmslawyer.ui.views.AnyTextView;


public class LawyerTypesItemBinder extends RecyclerViewBinder<LawyerType_CaseItem> {
//public class LawyerTypesItemBinder extends RecyclerViewBinder<LawyerType_CaseItem> {

    private ItemClickListener itemClickListener;


    public LawyerTypesItemBinder(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerViewBinder.BaseViewHolder createViewHolder(
            View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final LawyerType_CaseItem entity, final int position, Object viewHolder, Context context) {
        final ViewHolder holder = (ViewHolder) viewHolder;

        bindItemId(position);

        holder.txtType.setText(entity.getTitle());
//        holder.llroot.setSelected(false);
//        holder.txtType.setSelected(false);

/*
        if (entity.isClick()) {
            //setBackground(holder.llroot,"",context);
//            holder.iconType.setImageResource(R.drawable.crossicon);
            holder.llroot.setBackgroundResource(R.drawable.type_drawable_clicked);
        } else {
            //setBackground(holder.llroot,"",context);
//            holder.iconType.setImageResource(R.drawable.tickicon);
            holder.llroot.setBackgroundResource(R.drawable.type_drawable);
        }*/

        holder.llroot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                itemClickListener.itemClicked(entity, position);
                if (!holder.llroot.isSelected()) {
                    holder.llroot.setSelected(true);
//                    holder.iconType.setImageResource(R.drawable.ic_tick);
                    holder.iconType.setPressed(true);
                    holder.iconType.setSelected(true);
                } else if (holder.llroot.isSelected()) {
                    holder.llroot.setSelected(false);
                    holder.iconType.setPressed(false);
                    holder.iconType.setSelected(false);
                }

              /*  if (!holder.txtType.isPressed())
                    holder.txtType.setPressed(true);
                else if (holder.txtType.isSelected())
                    holder.txtType.setPressed(false);*/
            }
        });

    }

   /* private void setBackground(LinearLayout llroot, String myColor , Context context) {
        Drawable tempDrawable = ContextCompat.getDrawable(context, R.drawable.type_drawable);
        LayerDrawable bubble = (LayerDrawable) tempDrawable; //(cast to root element in xml)
        GradientDrawable solidColor = (GradientDrawable) bubble.findDrawableByLayerId(R.id.outerRectangle);
        solidColor.setColor(context.getColor(myColor));
        llroot.setBackground(tempDrawable);
    }*/

    @Override
    public void bindItemId(int position) {

    }


    public static class ViewHolder extends BaseViewHolder {

        private ImageView iconType;
        private AnyTextView txtType;
        private LinearLayout llroot;

        public ViewHolder(View view) {
            super(view);
            llroot = (LinearLayout) view.findViewById(R.id.llroot);
            iconType = (ImageView) view.findViewById(R.id.iconType);
            txtType = (AnyTextView) view.findViewById(R.id.txtType);

        }
    }

}
