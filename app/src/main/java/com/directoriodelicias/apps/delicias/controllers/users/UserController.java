package com.directoriodelicias.apps.delicias.controllers.users;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.directoriodelicias.apps.delicias.AppController;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.CustomSearchActivity;
import com.directoriodelicias.apps.delicias.activities.MainActivity;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.User;
import com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.UserParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Directorio on 7/13/2017.
 */

public class UserController {

    private static int nbrOfCheck = 0;

    public static boolean insertUsers(final RealmList<User> list) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(list);
            }
        });
        return true;

    }

    public static void checkUserConnection(final FragmentActivity context) {

        (new android.os.Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUserWithThread(context);
            }
        }, 10000);

    }

    public static void checkUserWithThread(final FragmentActivity context) {

        if (nbrOfCheck > 0)
            return;

        if (SessionsController.isLogged()) {


            User user = SessionsController.getSession().getUser();
            final String email = user.getEmail();
            final String userid = String.valueOf(user.getId());
            final String username = user.getUsername();
            /*final String senderid = user.getSenderid();*/

            SimpleRequest request = new SimpleRequest(Request.Method.POST,
                    Constances.API.API_USER_CHECK_CONNECTION, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {

                        if (AppConfig.APP_DEBUG)
                            Log.e("___checkUser", response);


                        JSONObject jsonObject = new JSONObject(response);
                        final UserParser mUserParser = new UserParser(jsonObject);

                        if (mUserParser.getSuccess() == 0 || mUserParser.getSuccess() == -1) {

                            userLogoutAlert(context);
                            nbrOfCheck = 0;

                        } else {

                            nbrOfCheck++;

                        }

                    } catch (JSONException e) {
                        //send a rapport to support
                        e.printStackTrace();

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("email", email);
                    params.put("userid", userid);
                    params.put("username", username);
                    //params.put("senderid",senderid);

                    return params;

                }

            };

            request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            VolleySingleton.getInstance(AppController.getInstance())
                    .getRequestQueue().add(request);


        }

    }


    public static void userLogoutAlert(final FragmentActivity activity) {

        new android.app.AlertDialog.Builder(activity)
                .setTitle(activity.getString(R.string.Logout) + "!")
                .setMessage(activity.getString(R.string.logout_alert))
                .setPositiveButton(activity.getString(R.string.Login), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                        SessionsController.logOut();
                        ActivityCompat.finishAffinity(activity);
                        activity.startActivity(new Intent(activity, CustomSearchActivity.LoginActivityV2.class));

                    }
                })
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        SessionsController.logOut();
                        ActivityCompat.finishAffinity(activity);
                        activity.startActivity(new Intent(activity, MainActivity.class));

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }
}
