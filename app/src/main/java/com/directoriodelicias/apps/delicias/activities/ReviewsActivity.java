package com.directoriodelicias.apps.delicias.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.adapter.lists.ReviewsListAdapter;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Guest;
import com.directoriodelicias.apps.delicias.classes.Review;
import com.directoriodelicias.apps.delicias.classes.Store;
import com.directoriodelicias.apps.delicias.classes.User;
import com.directoriodelicias.apps.delicias.controllers.sessions.GuestController;
import com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController;
import com.directoriodelicias.apps.delicias.controllers.stores.ReviewsController;
import com.directoriodelicias.apps.delicias.controllers.stores.StoreController;
import com.directoriodelicias.apps.delicias.load_manager.ViewManager;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.ReviewParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.utils.Utils;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.techery.properratingbar.ProperRatingBar;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;


public class ReviewsActivity extends AppCompatActivity implements ReviewsListAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener, ViewManager.CustomView {

    public ViewManager mViewManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private RecyclerView lisyView;
    private ReviewsListAdapter adapter;
    //GET CATEGORIES FROM  DATABASE
    private Toolbar toolbar;
    private TextView APP_TITLE_VIEW = null;
    private TextView APP_DESC_VIEW = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RequestQueue queue;
    private int COUNT = 0;
    private int PAGE = 1;
    private boolean loading = true;
    private int store_id = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        queue = VolleySingleton.getInstance(this).getRequestQueue();

        mViewManager = new ViewManager(this);
        mViewManager.setLoadingView(findViewById(R.id.loading));
        mViewManager.setContentView(findViewById(R.id.content_my_store));
        mViewManager.setErrorView(findViewById(R.id.error));
        mViewManager.setEmptyView(findViewById(R.id.empty));
        mViewManager.setCustumizeView(this);

        try {
            store_id = getIntent().getExtras().getInt("store_id");
        } catch (Exception e) {
        }

        initToolbar();


        lisyView = findViewById(R.id.list);
        lisyView.setVisibility(View.VISIBLE);

        adapter = new ReviewsListAdapter(this, getData());

        lisyView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lisyView.setLayoutManager(mLayoutManager);
        lisyView.setAdapter(adapter);

        adapter.setClickListener(this);


        mSwipeRefreshLayout = findViewById(R.id.refresh);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary
        );


        lisyView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                if (loading) {

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        if (ServiceHandler.isNetworkAvailable(ReviewsActivity.this)) {
                            if (COUNT > adapter.getItemCount())
                                getComments(PAGE);
                        } else {
                            Toast.makeText(ReviewsActivity.this, "Network not available ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        Store store = StoreController.findStoreById(store_id);

        APP_TITLE_VIEW.setText(R.string.review_title);
        APP_DESC_VIEW.setText(store.getName());
        APP_DESC_VIEW.setVisibility(View.VISIBLE);

        PAGE = 1;
        getComments(PAGE);

    }

    public List<Review> getData() {

        // return ReviewsController.findReviewyStoreId(store_id);
        return new ArrayList<>();
    }

    @Override
    public void itemClicked(View view, int position) {


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reviews_menu, menu);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reviews_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (android.R.id.home == item.getItemId()) {
            finish();
        } else if (R.id.add_review == item.getItemId()) {
            showRateDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void initToolbar() {

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setSubtitle("E-shop");
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_36dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        APP_TITLE_VIEW = toolbar.findViewById(R.id.toolbar_title);
        APP_DESC_VIEW = toolbar.findViewById(R.id.toolbar_subtitle);

        APP_DESC_VIEW.setVisibility(View.GONE);

    }


    @Override
    public void onRefresh() {

        PAGE = 1;
        getComments(PAGE);

    }


    public void getComments(final int page) {


        mSwipeRefreshLayout.setRefreshing(true);

        if (adapter.getItemCount() == 0)
            mViewManager.showLoading();

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_USER_GET_REVIEWS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (APP_DEBUG) {
                        Log.e("responseOffersString", response);
                    }

                    JSONObject jsonObject = new JSONObject(response);


                    //Log.e("response",response);

                    final ReviewParser oReviewParser = new ReviewParser(jsonObject);
                    // List<Store> list = mStoreParser.getEventFromDB();
                    COUNT = 0;
                    COUNT = Integer.parseInt(oReviewParser.getStringAttr(Tags.COUNT));
                    mViewManager.showContent();

                    if (AppConfig.APP_DEBUG)
                        Log.e("StoreReviewActivity", COUNT + " " + page);


                    if (page == 1) {

                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                RealmList<Review> list = oReviewParser.getComments();

                                adapter.removeAll();
                                for (int i = 0; i < list.size(); i++) {
                                    // if (list.get(i).getDistance() <= REQUEST_RANGE_RADIUS)
                                    adapter.addItem(list.get(i));
                                }

                                //set it into database
                                ReviewsController.insertReviews(list);

                                mSwipeRefreshLayout.setRefreshing(false);
                                loading = true;

                                mViewManager.showContent();

                                if (COUNT > adapter.getItemCount())
                                    PAGE++;

                                if (COUNT == 0 || adapter.getItemCount() == 0) {
                                    mViewManager.showEmpty();
                                }

                                if (APP_DEBUG) {
                                    Log.e("__count ", COUNT + " page = " + page + " " + adapter.getItemCount());
                                }

                            }
                        }, 800);
                    } else {
                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                RealmList<Review> list = oReviewParser.getComments();

                                for (int i = 0; i < list.size(); i++) {
                                    //if (list.get(i).getDistance() <=REQUEST_RANGE_RADIUS)
                                    adapter.addItem(list.get(i));
                                }


                                //set it into database
                                ReviewsController.insertReviews(list);

                                mSwipeRefreshLayout.setRefreshing(false);
                                mViewManager.showContent();
                                loading = true;
                                if (COUNT > adapter.getItemCount())
                                    PAGE++;

                                if (COUNT == 0 || adapter.getItemCount() == 0) {
                                    mViewManager.showEmpty();
                                }

                                if (APP_DEBUG) {
                                    Log.e("__count ", COUNT + " page = " + page + " " + adapter.getItemCount());
                                }
                            }
                        }, 800);

                    }

                } catch (JSONException e) {
                    //send a rapport to support
                    if (APP_DEBUG)
                        e.printStackTrace();

                    if (adapter.getItemCount() == 0)
                        mViewManager.showError();

                    mSwipeRefreshLayout.setRefreshing(false);
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

                params.put("store_id", String.valueOf(store_id));
                params.put("limit", String.valueOf(10));
                params.put("page", String.valueOf(page));
                //params.put("mac_adr", ServiceHandler.getMacAddress(getContext()));

                return params;
            }

        };

        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }


    @Override
    public void customErrorView(View v) {

        Button retry = v.findViewById(R.id.btn);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getComments(1);
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

        Button btn = v.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewManager.showLoading();
                getComments(1);
                PAGE = 1;
            }
        });


    }

    private void showRateDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rate);

        final ProperRatingBar ratingbar = dialog.findViewById(R.id.lowerRatingBar);
        final MaterialEditText review = dialog.findViewById(R.id.review);
        final MaterialEditText pseudo = dialog.findViewById(R.id.pseudo);
        final TextView addReview = dialog.findViewById(R.id.addReview);


        if (SessionsController.isLogged()) {
            User user = SessionsController.getSession().getUser();
            pseudo.setText(user.getUsername());
        }

        final Guest guest = GuestController.getGuest();
        int gid = 0;
        if (guest != null)
            gid = guest.getId();

        final int finalGid = gid;
        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ratingbar.getRating() > 0) {
                    if (true) {
                        //send review

                        sendReview(
                                ratingbar.getRating(),
                                pseudo.getText().toString().trim(),
                                review.getText().toString().trim(),
                                finalGid,
                                dialog
                        );

                    } else {
                        Toast.makeText(ReviewsActivity.this, getString(R.string.pleaseWriteReview), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ReviewsActivity.this, getString(R.string.selectRating), Toast.LENGTH_LONG).show();
                }
            }
        });


        dialog.show();

    }


    public void sendReview(final int rating, String pseudo, String review, int guest_id, final Dialog mDialog) {

        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();
        queue = VolleySingleton.getInstance(this).getRequestQueue();


        final LinearLayout progress = mDialog.findViewById(R.id.progressLayout);
        final LinearLayout mainLayout = mDialog.findViewById(R.id.mainLayout);

        mainLayout.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);

        if (pseudo.trim().trim().equals(""))
            pseudo = "Guest-" + guest_id;

        if (review.trim().trim().equals(""))
            review = " ";


        final Map<String, String> params = new HashMap<String, String>();

        params.put("store_id", store_id + "");
        params.put("rate", rating + "");
        params.put("review", review + "");
        params.put("pseudo", pseudo + "");
        params.put("guest_id", guest_id + "");
        params.put("token", Utils.getToken(this));
        params.put("mac_adr", ServiceHandler.getMacAddr());
        params.put("limit", "7");

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_RATING_STORE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Toast.makeText(ReviewsActivity.this, getString(R.string.thankYou), Toast.LENGTH_LONG).show();

                    mainLayout.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);

                    JSONObject jso = new JSONObject(response);
                    int success = jso.getInt("success");
                    if (success == 1) {

                        final Store store = StoreController.findStoreById(store_id);
                        if (store != null) {
                            Realm realm = Realm.getDefaultInstance();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    store.setNbr_votes(String.valueOf((Integer.parseInt(store.getNbr_votes()) + 1)));
                                    realm.copyToRealmOrUpdate(store);
                                }
                            });
                        }

                    } else {

                        mainLayout.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);

                    }

                    //add view
                    if (mDialog.isShowing())
                        mDialog.dismiss();


                    PAGE = 1;
                    getComments(PAGE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (APP_DEBUG) Log.e("ERROR", error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                return params;
            }

        };


        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }


}
