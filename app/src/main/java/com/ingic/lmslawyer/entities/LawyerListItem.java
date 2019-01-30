package com.ingic.lmslawyer.entities;

public class LawyerListItem {

    private int img;
    private String name;
    private String field;
    private String profession;
    private String experience;

    public LawyerListItem(int img, String name, String field, String profession, String experience) {
        setImg(img);
        setName(name);
        setField(field);
        setProfession(profession);
        setExperience(experience);
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
