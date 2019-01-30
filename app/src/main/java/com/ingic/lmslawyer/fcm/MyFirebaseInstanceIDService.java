package com.ingic.lmslawyer.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.ingic.lmslawyer.helpers.BasePreferenceHelper;
import com.ingic.lmslawyer.helpers.TokenUpdater;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    protected BasePreferenceHelper preferenceHelper;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        preferenceHelper = new BasePreferenceHelper(getApplicationContext());
        // sending fcm token to server
        if (preferenceHelper.getUser() != null)
            sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(final String token) {
        Log.e(TAG, "sendRegistrationToServer: " + token);
        TokenUpdater.getInstance().UpdateToken(getApplicationContext(),
                preferenceHelper.getUser().getToken(), "android", token);
    }
}