package com.ingic.lmslawyer.global;

import android.app.Activity;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.helpers.UIHelper;


public class RetrofitErrorHandler {

    public static void onServiceFail(Activity activity, Throwable error) {

        if (error.getMessage().contains("Failed to connect")) {
            UIHelper.showLongToastInCenter(activity, activity.getString(R.string.msg_connection_error));
        }
    }
}
