package com.directoriodelicias.apps.delicias.customView;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.directoriodelicias.apps.delicias.AppController;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.GalleryActivity;
import com.directoriodelicias.apps.delicias.adapter.lists.ImagesListAdapter;
import com.directoriodelicias.apps.delicias.adapter.lists.OfferListAdapter;
import com.directoriodelicias.apps.delicias.animation.Animation;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Images;
import com.directoriodelicias.apps.delicias.fragments.SlideshowDialogFragment;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.ImagesParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.views.HorizontalView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;

public class GalleryStoreCustomView extends HorizontalView implements OfferListAdapter.ClickListener, ImagesListAdapter.ClickListener {

    private Context mContext;
    private FragmentActivity mFragmentActivity;
    private ImagesListAdapter adapter;
    private RecyclerView listView;
    private Map<String, Object> optionalParams;
    private ShimmerRecyclerView shimmerRecycler;
    private List<Images> mGalleryList;
    private int int_id;
    private String type;
    private View mainContainer;


    public GalleryStoreCustomView(Context context) {
        super(context);
        mContext = context;
        setRecyclerViewAdapter();
    }

    public GalleryStoreCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;


        //setCustomAttribute(context, attrs);

        setRecyclerViewAdapter();

    }


    public void loadData(int int_id, String type, FragmentActivity mFragmentActivity, boolean fromDatabase) {
        this.int_id = int_id;
        this.type = type;
        this.mFragmentActivity = mFragmentActivity;

        if (!fromDatabase) loadDataFromAPi(int_id, type);
    }


    private void loadDataFromAPi(int int_id, String type) {


        RequestQueue queue = VolleySingleton.getInstance(mContext).getRequestQueue();


        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_GET_GALLERY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (AppConfig.APP_DEBUG)
                        Log.e("__Gallery", response);

                    JSONObject jsonObject = new JSONObject(response);
                    final ImagesParser mImagesParser = new ImagesParser(jsonObject);
                    int success = Integer.parseInt(mImagesParser.getStringAttr(Tags.SUCCESS));
                    if (success == 1) {
                        adapter.removeAll();
                        final RealmList<Images> list = mImagesParser.getGallery();
                        for (Images images : list) {
                            adapter.addItem(images);
                        }

                        shimmerRecycler.hideShimmerAdapter();

                        if (adapter.getItemCount() > 0) {
                            mainContainer.setVisibility(VISIBLE);
                        } else {
                            mainContainer.setVisibility(GONE);

                        }
                    }


                    int cols = mContext.getResources().getInteger(R.integer.nbr_gallery_cols);
                    int limit = cols * cols;

                    if (limit < mImagesParser.getIntArg(Tags.COUNT)) {
                        Animation.startZoomEffect(getChildAt(0).findViewById(R.id.card_show_more));
                    }


                } catch (JSONException e) {
                    //send a rapport to support
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (AppConfig.APP_DEBUG)
                    Log.e("ERROR", error.toString());


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                int cols = mContext.getResources().getInteger(R.integer.nbr_gallery_cols);
                int limit = cols * cols;

                params.put("module_id", int_id + "");
                params.put("module", type);
                params.put("limit", String.valueOf(limit));
                params.put("page", String.valueOf(1));

                if (AppConfig.APP_DEBUG)
                    Log.e("listGalleryRequested", "" + params.toString());

                return params;
            }

        };


        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);


    }

    private void setRecyclerViewAdapter() {

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL);

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.v2_horizontal_list_gallery, this, true);

        mainContainer = getChildAt(0).findViewById(R.id.gallery_container);


        //start showLoading shimmer effect
        shimmerRecycler = getChildAt(0).findViewById(R.id.shimmer_view_container);
        shimmerRecycler.showShimmerAdapter();

        listView = getChildAt(0).findViewById(R.id.list);
        adapter = new ImagesListAdapter(mContext, new ArrayList<Images>(), true);

        listView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        if (AppController.isRTL())
            mLayoutManager.setReverseLayout(true);

        listView.setLayoutManager(mLayoutManager);
        listView.setAdapter(adapter);
        adapter.setClickListener(this);


        //setup show more view
        TextView showMore = getChildAt(0).findViewById(R.id.card_show_more);
        Drawable arrowIcon = getResources().getDrawable(R.drawable.ic_arrow_forward_white_18dp);

        DrawableCompat.setTint(
                DrawableCompat.wrap(arrowIcon),
                ContextCompat.getColor(mContext, R.color.colorPrimary)
        );

        showMore.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowIcon, null);


    }

    @Override
    public void itemClicked(View view, int position) {
        //test
        if (AppConfig.APP_DEBUG)
            Log.e("itemClicked", "position:" + position);
        SlideshowDialogFragment.newInstance().show(mFragmentActivity, adapter.getData(), position);

    }

    @Override
    public void seeMoreClicked(View view, int position) {
        if (AppConfig.APP_DEBUG)
            Log.e("itemClicked", "position:" + position);

        Intent i = new Intent(mContext, GalleryActivity.class);
        i.putExtra("int_id", int_id);
        i.putExtra("type", type);
        mContext.startActivity(i);

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

}
