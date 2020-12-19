package com.directoriodelicias.apps.delicias.push_notification_firebase;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.directoriodelicias.apps.delicias.AppController;
import com.directoriodelicias.apps.delicias.GPS.GPStracker;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Guest;
import com.directoriodelicias.apps.delicias.controllers.sessions.GuestController;
import com.directoriodelicias.apps.delicias.dtmessenger.TokenInstance;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.GuestParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

/**
 * Created by idriss on 06/10/2016.
 */

public class FirebaseInstanceIDService extends FirebaseMessagingService {


    private static final String TAG = "FirebaseInstanceID";

    public static void reloadToken() {


        RequestQueue queue = VolleySingleton.getInstance(AppController.getInstance()).getRequestQueue();
        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_USER_REGISTER_TOKEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (APP_DEBUG)
                        Log.e("registerTokenResponse", response);

                    JSONObject js = new JSONObject(response);

                    GuestParser mGuestParser = new GuestParser(js);
                    int success = Integer.parseInt(mGuestParser.getStringAttr(Tags.SUCCESS));
                    if (success == 1) {

                        final List<Guest> list = mGuestParser.getData();

                        if (list.size() > 0) {

                            GuestController.clear();
                            GuestController.saveGuest(list.get(0));

                            (new android.os.Handler()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    refreshPositionGuest(list.get(0), AppController.getInstance());
                                }
                            }, 30000);
                        }

                    }

                } catch (JSONException e) {

                    if (APP_DEBUG)
                        e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (APP_DEBUG) {
                    Log.e("ERRORFitrbase", error.toString());
                }

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("fcm_id", FirebaseInstanceId.getInstance().getToken());
                params.put("sender_id", TokenInstance.getSenderID());
                params.put("platform", "android");
                params.put("mac_adr", ServiceHandler.getMacAddr());

                if (APP_DEBUG) {
                    Log.e(TAG, "TokenToSend" + FirebaseInstanceId.getInstance().getToken());
                    Log.e("reloadToken", params.toString());
                }

                return params;
            }

        };


        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }

    private static void refreshPositionGuest(final Guest mGuest, final Context context) {


        GPStracker gps = new GPStracker(context);
        if (mGuest != null && gps.canGetLocation()) {

            final int user_id = mGuest.getId();
            final double lat = gps.getLatitude();
            final double lng = gps.getLongitude();

            SimpleRequest request = new SimpleRequest(Request.Method.POST,
                    Constances.API.API_REFRESH_POSITION, new Response.Listener<String>() {
                @Override
                public void onResponse(final String response) {

                    if (APP_DEBUG)
                        Log.e("response", response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if (APP_DEBUG)
                        Log.e("ERROR", error.toString());

                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("guest_id", String.valueOf(user_id));
                    params.put("lat", String.valueOf(lat));
                    params.put("lng", String.valueOf(lng));
                    params.put("platform", "android");

                    if (APP_DEBUG)
                        Log.e("onRefreshSync", params.toString());

                    return params;
                }

            };


            request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            VolleySingleton.getInstance(context).getRequestQueue().add(request);

        }
    }

    @Override
    public void onNewToken(String s) {
        Log.e("NEW_TOKEN", s);
    }

}
