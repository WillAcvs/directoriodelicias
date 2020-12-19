package com.directoriodelicias.apps.delicias.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.Services.BusStation;
import com.directoriodelicias.apps.delicias.activities.CustomSearchActivity;
import com.directoriodelicias.apps.delicias.activities.ListUsersActivity;
import com.directoriodelicias.apps.delicias.activities.MessengerActivity;
import com.directoriodelicias.apps.delicias.adapter.messenger.ListDiscussionAdapter;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.appconfig.AppContext;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Discussion;
import com.directoriodelicias.apps.delicias.classes.Message;
import com.directoriodelicias.apps.delicias.classes.User;
import com.directoriodelicias.apps.delicias.controllers.ErrorsController;
import com.directoriodelicias.apps.delicias.controllers.messenger.MessengerController;
import com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController;
import com.directoriodelicias.apps.delicias.dtmessenger.DCMBroadcastReceiver;
import com.directoriodelicias.apps.delicias.dtmessenger.MessengerHelper;
import com.directoriodelicias.apps.delicias.load_manager.ViewManager;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.DiscussionParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.utils.Translator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Droideve on 7/12/2017.
 */

public class InboxFragment extends Fragment implements DCMBroadcastReceiver.NetworkStateReceiverListener,
        ViewManager.CustomView, ListDiscussionAdapter.ClickListener, ListDiscussionAdapter.TouchListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static int LAUNCH_FIN_USERS = 155;
    public ViewManager mViewManager;
    public SwipeRefreshLayout swipeRefreshLayout;
    public int INT_RESULT_VERSION = 120;
    private ListDiscussionAdapter adapter;
    private RecyclerView list;
    private Realm mRealm = Realm.getDefaultInstance();
    private RequestQueue queue;
    private DCMBroadcastReceiver mDCMBroadcastReceiver;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;
    private int PAGE = 1;
    private int COUNT = 0;
    private ShimmerRecyclerView shimmerRecycler;
    private BroadcastReceiver mBroadcastReceiverWakeUp;

    // newInstance constructor for creating fragment with arguments
    public static InboxFragment newInstance(int page, String title) {
        InboxFragment fragmentFirst = new InboxFragment();
        Bundle args = new Bundle();
        args.putInt("id", page);
        args.putString("title", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);

        initFab(rootView);

        //initialize the shimmer : recyclerview loader
        shimmerRecycler = rootView.findViewById(R.id.shimmer_rv_inbox);


        mDCMBroadcastReceiver = new DCMBroadcastReceiver();
        mDCMBroadcastReceiver.addListener(this);

        mViewManager = new ViewManager(getContext());
        mViewManager.setLoadingView(rootView.findViewById(R.id.loading));
        mViewManager.setContentView(rootView.findViewById(R.id.no_loading));
        mViewManager.setErrorView(rootView.findViewById(R.id.error));
        mViewManager.setEmptyView(rootView.findViewById(R.id.empty));
        mViewManager.setCustumizeView(this);

        adapter = new ListDiscussionAdapter(getActivity(), getData());
        list = rootView.findViewById(R.id.listmessages);


        list.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(mLayoutManager);
        list.setAdapter(adapter);


        list.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                if (AppConfig.APP_DEBUG)
                    Log.i("loadMore", "true");

                if (loading) {

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;

                        if (ServiceHandler.isNetworkAvailable(getActivity())) {
                            if (COUNT > adapter.getItemCount())
                                loadDiscussions(PAGE);
                        } else {
                            Toast.makeText(getActivity(), "Network not available ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        adapter.setClickListener(this);

        mBroadcastReceiverWakeUp = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("loa", "eakeUp");
            }
        };

        swipeRefreshLayout = rootView.findViewById(R.id.refresh);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary
        );


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        getActivity().registerReceiver(mDCMBroadcastReceiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mDCMBroadcastReceiver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (INT_RESULT_VERSION == requestCode && resultCode == Activity.RESULT_OK) {

            try {
                int discussionId = data.getExtras().getInt("discussionId", 0);
                for (int i = 0; i < adapter.getItemCount(); i++) {

                    if (adapter.getItem(i).getDiscussionId() == discussionId) {
                        adapter.getItem(i).setNbrMessage(0);
                        adapter.notifyDataSetChanged();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (LAUNCH_FIN_USERS == requestCode) {
            loadDiscussions(1);
        }
    }

    public List<Discussion> getData() {
        List<Discussion> data = new ArrayList<Discussion>();
        return data;
    }

    @Override
    public void itemClicked(View view, int position) {

        if (adapter.getItem(position).getNbrMessage() > 0) {

            adapter.getItem(position).setNbrMessage(0);
            adapter.notifyDataSetChanged();

        }


        Intent intent = new Intent(getActivity(), MessengerActivity.class);
        intent.putExtra("type", Discussion.DISCUSION_WITH_USER);
        int userId = adapter.getItem(position).getSenderUser().getId();

        intent.putExtra("userId", userId);
        intent.putExtra("discussionId", adapter.getItem(position).getDiscussionId());
        startActivityForResult(intent, INT_RESULT_VERSION);

        //update nbr messages alerted
        MessengerHelper.NbrMessagesManager
                .removeNbrDiscussion(adapter.getItem(position).getDiscussionId());

        //hide the badge
        try {

            //MainActivity.updateBadge(getActivity());

//           ActionItemBadge.hide(MainActivity.mainMenu.findItem(R.id.item_samplebadge));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void customErrorView(View v) {

        Button btn = v.findViewById(R.id.btn);
        TextView titleView = v.findViewById(R.id.title);
        TextView messageView = v.findViewById(R.id.text);

        if (!SessionsController.isLogged()) {
            titleView.setText(R.string.login_first);
            messageView.setText(getString(R.string.login_required));
            btn.setText(getString(R.string.login));
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), CustomSearchActivity.LoginActivityV2.class));
                    getActivity().overridePendingTransition(R.anim.lefttoright_enter, R.anim.lefttoright_exit);

                    /*FragmentManager manager = getActivity().getSupportFragmentManager();
                    Fragment fragmentAcc = new AuthenticationFragment();

                    if (manager != null) {
                        manager.beginTransaction()
                                .add(R.id.frame_container, fragmentAcc, "accountFrag")
                                .addToBackStack("accountFrag")
                                .commit();
                    }*/
                }
            });
        } else {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadDiscussions(1);
                }
            });
        }


    }

    @Override
    public void customLoadingView(View v) {

    }

    @Override
    public void customEmptyView(View v) {


        TextView text = v.findViewById(R.id.text);
        Button btn = v.findViewById(R.id.btn);

        text.setText(Translator.print(getString(R.string.noDiscussions), null));
        btn.setText(R.string.find_new_neighbours);
        btn.setVisibility(View.VISIBLE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), ListUsersActivity.class), LAUNCH_FIN_USERS);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        BusStation.getBus().register(this);

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        getActivity().registerReceiver(mBroadcastReceiverWakeUp, filter);

        //if (adapter.getItemCount() == 0)
        loadDiscussions(1);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusStation.getBus().unregister(this);
        getActivity().unregisterReceiver(mBroadcastReceiverWakeUp);
    }

    @Subscribe
    public void onMessageReceived(final Message message) {

        if (message != null) {

            final Message mesageData = message;

            list.getItemAnimator().setAddDuration(500);
            list.getItemAnimator().setRemoveDuration(500);


            if (AppContext.DEBUG)
                Log.i("onMessageReceived", message.getMessage());

            for (int i = 0; i < adapter.getItemCount(); i++) {

                if (adapter.getItem(i).getSenderUser().getId() == mesageData.getSenderId()) {


                    final JSONArray json = new JSONArray();
                    json.put(mesageData.getMessageid());

                    //
                    final int finalI = i;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //  MessengerController.inboxLoadedChangeStatus(mUser,json);
                            MessengerHelper.playSound(true);

                            //message.setStatus(Message.RECEIVER_VIEW);
                            MessengerHelper.updateInbox(mesageData.getSenderId(), message);

                            Discussion dis = adapter.getItem(finalI);
                            dis.getMessages().add(0, mesageData);

                            int nbrOfNewMessage = dis.getNbrMessage() + 1;

                            if (AppContext.DEBUG)
                                Log.i("onMessageReceived", "sender " + nbrOfNewMessage + " " + mesageData.getMessage());


                            dis.setNbrMessage(nbrOfNewMessage);
                            adapter.remove(finalI);
                            adapter.addItem(0, dis);
                            adapter.notifyItemInserted(0);
                            adapter.notifyDataSetChanged();

                            //update main badge
                            //MainActivity.updateBadge(getActivity());

                            //mark as loaded
                            if (SessionsController.isLogged()) {
                                MessengerController.inboxMarkAsLoaded(SessionsController.getSession().getUser(), dis.getDiscussionId());
                            }

                            mViewManager.showContent();

                        }
                    });

                }
            }

            list.getItemAnimator().setAddDuration(0);
            list.getItemAnimator().setRemoveDuration(0);

        }


    }


    private void loadDiscussions(int page) {

        shimmerRecycler.showShimmerAdapter();

        try {
            loadDiscussions(page, false);
        } catch (Exception e) {
        }

    }


    private void loadDiscussions(int page, boolean withoutLoading) {

        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        if (!SessionsController.isLogged()) {
            mViewManager.showContent();
            mViewManager.showError();
            return;
        }

        PAGE = page;

        if (adapter.getItemCount() == 0 && withoutLoading == false)
            shimmerRecycler.showShimmerAdapter();
        else if (PAGE > 1) {
            swipeRefreshLayout.setRefreshing(true);
        }

        final User mUser = SessionsController.getSession().getUser();
        final int user_id = mUser.getId();

        if (user_id > 0) {

            if (AppContext.DEBUG)
                Log.i("responseDiscussion", "startLoading... " + user_id);

            SimpleRequest request = new SimpleRequest(Request.Method.POST,
                    Constances.API.API_LOAD_DISCUSSION, new Response.Listener<String>() {
                @Override
                public void onResponse(final String response) {


                    if (PAGE == 0 || PAGE == 1)
                        adapter.removeAll();

                    try {

                        if (AppContext.DEBUG)
                            Log.i("responseDiscussion", "---" + response);

                        JSONObject json = new JSONObject(response);
                        DiscussionParser mDiscussionParser = new DiscussionParser(json);


                        //check server permission and display the errors
                        if (mDiscussionParser.getSuccess() == -1) {
                            ErrorsController.serverPermissionError(getActivity());
                        }

                        if (mDiscussionParser.getSuccess() == 1) {

                            COUNT = Integer.parseInt(mDiscussionParser.getStringAttr(Tags.COUNT));

                            mViewManager.showContent();

                            final List<Discussion> list = mDiscussionParser.getDiscussion();

                            for (int i = 0; i < list.size(); i++) {

                                int nbrOfNewMessages = 0;
                                final int finalI = i;

                                //save discussion and sender
                                mRealm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {

                                        realm.copyToRealmOrUpdate(list.get(finalI).getSenderUser());

                                        Discussion dis = list.get(finalI);
                                        RealmList<Message> listMessages;

                                        if (dis.getMessages().size() > 0) {
                                            Message lastMessage = dis.getMessages().get(0);
                                            listMessages = new RealmList<Message>();
                                            listMessages.add(lastMessage);
                                            dis.setMessages(listMessages);
                                        }
                                        realm.copyToRealmOrUpdate(dis);
                                    }
                                });


                                //calcul nmb of new messages
                                JSONArray msgId = new JSONArray();

                                for (int f = 0; f < list.get(i).getMessages().size(); f++) {

                                    if (AppContext.DEBUG) {
                                        Log.i("responseDiscussion", " status " + list.get(i).getMessages().get(f).getStatus());
                                        Log.i("responseDiscussion", " senderId " + list.get(i).getMessages().get(f).getSenderId());
                                    }

                                    if (list.get(i).getMessages().get(f).getSenderId() != mUser.getId()
                                            && list.get(i).getMessages().get(f).getStatus() < 0) {
                                        // nbrOfNewMessages++;
                                        msgId.put(list.get(i).getMessages().get(f).getMessageid());
                                    }
                                }


                                //list.get(i).setNbrMessage(nbrOfNewMessages);
                                adapter.addItem(list.get(i));

                                List<Message> listOfMessage = list.get(i).getMessages();
                                MessengerHelper.updateInbox(list.get(i).getSenderUser().getId(), listOfMessage);


                            }

                            loading = true;

                            if (COUNT > adapter.getItemCount())
                                PAGE++;

                            if (COUNT == 0 || adapter.getItemCount() == 0) {

                                if (adapter.getItemCount() == 0)
                                    mViewManager.showEmpty();

                            } else if (adapter.getItemCount() > 0) {

                                mViewManager.showContent();

                            }

                        }

                        mViewManager.showContent();

                        swipeRefreshLayout.setRefreshing(false);

                        shimmerRecycler.hideShimmerAdapter();


                        //show loadToast with showError
                    } catch (Exception e) {
                        e.printStackTrace();

                        mViewManager.showError();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if (AppContext.DEBUG)
                        Log.e("___ERROR", error.toString());

                    if (adapter.getItemCount() == 0)
                        mViewManager.showError();

                    mViewManager.showContent();

                    swipeRefreshLayout.setRefreshing(false);

                    shimmerRecycler.hideShimmerAdapter();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("user_id", user_id + "");
                    //params.put("status","-");
                    params.put("page", String.valueOf(PAGE));

                    if (AppContext.DEBUG)
                        Log.i("__inbox_params", params.toString());

                    return params;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(request);

        }


    }


    @Override
    public void networkAvailable() {
        try {
            if (SessionsController.isLogged()) {
                User mUser = SessionsController.getSession().getUser();
                MessengerController.loadMessages(mUser, getActivity());
            }

        } catch (Exception e) {

        }
    }

    @Override
    public void networkUnavailable() {

    }

    @Override
    public void itemTouched(View view, int position) {

    }

    @Override
    public void onRefresh() {
        loadDiscussions(1);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        try {
            bulkMarkerInboxLoaded();
        } catch (Exception e) {
            if (AppConfig.APP_DEBUG) e.printStackTrace();
        }

    }

    private void bulkMarkerInboxLoaded() {

        if (SessionsController.isLogged()) {
            User mUser = SessionsController.getSession().getUser();
            for (int i = 0; i < adapter.getItemCount(); i++) {
                //mark mesaages as loaded
                MessengerController.inboxMarkAsLoaded(mUser, adapter.getItem(i).getDiscussionId());
            }
        }

    }

    private void initFab(View view) {
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(new Intent(getActivity(), ListUsersActivity.class), LAUNCH_FIN_USERS);
            }
        });

        if (AppConfig.ENABLE_PEOPLE_AROUND_ME)
            fab.setVisibility(View.VISIBLE);
        fab.setVisibility(View.GONE);

    }
}
