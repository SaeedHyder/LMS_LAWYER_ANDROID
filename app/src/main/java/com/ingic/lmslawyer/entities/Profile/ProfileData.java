package com.ingic.lmslawyer.entities.Profile;

import java.io.File;

/**
 * Created by saeedhyder on 7/2/2018.
 */

public class ProfileData {

    String fullName;

    String Email;
    String PhoneNumber;
    String Academics;
    String Fees;
    String Affilations;
    String Awards;
    String Experince;
    String Bio;
    String location;
    String latitude;
    String longitude;
    File profile_image;

    public ProfileData(String fullName, String email, String phoneNumber, String academics, String fees, String affilations, String awards, String experince, String bio, String location, String latitude, String longitude, File profile_image) {
        this.fullName = fullName;
        Email = email;
        PhoneNumber = phoneNumber;
        Academics = academics;
        Fees = fees;
        Affilations = affilations;
        Awards = awards;
        Experince = experince;
        Bio = bio;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.profile_image = profile_image;
    }

    public File getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(File profile_image) {
        this.profile_image = profile_image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAcademics() {
        return Academics;
    }

    public void setAcademics(String academics) {
        Academics = academics;
    }

    public String getFees() {
        return Fees;
    }

    public void setFees(String fees) {
        Fees = fees;
    }

    public String getAffilations() {
        return Affilations;
    }

    public void setAffilations(String affilations) {
        Affilations = affilations;
    }

    public String getAwards() {
        return Awards;
    }

    public void setAwards(String awards) {
        Awards = awards;
    }

    public String getExperince() {
        return Experince;
    }

    public void setExperince(String experince) {
        Experince = experince;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }
}
