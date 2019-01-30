package com.ingic.lmslawyer.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ingic.lmslawyer.entities.event_entities.UserDoc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saeedhyder on 7/4/2018.
 */

public class SenderDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("resume_documents")
    @Expose
    private ArrayList<String> resumeDocuments = new ArrayList<>();
    @SerializedName("user_docs")
    @Expose
    private ArrayList<UserDoc> userDocs = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<String> getResumeDocuments() {
        return resumeDocuments;
    }

    public void setResumeDocuments(ArrayList<String> resumeDocuments) {
        this.resumeDocuments = resumeDocuments;
    }

    public ArrayList<UserDoc> getUserDocs() {
        return userDocs;
    }

    public void setUserDocs(ArrayList<UserDoc> userDocs) {
        this.userDocs = userDocs;
    }
}
