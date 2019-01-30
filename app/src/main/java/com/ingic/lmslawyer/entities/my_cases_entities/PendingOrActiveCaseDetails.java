
package com.ingic.lmslawyer.entities.my_cases_entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ingic.lmslawyer.entities.User;

import java.util.List;

public class PendingOrActiveCaseDetails  {

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
    @SerializedName("events_count")
    @Expose
    private Integer eventsCount;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("Photos")
    @Expose
    private Integer photos;
    @SerializedName("videos")
    @Expose
    private Integer videos;
    @SerializedName("files")
    @Expose
    private Integer files;
    @SerializedName("case_documents")
    @Expose
    private List<Object> caseDocuments = null;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("lawyer")
    @Expose
    private User lawyer;
    @SerializedName("payments")
    @Expose
    private List<Payment> payments = null;

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

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
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

    public List<Object> getCaseDocuments() {
        return caseDocuments;
    }

    public void setCaseDocuments(List<Object> caseDocuments) {
        this.caseDocuments = caseDocuments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getLawyer() {
        return lawyer;
    }

    public void setLawyer(User lawyer) {
        this.lawyer = lawyer;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public Integer getEventsCount() {
        return eventsCount;
    }

    public void setEventsCount(Integer eventsCount) {
        this.eventsCount = eventsCount;
    }
}
