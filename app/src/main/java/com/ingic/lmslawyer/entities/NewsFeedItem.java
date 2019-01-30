package com.ingic.lmslawyer.entities;

public class NewsFeedItem {


    private String id;

    private String is_deleted;

    private String title;

    private String updated_at;

    private String location;

    private String description;

    private String created_at;

    private String category_id;

    private String longitude;

    private String latitude;

    private String newsfeed_image;

    private String newsfeed_picture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
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

    public String getNewsfeed_image() {
        return newsfeed_image;
    }

    public void setNewsfeed_image(String newsfeed_image) {
        this.newsfeed_image = newsfeed_image;
    }

    public String getNewsfeed_picture() {
        return newsfeed_picture;
    }

    public void setNewsfeed_picture(String newsfeed_picture) {
        this.newsfeed_picture = newsfeed_picture;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", is_deleted = " + is_deleted + ", title = " + title + ", updated_at = " + updated_at + ", location = " + location + ", description = " + description + ", created_at = " + created_at + ", category_id = " + category_id + ", longitude = " + longitude + ", latitude = " + latitude + ", newsfeed_image = " + newsfeed_image + ", newsfeed_picture = " + newsfeed_picture + "]";
    }

}
