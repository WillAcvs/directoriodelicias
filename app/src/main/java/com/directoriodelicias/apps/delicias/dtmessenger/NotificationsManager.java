package com.directoriodelicias.apps.delicias.dtmessenger;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.EventsListActivity;
import com.directoriodelicias.apps.delicias.activities.MainActivity;
import com.directoriodelicias.apps.delicias.activities.SplashActivity;
import com.directoriodelicias.apps.delicias.appconfig.AppContext;
import com.directoriodelicias.apps.delicias.classes.Message;
import com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController;
import com.directoriodelicias.apps.delicias.utils.Translator;

import java.util.List;

/**
 * Created by Directorio on 10/9/2016.
 */

public class NotificationsManager {


    public static String TAG_NEED_TO_OPEN_NOTIFICATION = "open_notification";


    public static void pushNotifnewMessage(Context context, List<Message> messages) {
        //prepare intent
        try {

            Intent resultIntent;

            if (MainActivity.isOpend() == true) {
                resultIntent = new Intent();
            } else {
                resultIntent = new Intent(context, SplashActivity.class);
            }

            resultIntent.putExtra(TAG_NEED_TO_OPEN_NOTIFICATION, false);

            String message = "";
            Uri defaultSoundUri;

            if (messages.size() > 1) {
                message = Translator.print("You have %d messages", null, MessengerHelper.NbrMessagesManager.getNbrTotalMessages());
                //intent.putExtra(TAG_NEED_OPEN_INBOX,false);
                defaultSoundUri = null;

            } else {

                message = Translator.print("You have new message");
                defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                    PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(context.getResources().getString(R.string.app_name))
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);


            if (defaultSoundUri != null)
                notificationBuilder.setSound(defaultSoundUri);


            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            try {
                if (SessionsController.isLogged()) {

                    MessengerHelper.updateInbox(messages);
                    notificationManager.notify(2002, notificationBuilder.build());

                }
            } catch (Exception e) {
                if (AppContext.DEBUG)
                    e.printStackTrace();
            }


        } catch (Exception e) {
            if (AppContext.DEBUG)
                e.printStackTrace();
        }

    }

    public static void pushUpcomingEvent(Context context, String title, String description) {
        Intent resultIntent = new Intent(context, EventsListActivity.class);
        resultIntent.putExtra("upcomingEventsRequest", true);

        Uri defaultSoundUri;

        defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setContentTitle(title)
                .setContentText(description)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);


        if (defaultSoundUri != null)
            notificationBuilder.setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(2001, notificationBuilder.build());

    }


    public static void createNotification(String ID, String title, Context context, String aMessage, boolean canPush) {
        NotificationManager notifManager = null;
        final int NOTIFY_ID = 0; // ID of notification
        String id = ID; // default_channel_id
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(aMessage)  // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(context.getString(R.string.chat_notification_title))  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {
            builder = new NotificationCompat.Builder(context);
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(aMessage)                           // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(context.getString(R.string.chat_notification_title))  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        if (canPush) {
            Notification notification = builder.build();
            notifManager.notify(NOTIFY_ID, notification);
        }
    }
}
