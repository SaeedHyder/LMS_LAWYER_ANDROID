package com.ingic.lmslawyer.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.DockActivity;
import com.ingic.lmslawyer.activities.MainActivity;
import com.ingic.lmslawyer.entities.CaseDetail;
import com.ingic.lmslawyer.helpers.BasePreferenceHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 6/8/2018.
 */

public class SpinnerAdapter extends ArrayAdapter<CaseDetail> {

    int groupid;
    DockActivity context;
    ArrayList<CaseDetail> list;
    LayoutInflater inflater;
    BasePreferenceHelper prefHelper;
    MainActivity mainFragment;
    ImageLoader imageLoader;

    public SpinnerAdapter(DockActivity context, int groupid, int id, ArrayList<CaseDetail>
            list, BasePreferenceHelper prefHelper, MainActivity mainFragment) {
        super(context, id, list);
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid = groupid;
        this.prefHelper = prefHelper;
        this.mainFragment = mainFragment;
        imageLoader=ImageLoader.getInstance();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = inflater.inflate(groupid, parent, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img);

       /* if (position == 0) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            if (mainFragment != null) {
                if (prefHelper.getUserAllData().getGenderId() == 1) {
                    Glide.with(mainFragment).asGif()
                            .load(list.get(position).getMaleIcon())
                            .apply(bitmapTransform(new CircleCrop()))
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.placeholder3))
                            .into(imageView);
                 //  imageLoader.displayImage(list.get(position).getMaleIcon(),imageView);
                } else {
                    Glide.with(mainFragment).asGif()
                            .load(list.get(position).getFemaleIcon())
                            .apply(bitmapTransform(new CircleCrop()))
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.placeholder3))
                            .into(imageView);
                }
            }
        }*/

        TextView textView = (TextView) itemView.findViewById(R.id.txt);

            textView.setText(list.get(position).getSubject());

        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup
            parent) {
        return getView(position, convertView, parent);

    }
}

