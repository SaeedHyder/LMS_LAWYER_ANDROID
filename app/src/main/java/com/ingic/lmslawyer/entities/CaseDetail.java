package com.ingic.lmslawyer.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 6/29/2018.
 */

public class CaseDetail {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("lawyer_id")
    @Expose
    private Integer lawyerId;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("is_active")
    @Expose
    private Integer isActive;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("Photos")
    @Expose
    private Integer photos;
    @SerializedName("videos")
    @Expose
    private Integer videos;
    @SerializedName("files")
    @Expose
    private Integer files;

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("lawyer")
    @Expose
    private Lawyer lawyer;
    @SerializedName("payments")
    @Expose
    private ArrayList<String> payments = new ArrayList<>();

    @SerializedName("case_documents")
    @Expose
    private ArrayList<String> caseDocuments = new ArrayList<>();


    public CaseDetail() {
    }
 public CaseDetail(String subject) {
        this.subject = subject;
    }

    public ArrayList<String> getCaseDocuments() {
        return caseDocuments;
    }

    public void setCaseDocuments(ArrayList<String> caseDocuments) {
        this.caseDocuments = caseDocuments;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(Integer lawyerId) {
        this.lawyerId = lawyerId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getPhotos() {
        return photos;
    }

    public void setPhotos(Integer photos) {
        this.photos = photos;
    }

    public Integer getVideos() {
        return videos;
    }

    public void setVideos(Integer videos) {
        this.videos = videos;
    }

    public Integer getFiles() {
        return files;
    }

    public void setFiles(Integer files) {
        this.files = files;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lawyer getLawyer() {
        return lawyer;
    }

    public void setLawyer(Lawyer lawyer) {
        this.lawyer = lawyer;
    }

    public ArrayList<String> getPayments() {
        return payments;
    }

    public void setPayments(ArrayList<String> payments) {
        this.payments = payments;
    }
}
