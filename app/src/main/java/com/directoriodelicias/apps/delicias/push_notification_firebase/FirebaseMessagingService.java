package com.directoriodelicias.apps.delicias.push_notification_firebase;

import android.util.Log;

import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by idriss on 06/10/2016.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    public static final String TAG = "FirebaseMessaging";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (AppConfig.APP_DEBUG) {
            Log.d(TAG, "From " + remoteMessage.toString());
            Log.d(TAG, "From: " + remoteMessage.getFrom());
        }

        Map<String, String> messageFromOwnServer = remoteMessage.getData();
        try {
            showNotification(messageFromOwnServer);
        } catch (Exception e) {
            try {
                //showNotification(remoteMessage.getNotification().getBody());
            } catch (Exception e1) {
                if (AppConfig.APP_DEBUG)
                    e1.printStackTrace();
            }

            if (AppConfig.APP_DEBUG)
                e.printStackTrace();
        }


    }


    private void showNotification(Map<String, String> message) {

//        Intent i = new Intent(this,MainActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                .setAutoCancel(true)
//                .setContentTitle(getResources().getString(R.string.notification_title))
//                .setContentText(message)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        manager.notify(1,builder.build());

        if (AppConfig.APP_DEBUG) {
            Log.e(TAG, "InCommingData " + message.toString());
        }

        DTNotificationManager mCampaignNotifManager = new DTNotificationManager(this, message);
        mCampaignNotifManager.push();

    }
}
