package com.ingic.lmslawyer.entities.newsfeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 5/4/2017.
 */

public class Categories {
    @SerializedName("id")
    @Expose
    private Double id;

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("is_selected")
    @Expose
    private int is_selected;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return is_selected == 1;
    }
}
