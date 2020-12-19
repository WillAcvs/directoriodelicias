package com.directoriodelicias.apps.delicias.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.directoriodelicias.apps.delicias.GPS.GPStracker;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.OfferDetailActivity;
import com.directoriodelicias.apps.delicias.adapter.lists.OfferListAdapter;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Offer;
import com.directoriodelicias.apps.delicias.controllers.ErrorsController;
import com.directoriodelicias.apps.delicias.controllers.stores.OffersController;
import com.directoriodelicias.apps.delicias.load_manager.ViewManager;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.OfferParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.utils.DateUtils;
import com.directoriodelicias.apps.delicias.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import io.realm.RealmList;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;
import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.OFFERS_NUMBER_PER_ROW;

public class ListOffersFragment extends Fragment
        implements OfferListAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener, ViewManager.CustomView {

    public static Menu mainMenu;
    public ViewManager mViewManager;
    //showLoading
    public SwipeRefreshLayout swipeRefreshLayout;
    //for scrolling params
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int listType = 1;
    private RecyclerView list;
    private OfferListAdapter adapter;
    //init request http
    private RequestQueue queue;
    private boolean loading = true;
    //pager
    private int COUNT = 0;
    private int REQUEST_PAGE = 1;
    private GPStracker mGPS;
    private List<Offer> listStores = new ArrayList<>();
    private int REQUEST_RANGE_RADIUS = -1;
    private String REQUEST_SEARCH = "";
    private HashMap<String, Object> searchParams;
    private int store_id = 0;
    private ShimmerRecyclerView shimmerRecycler;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        REQUEST_RANGE_RADIUS = Integer.parseInt(getResources().getString(R.string.distance_max_display_route));

    }

    /*private void updateBadge() {
        if (MessengerHelper.NbrMessagesManager.getNbrTotalMessages() > 0) {
            ActionItemBadge.update(getActivity(), mainMenu.findItem(R.id.item_samplebadge),
                    CommunityMaterial.Icon.cmd_bell_ring_outline,
                    ActionItemBadge.BadgeStyles.RED,
                    MessengerHelper.NbrMessagesManager.getNbrTotalMessages());
        } else {
            ActionItemBadge.hide(mainMenu.findItem(R.id.item_samplebadge));
        }
    }*/

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.search_icon).setVisible(true);
        //updateBadge();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        mainMenu = menu;
        //updateBadge();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_offers_list, container, false);

        //initialize the shimmer : recyclerview loader
        shimmerRecycler = rootView.findViewById(R.id.shimmer_rv_offers);

        try {
            store_id = getArguments().getInt("store_id");
        } catch (Exception e) {
        }

        if (getArguments().containsKey("searchParams")) {
            searchParams = (HashMap<String, Object>) getArguments().getSerializable("searchParams");
            REQUEST_PAGE = 1;
        }


        mGPS = new GPStracker(getActivity());
        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        mViewManager = new ViewManager(getActivity());
        mViewManager.setLoadingView(rootView.findViewById(R.id.loading));
        mViewManager.setContentView(rootView.findViewById(R.id.content_my_store));
        mViewManager.setErrorView(rootView.findViewById(R.id.error));
        mViewManager.setEmptyView(rootView.findViewById(R.id.empty));
        mViewManager.setCustumizeView(this);


        list = rootView.findViewById(R.id.list);

        adapter = new OfferListAdapter(getActivity(), listStores, false);
        adapter.setClickListener(this);


        list.setHasFixedSize(true);
        /*mLayoutManager = new LinearLayoutManager(getActivity());
        //listcats.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);*/

        final RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), OFFERS_NUMBER_PER_ROW);
        list.setLayoutManager(mLayoutManager);

        list.setItemAnimator(new DefaultItemAnimator());
        list.setLayoutManager(mLayoutManager);
        list.setAdapter(adapter);

//
        list.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
                if (loading) {

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        if (ServiceHandler.isNetworkAvailable(getContext())) {
                            if (COUNT > adapter.getItemCount())
                                getOffers(REQUEST_PAGE);
                        } else {
                            Toast.makeText(getContext(), "Network not available ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        swipeRefreshLayout = rootView.findViewById(R.id.refresh);

        swipeRefreshLayout.setOnRefreshListener(this);


        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary
        );


        if (ServiceHandler.isNetworkAvailable(this.getActivity())) {
            getOffers(REQUEST_PAGE);

        } else {

            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), getString(R.string.check_network), Toast.LENGTH_LONG).show();
            if (adapter.getItemCount() == 0)
                mViewManager.showError();
        }

        return rootView;
    }

    @Override
    public void itemClicked(View view, int position) {

        Intent intent = new Intent(getActivity(), OfferDetailActivity.class);
        intent.putExtra("offer_id", adapter.getItem(position).getId());
        startActivity(intent);

    }

    public void getOffers(final int page) {

        mGPS = new GPStracker(getActivity());

        mViewManager.showContent();
        if (page == 1) {
            shimmerRecycler.showShimmerAdapter();
        } else {
            swipeRefreshLayout.setRefreshing(true);
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


                    //Log.e("response",response);

                    final OfferParser mOfferParser = new OfferParser(jsonObject);
                    // List<Store> list = mStoreParser.getEventFromDB();
                    COUNT = 0;
                    COUNT = mOfferParser.getIntArg(Tags.COUNT);
                    mViewManager.showContent();


                    //check server permission and display the errors
                    if (mOfferParser.getSuccess() == -1) {
                        ErrorsController.serverPermissionError(getActivity());
                    }

                    if (page == 1) {

                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                RealmList<Offer> list = mOfferParser.getOffers();

                                adapter.removeAll();
                                for (int i = 0; i < list.size(); i++) {
                                    Offer ofr = list.get(i);
                                    if (mGPS.getLongitude() == 0 && mGPS.getLatitude() == 0) {
                                        ofr.setDistance((double) 0);
                                    }
                                    // if (list.get(i).getDistance() <= REQUEST_RANGE_RADIUS)
                                    adapter.addItem(ofr);
                                }

                                OffersController.removeAll();
                                //set it into database
                                OffersController.insertOffers(list);

                                loading = true;

                                if (COUNT > adapter.getItemCount())
                                    REQUEST_PAGE++;
                                if (COUNT == 0 || adapter.getItemCount() == 0) {
                                    mViewManager.showEmpty();
                                }


                                if (APP_DEBUG) {
                                    Log.e("count ", COUNT + " page = " + page);
                                }

                                //hide shimmer RV
                                shimmerRecycler.hideShimmerAdapter();
                                swipeRefreshLayout.setRefreshing(false);

                            }
                        }, 800);
                    } else {
                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                RealmList<Offer> list = mOfferParser.getOffers();

                                for (int i = 0; i < list.size(); i++) {
                                    Offer ofr = list.get(i);
                                    if (mGPS.getLongitude() == 0 && mGPS.getLatitude() == 0) {
                                        ofr.setDistance((double) 0);
                                    }
                                    // if (list.get(i).getDistance() <= REQUEST_RANGE_RADIUS)
                                    adapter.addItem(ofr);
                                }


                                //set it into database
                                OffersController.insertOffers(list);

                                loading = true;
                                if (COUNT > adapter.getItemCount())
                                    REQUEST_PAGE++;

                                if (COUNT == 0 || adapter.getItemCount() == 0) {
                                    mViewManager.showEmpty();
                                } else {
                                    mViewManager.showContent();
                                }

                                //hide shimmer RV
                                shimmerRecycler.hideShimmerAdapter();
                                swipeRefreshLayout.setRefreshing(false);

                            }
                        }, 800);

                    }

                } catch (JSONException e) {
                    //send a rapport to support
                    if (APP_DEBUG)
                        e.printStackTrace();

                    if (adapter.getItemCount() == 0)
                        mViewManager.showError();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (APP_DEBUG) {
                    Log.e("ERROR", error.toString());
                }

                mViewManager.showError();

                //hide shimmer RV
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

                if (searchParams != null && !searchParams.isEmpty()) {
                    if (searchParams.containsKey("search") && searchParams.get("search") != null) {
                        params.put("search", (String) searchParams.get("search"));
                    }

                    if (searchParams.containsKey("category") && searchParams.get("category") != null) {
                        int category_id = (Integer) searchParams.get("category");
                        if (category_id > 0)
                            params.put("category_id", String.valueOf(category_id));
                    }

                    if (searchParams.containsKey("radius") && searchParams.get("radius") != null) {
                        params.put("radius", (String) searchParams.get("radius"));
                    }

                    if (searchParams.containsKey("order_by") && searchParams.get("order_by") != null) {
                        params.put("order_by", (String) searchParams.get("order_by"));
                    }

                    if (searchParams.containsKey("latitude") && searchParams.get("latitude") != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            params.replace("latitude", String.valueOf(searchParams.get("latitude")));
                        }
                    }

                    if (searchParams.containsKey("longitude") && searchParams.get("longitude") != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            params.put("longitude", String.valueOf(searchParams.get("latitude")));
                        }
                    }

                    if (searchParams.containsKey("offer_value") && searchParams.get("offer_value") != null) {
                        params.put("value_type", (String) searchParams.get("value_type"));
                        params.put("offer_value", (String) searchParams.get("offer_value"));
                    }

                } else {
                    params.put("search", REQUEST_SEARCH);
                    params.put("order_by", Constances.OrderByFilter.RECENT);

                    if (store_id > 0)
                        params.put("store_id", String.valueOf(store_id));

                    if (REQUEST_RANGE_RADIUS != -1) {
                        if (REQUEST_RANGE_RADIUS <= 99)
                            params.put("radius", String.valueOf((REQUEST_RANGE_RADIUS * 1000)));
                    }


                }


                params.put("date", DateUtils.getUTC("yyyy-MM-dd H:m:s"));
                params.put("timezone", TimeZone.getDefault().getID());

                params.put("token", Utils.getToken(getActivity()));
                params.put("mac_adr", ServiceHandler.getMacAddr());

                params.put("limit", "30");
                params.put("page", page + "");


                if (APP_DEBUG) {
                    Log.e("ListOffersFragment", "  params getOffers :" + params.toString());
                }


                return params;
            }

        };

        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }

    @Override
    public void onRefresh() {

        mGPS = new GPStracker(getActivity());
        if (mGPS.canGetLocation()) {

            REQUEST_SEARCH = "";
            REQUEST_PAGE = 1;
            REQUEST_RANGE_RADIUS = -1;
            getOffers(1);

        } else {
            swipeRefreshLayout.setRefreshing(false);
            mGPS.showSettingsAlert();

            if (adapter.getItemCount() == 0)
                mViewManager.showError();
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {
            //Toast.makeText(getActivity(), "  is Liked  :"+args.get("isLiked"), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void customErrorView(View v) {

        Button retry = v.findViewById(R.id.btn);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mGPS = new GPStracker(getActivity());

                if (!mGPS.canGetLocation() && listType == 1)
                    mGPS.showSettingsAlert();

                getOffers(1);
                REQUEST_PAGE = 1;
            }
        });

    }

    @Override
    public void customLoadingView(View v) {


    }

    @Override
    public void customEmptyView(View v) {

        Button btn = v.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOffers(1);
                REQUEST_PAGE = 1;
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
