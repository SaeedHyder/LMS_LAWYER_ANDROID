
package com.ingic.lmslawyer.entities.library_entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Case implements Serializable {

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
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;
    @SerializedName("videos")
    @Expose
    private List<Photo> videos = null;
    @SerializedName("files")
    @Expose
    private List<Photo> files = null;
    @SerializedName("photos_count")
    @Expose
    private Integer photosCount;
    @SerializedName("videos_count")
    @Expose
    private Integer videosCount;
    @SerializedName("files_count")
    @Expose
    private Integer filesCount;
    @SerializedName("case_documents")
    @Expose
    private List<String> caseDocuments = null;

/*    protected Case(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            userId = null;
        } else {
            userId = in.readInt();
        }
        if (in.readByte() == 0) {
            lawyerId = null;
        } else {
            lawyerId = in.readInt();
        }
        subject = in.readString();
        detail = in.readString();
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        if (in.readByte() == 0) {
            isActive = null;
        } else {
            isActive = in.readInt();
        }
        createdAt = in.readString();
        updatedAt = in.readString();
        deletedAt = in.readString();
        if (in.readByte() == 0) {
            photosCount = null;
        } else {
            photosCount = in.readInt();
        }
        if (in.readByte() == 0) {
            videosCount = null;
        } else {
            videosCount = in.readInt();
        }
        if (in.readByte() == 0) {
            filesCount = null;
        } else {
            filesCount = in.readInt();
        }
        caseDocuments = in.createStringArrayList();
    }

    public static final Creator<Case> CREATOR = new Creator<Case>() {
        @Override
        public Case createFromParcel(Parcel in) {
            return new Case(in);
        }

        @Override
        public Case[] newArray(int size) {
            return new Case[size];
        }
    };*/

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

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Photo> getVideos() {
        return videos;
    }

    public void setVideos(List<Photo> videos) {
        this.videos = videos;
    }

    public List<Photo> getFiles() {
        return files;
    }

    public void setFiles(List<Photo> files) {
        this.files = files;
    }

    public Integer getPhotosCount() {
        return photosCount;
    }

    public void setPhotosCount(Integer photosCount) {
        this.photosCount = photosCount;
    }

    public Integer getVideosCount() {
        return videosCount;
    }

    public void setVideosCount(Integer videosCount) {
        this.videosCount = videosCount;
    }

    public Integer getFilesCount() {
        return filesCount;
    }

    public void setFilesCount(Integer filesCount) {
        this.filesCount = filesCount;
    }

    public List<String> getCaseDocuments() {
        return caseDocuments;
    }

    public void setCaseDocuments(List<String> caseDocuments) {
        this.caseDocuments = caseDocuments;
    }
/*
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(userId);
        parcel.writeInt(lawyerId);
        parcel.writeString(subject);
        parcel.writeString(detail);
        parcel.writeInt(status);
        parcel.writeInt(isActive);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
        parcel.writeString(deletedAt);
        parcel.writeList(photos);
        parcel.writeList(videos);
        parcel.writeList(files);
        parcel.writeInt(photosCount);
        parcel.writeInt(videosCount);
        parcel.writeInt(filesCount);
    }*/


}
