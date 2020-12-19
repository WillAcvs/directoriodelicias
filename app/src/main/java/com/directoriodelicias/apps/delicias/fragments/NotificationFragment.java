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
import com.directoriodelicias.apps.delicias.Services.NotifyDataNotificationEvent;
import com.directoriodelicias.apps.delicias.activities.EventDetailActivity;
import com.directoriodelicias.apps.delicias.activities.OfferDetailActivity;
import com.directoriodelicias.apps.delicias.activities.StoreDetailActivity;
import com.directoriodelicias.apps.delicias.adapter.lists.NotificationAdapter;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Notification;
import com.directoriodelicias.apps.delicias.controllers.ErrorsController;
import com.directoriodelicias.apps.delicias.controllers.notification.NotificationController;
import com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController;
import com.directoriodelicias.apps.delicias.load_manager.ViewManager;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.NotificationParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.utils.CommunApiCalls;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.RealmList;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment implements ViewManager.CustomView, SwipeRefreshLayout.OnRefreshListener, NotificationAdapter.ClickListener {

    private RecyclerView recyclerView;
    private NotificationAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewManager mViewManager;
    //pager
    private int COUNT = 0;
    private int REQUEST_PAGE = 1;
    private Context context;
    private boolean loading = true;
    private ShimmerRecyclerView shimmerRecycler;
    //for scrolling params
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager mLayoutManager;


    // newInstance constructor for creating fragment with arguments
    public static NotificationFragment newInstance(int page, String title) {
        NotificationFragment fragmentFirst = new NotificationFragment();

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
        View rootView = inflater.inflate(R.layout.v2_fragment_notification, container, false);
        context = rootView.getContext();

        //initialize the shimmer : recyclerview loader
        shimmerRecycler = rootView.findViewById(R.id.shimmer_rv_notification);

        initSwipeRefresh(rootView);


        //init view manager
        initViewManager(rootView);


        initComponent(rootView);

        //sync data from server
        getNotifications(REQUEST_PAGE);

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
        mAdapter = new NotificationAdapter(getContext(), new ArrayList());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutDirection(AppController.isRTL() ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);
        recyclerView.setNestedScrollingEnabled(false);

        //set data and list adapter
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);


        //scroll to import data
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
                                getNotifications(REQUEST_PAGE);
                        } else {
                            Toast.makeText(getContext(), "Network not available ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        mAdapter.setClickListener(this);


    }

    private void getNotifications(final int page) {


        mViewManager.showContent();
        if (page == 1) {
            shimmerRecycler.showShimmerAdapter();
        } else {
            swipeRefreshLayout.setRefreshing(true);
        }

        Map<String, String> params = new HashMap<String, String>();

        if (SessionsController.getLocalDatabase.isLogged()) {
            params.put("user_id", String.valueOf(SessionsController.getLocalDatabase.getUserId()));
            params.put("guest_id", String.valueOf(SessionsController.getLocalDatabase.getGuestId()));
        } else {
            params.put("auth_type", "guest");
            params.put("auth_id", String.valueOf(99999));
        }

        params.put("page", page + "");
        params.put("limit", "30");

        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_NOTIFICATIONS_GET, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {

                try {

                    if (AppConfig.APP_DEBUG) {
                        Log.e("NotificationResponse", response);
                    }

                    JSONObject jsonObject = new JSONObject(response);
                    // Log.e("response", jsonObject.toString());
                    final NotificationParser mNotificationParser = new NotificationParser(jsonObject);
                    COUNT = 0;
                    COUNT = mNotificationParser.getIntArg(Tags.COUNT);


                    //check server permission and display the errors
                    if (mNotificationParser.getSuccess() == -1) {
                        ErrorsController.serverPermissionError(getActivity());
                    }

                    RealmList<Notification> list = mNotificationParser.getNotifications(context);

                    //remove all data
                    if (page == 1) mAdapter.removeAll();

                    mAdapter.addAll(list);

                    //update database
                    if (list.size() > 0)
                        NotificationController.insertNotifications(list);

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
                    CommunApiCalls.countUnseenNotifications(getContext());

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
                if (AppConfig.APP_DEBUG) {
                    Log.e("notificationFrag", "  params get notification :" + params.toString());
                }
                return params;
            }

        };

        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }

    private void archiveNotification(final int notification_id, final int position) {

        swipeRefreshLayout.setRefreshing(true);

        RequestQueue queue = VolleySingleton.getInstance(getContext()).getRequestQueue();

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_NOTIFICATIONS_REMOVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (AppConfig.APP_DEBUG) {
                        Log.i("archiveNotifResponse", response);
                    }

                    JSONObject jsonObject = new JSONObject(response);
                    // Log.i("response", jsonObject.toString());
                    final NotificationParser mNotificationParser = new NotificationParser(jsonObject);
                    int success = Integer.parseInt(mNotificationParser.getStringAttr(Tags.SUCCESS));

                    if (success == 1) {
                        onRefresh();
                        mViewManager.showLoading();
                        //mAdapter.removeItem(notification_id, position);
                    } else {
                        if (AppConfig.APP_DEBUG) {
                            Log.e("ERROR", mNotificationParser.getStringAttr(Tags.ERRORS));
                        }
                    }


                } catch (JSONException e) {
                    //send a rapport to support
                    e.printStackTrace();
                    mViewManager.showError();


                } finally {
                    swipeRefreshLayout.setRefreshing(false);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (AppConfig.APP_DEBUG) {
                    Log.e("ERROR", error.toString());
                }
                mViewManager.showError();


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", String.valueOf(notification_id));
                if (AppConfig.APP_DEBUG) {
                    Log.i("NotificationFragment", "  params remove notification :" + params.toString());
                }

                return params;
            }

        };


        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);


    }

    private void editStatusNotification(final int position, final int notification_id) {


        RequestQueue queue = VolleySingleton.getInstance(getContext()).getRequestQueue();

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_NOTIFICATIONS_EDIT_STATUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (AppConfig.APP_DEBUG) {
                        Log.i("archiveNotifResponse", response);
                    }

                    JSONObject jsonObject = new JSONObject(response);
                    final NotificationParser mNotificationParser = new NotificationParser(jsonObject);
                    int success = Integer.parseInt(mNotificationParser.getStringAttr(Tags.SUCCESS));


                    if (success == 1) {
                        Notification notif = mNotificationParser.getNotification(getContext());
                        //refresh database
                        NotificationController.updateNotification(notif);
                    }

                } catch (JSONException e) {
                    //send a rapport to support
                    e.printStackTrace();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (AppConfig.APP_DEBUG) {
                    Log.e("ERROR", error.toString());
                }


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", String.valueOf(notification_id));
                params.put("status", String.valueOf(1));
                if (AppConfig.APP_DEBUG) {
                    Log.i("NotificationFragment", "  params remove notification :" + params.toString());
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
            getNotifications(REQUEST_PAGE);


        } else {
            Toast.makeText(getActivity(), "Network not available ", Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void customErrorView(View v) {
        Button btn = v.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                REQUEST_PAGE = 1;
                getNotifications(REQUEST_PAGE);
            }
        });
    }

    @Override
    public void customLoadingView(View v) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void customEmptyView(View v) {

        Button btn = v.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNotifications(REQUEST_PAGE);
            }
        });
    }

    @Override
    public void onItemClick(View view, int pos) {
        Notification obj = mAdapter.getItems().get(pos);
        if (mAdapter.getItems() != null) {


            if (AppConfig.APP_DEBUG)
                Log.i("_productID", String.valueOf(obj.getModule_id()));

            handleClickNotification(obj);

            //update ui
            if (mAdapter.getItems().get(pos).getStatus() == 0) {

                mAdapter.getItems().get(pos).setStatus(1);

                //push counter to all badges
                Notification.notificationsUnseen = Notification.notificationsUnseen - 1;
                EventBus.getDefault().postSticky(new NotifyDataNotificationEvent("update_badges"));

                //Call the API to change the atatus
                editStatusNotification(pos, obj.getId());

            }

            mAdapter.notifyItemChanged(pos);

        }
    }

    private void handleClickNotification(Notification obj) {
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
        Notification obj = mAdapter.getItems().get(position);

        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (R.id.action_archive == item.getItemId()) {
                    //call the api to remove the notification remotely then from the database
                    archiveNotification(obj.getId(), position);
                }
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_notification_more);
        popupMenu.show();

    }
}
