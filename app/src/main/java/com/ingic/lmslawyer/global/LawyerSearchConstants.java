package com.ingic.lmslawyer.global;

import java.util.ArrayList;

public class LawyerSearchConstants {

    private static String budget;
    private static String latitude;
    private static String longitude;
    private static ArrayList<String> type_ids = new ArrayList<>();
    private static ArrayList<String> case_ids = new ArrayList<>();
    private static ArrayList<String> category_ids = new ArrayList<>();

    public static String getBudget() {
        return budget;
    }

    public static void setBudget(String budget) {
        LawyerSearchConstants.budget = budget;
    }

    public static String getLatitude() {
        return latitude;
    }

    public static void setLatitude(String latitude) {
        LawyerSearchConstants.latitude = latitude;
    }

    public static String getLongitude() {
        return longitude;
    }

    public static void setLongitude(String longitude) {
        LawyerSearchConstants.longitude = longitude;
    }

    public static ArrayList<String> getType_ids() {
        return type_ids;
    }

    public static void setType_ids(ArrayList<String> type_ids) {
        LawyerSearchConstants.type_ids = type_ids;
    }

    public static ArrayList<String> getCase_ids() {
        return case_ids;
    }

    public static void setCase_ids(ArrayList<String> case_ids) {
        LawyerSearchConstants.case_ids = case_ids;
    }

    public static ArrayList<String> getCategory_ids() {
        return category_ids;
    }

    public static void setCategory_ids(ArrayList<String> category_ids) {
        LawyerSearchConstants.category_ids = category_ids;
    }
}
