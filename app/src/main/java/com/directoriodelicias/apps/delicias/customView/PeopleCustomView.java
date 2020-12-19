package com.directoriodelicias.apps.delicias.customView;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.directoriodelicias.apps.delicias.AppController;
import com.directoriodelicias.apps.delicias.GPS.GPStracker;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.CustomSearchActivity;
import com.directoriodelicias.apps.delicias.activities.MessengerActivity;
import com.directoriodelicias.apps.delicias.activities.PeopleListActivity;
import com.directoriodelicias.apps.delicias.adapter.lists.ListUsersAdapter;
import com.directoriodelicias.apps.delicias.animation.Animation;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Discussion;
import com.directoriodelicias.apps.delicias.classes.User;
import com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController;
import com.directoriodelicias.apps.delicias.controllers.users.UserController;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.UserParser;
import com.directoriodelicias.apps.delicias.utils.Utils;
import com.directoriodelicias.apps.delicias.views.HorizontalView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.RealmList;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

public class PeopleCustomView extends HorizontalView implements ListUsersAdapter.ClickListener {

    private Context mContext;
    private ListUsersAdapter adapter;
    private RecyclerView listView;
    private Map<String, Object> optionalParams;
    private View mainContainer;
    private ShimmerRecyclerView shimmerRecycler;


    public PeopleCustomView(Context context) {
        super(context);
        mContext = context;
        setRecyclerViewAdapter();
    }

    public PeopleCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        setCustomAttribute(context, attrs);

        setRecyclerViewAdapter();
    }


    public void loadData(boolean fromDatabase) {

        shimmerRecycler.showShimmerAdapter();

        listView.setVisibility(GONE);

        if (!fromDatabase) loadDataFromAPi();
    }


    private void loadDataFromAPi() {

        //hide people layout container when the user is not logged
        /*if (!SessionsController.isLogged()) {
            mainContainer.setVisibility(GONE);
            return;
        } else {
            mainContainer.setVisibility(VISIBLE);
        }*/


        RequestQueue queue = VolleySingleton.getInstance(mContext).getRequestQueue();

        final GPStracker mGPS = new GPStracker(mContext);


        //final int user_id = mUserSession.getId();

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_GET_USERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (APP_DEBUG) {
                        Log.e("responseUsersString", response);
                    }

                    JSONObject jsonObject = new JSONObject(response);
                    if (APP_DEBUG) {
                        Log.e("response", jsonObject.toString());
                    }

                    //Log.e("response",response);

                    final UserParser mUsersParser = new UserParser(jsonObject);

                    RealmList<User> list = mUsersParser.getUser();

                    if (list.size() > 0) {
                        UserController.insertUsers(list);
                    }
                    adapter.removeAll();

                    for (int i = 0; i < list.size(); i++) {

                        adapter.addItem(list.get(i));
                    }

                    if (adapter.getItemCount() > 0) {
                        listView.setVisibility(VISIBLE);
                        mainContainer.setVisibility(VISIBLE);
                        shimmerRecycler.hideShimmerAdapter();
                    } else {
                        mainContainer.setVisibility(GONE);
                    }


                    if (APP_DEBUG) {
                        Log.e("count ", list.size() + " page = " + 1);
                    }

                    Animation.startZoomEffect(getChildAt(0).findViewById(R.id.card_show_more));

                } catch (JSONException e) {
                    //send a rapport to support
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (APP_DEBUG) {
                    Log.e("ERROR", error.toString());
                }

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                if (mGPS.canGetLocation()) {
                    params.put("lat", mGPS.getLatitude() + "");
                    params.put("lng", mGPS.getLongitude() + "");
                }

                params.put("token", Utils.getToken(mContext));
                params.put("mac_adr", ServiceHandler.getMacAddr());
                params.put("limit", "30");
                //params.put("user_id", String.valueOf(user_id));
                params.put("page", 1 + "");

                if (APP_DEBUG) {
                    Log.e("ListUsersFragment", "  params People  :" + params.toString());
                }

                return params;
            }

        };


        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }

    private void setRecyclerViewAdapter() {

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL);

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.v2_horizontal_list_users, this, true);

        mainContainer = getChildAt(0).findViewById(R.id.people_container);


        //layout direction
        getChildAt(0).setLayoutDirection(AppController.isRTL() ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);

        //header setup
        /*if (optionalParams.containsKey("header") && optionalParams.get("header") != null)
            ((TextView) getChildAt(0).findViewById(R.id.card_title)).setText((String) optionalParams.get("header"));*/

        //setup show more view
        TextView showMore = getChildAt(0).findViewById(R.id.card_show_more);

        Drawable arrowIcon = getResources().getDrawable(R.drawable.ic_arrow_forward_white_18dp);
        if (AppController.isRTL()) {
            arrowIcon = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_18);
        }

        DrawableCompat.setTint(
                DrawableCompat.wrap(arrowIcon),
                ContextCompat.getColor(mContext, R.color.colorPrimary)
        );

        if (!AppController.isRTL()) {
            showMore.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowIcon, null);
        } else {
            showMore.setCompoundDrawablesWithIntrinsicBounds(arrowIcon, null, null, null);
        }

        showMore.findViewById(R.id.card_show_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, PeopleListActivity.class));

            }
        });


        //list item setup
        listView = getChildAt(0).findViewById(R.id.list);

        //start showLoading shimmer effect
        shimmerRecycler = getChildAt(0).findViewById(R.id.shimmer_view_container);
        shimmerRecycler.showShimmerAdapter();


        adapter = new ListUsersAdapter(mContext, new ArrayList<>(), true);

        listView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        listView.setLayoutManager(mLayoutManager);
        listView.setAdapter(adapter);
        adapter.setClickListener(this);


    }


    private void setCustomAttribute(Context context, @Nullable AttributeSet attrs) {

        optionalParams = new HashMap<>();
        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.StoreCustomView, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            optionalParams.put("strLimit", a.getInteger(R.styleable.StoreCustomView_strLimit, 30));


        } finally {
            a.recycle();
        }
    }

    @Override
    public void itemClicked(int position) {

        if (SessionsController.isLogged()) {
            Intent intent = new Intent(mContext, MessengerActivity.class);
            intent.putExtra("userId", adapter.getItem(position).getId());
            intent.putExtra("type", Discussion.DISCUSION_WITH_USER);

            mContext.startActivity(intent);
        } else {
            mContext.startActivity(new Intent(mContext, CustomSearchActivity.LoginActivityV2.class));
        }


    }

    @Override
    public void itemOptionsClicked(View view, int position) {

    }
}
