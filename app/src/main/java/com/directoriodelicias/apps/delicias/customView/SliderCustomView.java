package com.directoriodelicias.apps.delicias.customView;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.EventDetailActivity;
import com.directoriodelicias.apps.delicias.activities.OfferDetailActivity;
import com.directoriodelicias.apps.delicias.activities.StoreDetailActivity;
import com.directoriodelicias.apps.delicias.adapter.pager.BannerAdapter;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Banner;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.BannerParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.views.HorizontalView;
import com.wuadam.awesomewebview.AwesomeWebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

public class SliderCustomView extends HorizontalView implements BannerAdapter.OnItemClickListener {

    private Context mContext;
    private View slider_container;
    private ViewPager viewPager;
    private LinearLayout layout_dots;
    private BannerAdapter adapterBanners;
    private Runnable runnable = null;
    private Handler handler = new Handler();
    private ShimmerRecyclerView shimmerRecycler;
    private View mainContainer;
    private Map<String, Object> optionalParams;


    public SliderCustomView(Context context) {
        super(context);
        mContext = context;
    }

    public SliderCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL);


        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.v2_slider_viewpager, this, true);

        mainContainer = getChildAt(0).findViewById(R.id.slider_container_layout);


        //start showLoading shimmer effect
        shimmerRecycler = getChildAt(0).findViewById(R.id.shimmer_view_container);
        slider_container = getChildAt(0).findViewById(R.id.slider_container);

        initSlider();

    }


    public void loadData(boolean fromDatabase) {

        shimmerRecycler.showShimmerAdapter();


        if (!fromDatabase) {
            loaDataFromApi();
        }
    }


    private void loaDataFromApi() {
        RequestQueue queue = VolleySingleton.getInstance(mContext).getRequestQueue();

        slider_container.setVisibility(GONE);

        SimpleRequest request = new SimpleRequest(Request.Method.GET,
                Constances.API.API_GET_SLIDERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (APP_DEBUG) {
                        Log.e("bannersResponse", response);
                    }

                    JSONObject jsonObject = new JSONObject(response);
                    final BannerParser mBannerParser = new BannerParser(jsonObject);
                    int success = Integer.parseInt(mBannerParser.getStringAttr(Tags.SUCCESS));

                    if (success == 1/* && mBannerParser.getBanners().size() > 0*/) {
                        adapterBanners.setbanners(mBannerParser.getBanners());


                        //stop showLoading animation
                        shimmerRecycler.hideShimmerAdapter();

                        if (adapterBanners.getCount() > 0) {
                            slider_container.setVisibility(VISIBLE);
                            mainContainer.setVisibility(VISIBLE);

                        } else {
                            //hide the view until showLoading data
                            mainContainer.setVisibility(GONE);

                        }

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
                    Log.e("Slider Error", error.toString());

                    //stop showLoading animation
                    shimmerRecycler.hideShimmerAdapter();
                }
            }
        }) {

        };

        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }


   /* private void addBottomDots(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        layout_dots.removeAllViews();
        if (dots != null && dots.length > 0)
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new ImageView(getContext());
                int width_height = 15;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
                params.setMargins(10, 10, 10, 10);
                dots[i].setLayoutParams(params);
                dots[i].setImageResource(R.drawable.shape_circle_outline);
                layout_dots.addView(dots[i]);
            }

        if (dots.length > 0) {
            dots[current].setImageResource(R.drawable.shape_circle);
        }
    }*/

    public void startAutoSlider() {
        runnable = new Runnable() {
            @Override
            public void run() {
                int pos = viewPager.getCurrentItem();
                pos = pos + 1;
                if (pos >= adapterBanners.getCount()) pos = 0;
                viewPager.setCurrentItem(pos);
                handler.postDelayed(runnable, 6000);
            }
        };
        handler.postDelayed(runnable, 6000);

    }


    private void initSlider() {
        //List<Banner> items = BannersController.getArrayList();
        //sldier_img_layout = view;
        viewPager = slider_container.findViewById(R.id.pager);

        //hide the view until showLoading data
        slider_container.setVisibility(GONE);
        // if (items.size()>0){

        adapterBanners = new BannerAdapter(mContext, new ArrayList<Banner>());
        //adapterBanners.setbanners(items);
        adapterBanners.setOnItemClickListener(this);


        viewPager.setAdapter(adapterBanners);

        // displaying selected image first
        viewPager.setCurrentItem(0);


    }

    @Override
    public void onItemClick(View view, Banner obj) {

        if (APP_DEBUG)
            Toast.makeText(getContext(), getContext().getString(R.string.redirected_to) + obj.getModule(), Toast.LENGTH_LONG).show();

        handleClickBanner(obj);
    }

    private void handleClickBanner(Banner obj) {
        if (obj != null) {
            switch (obj.getModule()) {
                case "store":
                    Intent intentStore = new Intent(mContext, StoreDetailActivity.class);
                    intentStore.putExtra("id", obj.getModule_id());
                    mContext.startActivity(intentStore);
                    break;
                case "offer":
                    Intent intentOffer = new Intent(mContext, OfferDetailActivity.class);
                    intentOffer.putExtra("id", obj.getModule_id());
                    mContext.startActivity(intentOffer);
                    break;
                case "event":
                    Intent intentEvent = new Intent(mContext, EventDetailActivity.class);
                    intentEvent.putExtra("id", obj.getModule_id());
                    mContext.startActivity(intentEvent);
                    break;
                default:
                    new AwesomeWebView.Builder(mContext)
                            .statusBarColorRes(R.color.colorPrimary)
                            .theme(R.style.FinestWebViewAppTheme)
                            .show(obj.getModule_id());
                    break;

            }
        }
    }


    private void setCustomAttribute(Context context, @Nullable AttributeSet attrs) {

        optionalParams = new HashMap<>();
        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SliderCustomView, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            optionalParams.put("height", a.getInteger(R.styleable.SliderCustomView_sliderItemHeight, 0));
            optionalParams.put("width", a.getInteger(R.styleable.SliderCustomView_sliderItemWidth, 0));

        } finally {
            a.recycle();
        }
    }
}
