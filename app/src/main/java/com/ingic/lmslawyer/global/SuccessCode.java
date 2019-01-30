package com.ingic.lmslawyer.global;

public enum SuccessCode {

    SUCCESS("2000"),
    FAILURE("1000"),;


    private final String successCode;

    SuccessCode(String successCode) {
        this.successCode = successCode;
    }

    public String getValue() {
        return this.successCode;
    }
}
