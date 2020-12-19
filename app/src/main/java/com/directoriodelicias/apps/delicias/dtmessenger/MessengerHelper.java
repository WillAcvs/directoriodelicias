package com.directoriodelicias.apps.delicias.dtmessenger;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.directoriodelicias.apps.delicias.AppController;
import com.directoriodelicias.apps.delicias.Services.Pusher;
import com.directoriodelicias.apps.delicias.activities.MessengerActivity;
import com.directoriodelicias.apps.delicias.adapter.messenger.ListMessageAdapter;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.classes.Message;
import com.directoriodelicias.apps.delicias.events.UnseenMessagesEvent;
import com.directoriodelicias.apps.delicias.parser.api_parser.MessageParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.utils.NotificationUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Directorio on 12/21/2016.
 */

public class MessengerHelper {


    private static Timer TYPING_TIMER_LENGTH = new Timer();
    private static boolean soundNotified = false;

    public static void updateInbox(List<Message> messages) {

        if (messages.size() > 0) {
            for (int i = 0; i < messages.size(); i++) {
                updateInbox(messages.get(i));
            }
        }

    }

    public static void updateInbox(Message message) {

        if (message != null) {
            updateInbox(message.getSenderId(), message);
        }

    }

    public static void updateInbox(int key, List<Message> messages) {

        if (messages.size() > 0) {

            for (int i = 0; i < messages.size(); i++) {
                updateInbox(key, messages.get(i));
            }
        }

    }

    public static boolean messageExists(ListMessageAdapter adapter, Message message) {
        if (adapter.getItemCount() > 0 && message != null) {
            for (int i = 0; i < adapter.getItemCount(); i++) {

                if (adapter.getItem(i).getType() != Message.LOADING_VIEW)
                    if (adapter.getItem(i).getMessageid()
                            .equals(message.getMessageid())) {

                        return true;
                    }
            }
        }

        return false;
    }

    //update message from recyclerview
    public static void updateInbox(int key, Message message) {

        List<Message> list;

        if (message != null)
            if (MessengerActivity.listMessagesOnSaves.containsKey(key)) {

                list = MessengerActivity.listMessagesOnSaves.get(key);
                if (list.size() > 0) {

                    boolean isExist = false;
                    for (int i = 0; i < list.size(); i++) {

                        try {
                            if (list.get(i).getMessageid().equals(
                                    message.getMessageid())) {
                                isExist = true;
                            }
                        } catch (Exception e) {

                        }

                    }

                    if (isExist == false)
                        MessengerActivity.saveMessage(key, message);
                }
            }

    }

    //save instance of message
    public static Message pushMessageInsideUi(Pusher pusher, int user_id) {

        if (MessengerActivity.inbox_opend == true)
            return pushMessageInsideUi(pusher, user_id, false);
        else
            return pushMessageInsideUi(pusher, user_id, true);
    }

    public static void changeStateMessagerAdapter(ListMessageAdapter adapter, Message newMessage, String tempMessageId) {

        if (adapter != null && adapter.getItemCount() > 0) {

            if (AppConfig.APP_DEBUG)
                Log.e("onSearch", "Start");

            for (int i = (adapter.getItemCount() - 1); i >= 0; i--) {

                if (AppConfig.APP_DEBUG)
                    Log.e("onSearch", adapter.getItem(i).getMessageid() + "==" + tempMessageId);

                if (adapter.getItem(i).getType() != Message.LOADING_VIEW)

                    if (adapter.getItem(i)
                            .getMessageid()
                            .equals(tempMessageId)
                            && adapter.getItem(i)
                            .getStatus() == Message.NO_SENT) {


                        if (AppConfig.APP_DEBUG)
                            Log.e("onSearch", tempMessageId + " Found it in pos=" + i);

                        newMessage.setStatus(Message.SENT);
                        newMessage.setType(adapter.getItem(i).getType());
                        adapter.getData().set(i, newMessage);


                        //save it in cache
                        MessengerActivity.saveMessage(adapter.getItem(i).getReceiver_id(), newMessage);

                        adapter.notifyDataSetChanged();
                        break;
                    }
            }
            if (AppConfig.APP_DEBUG)
                Log.e("onSearch", "End");
        }

    }

    public static Message parshToObj(String data) {
        MessageParser mMessageParser = null;
        try {
            mMessageParser = new MessageParser(new JSONObject(data));
            int success = Integer.parseInt(mMessageParser.getStringAttr(Tags.SUCCESS));


            if (success == 1) {

                List<Message> list = mMessageParser.getMessages();

                final Message mesageData = list.get(0);
                return mesageData;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Message pushMessageInsideUi(Pusher pusher, int user_id, boolean sound) {

        if (pusher.getType() == Pusher.MESSAGE)
            try {

                MessageParser mMessageParser = new MessageParser(new JSONObject(pusher.getMessage()));
                int success = Integer.parseInt(mMessageParser.getStringAttr(Tags.SUCCESS));

                if (success == 1) {

                    List<Message> list = mMessageParser.getMessages();


                    if (list.size() > 0 && list.get(0).getReceiver_id() == user_id) {
                        final Message mesageData = list.get(0);


                        // MessengerHelper.NbrMessagesManager.upNbrDiscussion(mesageData.getDiscussionId());

                        int senderId = mesageData.getSenderId();
                        MessengerHelper.updateInbox(senderId, mesageData);

                        return mesageData;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        return null;

    }

    public static void playSound(boolean state) {

        if (TYPING_TIMER_LENGTH != null) TYPING_TIMER_LENGTH.cancel();

        TYPING_TIMER_LENGTH = new Timer();
        long DELAY = 8000;
        TYPING_TIMER_LENGTH.schedule(new TimerTask() {
            @Override
            public void run() {
                soundNotified = false;
            }
        }, DELAY);

        SharedPreferences sp = AppController.getInstance().
                getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean soundActive = sp.getBoolean("messenger_sound", true);
        if (soundActive == true && soundNotified == false) {

            NotificationUtils.playMessageSound();
            soundNotified = true;
        }


    }


    public static class NbrMessagesManager {

        private static HashMap<Integer, Integer> nbrMessages = new HashMap<>();
        private static int nbrTotalMessages = 0;
        private static int nbrTotalDiscussion = 0;


        public static int getNbrTotalMessages() {
            return nbrTotalMessages;
        }

        public static int getNbrTotalDiscussion() {
            return nbrTotalDiscussion;
        }


        public static void removeNbrDiscussion(int disId) {

            int newKey = newKey(disId);

            nbrMessages.put(newKey, 0);
            calculateTotal();

        }

        public static void upNbrDiscussion(int disId) {

            int newKey = newKey(disId);
            if (nbrMessages.containsKey(newKey)) {

                nbrMessages.put(newKey, (nbrMessages.get(newKey) + 1));

                if (AppConfig.APP_DEBUG) {
                    Log.e("calculateTotal-Dis-t", String.valueOf(disId));
                    Log.e("calculateTotal-Msg-t", String.valueOf(nbrMessages.get(newKey)));
                }


            } else {
                nbrMessages.put(newKey, 1);
            }


            calculateTotal();

        }

        private static int newKey(int key) {

//            String[] inverse = key.split("@");
//            int x = Integer.parseInt( inverse[0]);
//            int y = Integer.parseInt( inverse[1]);
//
//            String newKey = String.valueOf((x+y));
            return key;
        }

        private static void calculateTotal() {

            nbrTotalDiscussion = 0;
            nbrTotalMessages = 0;

            for (Integer key : nbrMessages.keySet()) {
                int newKey = newKey(key);
                if (nbrMessages.containsKey(newKey)) {
                    if (nbrMessages.get(newKey) > 0) {
                        nbrTotalDiscussion++;
                        nbrTotalMessages = nbrTotalMessages + nbrMessages.get(newKey);
                    }
                }
            }

            EventBus.getDefault().postSticky(new UnseenMessagesEvent(String.valueOf(nbrTotalMessages)));


        }

    }


}
