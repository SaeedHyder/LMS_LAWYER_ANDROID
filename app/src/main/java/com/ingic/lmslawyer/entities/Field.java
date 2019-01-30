package com.ingic.lmslawyer.entities;

public class Field {

    private String id;
    private String field_id;
    private Field_Detail field_detail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getField_id() {
        return field_id;
    }

    public void setField_id(String field_id) {
        this.field_id = field_id;
    }

    public Field_Detail getField_detail() {
        return field_detail;
    }

    public void setField_detail(Field_Detail field_detail) {
        this.field_detail = field_detail;
    }
}
