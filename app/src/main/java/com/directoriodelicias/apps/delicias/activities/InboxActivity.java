package com.directoriodelicias.apps.delicias.activities;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.Services.BusStation;
import com.directoriodelicias.apps.delicias.adapter.messenger.ListDiscussionAdapter;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.appconfig.AppContext;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Discussion;
import com.directoriodelicias.apps.delicias.classes.Message;
import com.directoriodelicias.apps.delicias.classes.User;
import com.directoriodelicias.apps.delicias.controllers.messenger.MessengerController;
import com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController;
import com.directoriodelicias.apps.delicias.dtmessenger.DCMBroadcastReceiver;
import com.directoriodelicias.apps.delicias.dtmessenger.MessengerHelper;
import com.directoriodelicias.apps.delicias.load_manager.ViewManager;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.DiscussionParser;
import com.directoriodelicias.apps.delicias.utils.Translator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;


/**
 * Created by Droideve on 6/26/2016.
 */
public class InboxActivity extends AppCompatActivity implements ListDiscussionAdapter.ClickListener, ListDiscussionAdapter.TouchListener, ViewManager.CustomView, DCMBroadcastReceiver.NetworkStateReceiverListener, SwipeRefreshLayout.OnRefreshListener {


    public ViewManager mViewManager;
    public int INT_RESULT_VERSION = 120;
    Toolbar toolbar;
    private ListDiscussionAdapter adapter;
    private RecyclerView list;
    private Realm mRealm = Realm.getDefaultInstance();
    private User mUser;
    private RequestQueue queue;
    private DCMBroadcastReceiver mDCMBroadcastReceiver;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BroadcastReceiver mBroadcastReceiverWakeUp;
    private TextView APP_TITLE_VIEW = null;
    private TextView APP_DESC_VIEW = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        mDCMBroadcastReceiver = new DCMBroadcastReceiver();
        mDCMBroadcastReceiver.addListener(this);

        queue = VolleySingleton.getInstance(this).getRequestQueue();


        if (!SessionsController.isLogged()) {
            ActivityCompat.finishAffinity(this);
            startActivity(new Intent(this, CustomSearchActivity.LoginActivityV2.class));
            overridePendingTransition(R.anim.lefttoright_enter, R.anim.righttoleft_exit);
        }


        mViewManager = new ViewManager(getApplicationContext());
        mViewManager.setLoadingView(findViewById(R.id.loading));
        mViewManager.setContentView(findViewById(R.id.no_loading));
        mViewManager.setErrorView(findViewById(R.id.error));
        mViewManager.setEmptyView(findViewById(R.id.empty));
        mViewManager.showLoading();
        mViewManager.setCustumizeView(this);

        setupToolbar();


        mUser = SessionsController.getSession().getUser();

        adapter = new ListDiscussionAdapter(this, getData());
        list = findViewById(R.id.listmessages);


        list.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(mLayoutManager);
        list.setAdapter(adapter);

        adapter.setClickListener(this);
        adapter.setTouchListener(this);


        //loadDiscussions();
        loadDiscussionsFromRealm();

        mBroadcastReceiverWakeUp = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("loa", "eakeUp");
            }
        };

        try {

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(2001);
            notificationManager.cancel(2002);

        } catch (Exception e) {

        }


        swipeRefreshLayout = findViewById(R.id.refresh);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary
        );

    }

    @Override
    public void onRefresh() {
        //loadDiscussions();
        loadDiscussionsFromRealm();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.messenger_menu, menu);
//
//        return true;
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MessengerController.loadMessages(mUser);
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        this.registerReceiver(mDCMBroadcastReceiver, filter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(mDCMBroadcastReceiver);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

        }
    }

    public List<Discussion> getData() {
        List<Discussion> data = new ArrayList<Discussion>();
        return data;
    }

    @Override
    public void itemClicked(View view, int position) {

        Intent intent = new Intent(this, MessengerActivity.class);
        intent.putExtra("type", Discussion.DISCUSION_WITH_USER);
        int userId = adapter.getItem(position).getSenderUser().getId();

        intent.putExtra("userId", userId);
        intent.putExtra("discussionId", adapter.getItem(position).getDiscussionId());
        startActivityForResult(intent, INT_RESULT_VERSION);

    }

    @Override
    public void itemTouched(View view, int position) {


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (android.R.id.home == item.getItemId()) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupToolbar() {

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        // getSupportActionBar().setSubtitle("E-shop");
        getSupportActionBar().setTitle("");


        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_36dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        APP_TITLE_VIEW = toolbar.findViewById(R.id.toolbar_title);
        APP_DESC_VIEW = toolbar.findViewById(R.id.toolbar_subtitle);


        APP_TITLE_VIEW.setText(Translator.print("Discussions"));
        APP_DESC_VIEW.setVisibility(View.GONE);

    }


    @Override
    public void customErrorView(View v) {

    }

    @Override
    public void customLoadingView(View v) {

    }

    @Override
    public void customEmptyView(View v) {

        TextView text = v.findViewById(R.id.text);
        Button btn = v.findViewById(R.id.btn);

        text.setText(Translator.print("No Discussion", null));
        btn.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        BusStation.getBus().register(this);

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mBroadcastReceiverWakeUp, filter);

        if (adapter.getItemCount() == 0) {
            mViewManager.showLoading();
            loadDiscussionsFromRealm();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusStation.getBus().unregister(this);

        unregisterReceiver(mBroadcastReceiverWakeUp);
    }


    private void loadDiscussionsFromRealm() {

//        mViewManager.showLoading();
//        RealmResults<Discussion> result = mRealm.where(Discussion.class).equalTo("isSystem",false).findAllSorted("createdAt",Sort.DESCENDING);
//        RealmList<Discussion> list = new RealmList<Discussion>();
//        list.addAll(result.subList(0, result.size()));
//
//        if(list.size()>0){
//            adapter.setData(list);
//            mViewManager.showContent();
//        }else{
//            loadDiscussions();
//        }

        loadDiscussions();
    }

    private void loadDiscussions() {


        mViewManager.showLoading();
        final User mUser = SessionsController.getSession().getUser();
        final int user_id = mUser.getId();

        if (user_id > 0) {

            SimpleRequest request = new SimpleRequest(Request.Method.POST,
                    Constances.API.API_LOAD_DISCUSSION, new Response.Listener<String>() {
                @Override
                public void onResponse(final String response) {

                    mViewManager.showContent();
                    try {

                        if (AppContext.DEBUG)
                            Log.e("responseDiscussion", response);

                        JSONObject json = new JSONObject(response);
                        DiscussionParser mDiscussionParser = new DiscussionParser(json);

                        if (mDiscussionParser.getSuccess() == 1) {

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

                                    if (
                                            list.get(i).getMessages().get(f).getReceiver_id() == mUser.getId()
                                                    &&
                                                    (list.get(i).getMessages().get(f).getStatus() == -1
                                                            ||
                                                            list.get(i).getMessages().get(f).getStatus() == -2
                                                    )
                                    ) {

                                        nbrOfNewMessages++;
                                        msgId.put(list.get(i).getMessages().get(f).getMessageid());

                                    }
                                }


                                list.get(i).setNbrMessage(nbrOfNewMessages);
                                adapter.addItem(list.get(i));

                                List<Message> listOfMessage = list.get(i).getMessages();
                                MessengerHelper.updateInbox(list.get(i).getSenderUser().getId(), listOfMessage);

                            }

                        }

                        //show loadToast with showError
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERROR", error.toString());

                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("user_id", user_id + "");
                    params.put("status", "-1");

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


    @Override
    public void networkAvailable() {
        MessengerController.loadMessages(mUser, this);
    }

    @Override
    public void networkUnavailable() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            bulkMarkerInboxLoaded();
        } catch (Exception e) {
            if (AppConfig.APP_DEBUG) e.printStackTrace();
        }

    }


    private void bulkMarkerInboxLoaded() {

        for (int i = 0; i < adapter.getItemCount(); i++) {
            //mark mesaages as loaded
            MessengerController.inboxMarkAsLoaded(mUser, adapter.getItem(i).getDiscussionId());
        }
    }

}
