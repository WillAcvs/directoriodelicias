package com.directoriodelicias.apps.delicias.fragments;


import android.Manifest;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bumptech.glide.Glide;
import com.directoriodelicias.apps.delicias.AppController;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.customView.SwipeDisabledViewPager;
import com.directoriodelicias.apps.delicias.helper.AppHelper;
import com.directoriodelicias.apps.delicias.navigationdrawer.NavigationDrawerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private int FRAGS_ITEMS_NUM = 2;
    private SwipeDisabledViewPager viewPager;
    private BottomNavigationView navigation;

    private int maxRootViewHeight = 0;
    private int currentRootViewHeight = 0;

    private static String[] auth_bg_list = {
            "auth_bg/login_bg_1.jpg",
            "auth_bg/login_bg_2.jpg",
            "auth_bg/login_bg_3.jpg",
            "auth_bg/login_bg_4.jpg",
            "auth_bg/login_bg_5.jpg",
            "auth_bg/login_bg_6.jpg",
    };


    // newInstance constructor for creating fragment with arguments
    public static ProfileFragment newInstance(int page, String title) {
        ProfileFragment fragmentFirst = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt("id", page);
        args.putString("title", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.v2_fragment_profile_vp, container, false);

        view.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        currentRootViewHeight = view.getHeight();
                        if (currentRootViewHeight > maxRootViewHeight) {
                            maxRootViewHeight = currentRootViewHeight;
                        }

                        if (currentRootViewHeight >= maxRootViewHeight) {
                        } else {
                            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        }
                    }
                });


        //init toolbar
        initToolbar(view);
        //init View Pager
        initViewPagerAdapter(view);
        //init Navigation Bottom Pager
        initNavigationBottomView(view);

        // check read permission to upload image
        checkPermission();


        return view;
    }


    private void checkPermission() {

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    private void initToolbar(View view) {
        LinearLayout mToolbar = view.findViewById(R.id.toolbar);
        if (AppController.isRTL()) {
            Drawable arrowIcon = getResources().getDrawable(R.drawable.ic_arrow_forward_white_18dp);
            ((ImageView) mToolbar.findViewById(R.id.btnBack)).setImageDrawable(arrowIcon);
        }
        mToolbar.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHomePage();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        handleBackPressedEvent();

        //NavigationDrawerFragment.getInstance().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


    }

    @Override
    public void onPause() {
        super.onPause();

        // NavigationDrawerFragment.getInstance().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

    }

    private void initViewPagerAdapter(View view) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

    }


    private void initNavigationBottomView(View view) {

        navigation = view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_edit_profile:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_password_profile:
                        viewPager.setCurrentItem(1);
                        return true;
                }
                return false;
            }
        });
    }

    private void backToHomePage() {

        //enable nav drawer when fragment is closed
        if (NavigationDrawerFragment.getInstance() != null)
            NavigationDrawerFragment.getInstance().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);


        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            getActivity().onBackPressed();
        }

        getActivity().overridePendingTransition(R.anim.lefttoright_enter, R.anim.lefttoright_exit);
        V2MainFragment mf = (V2MainFragment) getFragmentManager().findFragmentByTag(V2MainFragment.TAG);
        if (mf != null) {
            mf.setCurrentFragment(0);
        }
    }

    private void handleBackPressedEvent() {

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    backToHomePage();
                    return true;
                }
                return false;
            }
        });

    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {


        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return FRAGS_ITEMS_NUM;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return EditProfileFragment.newInstance(0, "Page # 1");
                case 1:
                    return EditPasswordFragment.newInstance(1, "Page # 2");

                default:
                    return null;
            }
        }


        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

    public static void setup_background_img(Context context, ImageView image) {

        Random Dice = new Random();
        int n = Dice.nextInt(auth_bg_list.length);

        Glide.with(context)
                .load(AppHelper.loadDrawable(context, auth_bg_list[n]))
                .centerCrop().into(image);

    }
}
