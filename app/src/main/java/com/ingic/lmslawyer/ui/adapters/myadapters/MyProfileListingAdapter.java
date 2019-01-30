package com.ingic.lmslawyer.ui.adapters.myadapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.Profile.ProfileServicesEnt;
import com.ingic.lmslawyer.interfaces.ProfileCheckBoxInterface;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.ingic.lmslawyer.ui.views.AnyTextView;


public class MyProfileListingAdapter extends RecyclerViewListAdapter<ProfileServicesEnt> {
    private Context context;
    private ProfileCheckBoxInterface checkBoxInterface;


    public MyProfileListingAdapter(Context context, ProfileCheckBoxInterface checkBoxInterface) {
        super(context);
        this.context = context;
        this.checkBoxInterface = checkBoxInterface;

    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.item_my_profile, viewGroup, false);
    }

    @Override
    protected void bindView(final ProfileServicesEnt item, final RecyclerviewViewHolder viewHolder) {
        if (item != null) {
            AnyTextView tvName = (AnyTextView) viewHolder.getView(R.id.tvText);
            CheckBox cbCheck = (CheckBox) viewHolder.getView(R.id.cbCheck);
            tvName.setText(item.getTitle());
            tvName.setGravity(Gravity.CENTER_VERTICAL);
            //in some cases, it will prevent unwanted situations
            cbCheck.setOnCheckedChangeListener(null);

            //if true, your checkbox will be selected, else unselected
            if (item.getIsSelected() != null && item.getIsSelected().equals("1")) {
                checkBoxInterface.setHashMap(item, true);
                cbCheck.setChecked(true);
                item.setChecked(true);
            } else {
                cbCheck.setChecked(false);
                item.setChecked(false);
            }

            /*For local selection*/
            if (item.isChecked()) {
                cbCheck.setChecked(true);
            } else {
                cbCheck.setChecked(false);
            }
            /*============*/

            cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkBoxInterface.setHashMap(item, isChecked);
                    item.setChecked(isChecked);
                    if (isChecked) {
                        item.setIsSelected("1");
                    } else item.setIsSelected("0");

                }
            });


        }
    }

    @Override
    protected int bindItemViewType(int position) {
        return 0;
    }

    @Override
    protected int bindItemId(int position) {
        return 0;
    }
}
