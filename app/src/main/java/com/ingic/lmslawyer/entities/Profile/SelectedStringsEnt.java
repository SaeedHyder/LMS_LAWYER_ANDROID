package com.ingic.lmslawyer.entities.Profile;

/**
 * Created by saeedhyder on 7/2/2018.
 */

public class SelectedStringsEnt {

    String selectLanguageString;
    String specializationString;
    String lawFieldString;
    String typeOfLawyerString;
    String typeOfCaseServeString;
    ProfileData profileData;

    public SelectedStringsEnt(String specializationString, String lawFieldString, String typeOfLawyerString, String typeOfCaseServeString,String selectLanguageString, ProfileData profileData) {
        this.selectLanguageString = selectLanguageString;
        this.specializationString = specializationString;
        this.lawFieldString = lawFieldString;
        this.typeOfLawyerString = typeOfLawyerString;
        this.typeOfCaseServeString = typeOfCaseServeString;
        this.profileData = profileData;
    }

    public String getSelectLanguageString() {
        return selectLanguageString;
    }

    public void setSelectLanguageString(String selectLanguageString) {
        this.selectLanguageString = selectLanguageString;
    }

    public String getSpecializationString() {
        return specializationString;
    }

    public void setSpecializationString(String specializationString) {
        this.specializationString = specializationString;
    }

    public String getLawFieldString() {
        return lawFieldString;
    }

    public void setLawFieldString(String lawFieldString) {
        this.lawFieldString = lawFieldString;
    }

    public String getTypeOfLawyerString() {
        return typeOfLawyerString;
    }

    public void setTypeOfLawyerString(String typeOfLawyerString) {
        this.typeOfLawyerString = typeOfLawyerString;
    }

    public String getTypeOfCaseServeString() {
        return typeOfCaseServeString;
    }

    public void setTypeOfCaseServeString(String typeOfCaseServeString) {
        this.typeOfCaseServeString = typeOfCaseServeString;
    }

    public ProfileData getProfileData() {
        return profileData;
    }

    public void setProfileData(ProfileData profileData) {
        this.profileData = profileData;
    }
}
