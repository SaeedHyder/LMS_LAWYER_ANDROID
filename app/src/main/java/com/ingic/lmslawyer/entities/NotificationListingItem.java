package com.ingic.lmslawyer.entities;

public class NotificationListingItem {

    private String notificationText;
    private String notificationDate;
    private String notificationTime;

    public NotificationListingItem(String notificationText, String notificationDate, String notificationTime) {
        setNotificationText(notificationText);
        setNotificationDate(notificationDate);
        setNotificationTime(notificationTime);

    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }
}
