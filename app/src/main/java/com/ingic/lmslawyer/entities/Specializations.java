package com.ingic.lmslawyer.entities;

public class Specializations {

    private String id;
    private String specification_id;
    private Specialization_Detail specialization_detail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecification_id() {
        return specification_id;
    }

    public void setSpecification_id(String specification_id) {
        this.specification_id = specification_id;
    }

    public Specialization_Detail getSpecialization_detail() {
        return specialization_detail;
    }

    public void setSpecialization_detail(Specialization_Detail specialization_detail) {
        this.specialization_detail = specialization_detail;
    }
}
