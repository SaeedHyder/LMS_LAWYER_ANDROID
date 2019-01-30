package com.ingic.lmslawyer.entities;

public class Languages {

    private String id;
    private String language_id;
    private Language_Detail language_detail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(String language_id) {
        this.language_id = language_id;
    }

    public Language_Detail getLanguage_detail() {
        return language_detail;
    }

    public void setLanguage_detail(Language_Detail language_detail) {
        this.language_detail = language_detail;
    }
}
