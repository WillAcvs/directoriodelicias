package com.directoriodelicias.apps.delicias.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.directoriodelicias.apps.delicias.AppController;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.EventDetailActivity;
import com.directoriodelicias.apps.delicias.activities.OfferDetailActivity;
import com.directoriodelicias.apps.delicias.activities.StoreDetailActivity;
import com.directoriodelicias.apps.delicias.adapter.lists.BookmarkAdapter;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Bookmark;
import com.directoriodelicias.apps.delicias.controllers.BookmarkController;
import com.directoriodelicias.apps.delicias.controllers.ErrorsController;
import com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController;
import com.directoriodelicias.apps.delicias.load_manager.ViewManager;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.BookmarkParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.utils.CommunApiCalls;
import com.directoriodelicias.apps.delicias.utils.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import io.realm.RealmList;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends Fragment implements ViewManager.CustomView, SwipeRefreshLayout.OnRefreshListener, BookmarkAdapter.ClickListener {

    private RecyclerView recyclerView;
    private BookmarkAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewManager mViewManager;
    private Context context;
    //for scrolling params
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    private boolean loading = true;
    //pager
    private int COUNT = 0;
    private int REQUEST_PAGE = 1;


    private ShimmerRecyclerView shimmerRecycler;


    // newInstance constructor for creating fragment with arguments
    public static BookmarkFragment newInstance(int page, String title) {
        BookmarkFragment fragmentFirst = new BookmarkFragment();

        Bundle args = new Bundle();
        args.putInt("id", page);
        args.putString("title", title);
        fragmentFirst.setArguments(args);

        return fragmentFirst;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.v2_fragment_bookmarks, container, false);
        context = rootView.getContext();
        initSwipeRefresh(rootView);


        //initialize the shimmer : recyclerview loader
        shimmerRecycler = rootView.findViewById(R.id.shimmer_rv_bookmark);


        //init view manager
        initViewManager(rootView);


        initComponent(rootView);



        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();



    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume

            //sync data from server
            getBookMarks(REQUEST_PAGE);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    private void initSwipeRefresh(View view) {
        swipeRefreshLayout = view.findViewById(R.id.refresh);

        swipeRefreshLayout.setOnRefreshListener(this);


        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary
        );

    }


    private void initViewManager(View view) {
        mViewManager = new ViewManager(getContext());
        mViewManager.setLoadingView(view.findViewById(R.id.loading));
        mViewManager.setContentView(view.findViewById(R.id.container));
        mViewManager.setErrorView(view.findViewById(R.id.error));
        mViewManager.setEmptyView(view.findViewById(R.id.empty));
        mViewManager.setCustumizeView(this);
    }


    private void initComponent(View view) {

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        recyclerView = view.findViewById(R.id.list);
        mAdapter = new BookmarkAdapter(getContext(), new ArrayList());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutDirection(AppController.isRTL() ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);
        recyclerView.setNestedScrollingEnabled(false);

        //scroll to import data
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);


        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;

                        if (ServiceHandler.isNetworkAvailable(getContext())) {
                            if (COUNT > mAdapter.getItemCount())
                                getBookMarks(REQUEST_PAGE);
                        } else {
                            Toast.makeText(getContext(), "Network not available ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        //refreshing mode off
        swipeRefreshLayout.setRefreshing(false);

        mAdapter.setClickListener(this);
    }

    public void getBookMarks(final int page) {


        mViewManager.showContent();
        if (page == 1) {
            shimmerRecycler.showShimmerAdapter();
        } else {
            swipeRefreshLayout.setRefreshing(true);
        }

        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_GET_BOOKMARKS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (AppConfig.APP_DEBUG) {
                        Log.e("BookmarkResponse", response);
                    }

                    JSONObject jsonObject = new JSONObject(response);


                    final BookmarkParser mBookmarkParser = new BookmarkParser(jsonObject);
                    // List<Store> list = mStoreParser.getEventFromDB();
                    COUNT = 0;
                    COUNT = mBookmarkParser.getIntArg(Tags.COUNT);

                    //check server permission and display the errors
                    if (mBookmarkParser.getSuccess() == -1) {
                        ErrorsController.serverPermissionError(getActivity());
                    }

                    RealmList<Bookmark> list = mBookmarkParser.getBookmarks(context);
                    if (page == 1) mAdapter.removeAll();

                    mAdapter.addAll(list);

                    //update database
                    if (list.size() > 0)
                        BookmarkController.insertBookmarks(list);

                    loading = true;


                    if (COUNT > mAdapter.getItemCount())
                        REQUEST_PAGE++;
                    if (COUNT == 0 || mAdapter.getItemCount() == 0) {
                        mViewManager.showEmpty();
                    } else {
                        mViewManager.showContent();
                    }


                    if (APP_DEBUG) {
                        Log.e("count ", COUNT + " page = " + page);
                    }


                    //get user notification from the api
                    CommunApiCalls.getNotificationsCount(getContext());


                } catch (JSONException e) {
                    //send a rapport to support
                    if (APP_DEBUG)
                        e.printStackTrace();

                    if (mAdapter.getItemCount() == 0)
                        mViewManager.showError();

                }

                shimmerRecycler.hideShimmerAdapter();

                swipeRefreshLayout.setRefreshing(false);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (AppConfig.APP_DEBUG) {
                    Log.e("ERROR", error.toString());

                }

                swipeRefreshLayout.setRefreshing(false);

                shimmerRecycler.hideShimmerAdapter();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                //params.put("auth_type", "user");
                //params.put("auth_id", String.valueOf(finalUser_id));

                if (SessionsController.getLocalDatabase.isLogged()) {
                    params.put("user_id", String.valueOf(SessionsController.getLocalDatabase.getUserId()));
                } else {
                    params.put("user_id", String.valueOf(9999999));
                }
                params.put("limit", "30");
                params.put("page", page + "");
                params.put("timezone", TimeZone.getDefault().getID());
                params.put("date", DateUtils.getUTC("yyyy-MM-dd H:m:s"));

                if (AppConfig.APP_DEBUG) {
                    Log.e("bookmarksApiCall", " paramss :" + params.toString());
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

        if (ServiceHandler.isNetworkAvailable(getActivity())) {
            REQUEST_PAGE = 1;
            getBookMarks(REQUEST_PAGE);
        } else {
            Toast.makeText(getActivity(), "Network not available ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void customErrorView(View v) {
        Button btn = v.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getBookMarks(REQUEST_PAGE);
            }
        });
    }

    @Override
    public void customLoadingView(View v) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void customEmptyView(View v) {

        Button btn = (Button) v.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                REQUEST_PAGE = 1;
                getBookMarks(REQUEST_PAGE);
            }
        });
    }

    @Override
    public void onItemClick(View view, int pos) {
        Bookmark obj = mAdapter.getItems().get(pos);
        if (mAdapter.getItems() != null) {

            if (AppConfig.APP_DEBUG)
                Log.i("Bookmark_ModuleID", String.valueOf(obj.getModule_id()));

            handleClickBookmark(obj);

        }
    }

    private void handleClickBookmark(Bookmark obj) {
        if (obj != null) {
            switch (obj.getModule()) {
                case "store":
                    Intent intentStore = new Intent(getContext(), StoreDetailActivity.class);
                    intentStore.putExtra("id", obj.getModule_id());
                    getContext().startActivity(intentStore);
                    break;
                case "offer":
                    Intent intentOffer = new Intent(getContext(), OfferDetailActivity.class);
                    intentOffer.putExtra("id", obj.getModule_id());
                    getContext().startActivity(intentOffer);
                    break;
                case "event":
                    Intent intentEvent = new Intent(getContext(), EventDetailActivity.class);
                    intentEvent.putExtra("id", obj.getModule_id());
                    getContext().startActivity(intentEvent);
                    break;

            }
        }
    }

    @Override
    public void onMoreButtonClick(View view, int position) {

        Bookmark obj = mAdapter.getItems().get(position);


        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (R.id.action_archive == item.getItemId()) {
                    // todo : bookrmark to remove item
                }
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_notification_more);
        popupMenu.getMenu().findItem(R.id.action_archive).setTitle(R.string.remove_from_bookmark);
        popupMenu.show();

    }
}
