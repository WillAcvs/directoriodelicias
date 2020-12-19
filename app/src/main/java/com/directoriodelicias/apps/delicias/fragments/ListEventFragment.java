package com.directoriodelicias.apps.delicias.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.directoriodelicias.apps.delicias.activities.EventDetailActivity;
import com.directoriodelicias.apps.delicias.adapter.lists.EventListAdapter;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Event;
import com.directoriodelicias.apps.delicias.controllers.ErrorsController;
import com.directoriodelicias.apps.delicias.controllers.events.EventController;
import com.directoriodelicias.apps.delicias.controllers.events.UpComingEventsController;
import com.directoriodelicias.apps.delicias.load_manager.ViewManager;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.EventParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import io.realm.Realm;
import io.realm.RealmList;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

public class ListEventFragment extends Fragment
        implements EventListAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener, ViewManager.CustomView {

    public static Menu mainMenu;
    public ViewManager mViewManager;
    //showLoading
    public SwipeRefreshLayout swipeRefreshLayout;
    private int listType = 1;
    //to check if the event is liked
    private int isLiked = 0;
    private RecyclerView list;
    private EventListAdapter adapter;
    //init request http
    private RequestQueue queue;
    //for scrolling params
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;
    private LinearLayoutManager mLayoutManager;
    //pager
    private int COUNT = 0;
    private GPStracker mGPS;
    private List<Event> listEvents = new ArrayList<>();
    private int REQUEST_PAGE = 1;
    private String REQUEST_SEARCH = "";
    private int REQUEST_RANGE_RADIUS = -1;
    private HashMap<String, Object> searchParams;
    private ShimmerRecyclerView shimmerRecycler;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

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
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        //updateBadge();
        MenuItem item = menu.findItem(R.id.search_icon);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        mainMenu = menu;
        //updateBadge();

    }


    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_list, container, false);


        //initialize the shimmer : recyclerview loader
        shimmerRecycler = rootView.findViewById(R.id.shimmer_rv_events);


        mGPS = new GPStracker(getActivity());

        if (!mGPS.canGetLocation() && listType == 1)
            mGPS.showSettingsAlert();


        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();


        mViewManager = new ViewManager(getActivity());
        mViewManager.setLoadingView(rootView.findViewById(R.id.loading));
        mViewManager.setContentView(rootView.findViewById(R.id.content_events));
        mViewManager.setErrorView(rootView.findViewById(R.id.error));
        mViewManager.setEmptyView(rootView.findViewById(R.id.empty));
        mViewManager.setCustumizeView(this);


        list = rootView.findViewById(R.id.list);


        adapter = new EventListAdapter(getActivity(), listEvents, false);
        adapter.setClickListener(this);


        list.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        //listcats.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        list.setItemAnimator(new DefaultItemAnimator());
        list.setLayoutManager(mLayoutManager);
        list.setAdapter(adapter);


        list.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;

                        if (COUNT > adapter.getItemCount())
                            getEvents(REQUEST_PAGE);
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


        //load
        loadEvents();

        return rootView;
    }

    @Override
    public void itemClicked(View view, int position) {

        Event event_c = adapter.getItem(position);

        if (APP_DEBUG)
            Log.e("_1_event_id", String.valueOf(adapter.getItem(position).getId()));

        if (event_c != null) {

            Intent intent = new Intent(getActivity(), EventDetailActivity.class);
            intent.putExtra("id", event_c.getId());
            startActivity(intent);

        }

    }


    private void loadEvents() {

        try {


            if (!getArguments().isEmpty()) {


                if (getArguments().containsKey("searchParams")) {
                    searchParams = (HashMap<String, Object>) getArguments().getSerializable("searchParams");
                    REQUEST_PAGE = 1;
                    getEvents(REQUEST_PAGE);

                }

                isLiked = getArguments().getInt("isLiked");
                if (isLiked == 1) {

                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            List<Event> myEvent = EventController.getLikeEventsAsArrayList();
                            adapter.removeAll();
                            for (int j = 0; j < myEvent.size(); j++) {
                                myEvent.get(j).setFeatured(0);
                                adapter.addItem(myEvent.get(j));
                            }
                        }
                    });


                    if (adapter.getItemCount() == 0) {
                        mViewManager.showEmpty();
                    }

                }

            } else {
                REQUEST_SEARCH = "";
                REQUEST_PAGE = 1;
                REQUEST_RANGE_RADIUS = -1;
                getEvents(REQUEST_PAGE);
            }


        } catch (Exception e) {

            REQUEST_SEARCH = "";
            REQUEST_PAGE = 1;
            REQUEST_RANGE_RADIUS = -1;
            getEvents(REQUEST_PAGE);
        }

    }

    public void getEvents(final int page) {

        mGPS = new GPStracker(getActivity());

        mViewManager.showContent();
        if (page == 1) {
            shimmerRecycler.showShimmerAdapter();
            shimmerRecycler.setVisibility(View.VISIBLE);
        } else {
            swipeRefreshLayout.setRefreshing(true);
        }




        String ids = "";

        if (isLiked == 2) {
            ids = UpComingEventsController.getListAsString();
        }

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
                    COUNT = mEventParser.getIntArg(Tags.COUNT);


                    //check server permission and display the errors
                    if (mEventParser.getSuccess() == -1) {
                        ErrorsController.serverPermissionError(getActivity());
                    }

                    if (page == 1) {

                        adapter.removeAll();

                        if (APP_DEBUG) {
                            Log.e("count", COUNT + "");
                        }

                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                mGPS = new GPStracker(getContext());
                                RealmList<Event> list = mEventParser.getEvents();

                                if (list.size() > 0) {
                                    EventController.insertEvents(list);
                                }

                                for (int i = 0; i < list.size(); i++) {


                                    Event eV = list.get(i);
                                    if (mGPS.getLatitude() == 0 && mGPS.getLongitude() == 0) {
                                        eV.setDistance((double) 0);
                                    }
                                    //GET THE ID EVENT FROM EVENT LIKE IN DATABASE AND COMPARE IT WITH THIS ID
                                    adapter.addItem(eV);
                                    if (APP_DEBUG) {
                                        Log.e("EventParser", "id event " + list.get(i).getId() + "   description   " + list.get(i).getAddress());
                                    }
                                }


                                if (APP_DEBUG) {
                                    Log.e("EventParserCount", adapter.getItemCount() + "");
                                }


                                loading = true;

                                if (COUNT > adapter.getItemCount())
                                    REQUEST_PAGE++;


                                if (COUNT == 0 || adapter.getItemCount() == 0) {
                                    mViewManager.showEmpty();
                                } else {
                                    mViewManager.showContent();
                                }

                                shimmerRecycler.hideShimmerAdapter();
                                shimmerRecycler.setVisibility(View.GONE);


                            }
                        }, 800);
                    } else {
                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                RealmList<Event> list = mEventParser.getEvents();

                                if (list.size() > 0) {
                                    EventController.insertEvents(list);
                                }

                                for (int i = 0; i < list.size(); i++) {
                                    Event eV = list.get(i);
                                    if (mGPS.getLatitude() == 0 && mGPS.getLongitude() == 0) {
                                        eV.setDistance((double) 0);
                                    }
                                    //GET THE ID EVENT FROM EVENT LIKE IN DATABASE AND COMPARE IT WITH THIS ID
                                    adapter.addItem(eV);
                                }
                                loading = true;

                                if (COUNT > adapter.getItemCount())
                                    REQUEST_PAGE++;

                                if (COUNT == 0 || adapter.getItemCount() == 0) {
                                    mViewManager.showEmpty();
                                }


                                shimmerRecycler.hideShimmerAdapter();
                                shimmerRecycler.setVisibility(View.GONE);


                            }
                        }, 800);

                    }


                } catch (JSONException e) {
                    //send a rapport to support
                    Log.e("ERROR", response);
                    e.printStackTrace();
                    mViewManager.showError();
                }


                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (APP_DEBUG)
                    if (APP_DEBUG) {
                        Log.e("ERROR", error.toString());
                    }


                shimmerRecycler.hideShimmerAdapter();

                swipeRefreshLayout.setRefreshing(false);

                mViewManager.showError();

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

                    if (searchParams.containsKey("date_begin") && searchParams.get("date_begin") != null) {
                        params.put("date_begin", (String) searchParams.get("date_begin"));
                        params.put("timezone", TimeZone.getDefault().getID());
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
                            params.put("longitude", String.valueOf(searchParams.get("longitude")));
                        }
                    }

                } else {
                    if (REQUEST_RANGE_RADIUS != -1) {
                        if (REQUEST_RANGE_RADIUS <= 99)
                            params.put("radius", String.valueOf((REQUEST_RANGE_RADIUS * 1000)));
                    }

                    params.put("order_by", Constances.OrderByFilter.RECENT);
                    params.put("search", REQUEST_SEARCH);


                    if (isLiked == 2) {
                        params.put("event_ids", finalIds);
                    }

                }

                params.put("page", page + "");
                params.put("token", Utils.getToken(getActivity()));
                params.put("mac_adr", ServiceHandler.getMacAddr());
                params.put("limit", "30");


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


    @Override
    public void onRefresh() {

        mGPS = new GPStracker(getContext());
        if (mGPS.canGetLocation()) {
            loadEvents();

        } else {
            mGPS.showSettingsAlert();
        }

        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onResume() {
        super.onResume();
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

                loadEvents();
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
                loadEvents();
            }
        });


    }

}
