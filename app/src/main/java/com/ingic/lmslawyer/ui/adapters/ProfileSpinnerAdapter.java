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
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.LawyerRateResponse;
import com.ingic.lmslawyer.helpers.BasePreferenceHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 7/3/2018.
 */

public class ProfileSpinnerAdapter extends ArrayAdapter<LawyerRateResponse> {


    int groupid;
    DockActivity context;
    ArrayList<LawyerRateResponse> list;
    LayoutInflater inflater;
    BasePreferenceHelper prefHelper;
    MainActivity mainFragment;
    ImageLoader imageLoader;
    String Tag;

    public ProfileSpinnerAdapter(DockActivity context, int groupid, int id,
                                 ArrayList<LawyerRateResponse> list, BasePreferenceHelper prefHelper,
                                 MainActivity mainFragment, String Tag/* ,String hint*/) {
        super(context, id, list);
        this.list = list;
        list.add(0 ,new LawyerRateResponse("Please Select"));
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid = groupid;
        this.prefHelper = prefHelper;
        this.mainFragment = mainFragment;
        imageLoader = ImageLoader.getInstance();
        this.Tag = Tag;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = inflater.inflate(groupid, parent, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img);
        final TextView textView = (TextView) itemView.findViewById(R.id.txt);

        if (Tag.equals(WebServiceConstant.LawyerRates)) {
            if (position == 0) {
                textView.setText(list.get(position).getTitle());

            } else
                textView.setText(list.get(position).getTitle() + " (" + list.get(position).getFrom() + " -" + list.get(position).getTo() + " )");
        } else {
            textView.setText(list.get(position).getTitle());
        }

        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);

    }


}
