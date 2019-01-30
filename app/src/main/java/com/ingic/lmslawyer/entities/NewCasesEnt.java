package com.ingic.lmslawyer.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saeedhyder on 6/29/2018.
 */

public class NewCasesEnt {

    @SerializedName("Recent")
    @Expose
    private ArrayList<CaseDetail> recent = new ArrayList<>();
    /*@SerializedName("Pending")
    @Expose
    private ArrayList<CaseDetail> pending = new ArrayList<>();
    @SerializedName("Waiting")
    @Expose
    private ArrayList<CaseDetail> waiting = new ArrayList<>();*/

    public ArrayList<CaseDetail> getRecent() {
        return recent;
    }

    public void setRecent(ArrayList<CaseDetail> recent) {
        this.recent = recent;
    }

}
