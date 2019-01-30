package com.ingic.lmslawyer.entities;

public class NewsfeedListingItem {

    private String feedText;
    private String txtDate;
    private String txtTime;
    private String txtLoc;
    private int imgRes;
    private String detail;

    public NewsfeedListingItem(String feedText, String txtDate, String txtTime, String txtLoc, int imgRes, String detail) {
        setFeedText(feedText);
        setTxtDate(txtDate);
        setTxtTime(txtTime);
        setTxtLoc(txtLoc);
        setImgRes(imgRes);
        setDetail(detail);
    }

    public String getFeedText() {
        return feedText;
    }

    public void setFeedText(String feedText) {
        this.feedText = feedText;
    }

    public String getTxtDate() {
        return txtDate;
    }

    public void setTxtDate(String txtDate) {
        this.txtDate = txtDate;
    }

    public String getTxtTime() {
        return txtTime;
    }

    public void setTxtTime(String txtTime) {
        this.txtTime = txtTime;
    }

    public String getTxtLoc() {
        return txtLoc;
    }

    public void setTxtLoc(String txtLoc) {
        this.txtLoc = txtLoc;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
