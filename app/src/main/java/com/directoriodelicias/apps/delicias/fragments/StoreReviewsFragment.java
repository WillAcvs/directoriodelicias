package com.directoriodelicias.apps.delicias.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.adapter.StoreReviewsAdapter;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Guest;
import com.directoriodelicias.apps.delicias.classes.Review;
import com.directoriodelicias.apps.delicias.classes.Store;
import com.directoriodelicias.apps.delicias.classes.User;
import com.directoriodelicias.apps.delicias.controllers.ReviewController;
import com.directoriodelicias.apps.delicias.controllers.sessions.GuestController;
import com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController;
import com.directoriodelicias.apps.delicias.controllers.stores.ReviewsController;
import com.directoriodelicias.apps.delicias.controllers.stores.StoreController;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.ReviewParser;
import com.directoriodelicias.apps.delicias.utils.Utils;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.techery.properratingbar.ProperRatingBar;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

/**
 * Created by Directorio on 11/12/2017.
 */

public class StoreReviewsFragment extends Fragment {

    private int store_id;

    private LinearLayout emptyLayout;
    private LinearLayout loadingLayout;
    private LinearLayout containerLayout;
    private LinearLayout rateBtn;
    private List<Review> listReviews;
    private View currentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_store_reviews, container, false);
        currentView = rootView;


        emptyLayout = rootView.findViewById(R.id.emptyLayout);
        loadingLayout = rootView.findViewById(R.id.loadingLayout);
        containerLayout = rootView.findViewById(R.id.container);
        rateBtn = rootView.findViewById(R.id.rateBtn);
        try {
            store_id = getArguments().getInt("store_id", 0);
            if (APP_DEBUG)
                Log.e("_4_store_id", String.valueOf(store_id));


        } catch (Exception e) {
            return rootView;
        }

        List<Review> listReviews = ReviewsController.findReviewyStoreId(store_id);
        this.listReviews = listReviews;
        reloadReviews(rootView, listReviews);

        //do update from server
        getComment(rootView);


        return rootView;
    }


    private void reloadReviews(View rootView, List<Review> listReviews) {


        if (listReviews.size() > 0) {

            containerLayout.setVisibility(View.VISIBLE);
            loadingLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);

            StoreReviewsAdapter
                    .newInstance(getActivity()).load(listReviews)
                    .inflate(R.layout.item_store_review)
                    .into(rootView.findViewById(R.id.container));

        } else {

            containerLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);

        }

        rateBtn.setVisibility(View.VISIBLE);
        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ReviewController.isRated(store_id))
                    Toast.makeText(getActivity(), getString(R.string.you_ve_already_reviewd), Toast.LENGTH_LONG).show();
                else
                    showRateDialog();

            }
        });

    }


    public void getComment(final View rootview) {

        RequestQueue queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        if (listReviews.size() == 0) {
            containerLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }


        final String mac_adr = ServiceHandler.getMacAddr();

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_USER_GET_REVIEWS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (APP_DEBUG)
                        Log.e("_Comments", response);


                    JSONObject jsonObject = new JSONObject(response);
                    //Log.e("response",response);

                    final ReviewParser commentParser = new ReviewParser(jsonObject);
                    final RealmList<Review> list = commentParser.getComments();

                    final int count = commentParser.getIntArg("count");

                    if (list.size() > 0) {

                        ReviewsController.deleteAllReviews(store_id);

                        final Store store = StoreController.findStoreById(store_id);

                        if (store != null) {
                            Realm realm = Realm.getDefaultInstance();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    store.setNbr_votes(String.valueOf(count));
                                    realm.copyToRealmOrUpdate(store);
                                }
                            });
                        }

                        ReviewsController.insertReviews(list);
                        reloadReviews(rootview, list);

                        containerLayout.setVisibility(View.VISIBLE);
                        loadingLayout.setVisibility(View.GONE);
                        emptyLayout.setVisibility(View.GONE);

                    } else {

                        containerLayout.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);
                        emptyLayout.setVisibility(View.VISIBLE);

                    }


                } catch (JSONException e) {
                    //send a rapport to support
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (APP_DEBUG)
                    Log.e("ERROR", error.toString());


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("store_id", store_id + "");
                params.put("limit", String.valueOf(7));
                params.put("page", String.valueOf(1));
                params.put("mac_adr", mac_adr);

                if (APP_DEBUG)
                    Log.e("listReviewsRequested", "" + params.toString());

                return params;
            }

        };


        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);


    }


    public void sendReview(final int rating, String pseudo, String review, int guest_id, final Dialog mDialog) {

        RequestQueue queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();


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
        try {
            params.put("token", Utils.getToken(getActivity()));
        } catch (Exception e) {

        }
        params.put("mac_adr", ServiceHandler.getMacAddr());
        params.put("limit", "7");

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_RATING_STORE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    try {
                        Toast.makeText(getActivity(), getString(R.string.thankYou), Toast.LENGTH_LONG).show();
                        getComment(currentView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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


    private void showRateDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rate);

        final ProperRatingBar ratingbar = dialog.findViewById(R.id.lowerRatingBar);
        final MaterialEditText review = dialog.findViewById(R.id.review);
        final MaterialEditText pseudo = dialog.findViewById(R.id.pseudo);
        final TextView addReview = dialog.findViewById(R.id.addReview);


        if (SessionsController.isLogged()) {
            User user = SessionsController.getSession().getUser();
            pseudo.setText(user.getUsername());
            pseudo.setEnabled(false);
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
                        Toast.makeText(getActivity(), getString(R.string.pleaseWriteReview), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.selectRating), Toast.LENGTH_LONG).show();
                }
            }
        });


        dialog.show();

    }

}
