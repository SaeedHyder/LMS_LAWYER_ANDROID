package com.ingic.lmslawyer.entities;

public class LawyerType_CaseItem {

    private String id;

    private String is_deleted;

    private String title;

    private String updated_at;

    private String created_at;

    private boolean isClick = false;

    public LawyerType_CaseItem(String title) {
        this.title = title;
    }

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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", is_deleted = " + is_deleted + ", title = " + title + ", updated_at = " + updated_at + ", created_at = " + created_at + "]";
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
