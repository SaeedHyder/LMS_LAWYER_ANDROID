package com.ingic.lmslawyer.helpers;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.ImageView;

import java.util.HashMap;

public class PostVideoBitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView mImageView;
    private String TAG;

    public PostVideoBitmapWorkerTask(ImageView imageView) {
        mImageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(
            String... params) {
        Bitmap bitmap = null;
        try {
            bitmap = retriveVideoFrameFromVideo(params[0]);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
//    mFragment.addBitmapToCache(TAG,bitmap);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
//    if(mImageView.getTag().toString().equals(TAG)) {
        if (bitmap == null) return;
        mImageView.setImageBitmap(bitmap);
//    }
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);

            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }
}