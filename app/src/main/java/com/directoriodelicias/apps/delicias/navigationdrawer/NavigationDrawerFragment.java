package com.directoriodelicias.apps.delicias.navigationdrawer;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.Services.BusStation;
import com.directoriodelicias.apps.delicias.activities.AboutActivity;
import com.directoriodelicias.apps.delicias.activities.EventsListActivity;
import com.directoriodelicias.apps.delicias.activities.MapStoresListActivity;
import com.directoriodelicias.apps.delicias.activities.SettingActivity;
import com.directoriodelicias.apps.delicias.activities.SplashActivity;
import com.directoriodelicias.apps.delicias.activities.StoresListActivity;
import com.directoriodelicias.apps.delicias.adapter.navigation.SimpleListAdapterNavDrawer;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.classes.HeaderItem;
import com.directoriodelicias.apps.delicias.classes.Item;
import com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController;
import com.directoriodelicias.apps.delicias.customView.AdvancedWebViewActivity;
import com.directoriodelicias.apps.delicias.fragments.V2MainFragment;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class NavigationDrawerFragment extends Fragment implements SimpleListAdapterNavDrawer.ClickListener {

    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "learned_user_drawer";
    public static int INT_CHAT_BOX = 5;
    private static DrawerLayout mDrawerLayout;
    List<Item> listItems = Arrays.asList();
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private boolean mUserLearedLayout;
    private boolean mFromSaveInstanceState;
    private View containerView;
    private RecyclerView drawerList;


    //init request http
    private SimpleListAdapterNavDrawer adapter;

    public static DrawerLayout getInstance() {
        return mDrawerLayout;
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(preferenceName, preferenceValue);
        edit.apply();

    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mUserLearedLayout = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSaveInstanceState = true;
        }

       /* if(SessionsController.isLogged())
             user = SessionsController.getSession().getUser();*/

    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.navigation_drawer_content, container, false);

        rootView.setClickable(true);

        drawerList = rootView.findViewById(R.id.drawerLayout);
        drawerList.setVisibility(View.VISIBLE);

        adapter = new SimpleListAdapterNavDrawer(getActivity(), getData());

        drawerList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        drawerList.setLayoutManager(mLayoutManager);
        drawerList.setAdapter(adapter);

        adapter.setClickListener(this);

        return rootView;

    }

    public List<Item> getData() {

        listItems = new ArrayList<Item>();


        HeaderItem header_item = new HeaderItem();
        header_item.setName(getResources().getString(R.string.Home));
        header_item.setEnabled(true);


        Item homeItem = new Item();
        homeItem.setName(getResources().getString(R.string.Home));
        homeItem.setIconDraw(MaterialDrawableBuilder.IconValue.HOME);
        homeItem.setID(Menu.HOME_ID);

        Item webdashboard = new Item();
        webdashboard.setName(getResources().getString(R.string.ManageThings));
        webdashboard.setIconDraw(MaterialDrawableBuilder.IconValue.WEB);
        webdashboard.setID(Menu.MANAGE_YOUr_BUSINESS);


        Item mapStoresItem = new Item();
        mapStoresItem.setName(getResources().getString(R.string.MapStoresMenu));
        mapStoresItem.setIconDraw(MaterialDrawableBuilder.IconValue.MAP_MARKER_RADIUS);
        mapStoresItem.setID(Menu.MAP_STORES);

        Item savesItem = new Item();
        //savesItem.setName(getResources().getString(R.string.Favoris)+" /*("+bookmaeks_count+")*/");
        savesItem.setName(getResources().getString(R.string.Favoris));
        savesItem.setIconDraw(MaterialDrawableBuilder.IconValue.BOOKMARK_CHECK);
        savesItem.setID(Menu.FAV);


        Item EventLikeItem = new Item();
        EventLikeItem.setName(getResources().getString(R.string.EventLike));
        EventLikeItem.setIconDraw(MaterialDrawableBuilder.IconValue.ALARM_CHECK);
        EventLikeItem.setID(Menu.MY_EVENT);


        Item aboutItem = new Item();
        aboutItem.setName(getResources().getString(R.string.about));
        aboutItem.setIconDraw(MaterialDrawableBuilder.IconValue.INFORMATION_OUTLINE);
        aboutItem.setID(Menu.ABOUT);


        Item settingItem = new Item();
        settingItem.setName(getResources().getString(R.string.Settings));
        settingItem.setIconDraw(MaterialDrawableBuilder.IconValue.SETTINGS);
        settingItem.setID(Menu.SETTING);


        if (header_item.isEnabled())
            listItems.add(header_item);

        //HOME
        if (homeItem.isEnabled())
            listItems.add(homeItem);

        //GEO Stores
        listItems.add(mapStoresItem);


        //Settings
        if (AppConfig.ENABLE_WEB_DASHBOARD) {
            listItems.add(webdashboard);
        }

        if (SessionsController.isLogged()) {

            Item logout = new Item();
            //savesItem.setName(getResources().getString(R.string.Favoris)+" ");
            logout.setName(getResources().getString(R.string.Logout));
            logout.setIconDraw(MaterialDrawableBuilder.IconValue.LOGOUT);
            logout.setID(Menu.LOGOUT);

            listItems.add(logout);

        }


        //About US
        if (aboutItem.isEnabled())
            listItems.add(aboutItem);

        //Settings
        if (settingItem.isEnabled())
            listItems.add(settingItem);


        return listItems;
    }

    public void setUp(int FragId, DrawerLayout drawerlayout, final Toolbar toolbar) {

        containerView = getView().findViewById(FragId);
        mDrawerLayout = drawerlayout;

        //mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                drawerlayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
                if (!mUserLearedLayout) {
                    mUserLearedLayout = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearedLayout + "");
                }


                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }


            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

            }
        };

        if (!mUserLearedLayout && !mFromSaveInstanceState) {
            mDrawerLayout.closeDrawer(containerView);
        }


        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();

            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INT_CHAT_BOX) {

            adapter.getData().get(1).setNotify(0);
            adapter.update(1, adapter.getData().get(1));

        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void itemClicked(View view, int position) {


        Item item = adapter.getData().get(position);
        if (item instanceof Item) {
            switch (item.getID()) {
                case Menu.HOME_ID:

                    if (mDrawerLayout != null)
                        mDrawerLayout.closeDrawers();
                    V2MainFragment mf = (V2MainFragment) getFragmentManager().findFragmentByTag(V2MainFragment.TAG);
                    mf.setCurrentFragment(0);

                    break;
                case Menu.MANAGE_YOUr_BUSINESS:

                    if (!AppConfig.ENABLE_WEB_DASHBOARD)
                        break;

                    if (mDrawerLayout != null)
                        mDrawerLayout.closeDrawers();

                    //https://test.Directorio.com/nbs2/index.php/business_manager/index?session={username}&lang={lang}&
                    String url = AppConfig.BASE_URL + "/business_manager/index?uri=businesses&lang=" + Locale.getDefault().getLanguage();
                    if (SessionsController.isLogged() && SessionsController.getSession() != null && SessionsController.getSession().getUser() != null) {
                        url = url + "&session=" +
                                SessionsController.getSession().getUser().getUsername();
                    }


                    Intent intent = new Intent(getActivity(), AdvancedWebViewActivity.class);
                    intent.putExtra("BMLink", url);
                    startActivity(intent);

                    if (AppConfig.APP_DEBUG)
                        Log.e("BMLink", url);


                    break;

                case Menu.FAV:

                    Intent intStore = new Intent(getActivity(), StoresListActivity.class);
                    intStore.putExtra("fav", -1);
                    startActivity(intStore);
                    getActivity().overridePendingTransition(R.anim.lefttoright_enter, R.anim.lefttoright_exit);

                    break;
                case Menu.MY_EVENT:

                    Intent intEvent = new Intent(getActivity(), EventsListActivity.class);
                    intEvent.putExtra("isLiked", -1);
                    startActivity(intEvent);
                    getActivity().overridePendingTransition(R.anim.lefttoright_enter, R.anim.lefttoright_exit);

                    break;
                case Menu.ABOUT:

                    if (mDrawerLayout != null)
                        mDrawerLayout.closeDrawers();

                    startActivity(new Intent(getActivity(), AboutActivity.class));
                    getActivity().overridePendingTransition(R.anim.lefttoright_enter, R.anim.lefttoright_exit);

                    break;

                case Menu.SETTING:

                    if (mDrawerLayout != null)
                        mDrawerLayout.closeDrawers();

                    startActivity(new Intent(getActivity(), SettingActivity.class));
                    getActivity().overridePendingTransition(R.anim.lefttoright_enter, R.anim.lefttoright_exit);

                    break;
                case Menu.MAP_STORES:
                    startActivity(new Intent(getActivity(), MapStoresListActivity.class));
                    getActivity().overridePendingTransition(R.anim.lefttoright_enter, R.anim.lefttoright_exit);

                    break;
                case Menu.LOGOUT:

                    SessionsController.logOut();
                    ActivityCompat.finishAffinity(getActivity());
                    startActivity(new Intent(getActivity(), SplashActivity.class));

                    break;


            }
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        BusStation.getBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusStation.getBus().unregister(this);
    }

    @Subscribe
    public void onToggle(NavigationDrawerEvent object) {
        if (object.state == 1) {

            if (mDrawerLayout.isDrawerOpen(containerView))
                mDrawerLayout.closeDrawer(containerView);
            else
                mDrawerLayout.openDrawer(containerView);

        }
    }

    private static class Menu {
        static final int HOME_ID = 1;
        static final int MANAGE_YOUr_BUSINESS = 2;
        static final int MAP_STORES = 3;
        static final int FAV = 4;
        static final int MY_EVENT = 5;
        static final int ABOUT = 6;
        static final int SETTING = 7;
        static final int LOGOUT = 9;
    }

    public static class NavigationDrawerEvent {
        private int state = 0;

        public NavigationDrawerEvent(int state) {
            this.state = state;
        }
    }


}
