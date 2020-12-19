package com.directoriodelicias.apps.delicias.utils;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;
import com.directoriodelicias.apps.delicias.AppController;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Directorio on 6/26/2016.
 */
public class NotificationUtils {

    public static int NOTIFY_ID = 0;
    private Context mContext;
    private boolean multi = false;

    public static void sendNotification(Context context, String title, String messageBody, Bitmap icon, Class classToOpen, Map<String, String> dataToSend) {
        sendNotification(context, title, messageBody, icon, null, classToOpen, dataToSend);
    }

    public static void sendNotification(int notification_id, final Context context, final String title, String messageBody, Bitmap icon, final String bigImageUrl, Class classToOpen, Map<String, String> dataToSend) {

        Intent intent = new Intent(context, classToOpen);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (dataToSend != null)
            for (String key : dataToSend.keySet()) {
                intent.putExtra(key, dataToSend.get(key));
            }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        final NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        final NotificationCompat.Builder notificationBuilder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = "campaign_notif";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notificationManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(mChannel);
            }
            notificationBuilder = new NotificationCompat.Builder(context, id);

        } else {
            notificationBuilder = new NotificationCompat.Builder(context);
        }

        notificationBuilder.setAutoCancel(true);

        if (icon != null)
            notificationBuilder.setLargeIcon(icon);/*Notification icon image*/

        notificationBuilder.setSmallIcon(R.drawable.navigation_menu_background);
        notificationBuilder.setContentTitle(title);

        if (messageBody != null)
            notificationBuilder.setContentText(messageBody);


        if (AppConfig.NOTIFICATION_SOUND) {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder.setSound(defaultSoundUri);
            notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
        }

        notificationBuilder.setContentIntent(pendingIntent);

        if (bigImageUrl != null && !bigImageUrl.equals("")) {

            ImageRequest imageRequest = new ImageRequest(bigImageUrl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {

                    if (AppConfig.APP_DEBUG) {
                        // notificationBuilder.setContentTitle(title+" (with banner) "+response.getByteCount());
                        Log.e("_ImageRequest__", "ImageRequest " + bigImageUrl);
                    }
                    NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(response);
                    s.setSummaryText("");
                    notificationBuilder.setStyle(s);

                    notificationManager.notify(101 /* ID of notification */, notificationBuilder.build());

                }
            }, 0, 0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            VolleySingleton.getInstance(context).getRequestQueue().add(imageRequest);
            return;
        }

        notificationManager.notify(notification_id /* ID of notification */, notificationBuilder.build());

    }

    public static void sendNotification(final Context context, final String title, String messageBody, Bitmap icon, final String bigImageUrl, Class classToOpen, Map<String, String> dataToSend) {

        Intent intent = new Intent(context, classToOpen);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        for (String key : dataToSend.keySet()) {
            intent.putExtra(key, dataToSend.get(key));
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        final NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        final NotificationCompat.Builder notificationBuilder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = "campaign_notif";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notificationManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(mChannel);
            }
            notificationBuilder = new NotificationCompat.Builder(context, id);

        } else {
            notificationBuilder = new NotificationCompat.Builder(context);
        }

        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setLargeIcon(icon);/*Notification icon image*/
        notificationBuilder.setSmallIcon(R.drawable.navigation_menu_background);
        notificationBuilder.setContentTitle(title);

        if (messageBody != null)
            notificationBuilder.setContentText(messageBody);


        if (AppConfig.NOTIFICATION_SOUND) {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder.setSound(defaultSoundUri);
            notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
        }

        notificationBuilder.setContentIntent(pendingIntent);

        if (bigImageUrl != null && !bigImageUrl.equals("")) {

            ImageRequest imageRequest = new ImageRequest(bigImageUrl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {

                    if (AppConfig.APP_DEBUG) {
                        // notificationBuilder.setContentTitle(title+" (with banner) "+response.getByteCount());
                        Log.e("_ImageRequest__", "ImageRequest " + bigImageUrl);
                    }
                    NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(response);
                    s.setSummaryText("");
                    notificationBuilder.setStyle(s);

                    notificationManager.notify(101 /* ID of notification */, notificationBuilder.build());

                }
            }, 0, 0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            VolleySingleton.getInstance(context).getRequestQueue().add(imageRequest);
            return;
        }

        notificationManager.notify(101 /* ID of notification */, notificationBuilder.build());

    }

    public static void playMessageSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + AppController.getInstance().getApplicationContext().getPackageName() + "/raw/all_eyes_on_me");
            Ringtone r = RingtoneManager.getRingtone(AppController.getInstance().getApplicationContext(), alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    public static long getTimeMilliSec(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(timeStamp);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void showSmallNotification(String iconUrl, NotificationCompat.Builder mBuilder, int icon,
                                       String title, String message, String timeStamp,
                                       PendingIntent resultPendingIntent, Uri alarmSound) {

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        if (AppConfig.GCMConfig.appendNotificationMessages) {
            // store the notification in shared pref first
            AppController.getInstance().getPrefManager().addNotification(message);

            // get the notifications from shared preferences
            String oldNotification = AppController.getInstance().getPrefManager().getNotifications();

            List<String> messages = Arrays.asList(oldNotification.split("\\|"));

            for (int i = messages.size() - 1; i >= 0; i--) {
                inboxStyle.addLine(messages.get(i));
            }
        } else {
            inboxStyle.addLine(message);
        }


        Notification notification;
        notification = mBuilder.setTicker(title).setWhen(0)
                .setAutoCancel(true)
                // .setLargeIcon(iconBitmap)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                //.setSound(alarmSound)
                .setStyle(inboxStyle)
                .setWhen(getTimeMilliSec(timeStamp))
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setContentText(message)
                .build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);


        final RemoteViews contentView = notification.contentView;
        final int iconId = android.R.id.icon;

        if (iconUrl != null && !iconUrl.isEmpty()) {
            Log.e("images", "addToNotification");
            Glide.with(mContext)
                    .asBitmap()
                    .load(iconUrl)
                    .into(new NotificationTarget(
                            mContext,
                            iconId,
                            notification.bigContentView,
                            notification,
                            NOTIFY_ID));
        }

        if (multi == true) {
            notificationManager.notify(NOTIFY_ID, notification);
        } else {
            notificationManager.notify(NOTIFY_ID, notification);
        }
    }

    private void showBigNotification(String bigImageUrl, String iconUrl,
                                     NotificationCompat.Builder mBuilder, int icon, String title,
                                     String message, String timeStamp, PendingIntent resultPendingIntent,
                                     Uri alarmSound) {

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        //bigPictureStyle.bigPicture(bitmap);

        Notification notification;
        notification = mBuilder.setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                //.setSound(alarmSound)
                //.setLargeIcon(iconBitmap)
                .setStyle(bigPictureStyle)
                .setWhen(getTimeMilliSec(timeStamp))
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setContentText(message)
                .build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;


        final RemoteViews bigContentView = notification.bigContentView;
        int bigIconId = mContext.getResources().getIdentifier("android:id/big_picture", null, null);

        NotificationTarget target = new NotificationTarget(
                mContext,
                bigIconId,
                bigContentView,
                notification,
                NOTIFY_ID);

        if (currentapiVersion >= Build.VERSION_CODES.JELLY_BEAN) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {

                Glide.with(mContext)
                        .asBitmap()
                        .load(bigImageUrl)
                        .into(target);
            }

        }

        final RemoteViews contentView = notification.contentView;

        if (iconUrl != null && !iconUrl.isEmpty())

            Glide.with(mContext)
                    .asBitmap()
                    .load(iconUrl)
                    .into(target);


        if (multi == true) {
            notificationManager.notify(NOTIFY_ID, notification);
        } else {
            notificationManager.notify(NOTIFY_ID, notification);
        }


    }

}