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
import com.directoriodelicias.apps.delicias.activities.EventDetailActivity;
import com.directoriodelicias.apps.delicias.activities.EventsListActivity;
import com.directoriodelicias.apps.delicias.adapter.lists.EventListAdapter;
import com.directoriodelicias.apps.delicias.animation.Animation;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Event;
import com.directoriodelicias.apps.delicias.controllers.events.EventController;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.EventParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.utils.Utils;
import com.directoriodelicias.apps.delicias.views.HorizontalView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.RealmList;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

public class EventCustomView extends HorizontalView implements EventListAdapter.ClickListener {

    private Context mContext;
    private EventListAdapter adapter;
    private RecyclerView listView;
    private Map<String, Object> optionalParams;
    private View mainContainer;
    private ShimmerRecyclerView shimmerRecycler;


    public EventCustomView(Context context) {
        super(context);
        mContext = context;
        setRecyclerViewAdapter();
    }

    public EventCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        setCustomAttribute(context, attrs);

        setRecyclerViewAdapter();
    }


    public void loadData(boolean fromDatabase) {

        shimmerRecycler.showShimmerAdapter();
        listView.setVisibility(GONE);

        //OFFLINE MODE
        if (ServiceHandler.isNetworkAvailable(mContext)) {
            if (!fromDatabase) loadDataFromAPi();
            else loadDataFromDB();
        } else {
            loadDataFromDB();
        }

    }

    public void loadDataFromDB() {
        //ensure the data exist on the database if not load it from api
        RealmList<Event> list = EventController.list();
        if (!list.isEmpty()) {
            adapter.removeAll();
            if (!list.isEmpty()) {
                adapter.addAllItems(list);
                listView.setVisibility(VISIBLE);
                shimmerRecycler.hideShimmerAdapter();
            } else {
                listView.setVisibility(GONE);
                shimmerRecycler.hideShimmerAdapter();
            }
            adapter.notifyDataSetChanged();
        } else {
            loadDataFromAPi();
        }

    }



    /*public List<Event> loadDataFromDB() {

        List<Event> results = new ArrayList<>();

        RealmList<Event> listEvents = EventController.list();

        for (Event cat : listEvents) {
            results.add(cat);
        }

        return results;
    }*/


    private void loadDataFromAPi() {
        RequestQueue queue = VolleySingleton.getInstance(mContext).getRequestQueue();

        final GPStracker mGPS = new GPStracker(mContext);


        String ids = "";


        final String finalIds = ids;
        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_USER_GET_EVENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonObject = new JSONObject(response);

                    if (APP_DEBUG)
                        Log.e("response", jsonObject.toString());

                    final EventParser mEventParser = new EventParser(jsonObject);


                    RealmList<Event> list = mEventParser.getEvents();
                    adapter.removeAll();
                    for (int i = 0; i < list.size(); i++) {

                        Event eV = list.get(i);
                        if (mGPS.getLatitude() == 0 && mGPS.getLongitude() == 0) {
                            eV.setDistance((double) 0);
                        }
                        //GET THE ID EVENT FROM EVENT LIKE IN DATABASE AND COMPARE IT WITH THIS ID
                        adapter.addItem(eV);
                               /* if (APP_DEBUG) {
                                    Log.e("EventParser", "id event " + list.get(i).getId() + "   description   " + list.get(i).getAddress());
                                }*/
                    }

                    //save data into database
                    if (list.size() > 0)
                        EventController.insertEvents(list);


                    //hide the custom event view when the there's no event on the adapter
                    if (adapter.getItemCount() == 0) {
                        mainContainer.setVisibility(GONE);
                    } else {
                        listView.setVisibility(VISIBLE);

                        mainContainer.setVisibility(VISIBLE);

                        shimmerRecycler.hideShimmerAdapter();
                    }

                    if (APP_DEBUG) {
                        Log.e("EventParserCount", adapter.getItemCount() + "");
                    }

                    int limit = Integer.parseInt(String.valueOf(optionalParams.get("evtLimit")));
                    if (limit < mEventParser.getIntArg(Tags.COUNT)) {
                        Animation.startZoomEffect(getChildAt(0).findViewById(R.id.card_show_more));
                    }


                } catch (JSONException e) {
                    //send a rapport to support
                    Log.e("ERROR", response);
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (APP_DEBUG)
                    if (APP_DEBUG) {
                        Log.e("ERROR", error.toString());
                    }

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                if (mGPS.canGetLocation()) {
                    params.put("latitude", mGPS.getLatitude() + "");
                    params.put("longitude", mGPS.getLongitude() + "");
                }

                params.put("token", Utils.getToken(getContext()));
                params.put("mac_adr", ServiceHandler.getMacAddr());
                params.put("limit", String.valueOf(optionalParams.get("evtLimit")));
                params.put("page", 1 + "");
                params.put("order_by", "by_date_desc");
                //params.put("date", DateUtils.getUTC("dd-MM-yyyy hh:mm"));


                if (APP_DEBUG) {
                    Log.e("ListEventFragment", "  params getEvent :" + params.toString());
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
        inflater.inflate(R.layout.v2_horizontal_list_events, this, true);

        //layout direction
        getChildAt(0).setLayoutDirection(AppController.isRTL() ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);

        //header setup
        if (optionalParams.containsKey("header") && optionalParams.get("header") != null)
            ((TextView) getChildAt(0).findViewById(R.id.card_title)).setText((String) optionalParams.get("header"));

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
                mContext.startActivity(new Intent(mContext, EventsListActivity.class));

            }
        });



        //list item setup
        mainContainer = getChildAt(0).findViewById(R.id.event_container);
        listView = getChildAt(0).findViewById(R.id.list);
        adapter = new EventListAdapter(mContext, new ArrayList<>(), true, (Float) optionalParams.get("width"), (Float) optionalParams.get("height"));
        listView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        //start showLoading shimmer effect
        shimmerRecycler = getChildAt(0).findViewById(R.id.shimmer_view_container);
        if ((Boolean) optionalParams.get("loader")) {
            shimmerRecycler.showShimmerAdapter();
        } else {
            shimmerRecycler.hideShimmerAdapter();
        }

        listView.setLayoutManager(mLayoutManager);
        listView.setAdapter(adapter);
        adapter.setClickListener(this);



    }

    @Override
    public void itemClicked(View view, int position) {

        if (APP_DEBUG)
            Log.e("_1_event_id", String.valueOf(adapter.getItem(position).getId()));

        Intent intent = new Intent(mContext, EventDetailActivity.class);
        intent.putExtra("id", adapter.getItem(position).getId());
        mContext.startActivity(intent);
    }


    private void setCustomAttribute(Context context, @Nullable AttributeSet attrs) {

        optionalParams = new HashMap<>();
        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.EventCustomView, 0, 0);


        try {
            //get the text and colors specified using the names in attrs.xml
            optionalParams.put("evtLimit", a.getInteger(R.styleable.EventCustomView_evtLimit, 30));
            optionalParams.put("height", a.getDimension(R.styleable.EventCustomView_eventItemHeight, 0));
            optionalParams.put("width", a.getDimension(R.styleable.EventCustomView_eventItemWidth, 0));
            optionalParams.put("loader", a.getBoolean(R.styleable.EventCustomView_evtLoader, true));
            optionalParams.put("header", a.getString(R.styleable.EventCustomView_evtyHeader));


        } finally {
            a.recycle();
        }
    }

}
