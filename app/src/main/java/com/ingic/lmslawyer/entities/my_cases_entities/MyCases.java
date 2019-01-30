
package com.ingic.lmslawyer.entities.my_cases_entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyCases {

    @SerializedName("Recent")
    @Expose
    private ArrayList<PendingOrActiveCaseDetails> active = null;
    @SerializedName("Pending")
    @Expose
    private ArrayList<PendingOrActiveCaseDetails> pending = null;
    @SerializedName("Waiting")
    @Expose
    private ArrayList<PendingOrActiveCaseDetails> waiting = null;

    public ArrayList<PendingOrActiveCaseDetails> getActive() {
        return active;
    }

    public void setActive(ArrayList<PendingOrActiveCaseDetails> active) {
        this.active = active;
    }

    public ArrayList<PendingOrActiveCaseDetails> getPending() {
        return pending;
    }

    public void setPending(ArrayList<PendingOrActiveCaseDetails> pendingOrRecent) {
        this.pending = pendingOrRecent;
    }

    public ArrayList<PendingOrActiveCaseDetails> getWaiting() {
        return waiting;
    }

    public void setWaiting(ArrayList<PendingOrActiveCaseDetails> waiting) {
        this.waiting = waiting;
    }
}
