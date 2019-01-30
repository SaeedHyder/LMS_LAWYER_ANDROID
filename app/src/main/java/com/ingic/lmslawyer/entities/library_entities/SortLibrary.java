package com.ingic.lmslawyer.entities.library_entities;

import java.util.List;

public class SortLibrary {
    String title;
    Integer count;
    String placeHolder;
    List<Photo> docList;

    public SortLibrary(String title, Integer count, String placeHolder, List<Photo> docList) {
        this.title = title;
        this.count = count;
        this.placeHolder = placeHolder;
        this.docList = docList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public List<Photo> getDocList() {
        return docList;
    }

    public void setDocList(List<Photo> docList) {
        this.docList = docList;
    }
}