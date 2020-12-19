package com.directoriodelicias.apps.delicias.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.GalleryActivity;
import com.directoriodelicias.apps.delicias.adapter.lists.ImagesListAdapter;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Images;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.ImagesParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;

/**
 * Created by Directorio on 11/12/2017.
 */

public class GalleryFragment extends Fragment implements ImagesListAdapter.ClickListener {

    private int int_id;
    private String type;
    private int parent_width = 0;
    private boolean short_mode = false;
    private LinearLayout emptyLayout;
    private LinearLayout loadingLayout;
    private RecyclerView listLayout;
    private List<Images> mGalleryList;
    private LinearLayoutManager mLayoutManager;
    private int PAGE = 1;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;
    private ImagesListAdapter adapter;
    private int count = 0;

    public void setShort_mode(boolean short_mode) {
        this.short_mode = short_mode;
    }

    public void setInt_id(int int_id) {
        this.int_id = int_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setParent_width(int parent_width) {
        this.parent_width = parent_width;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);


        emptyLayout = rootView.findViewById(R.id.emptyLayout);
        loadingLayout = rootView.findViewById(R.id.loadingLayout);
        listLayout = rootView.findViewById(R.id.list);
        try {

            int_id = getArguments().getInt("int_id", 0);
            type = getArguments().getString("type", "store");

        } catch (Exception e) {

            listLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);

            if (AppConfig.APP_DEBUG)
                e.printStackTrace();

            return rootView;
        }


        // List<Images> gallery = ReviewsController.findReviewyStoreId(store_id);
        this.mGalleryList = new ArrayList<>();
        //reloadReviews(rootView,mGalleryList);


        //do update from server
        getGallery();
        adapter = new ImagesListAdapter(getActivity(), new ArrayList<Images>(), false);
        adapter.setClickListener(this);

        if (parent_width > 0) {
            adapter.setParent_width(parent_width);
        }

        //listLayout.setHasFixedSize(true);
        //mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager = new GridLayoutManager(getActivity(), getActivity().getResources().getInteger(R.integer.nbr_gallery_cols));
        listLayout.setLayoutManager(mLayoutManager);
        listLayout.setItemAnimator(new DefaultItemAnimator());

        listLayout.setAdapter(adapter);


        if (short_mode == false)
            listLayout.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            if (ServiceHandler.isNetworkAvailable(getContext())) {
                                if (count > adapter.getItemCount())
                                    getGallery();
                            } else {
                                Toast.makeText(getContext(), "Network not available ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });


        return rootView;
    }

    private void reloadGalleryView(List<Images> gallery) {

        if (gallery.size() > 0) {


            int cols = getActivity().getResources().getInteger(R.integer.nbr_gallery_cols);
            int limit = cols * cols;


            listLayout.setVisibility(View.VISIBLE);
            loadingLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);

            /*GalleryAdapter
                    .newInstance(getActivity()).load(gallery)
                    .inflate(R.layout.item_custom_gallery.xml)
                    .setType(type)
                    .setId(int_id)
                    .into((LinearLayout) rootView.findViewById(R.id.container));
            */
            for (int i = 0; i < gallery.size(); i++) {

                //prepare see more button for short mode
                if (short_mode == true && (i == limit - 1)) {
                    if (limit < count) {
                        gallery.get(i).setUrlFull(null);
                        adapter.setRest(count - limit);
                    }
                }

                Images image = gallery.get(i);
                adapter.addItem(image);
                adapter.notifyDataSetChanged();


                //break on the limit for short mode
                int u = limit - 1;
                if (short_mode == true && i == u) {
                    break;
                }

            }

        } else {

            listLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);

        }


    }

    public void getGallery() {

        RequestQueue queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        if (mGalleryList.size() == 0) {
            listLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }


        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_GET_GALLERY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (AppConfig.APP_DEBUG)
                        Log.e("__Gallery", response);

                    JSONObject jsonObject = new JSONObject(response);
                    //Log.e("response",response);

                    final ImagesParser mImagesParser = new ImagesParser(jsonObject);
                    final RealmList<Images> list = mImagesParser.getGallery();

                    count = mImagesParser.getIntArg("count");


                    if (list.size() > 0) {

                        reloadGalleryView(list);

                        listLayout.setVisibility(View.VISIBLE);
                        loadingLayout.setVisibility(View.GONE);
                        emptyLayout.setVisibility(View.GONE);

                    } else {

                        listLayout.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);
                        emptyLayout.setVisibility(View.VISIBLE);

                    }


                    if (adapter.getItemCount() < count) {
                        PAGE++;
                    }

                    loading = true;

                } catch (JSONException e) {
                    //send a rapport to support
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (AppConfig.APP_DEBUG)
                    Log.e("ERROR", error.toString());


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                int cols = getActivity().getResources().getInteger(R.integer.nbr_gallery_cols);
                int limit = cols * cols;

                if (short_mode == false)
                    limit = limit * 2;

                params.put("int_id", int_id + "");
                params.put("type", type);
                params.put("limit", String.valueOf(limit));
                params.put("page", String.valueOf(PAGE));

                if (AppConfig.APP_DEBUG)
                    Log.e("listGalleryRequested", "" + params.toString());

                return params;
            }

        };


        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);


    }


    @Override
    public void itemClicked(View view, int position) {
        //test
        if (AppConfig.APP_DEBUG)
            Log.e("itemClicked", "pOsition:" + position);
        SlideshowDialogFragment.newInstance().show(getActivity(), adapter.getData(), position);

    }

    @Override
    public void seeMoreClicked(View view, int position) {
        if (AppConfig.APP_DEBUG)
            Log.e("itemClicked", "pOsition:" + position);

        Intent i = new Intent(getActivity(), GalleryActivity.class);
        i.putExtra("int_id", int_id);
        i.putExtra("type", type);
        startActivity(i);

    }

}
