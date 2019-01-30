package com.ingic.lmslawyer.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.ingic.lmslawyer.entities.User;
import com.ingic.lmslawyer.retrofit.GsonFactory;


public class BasePreferenceHelper extends PreferenceHelper {

    protected static final String KEY_LOGIN_STATUS = "islogin";
    protected static final String KEY_USER = "userObject";
    private static final String FILENAME = "preferences";
    protected static final String TOKEN = "TOKEN";
    private Context context;


    public BasePreferenceHelper(Context c) {
        this.context = c;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
    }

    public void setLoginStatus(boolean isLogin) {
        putBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS, isLogin);
    }

    public boolean isLogin() {
        return getBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS);
    }

    public User getUser() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_USER), User.class);
    }

    public void putUser(User user) {
        putStringPreference(context, FILENAME, KEY_USER, GsonFactory
                .getConfiguredGson().toJson(user));
    }


}
