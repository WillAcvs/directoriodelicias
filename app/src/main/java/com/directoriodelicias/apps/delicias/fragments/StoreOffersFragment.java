package com.directoriodelicias.apps.delicias.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.OfferDetailActivity;
import com.directoriodelicias.apps.delicias.adapter.StoreOfferAdapter;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Offer;
import com.directoriodelicias.apps.delicias.controllers.stores.OffersController;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.OfferParser;
import com.directoriodelicias.apps.delicias.utils.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import io.realm.RealmList;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

/**
 * Created by Directorio on 11/12/2017.
 */

public class StoreOffersFragment extends Fragment {

    private int store_id;

    private LinearLayout emptyLayout;
    private LinearLayout loadingLayout;
    private LinearLayout containerLayout;
    private List<Offer> listOffers;
    private String current_date;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        current_date = DateUtils.getUTC("yyyy-MM-dd H:m:s");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_store_offers, container, false);


        emptyLayout = rootView.findViewById(R.id.emptyLayout);
        loadingLayout = rootView.findViewById(R.id.loadingLayout);
        containerLayout = rootView.findViewById(R.id.container);


        try {
            store_id = getArguments().getInt("store_id", 0);
        } catch (Exception e) {
            return rootView;
        }

        reloadOffers(rootView);

        //do update from server
        getOffers(rootView);


        return rootView;

    }


    private void reloadOffers(View rootView) {

        listOffers = OffersController.findOffersByStoreId(store_id);


        if (listOffers.size() > 0) {

            containerLayout.setVisibility(View.VISIBLE);
            loadingLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);

            StoreOfferAdapter
                    .newInstance(getActivity()).load(listOffers)
                    .inflate(R.layout.item_store_offer)
                    .into(rootView.findViewById(R.id.container)).setOnistener(new StoreOfferAdapter.Listener() {
                @Override
                public void onOfferClicked(int position) {

                    Intent intent = new Intent(getActivity(), OfferDetailActivity.class);
                    intent.putExtra("offer_id", listOffers.get(position).getId());
                    startActivity(intent);

                    if (APP_DEBUG)
                        Toast.makeText(getActivity(), "t" + position, Toast.LENGTH_LONG).show();
                }
            });

        } else {

            containerLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);

        }

    }


    public void getOffers(final View rootView) {

        RequestQueue queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();


        if (listOffers.size() == 0) {
            containerLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_GET_OFFERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (APP_DEBUG) {
                        Log.e("responseOffersString", response);
                    }


                    JSONObject jsonObject = new JSONObject(response);
                    final OfferParser mOfferParser = new OfferParser(jsonObject);

                    RealmList<Offer> list = mOfferParser.getOffers();
                    OffersController.deleteAllOffers(store_id);

                    if (list.size() > 0) {

                        OffersController.insertOffers(list);
                        reloadOffers(rootView);

                        containerLayout.setVisibility(View.VISIBLE);
                        loadingLayout.setVisibility(View.GONE);
                        emptyLayout.setVisibility(View.GONE);

                    } else {

                        containerLayout.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);
                        emptyLayout.setVisibility(View.VISIBLE);

                    }

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

                containerLayout.setVisibility(View.GONE);
                loadingLayout.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.VISIBLE);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("store_id", String.valueOf(store_id));
                params.put("limit", "7");
                params.put("date", current_date);
                params.put("timezone", TimeZone.getDefault().getID());

                return params;
            }

        };


        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);


    }


}
