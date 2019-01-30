package com.ingic.lmslawyer.global;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class FilterSearchConstants {

    public static boolean isFilter = false;
    private static String name = null;
    private static ArrayList<String> specializationIds = null;
    private static String experienceIds = null;
    private static String successRate = null;
    private static String education = null;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        FilterSearchConstants.name = name;
    }

    public static ArrayList<String> getSpecializationIds() {
        return specializationIds;
    }

    public static void setSpecializationIds(ArrayList<String> specializationIds) {
        FilterSearchConstants.specializationIds = specializationIds;
    }

    public static String getExperienceIds() {
        return experienceIds;
    }

    public static void setExperienceIds(String experienceIds) {
        FilterSearchConstants.experienceIds = experienceIds;
    }

    public static String getSuccessRate() {
        return successRate;
    }

    public static void setSuccessRate(String successRate) {
        FilterSearchConstants.successRate = successRate;
    }

    public static String getEducation() {
        return education;
    }

    public static void setEducation(String education) {
        FilterSearchConstants.education = education;
    }

    public static void emptyAllFields(FilterSearchConstants obj) {
        try {
            for (Field f : obj.getClass().getFields()) {

                if (f.getName().endsWith("serialVersionUID")) {
                    continue;
                }

                f.setAccessible(true);
                f.set(obj, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfAllFieldsAreEmpty() {
        return name == null && specializationIds == null && experienceIds == null && successRate == null && education == null;

    }

}
