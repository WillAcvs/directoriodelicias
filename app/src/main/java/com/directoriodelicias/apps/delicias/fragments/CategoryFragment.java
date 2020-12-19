package com.directoriodelicias.apps.delicias.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.CustomSearchActivity;
import com.directoriodelicias.apps.delicias.adapter.lists.CategoriesListAdapter;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Category;
import com.directoriodelicias.apps.delicias.controllers.categories.CategoryController;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.CategoryParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, CategoriesListAdapter.ClickListener {


    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;


    private CategoriesListAdapter adapter;
    private RequestQueue queue;


    // newInstance constructor for creating fragment with arguments
    public static CategoryFragment newInstance(int page, String title) {
        CategoryFragment fragmentFirst = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt("id", page);
        args.putString("title", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.v2_fragment_category, container, false);
        ButterKnife.bind(this, view);
        //init swipeRefresh
        initSwipeRefresh();

        adapter = new CategoriesListAdapter(getContext(), getData(), false, null, 0, 0, false);

        list.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(mLayoutManager);
        list.setAdapter(adapter);

        adapter.setClickListener(this);

        refresh.setOnRefreshListener(this);


        //load data from api if there no content on database
        if (CategoryController.list().size() == 0) {
            getCatsFromAPIs();
        }


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    public List<Category> getData() {

        List<Category> results = new ArrayList<>();

        RealmList<Category> listCats = CategoryController.list();

        for (Category cat : listCats) {
            if (cat.getNumCat() > 0)
                results.add(cat);
        }


        return results;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onRefresh() {

        getCatsFromAPIs();
        refresh.setRefreshing(false);
    }


    private void getCatsFromAPIs() {

        if (!ServiceHandler.isNetworkAvailable(getContext())) {
            if (CategoryController.list().size() == 0) {
                //database.insertCats(getCatsFromAPIser.parseCategoriesFromAssets(this));
            }
        }

        queue = VolleySingleton.getInstance(getContext()).getRequestQueue();

        SimpleRequest request = new SimpleRequest(Request.Method.GET,
                Constances.API.API_USER_GET_CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                refresh.setRefreshing(false);

                try {

                    if (APP_DEBUG) {
                        Log.e("catsResponse", response);
                    }

                    JSONObject jsonObject = new JSONObject(response);
                    // Log.e("response", jsonObject.toString());
                    final CategoryParser mCategoryParser = new CategoryParser(jsonObject);
                    int success = Integer.parseInt(mCategoryParser.getStringAttr(Tags.SUCCESS));

                    if (success == 1) {

                        RealmList<Category> list = mCategoryParser.getCategories();

                        adapter.clear();

                        for (int i = 0; i < list.size(); i++) {
                            adapter.addItem(list.get(i));
                        }

                        CategoryController.insertCategories(list);

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

                refresh.setRefreshing(false);

            }


        }) {


        };

        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);


    }


    private void initSwipeRefresh() {

        refresh.setOnRefreshListener(this);

        refresh.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary
        );

    }


    @Override
    public void itemClicked(View view, int position) {

        HashMap<String, Object> searchParams = new HashMap<>();
        searchParams.put("module", "store");
        searchParams.put("category", adapter.getItem(position).getNumCat());
        searchParams.put("category_selected_index", position);

        Intent intent = new Intent(getActivity(), CustomSearchActivity.ResultFilterActivity.class);
        intent.putExtra("searchParams", searchParams);
        getActivity().startActivity(intent);
    }
}
