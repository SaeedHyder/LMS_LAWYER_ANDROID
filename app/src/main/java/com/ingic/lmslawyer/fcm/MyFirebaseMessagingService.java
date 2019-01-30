package com.ingic.lmslawyer.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.MainActivity;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.helpers.BasePreferenceHelper;
import com.ingic.lmslawyer.retrofit.WebService;

import java.util.Map;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private WebService webservice;
    private BasePreferenceHelper preferenceHelper;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        preferenceHelper = new BasePreferenceHelper(getApplicationContext());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> msgData = remoteMessage.getData();
            String msg = msgData.get("message");
            String title = msgData.get("title");
            String actionType = msgData.get("action_type");

            Log.e(TAG, "onMessageReceived: msg->" + msg + " title->" + title);
            sendNotification(title, msg, actionType);
            if (actionType != null) {
                if (actionType.equals(AppConstant.ACCOUNT_APPROVED)) {
                    hitUpdateProfileBroadCast("", "");
                } else if (actionType.equals(AppConstant.ACCOUNT_REJECT)) {
                    hitBlockUserBroadCast("", "");
                }
            }


        } else
            //Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
                String msg = remoteMessage.getNotification().getBody();
                String title = remoteMessage.getNotification().getTitle();
                sendNotification(title, msg, "");
            }
    }

    private void sendNotification(String title, String msg, String account_type) {
        if (TextUtils.isEmpty(title)) title = getResources().getString(R.string.app_name);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(AppConstant.ACTION_TYPE, account_type);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setDefaults(Notification.DEFAULT_ALL)  //to show alert notification
                .setPriority(Notification.PRIORITY_MAX)  //on top of app
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Random rn = new Random();
        int range = 10000 - 1 + 1;
        int randomNum = rn.nextInt(range) + 1;
        notificationManager.notify(randomNum /* ID of notification */, notificationBuilder.build());
    }

    private void hitBlockUserBroadCast(String type, String msg) {
        Intent intent = new Intent();
        intent.setAction(AppConstant.BLOCK_USER_BROADCAST);
//        intent.putExtra(AppConstant.BLOCK_USER, type);
        this.sendBroadcast(intent);
        preferenceHelper.setLoginStatus(false);


    }

    private void hitUpdateProfileBroadCast(String type, String msg) {
        Intent intent = new Intent();
        intent.setAction(AppConstant.UPDATE_PROFILE_BROADCAST);
//        intent.putExtra(AppConstant.BLOCK_USER, type);
        this.sendBroadcast(intent);
    }
}