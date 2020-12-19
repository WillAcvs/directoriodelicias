package com.directoriodelicias.apps.delicias.customView;

import android.animation.Animator;
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
import com.directoriodelicias.apps.delicias.Services.SetSelectedCategoryEvent;
import com.directoriodelicias.apps.delicias.activities.CategoriesActivity;
import com.directoriodelicias.apps.delicias.activities.CustomSearchActivity;
import com.directoriodelicias.apps.delicias.adapter.lists.CategoriesListAdapter;
import com.directoriodelicias.apps.delicias.animation.Animation;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Category;
import com.directoriodelicias.apps.delicias.controllers.categories.CategoryController;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.CategoryParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.views.HorizontalView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.RealmList;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

public class CategoryCustomView extends HorizontalView implements CategoriesListAdapter.ClickListener {

    //this field will contain the selected id category
    public static int itemCategoryselectedId = -1, itemCategoryselectedIndex = -1;
    private Context mContext;
    private CategoriesListAdapter adapter;
    private RecyclerView listView;
    private Map<String, Object> optionalParams;
    private Boolean rectCategoryView = false;
    //private ShimmerFrameLayout shimmer;
    private View mainContainer;
    private HashMap<String, Object> searchParams;
    private ShimmerRecyclerView shimmerRecycler;
    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private Animator currentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int shortAnimationDuration;


    public CategoryCustomView(Context context) {
        super(context);
        mContext = context;
        setRecyclerViewAdapter();
    }

    public CategoryCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        setCustomAttribute(context, attrs);
        setRecyclerViewAdapter();
    }

    @Override
    public void hide() {

        SetSelectedCategoryEvent event = EventBus.getDefault().getStickyEvent(SetSelectedCategoryEvent.class);
        if (event != null) {
            EventBus.getDefault().removeStickyEvent(event);
        }
        //EventBus.getDefault().unregister(this);
        super.hide();
        Log.i("StickyEvent", "eventBus unregister");


    }

    @Override
    public void show() {
        Log.i("StickyEvent", "eventBus register");
        //EventBus.getDefault().register(this);
        super.show();
    }

    public void loadData(boolean fromDatabase) {

        shimmerRecycler.showShimmerAdapter();

        //OFFLINE MODE
        if (ServiceHandler.isNetworkAvailable(mContext)) {

            if (!fromDatabase) loadDataFromAPi();
            else loadDataFromDB();
        } else {
            loadDataFromDB();
        }


    }

    public void loadDataFromDB() {
        //ensure the data exist on the database if not load it from api
        RealmList<Category> listCats = CategoryController.list();
        if (!listCats.isEmpty()) {
            adapter.clear();
            if (!listCats.isEmpty()) {

                /*//add all categories item
                Category all_categories_menu = new Category(-1,
                        getContext().getString(R.string.all_categories_menu), 0, null);
                adapter.addItem(all_categories_menu);*/


                adapter.addAllItems(listCats);
                listView.setVisibility(VISIBLE);
                shimmerRecycler.hideShimmerAdapter();
            } else {
                listView.setVisibility(GONE);
                shimmerRecycler.hideShimmerAdapter();
            }
            adapter.notifyDataSetChanged();
        } else {
            loadDataFromAPi();
        }

    }


    private void loadDataFromAPi() {

        RequestQueue queue = VolleySingleton.getInstance(mContext).getRequestQueue();


        listView.setVisibility(GONE);

        SimpleRequest request = new SimpleRequest(Request.Method.GET,
                Constances.API.API_USER_GET_CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (APP_DEBUG) {
                        Log.e("catsResponse", response);
                    }

                    JSONObject jsonObject = new JSONObject(response);
                    // Log.e("response", jsonObject.toString());
                    final CategoryParser mCategoryParser = new CategoryParser(jsonObject);
                    int success = Integer.parseInt(mCategoryParser.getStringAttr(Tags.SUCCESS));
                    if (success == 1) {
                        RealmList<Category> listCats = mCategoryParser.getCategories();


                        if (!listCats.isEmpty()) {
                            adapter.removeAll();

                           /* //add all categories item
                            Category all_categories_menu = new Category(-1,
                                    getContext().getString(R.string.all_categories_menu), 0, null);
                            adapter.addItem(all_categories_menu);*/


                            adapter.addAllItems(listCats);

                            mainContainer.setVisibility(VISIBLE);
                            listView.setVisibility(VISIBLE);

                            //save into the database
                            if (listCats.size() > 0)
                                CategoryController.insertCategories(listCats);

                        } else {

                            mainContainer.setVisibility(GONE);
                            listView.setVisibility(GONE);
                        }

                        adapter.notifyDataSetChanged();


                        int limit = Integer.parseInt(String.valueOf(optionalParams.get("siLimit")));
                        if (limit < mCategoryParser.getIntArg(Tags.COUNT)) {
                            Animation.startZoomEffect(getChildAt(0).findViewById(R.id.card_show_more));
                        }


                    }


                } catch (JSONException e) {
                    //send a rapport to support
                    e.printStackTrace();

                }

                // hide shimmer in all cases
                shimmerRecycler.hideShimmerAdapter();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (APP_DEBUG) {
                    Log.e("ERROR", error.toString());
                }

                // hide shimmer in all cases
                shimmerRecycler.hideShimmerAdapter();
            }


        }) {


        };

        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }

    private void setRecyclerViewAdapter() {

        if (rectCategoryView) {
            setOrientation(LinearLayout.HORIZONTAL);
            setGravity(Gravity.CENTER_HORIZONTAL);
        } else {
            setOrientation(LinearLayout.VERTICAL);
            setGravity(Gravity.TOP);
        }


        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.v2_horizontal_list_categories, this, true);


        mainContainer = getChildAt(0).findViewById(R.id.cat_container);

        //start showLoading shimmer effect
        shimmerRecycler = getChildAt(0).findViewById(R.id.shimmer_view_container);


        if (optionalParams.containsKey("header") && optionalParams.get("header") != null)
            ((TextView) getChildAt(0).findViewById(R.id.card_title)).setText((String) optionalParams.get("header"));


        if ((Boolean) optionalParams.get("displayCatTitle")) {
            getChildAt(0).findViewById(R.id.layout_header).setVisibility(VISIBLE);
            // ((TextView) getChildAt(0).findViewById(R.id.card_title)).setText((String) optionalParams.get("title"));
        } else {
            getChildAt(0).findViewById(R.id.layout_header).setVisibility(GONE);
        }

        //hide or show cat loader
        if ((Boolean) optionalParams.get("loader")) {
            shimmerRecycler.showShimmerAdapter();
            shimmerRecycler.setVisibility(VISIBLE);

        } else {
            shimmerRecycler.hideShimmerAdapter();
            shimmerRecycler.setVisibility(GONE);
        }


        listView = getChildAt(0).findViewById(R.id.list);
        adapter = new CategoriesListAdapter(mContext, new ArrayList<>(), rectCategoryView, optionalParams, (Float) optionalParams.get("width"), (Float) optionalParams.get("height"), (Boolean) optionalParams.get("itemClickRedirection"));

        LinearLayoutManager mLayoutManager;

        if (rectCategoryView) {
            //mLayoutManager = new CenterZoomLayoutManager(mContext);
            mLayoutManager = new LinearLayoutManager(mContext);
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        } else {
            mLayoutManager = new LinearLayoutManager(mContext);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        }

        if (AppController.isRTL())
            mLayoutManager.setReverseLayout(true);


        listView.setLayoutManager(mLayoutManager);
        listView.setHasFixedSize(true);
        /*LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(rectCategoryView ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(mLayoutManager);*/
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
        showMore.findViewById(R.id.card_show_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CategoriesActivity.class));

            }
        });
    }

    @Override
    public void itemClicked(View view, int position) {

        itemCategoryselectedId = adapter.getItem(position).getNumCat();
        itemCategoryselectedIndex = position;

        if (optionalParams.containsKey("itemClickRedirection") && (Boolean) optionalParams.get("itemClickRedirection")) {
            searchParams = new HashMap<>();
            searchParams.put("module", "store");
            searchParams.put("category", itemCategoryselectedId);
            searchParams.put("category_selected_index", itemCategoryselectedIndex);

            Intent intent = new Intent(mContext, CustomSearchActivity.ResultFilterActivity.class);
            intent.putExtra("searchParams", searchParams);
            mContext.startActivity(intent);
        } else {
            // get selected index
            itemCategoryselectedIndex = position;
            // Toast.makeText(mContext, adapter.getItem(position).getNameCat() + " Selected ", Toast.LENGTH_LONG).show();
        }

    }

    public void focusOnViewAfterAction(final int pos) {
        adapter.setSelectedPos(pos);
    }


   /* @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SetSelectedCategoryEvent event) {
        if (event.index != -1) {
            adapter.setSelectedPos(event.index);
            event.index = -1;
        }

    }*/


    private void setCustomAttribute(Context context, @Nullable AttributeSet attrs) {

        optionalParams = new HashMap<>();
        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CategoryCustomView, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            optionalParams.put("siLimit", a.getInteger(R.styleable.CategoryCustomView_siLimit, 30));
            optionalParams.put("displayCatTitle", a.getBoolean(R.styleable.CategoryCustomView_ccDisplayTitle, true));
            optionalParams.put("displayStoreNumber", a.getBoolean(R.styleable.CategoryCustomView_ccDisplayStoreNumber, true));
            optionalParams.put("itemClickRedirection", a.getBoolean(R.styleable.CategoryCustomView_ccClickRedirection, true));
            optionalParams.put("loader", a.getBoolean(R.styleable.CategoryCustomView_ccLoader, true));
            optionalParams.put("height", a.getDimension(R.styleable.CategoryCustomView_catItemHeight, 0));
            optionalParams.put("width", a.getDimension(R.styleable.CategoryCustomView_cattItemWidth, 0));
            optionalParams.put("header", a.getString(R.styleable.CategoryCustomView_ccHeader));

            rectCategoryView = a.getBoolean(R.styleable.CategoryCustomView_ccRect, true);
        } finally {
            a.recycle();
        }
    }


}
