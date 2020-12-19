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
import com.directoriodelicias.apps.delicias.activities.OfferDetailActivity;
import com.directoriodelicias.apps.delicias.activities.OffersListActivity;
import com.directoriodelicias.apps.delicias.adapter.lists.OfferListAdapter;
import com.directoriodelicias.apps.delicias.animation.Animation;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Offer;
import com.directoriodelicias.apps.delicias.controllers.stores.OffersController;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.OfferParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.utils.Utils;
import com.directoriodelicias.apps.delicias.views.HorizontalView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import io.realm.RealmList;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

public class OfferCustomView extends HorizontalView implements OfferListAdapter.ClickListener {

    private Context mContext;
    private OfferListAdapter adapter;
    private RecyclerView listView;
    private Map<String, Object> optionalParams;
    private ShimmerRecyclerView shimmerRecycler;
    private View mainContainer;


    public OfferCustomView(Context context) {
        super(context);
        mContext = context;
        setRecyclerViewAdapter();
    }

    public OfferCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;


        setCustomAttribute(context, attrs);

        setRecyclerViewAdapter();

    }


    public void loadData(boolean fromDatabase, Map<String, Object> optionalParams) {

        shimmerRecycler.showShimmerAdapter();
        listView.setVisibility(GONE);

        //OFFLINE MODE
        if (ServiceHandler.isNetworkAvailable(mContext)) {
            if (!fromDatabase) loadDataFromAPi(optionalParams);
            else loadDataFromDB(optionalParams);
        } else {
            loadDataFromDB(optionalParams);
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
    }*/

    public void loadDataFromDB(final Map<String, Object> optionalParams) {
        //ensure the data exist on the database if not load it from api
        RealmList<Offer> list = OffersController.list();
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
            loadDataFromAPi(optionalParams);
        }

    }


    private void loadDataFromAPi(Map<String, Object> _optionalParams) {
        RequestQueue queue = VolleySingleton.getInstance(mContext).getRequestQueue();

        GPStracker mGPS = new GPStracker(mContext);


        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_GET_OFFERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (APP_DEBUG) {
                        Log.e("responseOffersString", response);
                    }

                    JSONObject jsonObject = new JSONObject(response);


                    //Log.e("response",response);

                    final OfferParser mOfferParser = new OfferParser(jsonObject);


                    RealmList<Offer> list = mOfferParser.getOffers();

                    adapter.removeAll();
                    for (int i = 0; i < list.size(); i++) {
                        Offer ofr = list.get(i);
                        if (mGPS.getLongitude() == 0 && mGPS.getLatitude() == 0) {
                            ofr.setDistance((double) 0);
                        }
                        adapter.addItem(ofr);
                    }

                    //save data into database
                    if (list.size() > 0)
                        OffersController.insertOffers(list);


                    //hide the custom event view when the there's no event on the adapter
                    if (adapter.getItemCount() == 0) {
                        mainContainer.setVisibility(GONE);
                    } else {
                        mainContainer.setVisibility(VISIBLE);
                        shimmerRecycler.hideShimmerAdapter();
                        listView.setVisibility(VISIBLE);
                    }


                    String limit_param = optionalParams != null && optionalParams.containsKey("strLimit") ? String.valueOf(optionalParams.get("strLimit")) : "30";
                    int limit = Integer.parseInt(limit_param);

                    if (limit < mOfferParser.getIntArg(Tags.COUNT)) {
                        Animation.startZoomEffect(getChildAt(0).findViewById(R.id.card_show_more));
                    }


                } catch (JSONException e) {
                    //send a rapport to support
                    if (APP_DEBUG)
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
                    params.put("latitude", mGPS.getLatitude() + "");
                    params.put("longitude", mGPS.getLongitude() + "");
                }

                params.put("token", Utils.getToken(mContext));
                params.put("mac_adr", ServiceHandler.getMacAddr());


                if (_optionalParams.containsKey("store_id"))
                    params.put("store_id", String.valueOf(_optionalParams.get("store_id")));


                params.put("limit", String.valueOf(optionalParams.get("strLimit")));
                params.put("page", 1 + "");
                params.put("timezone", TimeZone.getDefault().getID());


                if (APP_DEBUG) {
                    Log.e("OfferCustomView", "  params getOffers :" + params.toString());
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
        inflater.inflate(R.layout.v2_horizontal_list_offers, this, true);

        mainContainer = getChildAt(0).findViewById(R.id.offer_container);

        //layout direction
        mainContainer.setLayoutDirection(AppController.isRTL() ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);

        //header setup
        if ((Boolean) optionalParams.get("displayHeader")) {
            mainContainer.findViewById(R.id.layoutOffersHeader).setVisibility(VISIBLE);
        } else {
            mainContainer.findViewById(R.id.layoutOffersHeader).setVisibility(GONE);
        }

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
                mContext.startActivity(new Intent(mContext, OffersListActivity.class));

            }
        });


        //start showLoading shimmerRecycler effect
        shimmerRecycler = getChildAt(0).findViewById(R.id.shimmer_view_container);

        if ((Boolean) optionalParams.get("loader")) {
            shimmerRecycler.showShimmerAdapter();
        } else {
            shimmerRecycler.hideShimmerAdapter();
        }


        listView = getChildAt(0).findViewById(R.id.list);
        adapter = new OfferListAdapter(mContext, new ArrayList<>(), true, (Float) optionalParams.get("width"), (Float) optionalParams.get("height"));
        listView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        listView.setLayoutManager(mLayoutManager);
        listView.setAdapter(adapter);
        adapter.setClickListener(this);

    }

    @Override
    public void itemClicked(View view, int position) {
        if (APP_DEBUG)
            Log.e("_1_offer_id", String.valueOf(adapter.getItem(position).getId()));

        Intent intent = new Intent(mContext, OfferDetailActivity.class);
        intent.putExtra("id", adapter.getItem(position).getId());
        mContext.startActivity(intent);
    }


    private void setCustomAttribute(Context context, @Nullable AttributeSet attrs) {

        optionalParams = new HashMap<>();
        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.OfferCustomView, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            optionalParams.put("strLimit", a.getInteger(R.styleable.OfferCustomView_ocvLimit, 30));
            optionalParams.put("displayHeader", a.getBoolean(R.styleable.OfferCustomView_ccDisplayHeader, true));
            optionalParams.put("height", a.getDimension(R.styleable.OfferCustomView_offerItemHeight, 0));
            optionalParams.put("width", a.getDimension(R.styleable.OfferCustomView_offerItemWidth, 0));
            optionalParams.put("loader", a.getBoolean(R.styleable.OfferCustomView_ocvLoader, true));
            optionalParams.put("header", a.getString(R.styleable.OfferCustomView_ocvHeader));

        } finally {
            a.recycle();
        }
    }

}
