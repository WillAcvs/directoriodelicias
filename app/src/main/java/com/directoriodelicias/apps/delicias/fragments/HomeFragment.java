package com.directoriodelicias.apps.delicias.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.Services.BusStation;
import com.directoriodelicias.apps.delicias.activities.CustomSearchActivity;
import com.directoriodelicias.apps.delicias.activities.EventsListActivity;
import com.directoriodelicias.apps.delicias.activities.OffersListActivity;
import com.directoriodelicias.apps.delicias.activities.PeopleListActivity;
import com.directoriodelicias.apps.delicias.activities.StoresListActivity;
import com.directoriodelicias.apps.delicias.customView.CategoryCustomView;
import com.directoriodelicias.apps.delicias.customView.EventCustomView;
import com.directoriodelicias.apps.delicias.customView.OfferCustomView;
import com.directoriodelicias.apps.delicias.customView.PeopleCustomView;
import com.directoriodelicias.apps.delicias.customView.SliderCustomView;
import com.directoriodelicias.apps.delicias.customView.StoreCustomView;
import com.directoriodelicias.apps.delicias.helper.AppHelper;
import com.directoriodelicias.apps.delicias.navigationdrawer.NavigationDrawerFragment;
import com.nirhart.parallaxscroll.views.ParallaxScrollView;

import java.util.HashMap;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public final static String TAG = "homefragment";
    //binding
    @BindView(R.id.mScroll)
    ParallaxScrollView mNestedScrollView;
    //ImageSlider
    private CategoryCustomView mCategoryCustomView;
    private SliderCustomView mSliderCustomView;
    private StoreCustomView mStoreCustomView;
    private OfferCustomView mOfferCustomView;
    private EventCustomView mEventCustomView;
    private PeopleCustomView mPeopleCustomView;
    private View rootview;
    //private SwipeRefreshLayout swipeRefreshLayout;
    private String[] header_bg_list = {
            "header_home_bg/header_bg_1.jpg",
            "header_home_bg/header_bg_2.jpg",
            "header_home_bg/header_bg_3.jpg",
    };
    private Listener mListener;

    // newInstance constructor for creating fragment with arguments
    public static HomeFragment newInstance(int page, String title) {
        HomeFragment fragmentFirst = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("id", page);
        args.putString("title", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    private void setup_header(View rootview) {

        AppCompatImageView header_bg = rootview.findViewById(R.id.header_bg);

        Random Dice = new Random();
        int n = Dice.nextInt(header_bg_list.length);

        Glide.with(this)
                .load(AppHelper.loadDrawable(getActivity(), header_bg_list[n]))
                .centerCrop().into(header_bg);

        ImageButton navigation = rootview.findViewById(R.id.navigation);
        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusStation.getBus().post(new NavigationDrawerFragment.NavigationDrawerEvent(1));
            }
        });

        rootview.findViewById(R.id.lbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CustomSearchActivity.class));
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.v2_fragment_home, container, false);

        rootview = rootView.getRootView();

        ButterKnife.bind(this, rootview);

        initCategoryRV(rootview);

        initSliderCustomView(rootview);

        initStoreRV(rootview);

        initOfferRV(rootview);

        initEventRv(rootview);

        initPeopleRV(rootview);

        setupScroll();


        setup_header(rootview);


        return rootview;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    private void setupScroll() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mNestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    Log.e(getTag(), scrollX + " - " + scrollY);

                    if (mListener != null)
                        mListener.onScroll(scrollX, scrollY);

                    /*if (scrollY < 600) {
                        toolbar.setBackground(getDrawable(R.drawable.gradient_bg_top_to_bottom_70));
                        toolbarTitle.setVisibility(View.GONE);
                    } else {
                        toolbar.setBackgroundColor(getColor(R.color.toolbarColor));
                        toolbarTitle.setTextColor(getColor(R.color.colorAccent));
                        toolbarTitle.setVisibility(View.VISIBLE);
                    }*/
                }
            });
        }
    }

    private void initSliderCustomView(View view) {
        mSliderCustomView = view.findViewById(R.id.sliderCV);
        mSliderCustomView.loadData(false);
        mSliderCustomView.startAutoSlider();

        mSliderCustomView.show();
    }

    private void initCategoryRV(View view) {
        mCategoryCustomView = view.findViewById(R.id.rectCategoryList);
        mCategoryCustomView.loadData(false);
        mCategoryCustomView.show();
    }

    private void initStoreRV(View view) {
        mStoreCustomView = view.findViewById(R.id.horizontalStoreList);
        mStoreCustomView.loadData(false);

        view.findViewById(R.id.card_show_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StoresListActivity.class));
                getActivity().overridePendingTransition(R.anim.lefttoright_enter, R.anim.lefttoright_exit);
            }
        });

        mStoreCustomView.show();
    }

    private void initOfferRV(View view) {
        mOfferCustomView = view.findViewById(R.id.horizontalOfferList);
        mOfferCustomView.loadData(false, new HashMap<>());
        view.findViewById(R.id.card_show_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OffersListActivity.class));
                getActivity().overridePendingTransition(R.anim.lefttoright_enter, R.anim.lefttoright_exit);
            }
        });

        mOfferCustomView.show();
    }

    private void initPeopleRV(View view) {
        mPeopleCustomView = view.findViewById(R.id.horizontalPeopleList);
        mPeopleCustomView.loadData(false);
        view.findViewById(R.id.card_show_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PeopleListActivity.class));
                getActivity().overridePendingTransition(R.anim.lefttoright_enter, R.anim.lefttoright_exit);
            }
        });


        mPeopleCustomView.hide();

    }

    private void initEventRv(View view) {
        mEventCustomView = view.findViewById(R.id.horizontalEventList);
        mEventCustomView.loadData(false);
        view.findViewById(R.id.card_show_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EventsListActivity.class));
                getActivity().overridePendingTransition(R.anim.lefttoright_enter, R.anim.lefttoright_exit);
            }
        });

        mEventCustomView.show();
    }

    @Override
    public void onRefresh() {


    }

    public void setListener(final Listener mItemListener) {
        this.mListener = mItemListener;
    }

    public interface Listener {
        void onScroll(int scrollX, int scrollY);
    }


}
