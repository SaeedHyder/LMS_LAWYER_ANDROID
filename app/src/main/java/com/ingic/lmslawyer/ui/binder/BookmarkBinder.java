package com.ingic.lmslawyer.ui.binder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.DockActivity;
import com.ingic.lmslawyer.entities.BookmarkEnt;
import com.ingic.lmslawyer.helpers.BasePreferenceHelper;
import com.ingic.lmslawyer.helpers.DateHelper;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.interfaces.BookmarkInterface;
import com.ingic.lmslawyer.interfaces.RecyclerClickListner;
import com.ingic.lmslawyer.ui.viewbinders.abstracts.CustomRecyclerViewBinder;
import com.ingic.lmslawyer.ui.views.AnyTextView;
import com.ingic.lmslawyer.util.RoundCornersImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BookmarkBinder extends CustomRecyclerViewBinder<BookmarkEnt> {
    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    private BookmarkInterface clickListner;

    public BookmarkBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, BookmarkInterface clickListner) {
        super(R.layout.row_item_bookmark);
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
    public void bindView(BookmarkEnt entity, int position, Object viewHolder, Context context) {

        final ViewHolder holder = (ViewHolder) viewHolder;
        imageLoader.displayImage(entity.getImageUrl(),holder.profileImage);
        holder.txtName.setText(entity.getTitle()+"");
        holder.tvDetail.setText(entity.getDescription()+"");

        TextViewHelper.setText(holder.tvDateTime, DateHelper.getTime(entity.getCreatedAt())
                + " | " + DateHelper.getLocalDateTime2(entity.getCreatedAt()));


        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.share(entity,position);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.delete(entity,position);
            }
        });



    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.profileImage)
        RoundCornersImageView profileImage;
        @BindView(R.id.txtName)
        AnyTextView txtName;
        @BindView(R.id.tvDateTime)
        AnyTextView tvDateTime;
        @BindView(R.id.tvDetail)
        AnyTextView tvDetail;
        @BindView(R.id.textLayout)
        LinearLayout textLayout;
        @BindView(R.id.btnShare)
        ImageView btnShare;
        @BindView(R.id.btnDelete)
        ImageView btnDelete;
        @BindView(R.id.mainlayout)
        LinearLayout mainlayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
