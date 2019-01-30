package com.ingic.lmslawyer.entities.Profile;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 7/2/2018.
 */

public class ProfileListingEnt {

    String data;
    boolean isChecked;

    public ProfileListingEnt(String data, boolean isChecked) {
        this.data = data;
        this.isChecked = isChecked;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
