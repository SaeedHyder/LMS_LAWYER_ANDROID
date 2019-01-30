package com.ingic.lmslawyer.helpers;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.dmallcott.dismissibleimageview.DismissibleImageView;
import com.ingic.lmslawyer.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

public class ImageLoaderHelper {

    public static void loadImage(String url, ImageView img) {
        if (img == null) return;
        ImageLoader.getInstance()
                .displayImage(url, img);
    }

    public static void loadImage(String url, DismissibleImageView img) {
        if (img == null) return;
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageLoader.getInstance()
                .displayImage(url, img);

    }

    public static void loadImageWithPicasso(Context context, String url, ImageView imageView) {
        if (imageView == null || TextUtils.isEmpty(url)) return;
        Picasso.with(context)
                .load(url)
//                    .error(R.drawable.placeholder_banner_home)
                .placeholder(R.drawable.placeholder_image)
                .into(imageView);
    }
}
