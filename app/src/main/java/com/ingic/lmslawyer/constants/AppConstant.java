/*
 * Created by Ingic on 10/6/17 1:06 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 9/20/17 1:45 PM
 */

package com.ingic.lmslawyer.constants;


public class AppConstant {
    public static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static final String DATE_FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_TIME = "HH:mm:ss";
    public static final String DATE_FORMAT_DATE = "yyyy-MM-dd";
    public static final String URL_PATTERN = "((?:http|https)://)?(?:www\\.)?[\\w\\d\\-_]+\\.\\w{2,3}(\\.\\w{2})?(/(?<=/)(?:[\\w\\d\\-./_]+)?)?";
    public static final String IS_NEW_CASE = "IS_NEW_CASE";
    public static final String IS_CASE_DESC = "IS_CASE_DESC";
    public static final String WAITING_CASES = "Awaiting for acknowledgment";
    public static final String IS_WAITING_ACKNOWLEDGE = "IS_WAITING_ACKNOWLEDGE";
    public static final String IS_EDIT_EVENT = "IS_EDIT_EVENT";
    public static final String IS_FROM_PAYMENT = "IS_FROM_PAYMENT";
    public static final String DEVICE_TYPE = "android";
    public static final String EMAIL = "email";
    public static final double ROLE_ID = 4; //3 For USer
    public static final String ACTION_TYPE = "ACTION_TYPE";

    public static String HeaderToken = "HeaderToken";
    public static final String CASE_ID = "CASE_ID";
    public static final String EVENT_DETAIL = "EVENT_DETAIL";
    public static final String ProfileDetail = "ProfileDetail";
    public static final String GetSpecialization = "GetSpecialization";
    public static final String getLawyerSteps = "getLawyerSteps";
    public static final String getLawyerType = "getLawyerType";
    public static final String getCaseTypes = "getCaseTypes";
    public static final String getLanguage = "getLanguage";
    public static final String getCaseLibrary = "getCaseLibrary";
    public static final String CASE_LIBRARY = "CASE_LIBRARY";
    public static final String ACTIVE_CASES = "Active Cases";
    public static final String DOC_LIST = "DOC_LIST";
    public static final String DOC_TYPE = "DOC_TYPE";
    public static final String PENDING_FOR_APPROVAL = "Pending for Approval";
    public static final String IS_PENDING = "IS_PENDING";

    public static final String NEWSFEED_URL = "NEWSFEED_URL";
    /*Cases Constant*/
    public static final int ACCEPT = 1;
    public static final int REJECT = 2;

    //Notifications broadcast
    public static final String BLOCK_USER_BROADCAST = "blockUserBroadCast";
    public static final String UPDATE_PROFILE_BROADCAST = "updateProfileBroadcast";

    public static final String ACCOUNT_APPROVED = "account_approved";
    public static final String ACCOUNT_REJECT = "account_rejected";

}