package com.directoriodelicias.apps.delicias.customView;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.StoreDetailActivity;
import com.directoriodelicias.apps.delicias.animation.ImageLoaderAnimation;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Store;
import com.directoriodelicias.apps.delicias.controllers.stores.StoreController;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.StoreParser;
import com.directoriodelicias.apps.delicias.utils.NSLog;
import com.directoriodelicias.apps.delicias.views.HorizontalView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import io.realm.RealmList;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

public class StoreCardCustomView extends HorizontalView {

    private Context mContext;
    private Store mStore;
    private Map<String, Object> optionalParams;
    private View mView;
    private StoreListener sl;
    private int store_id = -1;

    public StoreCardCustomView(Context context) {
        super(context);
        mContext = context;
        setUIComponenet();
    }

    public StoreCardCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        setCustomAttribute(context, attrs);

        setUIComponenet();
    }

    public void loadData(int store_id, boolean fromDatabase) {

        this.store_id = store_id;
        if (!fromDatabase) {
            loadDataFromAPi(store_id);
        }

    }

    public void loadData(int store_id, boolean fromDatabase, StoreListener sl) {

        this.sl = sl;
        this.store_id = store_id;

        if (!fromDatabase) {
            loadDataFromAPi(store_id);
        }

    }

    private void loadDataFromAPi(int store_id) {

        findViewById(R.id.placeholder).setVisibility(VISIBLE);
        findViewById(R.id.content).setVisibility(GONE);

        RequestQueue queue = VolleySingleton.getInstance(mContext).getRequestQueue();

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_USER_GET_STORES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parse_data(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NSLog.e("ERROR", error.toString());

                findViewById(R.id.placeholder).setVisibility(VISIBLE);
                findViewById(R.id.content).setVisibility(GONE);

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("limit", "1");
                params.put("store_id", String.valueOf(store_id));

                NSLog.e("storeItem", "  params getStores :" + params.toString());

                return params;
            }

        };


        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }

    private void parse_data(String response) {

        try {

            NSLog.e("responseStoresString", response);

            JSONObject jsonObject = new JSONObject(response);

            //Log.e("response",response);

            final StoreParser mStoreParser = new StoreParser(jsonObject);
            RealmList<Store> list = mStoreParser.getStore();

            if (list.size() > 0) {

                StoreController.insertStores(list);
                mStore = list.get(0);
                setupComponent(mStore);

                if (sl != null) {
                    sl.onLoaded(mStore);
                }


            } else {

                if (APP_DEBUG)
                    Toast.makeText(mContext, mContext.getString(R.string.store_not_found), Toast.LENGTH_LONG).show();
                //hide item
                findViewById(R.id.cv_store_item).setVisibility(GONE);

            }


        } catch (JSONException e) {
            //send a rapport to support
            if (APP_DEBUG)
                Log.e("ERROR", e.toString());

        }
    }

    private void setUIComponenet() {

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL);

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.v2_card_item_store, this, true);

        mView = getChildAt(0);

        mView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClicked();
            }
        });

    }

    public void itemClicked() {

        if (APP_DEBUG)
            Log.e("_1_store_id", String.valueOf(store_id));

        Intent intent = new Intent(mContext, StoreDetailActivity.class);
        intent.putExtra("id", store_id);
        mContext.startActivity(intent);

    }


    private void setCustomAttribute(Context context, @Nullable AttributeSet attrs) {

        optionalParams = new HashMap<>();
        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.StoreCustomView, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            optionalParams.put("strLimit", a.getInteger(R.styleable.StoreCustomView_strLimit, 30));

        } finally {
            a.recycle();
        }
    }

    public void setupComponent(Store mStore) {
        if (mStore != null) {


            if (mStore.getListImages() != null && mStore.getListImages().size() > 0) {

                Glide.with(mContext.getApplicationContext())
                        .load(mStore.getListImages().get(0)
                                .getUrl200_200())
                        .centerCrop().placeholder(ImageLoaderAnimation.glideLoader(mContext))
                        .into((ImageView) mView.findViewById(R.id.image));

            } else {

                Glide.with(mContext.getApplicationContext())
                        .load(R.drawable.def_logo)
                        .centerCrop().placeholder(R.drawable.def_logo)
                        .into((ImageView) mView.findViewById(R.id.image));

            }
            ((TextView) mView.findViewById(R.id.address)).setText(mStore.getAddress());
            double votes = mStore.getVotes();
            DecimalFormat decim = new DecimalFormat("#.##");
            ((TextView) mView.findViewById(R.id.rate)).setText(decim.format(votes) + "  (" + mStore.getNbr_votes() + ")");
            ((TextView) mView.findViewById(R.id.name)).setText(mStore.getName());

            updateCategoryBadge(mStore.getCategory_name(), mStore.getCategory_color());

            findViewById(R.id.placeholder).setVisibility(GONE);
            findViewById(R.id.content).setVisibility(VISIBLE);

        }
    }

    private void updateCategoryBadge(String title, String color_hex) {

        TextView catView = mView.findViewById(R.id.store_tag_category);

        catView.setVisibility(View.VISIBLE);

        int color = ContextCompat.getColor(mContext, R.color.colorPrimary);

        if (color_hex != null && !color_hex.equals("null"))
            color = Color.parseColor(color_hex);

        Drawable badge_cat_background = catView.getBackground();
        if (badge_cat_background instanceof ShapeDrawable) {
            ((ShapeDrawable) badge_cat_background).getPaint().setColor(color);
        } else if (badge_cat_background instanceof GradientDrawable) {
            ((GradientDrawable) badge_cat_background).setColor(color);
        } else if (badge_cat_background instanceof ColorDrawable) {
            ((ColorDrawable) badge_cat_background).setColor(color);
        }

        catView.setText(title);

    }

    public void setStoreListener(StoreListener l) {
        sl = l;
    }

    public interface StoreListener {
        void onLoaded(Store object);
    }

}
