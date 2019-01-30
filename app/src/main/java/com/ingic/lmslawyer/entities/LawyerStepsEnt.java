package com.ingic.lmslawyer.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ingic.lmslawyer.entities.Profile.ProfileServicesEnt;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 7/2/2018.
 */

public class LawyerStepsEnt {

    @SerializedName("Case")
    @Expose
    private ArrayList<ProfileServicesEnt> _case = new ArrayList<>();
    @SerializedName("Specialization")
    @Expose
    private ArrayList<ProfileServicesEnt> specialization = new ArrayList<>();
    @SerializedName("Law")
    @Expose
    private ArrayList<ProfileServicesEnt> law = new ArrayList<>();
    @SerializedName("LawyerType")
    @Expose
    private ArrayList<ProfileServicesEnt> lawyerType = new ArrayList<>();
    @SerializedName("Language")
    @Expose
    private ArrayList<ProfileServicesEnt> language = new ArrayList<>();


    public ArrayList<ProfileServicesEnt> get_case() {
        return _case;
    }

    public void set_case(ArrayList<ProfileServicesEnt> _case) {
        this._case = _case;
    }

    public ArrayList<ProfileServicesEnt> getLanguage() {
        return language;
    }

    public void setLanguage(ArrayList<ProfileServicesEnt> language) {
        this.language = language;
    }

    public ArrayList<ProfileServicesEnt> getLaw() {
        return law;
    }

    public void setLaw(ArrayList<ProfileServicesEnt> law) {
        this.law = law;
    }

    public ArrayList<ProfileServicesEnt> getLawyerType() {
        return lawyerType;
    }

    public void setLawyerType(ArrayList<ProfileServicesEnt> lawyerType) {
        this.lawyerType = lawyerType;
    }

    public ArrayList<ProfileServicesEnt> getSpecialization() {
        return specialization;
    }

    public void setSpecialization(ArrayList<ProfileServicesEnt> specialization) {
        this.specialization = specialization;
    }
}
