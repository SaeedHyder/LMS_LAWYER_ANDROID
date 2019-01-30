package com.ingic.lmslawyer.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ingic.lmslawyer.entities.event_entities.UserDoc;

import java.util.ArrayList;
import java.util.List;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("is_active")
    @Expose
    private Integer isActive;
    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("academics")
    @Expose
    private String academics;
    @SerializedName("fees")
    @Expose
    private Integer fees;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("affilation_id")
    @Expose
    private Integer affilationId;
    @SerializedName("award")
    @Expose
    private String award;
    @SerializedName("experience_id")
    @Expose
    private Integer experienceId;
    @SerializedName("socialmedia_type")
    @Expose
    private String socialmediaType;
    @SerializedName("socialmedia_id")
    @Expose
    private String socialmediaId;
    @SerializedName("is_notify")
    @Expose
    private Integer isNotify;
    @SerializedName("verification_code")
    @Expose
    private String verificationCode;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("avg_rate")
    @Expose
    private Integer avgRate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("is_selected")
    @Expose
    private int isSelected;
    @SerializedName("resume_documents")
    @Expose
    private ArrayList<String> resumeDocuments = new ArrayList<>();
    @SerializedName("user_docs")
    @Expose
    private ArrayList<UserDocs> userDocs = new ArrayList<>();
    @SerializedName("subscribe_category_id")
    @Expose
    private Integer subscribeCategoryId=0;
    @SerializedName("win_case")
    @Expose
    private String win_case;
    @SerializedName("loss_case")
    @Expose
    private String lossCase;

    public String getWin_case() {
        return win_case;
    }

    public void setWin_case(String win_case) {
        this.win_case = win_case;
    }

    public String getLossCase() {
        return lossCase;
    }

    public void setLossCase(String lossCase) {
        this.lossCase = lossCase;
    }

    public Integer getSubscribeCategoryId() {
        return subscribeCategoryId;
    }

    public void setSubscribeCategoryId(Integer subscribeCategoryId) {
        this.subscribeCategoryId = subscribeCategoryId;
    }

    boolean isAdded=false;

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getAcademics() {
        return academics;
    }

    public void setAcademics(String academics) {
        this.academics = academics;
    }

    public Integer getFees() {
        return fees;
    }

    public void setFees(Integer fees) {
        this.fees = fees;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getAffilationId() {
        return affilationId;
    }

    public void setAffilationId(Integer affilationId) {
        this.affilationId = affilationId;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public Integer getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(Integer experienceId) {
        this.experienceId = experienceId;
    }

    public String getSocialmediaType() {
        return socialmediaType;
    }

    public void setSocialmediaType(String socialmediaType) {
        this.socialmediaType = socialmediaType;
    }

    public String getSocialmediaId() {
        return socialmediaId;
    }

    public void setSocialmediaId(String socialmediaId) {
        this.socialmediaId = socialmediaId;
    }

    public Integer getIsNotify() {
        return isNotify;
    }

    public void setIsNotify(Integer isNotify) {
        this.isNotify = isNotify;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Integer avgRate) {
        this.avgRate = avgRate;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public ArrayList<UserDocs> getUserDocs() {
        return userDocs;
    }

    public void setUserDocs(ArrayList<UserDocs> userDocs) {
        this.userDocs = userDocs;
    }
}
