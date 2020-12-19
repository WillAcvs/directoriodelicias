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
import com.directoriodelicias.apps.delicias.activities.StoreDetailActivity;
import com.directoriodelicias.apps.delicias.activities.StoresListActivity;
import com.directoriodelicias.apps.delicias.adapter.lists.StoreListAdapter;
import com.directoriodelicias.apps.delicias.animation.Animation;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Store;
import com.directoriodelicias.apps.delicias.controllers.stores.StoreController;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.StoreParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.views.HorizontalView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.RealmList;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

public class StoreCustomView extends HorizontalView implements StoreListAdapter.ClickListener {

    private Context mContext;
    private StoreListAdapter adapter;
    private RecyclerView listView;
    private Map<String, Object> optionalParams;
    private ShimmerRecyclerView shimmerRecycler;
    private View storeContainer;


    public StoreCustomView(Context context) {
        super(context);
        mContext = context;
        setRecyclerViewAdapter();
    }

    public StoreCustomView(Context context, @Nullable AttributeSet attrs) {
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
        RealmList<Store> list = StoreController.list();
        if (!list.isEmpty()) {
            adapter.clear();
            if (!list.isEmpty()) {
                adapter.addAllItems(list);
                listView.setVisibility(VISIBLE);
                storeContainer.setVisibility(VISIBLE);
                shimmerRecycler.hideShimmerAdapter();
            } else {
                listView.setVisibility(GONE);
                storeContainer.setVisibility(GONE);

            }
            adapter.notifyDataSetChanged();
        } else {
            loadDataFromAPi();
        }

    }



   /* public List<Category> loadDataFromDB() {

        List<Category> results = new ArrayList<>();

        RealmList<Category> listCats = CategoryController.list();

        for (Category cat : listCats) {
            if (cat.getNumCat() > 0)
                results.add(cat);
        }


        return results;
    }
*/

    private void loadDataFromAPi() {
        RequestQueue queue = VolleySingleton.getInstance(mContext).getRequestQueue();

        GPStracker mGPS = new GPStracker(mContext);


        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_USER_GET_STORES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (APP_DEBUG) {
                        Log.e("responseStoresString", response);
                    }

                    JSONObject jsonObject = new JSONObject(response);

                    //Log.e("response",response);

                    final StoreParser mStoreParser = new StoreParser(jsonObject);
                    int success = Integer.parseInt(mStoreParser.getStringAttr(Tags.SUCCESS));
                    if (success == 1) {

                        RealmList<Store> list = mStoreParser.getStore();
                        adapter.removeAll();
                        for (int i = 0; i < list.size(); i++) {
                            Store sTr = list.get(i);
                            if (mGPS.getLatitude() == 0 && mGPS.getLongitude() == 0) {
                                sTr.setDistance((double) 0);
                            }
                            //if (list.get(i).getDistance() <=REQUEST_RANGE_RADIUS)
                            adapter.addItem(sTr);

                        }

                        //save data into database
                        if (list.size() > 0)
                            StoreController.insertStores(list);

                        if (adapter.getItemCount() > 0) {
                            storeContainer.setVisibility(VISIBLE);
                            listView.setVisibility(VISIBLE);
                        } else {
                            storeContainer.setVisibility(GONE);
                            listView.setVisibility(GONE);
                        }


                        adapter.notifyDataSetChanged();


                        String limit_param = optionalParams != null && optionalParams.containsKey("strLimit") ? String.valueOf(optionalParams.get("strLimit")) : "30";
                        int limit = Integer.parseInt(limit_param);

                        if (limit < mStoreParser.getIntArg(Tags.COUNT)) {
                            Animation.startZoomEffect(getChildAt(0).findViewById(R.id.card_show_more));
                        }
                    }

                } catch (JSONException e) {
                    //send a rapport to support
                    if (APP_DEBUG)
                        Log.e("ERROR", e.toString());

                }

                shimmerRecycler.hideShimmerAdapter();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (APP_DEBUG) {
                    Log.e("ERROR", error.toString());
                }
                shimmerRecycler.hideShimmerAdapter();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                if (mGPS.canGetLocation()) {
                    params.put("latitude", mGPS.getLatitude() + "");
                    params.put("longitude", mGPS.getLongitude() + "");
                }
                params.put("order_by", Constances.OrderByFilter.NEARBY);
                params.put("limit", String.valueOf(optionalParams.get("strLimit")));
                params.put("page", 1 + "");

                if (APP_DEBUG) {
                    Log.e("paramsStoresString", "  params getStores :" + params.toString());
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
        inflater.inflate(R.layout.v2_horizontal_list_store, this, true);

        //setup show more view
        storeContainer = getChildAt(0);

        //layout direction
        storeContainer.setLayoutDirection(AppController.isRTL() ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);

        //header setup
        if (optionalParams.containsKey("header") && optionalParams.get("header") != null)
            ((TextView) storeContainer.findViewById(R.id.card_title)).setText((String) optionalParams.get("header"));

        //setup show more view
        TextView showMore = storeContainer.findViewById(R.id.card_show_more);


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


        showMore.findViewById(R.id.card_show_more).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, StoresListActivity.class));

            }
        });


        //list item setup
        listView = getChildAt(0).findViewById(R.id.list);
        adapter = new StoreListAdapter(mContext, new ArrayList<>(), true, (Float) optionalParams.get("width"), (Float) optionalParams.get("height"));
        //start showLoading shimmer effect
        shimmerRecycler = getChildAt(0).findViewById(R.id.shimmer_view_container);

        if ((Boolean) optionalParams.get("loader")) {
            shimmerRecycler.setVisibility(VISIBLE);
        } else {
            shimmerRecycler.hideShimmerAdapter();
        }

        listView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        if (AppController.isRTL())
            mLayoutManager.setReverseLayout(true);

        listView.setLayoutManager(mLayoutManager);
        listView.setAdapter(adapter);
        adapter.setClickListener(this);


    }

    @Override
    public void itemClicked(View view, int position) {

        if (APP_DEBUG)
            Log.e("_1_store_id", String.valueOf(adapter.getItem(position).getId()));

        Intent intent = new Intent(mContext, StoreDetailActivity.class);
        intent.putExtra("id", adapter.getItem(position).getId());
        mContext.startActivity(intent);
    }


    private void setCustomAttribute(Context context, @Nullable AttributeSet attrs) {

        optionalParams = new HashMap<>();
        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.StoreCustomView, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            optionalParams.put("strLimit", a.getInteger(R.styleable.StoreCustomView_strLimit, 30));
            optionalParams.put("height", a.getDimension(R.styleable.StoreCustomView_storeItemHeight, 0));
            optionalParams.put("width", a.getDimension(R.styleable.StoreCustomView_storeItemWidth, 0));
            optionalParams.put("loader", a.getBoolean(R.styleable.StoreCustomView_strLoader, true));
            optionalParams.put("header", a.getString(R.styleable.StoreCustomView_strHeader));

        } finally {
            a.recycle();
        }
    }


}
