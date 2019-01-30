
package com.ingic.lmslawyer.entities.library_entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LibraryEnt {

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
    @SerializedName("cases")
    @Expose
    private List<Case> cases = null;

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

    public List<Case> getCases() {
        return cases;
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
    }

}
