package com.directoriodelicias.apps.delicias.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.events.UnseenNotificationEvent;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class NotificationChatVPFragment extends Fragment {

    public static int NotificationChaCount = 0;
    private ViewPager view_pager;
    private SectionsPagerAdapter viewPagerAdapter;
    private SmartTabLayout tab_layout;
    private Context mContext;
    private View view;

    // newInstance constructor for creating fragment with arguments
    public static NotificationChatVPFragment newInstance(int page, String title) {
        NotificationChatVPFragment fragmentFirst = new NotificationChatVPFragment();
        Bundle args = new Bundle();
        args.putInt("id", page);
        args.putString("title", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.v2_notification_chat_viewpager, container, false);

        mContext = view.getContext();
        ButterKnife.bind(this, view);

        //init component
        initComponent(inflater, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void initComponent(LayoutInflater inflater, View view) {
        view_pager = view.findViewById(R.id.view_pager);

        setupViewPager(view_pager);

        tab_layout = view.findViewById(R.id.tab_layout);
        tab_layout.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {

                TextView txt = (TextView) inflater.inflate(R.layout.custom_tab_icon1, container,
                        false);

                txt.setGravity(Gravity.CENTER);

                if (position == 0) {
                    txt.setText(getString(R.string.notifications));
                } else if (position == 1) {
                    txt.setText(getString(R.string.inbox));
                }

                return txt;
            }
        });


        tab_layout.setViewPager(view_pager);
        tab_layout.setDistributeEvenly(false);




        /*tab_layout.setupWithViewPager(view_pager);

        tab_layout.getTabAt(0).setIcon(R.drawable.ic_notifications_white_24dp);
        tab_layout.getTabAt(1).setIcon(R.drawable.ic_chat_white_18dp);

        // set icon color pre-selected
        tab_layout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);

        //set custom view
        tab_layout.getTabAt(0).setCustomView(R.layout.v2_notification_badge);
        TextView textView = (TextView) tab_layout.getTabAt(0).getCustomView().findViewById(R.id.text);
        ImageView icon = (ImageView) tab_layout.getTabAt(0).getCustomView().findViewById(R.id.icon_badge);
        *//*textView.setText("5");*//*
        icon.setImageResource(R.drawable.ic_notifications_white_24dp);


        //set custom view
        tab_layout.getTabAt(1).setCustomView(R.layout.v2_notification_badge);
        TextView textView2 = (TextView) tab_layout.getTabAt(1).getCustomView().findViewById(R.id.text);
        ImageView icon2 = (ImageView) tab_layout.getTabAt(1).getCustomView().findViewById(R.id.icon_badge);
        *//*textView2.setText("10");*//*
        icon2.setImageResource(R.drawable.ic_chat_white_18dp);


        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //((AppCompatActivity) mContext).getSupportActionBar().setTitle(viewPagerAdapter.getTitle(tab.getPosition()));
                tab.getIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
*/

    }

    // This method will be called when a Notification is posted (in the UI thread for Toast)
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UnseenNotificationEvent event) {
       /* //update notification badge
        if (Integer.valueOf(event.message) > 0) {
            ((TextView) Objects.requireNonNull(tab_layout.getTabAt(0)).getCustomView().findViewById(R.id.text)).setText(event.message);
            ((TextView) Objects.requireNonNull(tab_layout.getTabAt(0)).getCustomView().findViewById(R.id.text)).setVisibility(View.VISIBLE);
        } else
            ((TextView) Objects.requireNonNull(tab_layout.getTabAt(0)).getCustomView().findViewById(R.id.text)).setVisibility(View.GONE);
*/
    }


    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
        viewPagerAdapter.removeAll();
        viewPagerAdapter.addFragment(NotificationFragment.newInstance(1, "Notification"), "Notification");    // index 0
        viewPagerAdapter.addFragment(InboxFragment.newInstance(2, "Chat"), "Chat");    // index 1
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
    }


    private class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public void removeAll() {
            mFragmentList.clear();
        }

        public String getTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }


}
