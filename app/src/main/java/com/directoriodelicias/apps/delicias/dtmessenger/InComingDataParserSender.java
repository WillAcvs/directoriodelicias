package com.directoriodelicias.apps.delicias.dtmessenger;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.Services.BusStation;
import com.directoriodelicias.apps.delicias.Services.Pusher;
import com.directoriodelicias.apps.delicias.activities.MainActivity;
import com.directoriodelicias.apps.delicias.activities.SplashActivity;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.classes.Message;
import com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController;
import com.directoriodelicias.apps.delicias.utils.NotificationUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Directorio on 1/26/2018.
 */

public class InComingDataParserSender {

    public static String TAG_NEED_OPEN_LIST_DISCUSSIONS = "need_open_list_discussions";


    /**
     * Returns true if the device is locked or screen turned off (in case password not set)
     */
    public static boolean isDeviceLocked(Context context) {
        boolean isLocked = false;

        // First we check the locked state
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean inKeyguardRestrictedInputMode = keyguardManager.inKeyguardRestrictedInputMode();

        if (inKeyguardRestrictedInputMode) {
            isLocked = true;

        } else {
            // If password is not set in the settings, the inKeyguardRestrictedInputMode() returns false,
            // so we need to check if screen on for this case

            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                isLocked = !powerManager.isInteractive();
            } else {
                //noinspection deprecation
                isLocked = !powerManager.isScreenOn();
            }
        }

        Log.d("Device locked", String.format("Now device is %s.", isLocked ? "locked" : "unlocked"));
        return isLocked;
    }


    public static void parseAndSend(final Context context, final JSONObject data) {

        if (!MainActivity.isAppInForeground(context)) {

            Intent intent = new Intent(context, SplashActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("chat", true);

            String message = "";

            if (AppConfig.APP_DEBUG) {
                Log.e("__Dis-t", String.valueOf(MessengerHelper.NbrMessagesManager.getNbrTotalDiscussion()));
                Log.e("__Msg-t", String.valueOf(MessengerHelper.NbrMessagesManager.getNbrTotalMessages()));
            }

            if (MessengerHelper.NbrMessagesManager.getNbrTotalDiscussion() > 0
                    && MessengerHelper.NbrMessagesManager.getNbrTotalMessages() > 1) {

                message = String.format(context.getString(R.string.youHaveDiscussions),
                        MessengerHelper.NbrMessagesManager.getNbrTotalDiscussion(),
                        MessengerHelper.NbrMessagesManager.getNbrTotalMessages());

                intent.putExtra(TAG_NEED_OPEN_LIST_DISCUSSIONS, true);

            } else {

                Message messageData = MessengerHelper.parshToObj(data.toString());
                //MANAGE NBR MESSAGES
                MessengerHelper.NbrMessagesManager.upNbrDiscussion(messageData.getDiscussionId());

                message = context.getString(R.string.youHaveMessage);
                //intent.putExtra(TAG_NEED_OPEN_INBOX,false);
                intent.putExtra(TAG_NEED_OPEN_LIST_DISCUSSIONS, true);

            }

            //check user login
            if (isDeviceLocked(context) && SessionsController.getLocalDatabase.isLogged()) {

                Map<String, String> _data = new HashMap<>();
                _data.put("chat", String.valueOf(true));

                NotificationUtils.sendNotification(1003, context, context.getString(R.string.app_name), message, null, null, MainActivity.class, _data);

            } else if (SessionsController.getLocalDatabase.isLogged()) {

                NotificationsManager.createNotification(
                        "notify_002",
                        "inComingMessageNotif",
                        context,
                        context.getString(R.string.youHaveMessage),
                        true
                );

            }

        } else {

            try {


                //get default realm
                //check user is connected

                if (AppConfig.APP_DEBUG)
                    Log.e("getLocalDatabase-1", String.valueOf(SessionsController.getLocalDatabase.getUserId()));

                if (SessionsController.getLocalDatabase.isLogged()) {

                    if (AppConfig.APP_DEBUG)
                        Log.e("onMessageReceived-1", data.toString());

                    Pusher pusher = new Pusher(Pusher.MESSAGE, data.toString());
                    Message message = MessengerHelper.pushMessageInsideUi(pusher, SessionsController.getLocalDatabase.getUserId());

                    MessengerHelper.NbrMessagesManager.upNbrDiscussion(message.getDiscussionId());

                    BusStation.getBus().post(message);

                    if (isDeviceLocked(context) && SessionsController.getLocalDatabase.isLogged()) {

                        NotificationsManager.createNotification(
                                "notify_002",
                                "inComingMessageNotif",
                                context,
                                context.getString(R.string.youHaveMessage),
                                true
                        );

                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


}

