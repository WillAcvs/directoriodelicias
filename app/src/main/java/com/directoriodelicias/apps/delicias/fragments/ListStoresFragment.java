package com.directoriodelicias.apps.delicias.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
import com.directoriodelicias.apps.delicias.activities.StoreDetailActivity;
import com.directoriodelicias.apps.delicias.adapter.lists.StoreListAdapter;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Category;
import com.directoriodelicias.apps.delicias.classes.Store;
import com.directoriodelicias.apps.delicias.controllers.ErrorsController;
import com.directoriodelicias.apps.delicias.controllers.stores.StoreController;
import com.directoriodelicias.apps.delicias.load_manager.ViewManager;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.StoreParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

public class ListStoresFragment extends Fragment
        implements StoreListAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener, ViewManager.CustomView {

    public ViewManager mViewManager;
    //showLoading
    public SwipeRefreshLayout swipeRefreshLayout;
    //for scrolling params
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    private int listType = 1;
    private RecyclerView list;
    private StoreListAdapter adapter;
    //init request http
    private RequestQueue queue;
    private boolean loading = true;
    //pager
    private int COUNT = 0;
    private int REQUEST_PAGE = 1;
    private Category mCat;
    private GPStracker mGPS;
    private List<Store> listStores = new ArrayList<>();
    private ShimmerRecyclerView shimmerRecycler;


    private int REQUEST_RANGE_RADIUS = -1;
    private String REQUEST_SEARCH = "";
    private int Fav = 0;

    private HashMap<String, Object> searchParams;
    private int owner_id = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        REQUEST_RANGE_RADIUS = Integer.parseInt(getResources().getString(R.string.distance_max_display_route));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_menu, menu);
        //updateBadge(menu);

    }

    /*private void updateBadge(Menu menu) {
        if (menu != null) {
            if (MessengerHelper.NbrMessagesManager.getNbrTotalMessages() > 0) {
                ActionItemBadge.update(getActivity(), menu.findItem(R.id.item_samplebadge),
                        CommunityMaterial.Icon.cmd_bell_ring_outline,
                        ActionItemBadge.BadgeStyles.RED,
                        MessengerHelper.NbrMessagesManager.getNbrTotalMessages());
            } else {
                ActionItemBadge.hide(menu.findItem(R.id.item_samplebadge));
            }
        }
    }*/

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //updateBadge(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_store_list, container, false);


        //initialize the shimmer : recyclerview loader
        shimmerRecycler = rootView.findViewById(R.id.shimmer_rv_stores);


        try {

            owner_id = getArguments().getInt("user_id");

            int CatId = getArguments().getInt("category");
            mCat = Realm.getDefaultInstance().where(Category.class).equalTo("numCat", CatId).findFirst();


            if (getArguments().containsKey("searchParams")) {
                searchParams = (HashMap<String, Object>) getArguments().getSerializable("searchParams");
            }

            //load from assets
           /*if(!ServiceHandler.isNetworkAvailable(getActivity())){

               if(Constances.ENABLE_OFFLINE)
                listStores = loader.parseStoresFromAssets(getActivity(),mCat.getNumCat());
           }*/

        } catch (Exception e) {

            e.printStackTrace();
        }

        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        mViewManager = new ViewManager(getActivity());
        mViewManager.setLoadingView(rootView.findViewById(R.id.loading));
        mViewManager.setContentView(rootView.findViewById(R.id.content_my_store));
        mViewManager.setErrorView(rootView.findViewById(R.id.error));
        mViewManager.setEmptyView(rootView.findViewById(R.id.empty));
        mViewManager.setCustumizeView(this);

        list = rootView.findViewById(R.id.list);

        adapter = new StoreListAdapter(getActivity(), listStores, false);
        adapter.setClickListener(this);


        list.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        //listcats.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        list.setItemAnimator(new DefaultItemAnimator());
        list.setLayoutManager(mLayoutManager);
        list.setAdapter(adapter);

//
        list.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();


                if (loading) {

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;

                        if (ServiceHandler.isNetworkAvailable(getContext())) {
                            if (COUNT > adapter.getItemCount())
                                getStores(REQUEST_PAGE);
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
            getStores(REQUEST_PAGE);
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

        Store store = adapter.getItem(position);

        if (store != null) {


            if (APP_DEBUG)
                Log.e("_1_store_id", String.valueOf(store.getId()));

            Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
            intent.putExtra("id", store.getId());
            startActivity(intent);
        }

    }

    public void getStores(final int page) {

        mGPS = new GPStracker(getActivity());

        mViewManager.showContent();
        if (page == 1) {
            shimmerRecycler.showShimmerAdapter();
        } else {
            swipeRefreshLayout.setRefreshing(true);
        }

        //IF ther's no category in the db then go to the home page ( 0 )
        final int numCat = mCat != null ? mCat.getNumCat() : Constances.initConfig.Tabs.HOME;

        final String strIds = StoreController.getSavedStoresAsString();

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
                    // List<Store> list = mStoreParser.getEventFromDB();
                    COUNT = 0;
                    COUNT = mStoreParser.getIntArg(Tags.COUNT);

                    //check server permission and display the errors
                    if (mStoreParser.getSuccess() == -1) {
                        ErrorsController.serverPermissionError(getActivity());
                    }

                    RealmList<Store> list = mStoreParser.getStore();
                    if (page == 1) adapter.removeAll();

                    for (int i = 0; i < list.size(); i++) {
                        Store sTr = list.get(i);
                        if (mGPS.getLatitude() == 0 && mGPS.getLongitude() == 0) {
                            sTr.setDistance((double) 0);
                        }
                        //if (list.get(i).getDistance() <=REQUEST_RANGE_RADIUS)
                        adapter.addItem(sTr);

                    }

                    if (list.size() > 0) {
                        StoreController.insertStores(list);
                    }

                    loading = true;


                    if (COUNT > adapter.getItemCount())
                        REQUEST_PAGE++;

                    if (COUNT == 0 || adapter.getItemCount() == 0) {
                        mViewManager.showEmpty();
                        //hide shimmer RV
                        shimmerRecycler.hideShimmerAdapter();
                        swipeRefreshLayout.setRefreshing(false);
                    } else {
                        mViewManager.showContent();
                    }


                    if (APP_DEBUG) {
                        Log.e("count ", COUNT + " page = " + page);
                    }


                } catch (JSONException e) {
                    //send a rapport to support
                    if (APP_DEBUG)
                        e.printStackTrace();

                    if (adapter.getItemCount() == 0)
                        mViewManager.showError();


                }

                swipeRefreshLayout.setRefreshing(false);

                shimmerRecycler.hideShimmerAdapter();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (APP_DEBUG) {
                    Log.e("ERROR", error.toString());
                }

                swipeRefreshLayout.setRefreshing(false);

                shimmerRecycler.hideShimmerAdapter();

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

                    if (searchParams.containsKey("openingStatus") && searchParams.get("openingStatus") != null) {
                        int openingStatus = (Integer) searchParams.get("openingStatus");
                        if (openingStatus == 1)
                            params.put("opening_time", String.valueOf(openingStatus));
                    }
                    if (searchParams.containsKey("order_by") && searchParams.get("order_by") != null) {
                        params.put("order_by", (String) searchParams.get("order_by"));
                    } else {
                        //set default order filter by distance
                        params.put("order_by", Constances.OrderByFilter.NEARBY);
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
                    if (REQUEST_RANGE_RADIUS > -1) {
                        if (REQUEST_RANGE_RADIUS <= 99)
                            params.put("radius", String.valueOf((REQUEST_RANGE_RADIUS * 1000)));
                    }
                    params.put("search", REQUEST_SEARCH);
                    params.put("order_by", Constances.OrderByFilter.NEARBY);

                    if (Fav == -1) {
                        if (!strIds.equals(""))
                            params.put("store_ids", strIds);
                        else {
                            params.put("store_ids", "0");
                        }
                    } else {
                        if (numCat == Constances.initConfig.Tabs.BOOKMAKRS) {

                            if (!strIds.equals(""))
                                params.put("store_ids", strIds);
                            else {
                                params.put("store_ids", "0");
                            }
                        }
                    }


                }


                params.put("limit", "30");

                if (owner_id > 0)
                    params.put("user_id", String.valueOf(owner_id));

                params.put("page", page + "");

                if (APP_DEBUG) {
                    Log.e("ListStoreFragment", "  params getStores :" + params.toString());
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
            getStores(1);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            mGPS.showSettingsAlert();
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {
            //Toast.makeText(getActivity(), "  is Liked  :"+args.get("isLiked"), Toast.LENGTH_LONG).show();
            Fav = args.getInt("fav");

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

                getStores(1);
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
                REQUEST_PAGE = 1;
                getStores(REQUEST_PAGE);

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();


    }

}
