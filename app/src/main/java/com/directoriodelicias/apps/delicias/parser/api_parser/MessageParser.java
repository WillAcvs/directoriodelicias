package com.directoriodelicias.apps.delicias.parser.api_parser;


import com.directoriodelicias.apps.delicias.classes.Message;
import com.directoriodelicias.apps.delicias.parser.Parser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmList;

/**
 * Created by Droideve on 1/12/2016.
 */
public class MessageParser extends Parser {

    public MessageParser(JSONObject json) {
        super(json);
    }

    public RealmList<Message> getMessages() throws Exception {

        RealmList<Message> list = new RealmList<Message>();

        try {

            JSONObject json_array = json.getJSONObject(Tags.RESULT);

            for (int i = 0; i < json_array.length(); i++) {

                try {

                    JSONObject json_user = json_array.getJSONObject(i + "");

                    Message mMessage = new Message();

                    mMessage.setMessageid(json_user.getString("id_message"));
                    mMessage.setDiscussionId(json_user.getInt("discussion_id"));
                    mMessage.setDate(json_user.getString("created_at"));
                    mMessage.setMessage(json_user.getString("content"));
                    mMessage.setStatus(json_user.getInt("status"));
                    //mMessage.setStatus(Message.NEW);
                    mMessage.setType(Message.RECEIVER_VIEW);

                    mMessage.setSenderId(json_user.getInt("sender_id"));
                    mMessage.setReceiver_id(json_user.getInt("receiver_id"));
                    list.add(mMessage);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }


}
