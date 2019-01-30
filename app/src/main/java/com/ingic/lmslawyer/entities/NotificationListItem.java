package com.ingic.lmslawyer.entities;

public class NotificationListItem {

    private String message;

    private String action_id;

    private String id;

    private String is_deleted;

    private String updated_at;

    private String action_type;

    private String status;

    private String sender_id;

    private String created_at;

    private String receiver_id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction_id() {
        return action_id;
    }

    public void setAction_id(String action_id) {
        this.action_id = action_id;
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

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [message = " + message + ", action_id = " + action_id + ", id = " + id + ", is_deleted = " + is_deleted + ", updated_at = " + updated_at + ", action_type = " + action_type + ", status = " + status + ", sender_id = " + sender_id + ", created_at = " + created_at + ", receiver_id = " + receiver_id + "]";
    }
}
