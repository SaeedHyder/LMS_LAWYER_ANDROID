package com.ingic.lmslawyer.ui.viewbinders.abstracts;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.NewsFeedItem;
import com.ingic.lmslawyer.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.commons.lang3.text.WordUtils;

public class NewsFeedListItemBinder extends ViewBinder<NewsFeedItem> {

    private Context context;
    private ImageLoader imageLoader;

    public NewsFeedListItemBinder(Context context) {
        super(R.layout.newsfeed_list_item);

        this.context = context;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(NewsFeedItem entity, int position, int grpPosition, View view, Activity activity) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.txtFeed.setText(entity.getTitle());
        viewHolder.txtTime.setText(entity.getUpdated_at().subSequence(entity.getUpdated_at().indexOf(" "), entity.getUpdated_at().length()));
        viewHolder.txtDate.setText(entity.getUpdated_at().subSequence(0, entity.getUpdated_at().indexOf(" ")));
        viewHolder.txtLocation.setText(WordUtils.capitalize(entity.getLocation()));
        imageLoader.displayImage(entity.getNewsfeed_image(), viewHolder.imgFeed);
        viewHolder.txtDetail.setText(entity.getDescription());
    }

    public static class ViewHolder extends BaseViewHolder {

        private AnyTextView txtFeed;
        private AnyTextView txtTime;
        private AnyTextView txtDate;
        private AnyTextView txtLocation;
        private ImageView imgFeed;
        private AnyTextView txtDetail;

        public ViewHolder(View view) {

            txtFeed = (AnyTextView) view.findViewById(R.id.txtFeed);
            txtTime = (AnyTextView) view.findViewById(R.id.txtTime);
            txtDate = (AnyTextView) view.findViewById(R.id.txtDate);
            txtLocation = (AnyTextView) view.findViewById(R.id.txtLocation);
            imgFeed = (ImageView) view.findViewById(R.id.imgFeed);
            txtDetail = (AnyTextView) view.findViewById(R.id.txtDetail);
        }
    }
}
