package com.directoriodelicias.apps.delicias.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
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
import com.directoriodelicias.apps.delicias.GPS.GPStracker;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.CustomSearchActivity;
import com.directoriodelicias.apps.delicias.activities.MessengerActivity;
import com.directoriodelicias.apps.delicias.adapter.lists.ListUsersAdapter;
import com.directoriodelicias.apps.delicias.appconfig.AppContext;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Discussion;
import com.directoriodelicias.apps.delicias.classes.User;
import com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController;
import com.directoriodelicias.apps.delicias.controllers.users.UserController;
import com.directoriodelicias.apps.delicias.helper.AppHelper;
import com.directoriodelicias.apps.delicias.load_manager.ViewManager;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.UserParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

public class ListUsersFragment extends Fragment
        implements ListUsersAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener, ViewManager.CustomView {

    public ViewManager mViewManager;
    //showLoading
    public SwipeRefreshLayout swipeRefreshLayout;
    //for scrolling params
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    private int listType = 1;
    private RecyclerView list;
    private ListUsersAdapter adapter;
    //init request http
    private RequestQueue queue;
    private boolean loading = true;
    //pager
    private int COUNT = 0;
    private int PAGE = 1;
    private GPStracker mGPS;
    private List<User> listUsers = new ArrayList<>();
    private User mUserSession;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //updateBadge();
    }

    /*private void updateBadge() {
        if (MessengerHelper.NbrMessagesManager.getNbrTotalMessages() > 0) {
            ActionItemBadge.update(getActivity(), MainActivity.mainMenu.findItem(R.id.item_samplebadge),
                    CommunityMaterial.Icon.cmd_bell_ring_outline,
                    ActionItemBadge.BadgeStyles.RED,
                    MessengerHelper.NbrMessagesManager.getNbrTotalMessages());
        } else {
            ActionItemBadge.hide(MainActivity.mainMenu.findItem(R.id.item_samplebadge));
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_list, container, false);


        if (SessionsController.isLogged()) {
            mUserSession = SessionsController.getSession().getUser();
        } else {

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

        adapter = new ListUsersAdapter(getActivity(), listUsers, false);
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

                        if (ServiceHandler.isNetworkAvailable(getActivity())) {
                            if (COUNT > adapter.getItemCount())
                                getUsers(PAGE);
                        } else {
                            Toast.makeText(getActivity(), "Network not available ", Toast.LENGTH_SHORT).show();
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
            getUsers(PAGE);

        }


        return rootView;
    }


    public void getUsers(final int page) {

        mGPS = new GPStracker(getActivity());
        swipeRefreshLayout.setRefreshing(true);

        if (adapter.getItemCount() == 0)
            mViewManager.showLoading();

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_GET_USERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (APP_DEBUG) {
                        Log.e("responseUsersString", response);
                    }

                    JSONObject jsonObject = new JSONObject(response);
                    if (APP_DEBUG) {
                        Log.e("response", jsonObject.toString());
                    }

                    //Log.e("response",response);

                    final UserParser mUsersParser = new UserParser(jsonObject);
                    // List<Store> list = mStoreParser.getEventFromDB();
                    COUNT = 0;

                    COUNT = mUsersParser.getIntArg(Tags.COUNT);
                    mViewManager.showContent();

                    if (page == 1) {

                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                RealmList<User> list = mUsersParser.getUser();

                                if (list.size() > 0) {
                                    UserController.insertUsers(list);
                                }
                                adapter.removeAll();

                                for (int i = 0; i < list.size(); i++) {

                                    adapter.addItem(list.get(i));
                                }

                                if (adapter.getItemCount() > 0) {
                                    adapter.setData(AppHelper.prepareListWithHeaders(adapter.getData()));
                                    adapter.notifyDataSetChanged();
                                }


                                swipeRefreshLayout.setRefreshing(false);
                                loading = true;


                                if (COUNT > adapter.getItemCount())
                                    PAGE++;
                                if (COUNT == 0 || adapter.getItemCount() == 0) {
                                    mViewManager.showEmpty();
                                } else {
                                    mViewManager.showContent();
                                }

                                if (APP_DEBUG) {
                                    Log.e("count ", list.size() + " page = " + page);
                                }

                            }
                        }, 800);
                    } else {
                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                RealmList<User> list = mUsersParser.getUser();

                                if (list.size() > 0) {
                                    UserController.insertUsers(list);
                                }
                                for (int i = 0; i < list.size(); i++) {
                                    //if (list.get(i).getDistance() <=radius_range)
                                    adapter.addItem(list.get(i));
                                }

                                if (adapter.getItemCount() > 0) {
                                    adapter.setData(AppHelper.prepareListWithHeaders(adapter.getData()));
                                    adapter.notifyDataSetChanged();
                                }


                                swipeRefreshLayout.setRefreshing(false);
                                mViewManager.showContent();
                                loading = true;
                                if (COUNT > adapter.getItemCount())
                                    PAGE++;

                                if (COUNT == 0 || adapter.getItemCount() == 0) {
                                    mViewManager.showEmpty();
                                } else {
                                    mViewManager.showContent();
                                }
                            }
                        }, 800);

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

                mViewManager.showError();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                if (mGPS.canGetLocation()) {
                    params.put("lat", mGPS.getLatitude() + "");
                    params.put("lng", mGPS.getLongitude() + "");
                }

                params.put("token", Utils.getToken(getContext()));
                params.put("mac_adr", ServiceHandler.getMacAddr());
                params.put("limit", "30");
                params.put("page", page + "");

                /*if (mUserSession != null)
                    params.put("user_id", String.valueOf(mUserSession.getId()));*/


                if (APP_DEBUG) {
                    Log.e("ListUsersFragment", "  params getOffers :" + params.toString());
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
            getUsers(1);
            PAGE = 1;
        } else {
            swipeRefreshLayout.setRefreshing(false);
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

                getUsers(1);
                PAGE = 1;
                mViewManager.showLoading();
            }
        });

    }

    @Override
    public void customLoadingView(View v) {


    }

    @Override
    public void customEmptyView(View v) {

        TextView text = v.findViewById(R.id.text);

        text.setText(R.string.no_users);
        Button btn = v.findViewById(R.id.btn);
        btn.setText(R.string.refresh);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewManager.showLoading();
                getUsers(1);
                PAGE = 1;
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void itemClicked(int position) {

        sendMessage(adapter.getItem(position));

        if (APP_DEBUG)
            if (mUserSession != null)
                Log.e("___clicked", adapter.getItem(position).getUsername() + "==" + mUserSession.getUsername());


    }

    @Override
    public void itemOptionsClicked(View view, int position) {
        itemMenuClicked(view, position);
    }


    private void sendMessage(final User user) {
        if (SessionsController.isLogged()) {
            Intent intent = new Intent(getActivity(), MessengerActivity.class);
            intent.putExtra("userId", user.getId());
            intent.putExtra("type", Discussion.DISCUSION_WITH_USER);
            startActivity(intent);
        } else {
            getActivity().startActivity(new Intent(getContext(), CustomSearchActivity.LoginActivityV2.class));
        }


    }

    public void itemMenuClicked(View view, final int position) {

        // mMenuDialogFragment.show(getFragmentManager(), "ContextMenuDialogFragment");

        //////////////////////////////////////////////
        final PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(R.menu.user_option);

        // Force icons to show
        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popupMenu);
            argTypes = new Class[]{boolean.class};
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {

            //Log.w(SliderActivity.class.getName(), "showError forcing menu icons to show", e);
            popupMenu.show();
            return;
        }


        if (adapter.getItem(position).isBlocked()) {
            popupMenu.getMenu().findItem(R.id.action_block).setVisible(false);
            popupMenu.getMenu().findItem(R.id.action_unblock).setVisible(true);
        } else {
            popupMenu.getMenu().findItem(R.id.action_block).setVisible(true);
            popupMenu.getMenu().findItem(R.id.action_unblock).setVisible(false);
        }

        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int mId = mUserSession.getId();
                int userId = adapter.getItem(position).getId();

                switch (item.getItemId()) {

                    case R.id.action_block:
                        //send Report to admin
                        blockUser(mId, userId, true, position);
                        break;
                    case R.id.action_unblock:
                        //send Report to admin
                        blockUser(mId, userId, false, position);
                        break;
                }

                return false;
            }
        });

    }

    private void blockUser(final int meId, final int userIdToblock, final boolean state, final int pos) {

        final ProgressDialog pdialog = new ProgressDialog(getActivity());
        pdialog.setMessage("Loading ...");
        pdialog.show();

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_BLOCK_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pdialog.dismiss();
                try {

                    if (AppContext.DEBUG)
                        Log.e("response", response);

                    JSONObject js = new JSONObject(response);

                    UserParser mUserParser = new UserParser(js);
                    int success = Integer.parseInt(mUserParser.getStringAttr(Tags.SUCCESS));

                    if (success == 1) {

                        if (state == true) {

                            adapter.notifyItemChanged(pos);

                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            adapter.getItem(pos).setBlocked(true);
                            realm.copyToRealmOrUpdate(adapter.getItem(pos));
                            realm.commitTransaction();

                        } else {

                            adapter.notifyItemChanged(pos);
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            adapter.getItem(pos).setBlocked(false);
                            realm.copyToRealmOrUpdate(adapter.getItem(pos));
                            realm.commitTransaction();
                        }

                    } else {

                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                    //show loadToast with showError


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.toString());

                pdialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", String.valueOf(meId));
                params.put("blocked_id", String.valueOf(userIdToblock));
                params.put("state", String.valueOf(state));

                if (AppContext.DEBUG)
                    Log.e("sync", params.toString());

                return params;
            }

        };

        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);


    }


}
