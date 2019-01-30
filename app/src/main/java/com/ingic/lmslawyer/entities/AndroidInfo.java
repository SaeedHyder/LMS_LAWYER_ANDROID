package com.ingic.lmslawyer.entities;

/**
 * Created on 5/4/2017.
 */

public class AndroidInfo {
    private String name;
    private String version;

    public AndroidInfo(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public AndroidInfo(String name) {

        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
