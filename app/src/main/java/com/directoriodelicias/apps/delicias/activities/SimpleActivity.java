package com.directoriodelicias.apps.delicias.activities;

import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.utils.NSLog;
import com.nirhart.parallaxscroll.views.ParallaxScrollView;

public class SimpleActivity extends AppCompatActivity {

    private Toolbar myAppBar = null;

    public Toolbar getAppBar() {
        return myAppBar;
    }

    public TextView getAppBarTitle() {
        return myAppBar.findViewById(R.id.toolbar_title);
    }

    public TextView getAppBarSubtitle() {
        return myAppBar.findViewById(R.id.toolbar_subtitle);
    }

    public void setupToolbar(Toolbar appbar) {

        setSupportActionBar(appbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        myAppBar = appbar;

    }


    public TextView setAppBarTitle(String title) {

        if (myAppBar != null) {
            TextView toolbar_title = myAppBar.findViewById(R.id.toolbar_title);
            toolbar_title.setText(title);
            return toolbar_title;
        } else {

            new Exception("Please call setuptoolbar before");
        }

        return null;
    }

    public TextView setAppBarSubTitle(String title) {

        if (myAppBar != null) {
            TextView toolbar_subtitle = myAppBar.findViewById(R.id.toolbar_subtitle);
            toolbar_subtitle.setText(title);
            return toolbar_subtitle;
        } else {
            new Exception("Please call setuptoolbar before");
        }

        return null;
    }

    protected void changeViewSize(LinearLayout view, int s) {

        //define height header size
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int height = metrics.heightPixels;

        height = height / s;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        view.setLayoutParams(lp);


    }

    protected void setupScrollNHeader(NestedScrollView scrollView, LinearLayout header, int s) {
        setupScrollNHeader(scrollView, header, s);
    }

    protected void setupScrollNHeader(NestedScrollView scrollView, LinearLayout header, int s, View viewWithAlpha) {

        //define height header size
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int height = metrics.heightPixels;

        height = height / s;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        header.setLayoutParams(lp);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int finalHeight = height;
            int finalHeight1 = height;
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    NSLog.e("onScrollChange", "scrollX=" + scrollX + ";scrollY=" + scrollY + " height_header=" + finalHeight);

                    if (scrollY < (finalHeight1 / 3)) {

                        getAppBar().setBackground(getDrawable(R.drawable.gradient_bg_top_to_bottom_70));
                        getAppBarTitle().setVisibility(View.GONE);

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, finalHeight - (scrollY / 2));
                        header.setLayoutParams(lp);

                    } else {

                        getAppBar().setBackgroundColor(getColor(R.color.colorPrimary));
                        getAppBarTitle().setVisibility(View.VISIBLE);

                    }


                    if (scrollY < (finalHeight1 / 2)) {
                        if (viewWithAlpha != null) {
                            viewWithAlpha.animate().alpha(1.0f).setDuration(200);
                        }
                    } else {
                        if (viewWithAlpha != null) {
                            //viewWithAlpha.setVisibility(View.GONE);
                            viewWithAlpha.animate().alpha(0.0f).setDuration(200);
                        }
                    }

                }
            });
        }
    }

    protected void setupScrollNHeader(ParallaxScrollView scrollView, LinearLayout header, int s, View viewWithAlpha) {

        //define height header size
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int height = metrics.heightPixels;

        height = height / s;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        header.setLayoutParams(lp);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int finalHeight = height;
            int finalHeight1 = height;
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    NSLog.e("onScrollChange", "scrollX=" + scrollX + ";scrollY=" + scrollY + " height_header=" + finalHeight);

                    if (scrollY < (finalHeight1 / 3)) {

                        getAppBar().setBackground(getDrawable(R.drawable.gradient_bg_top_to_bottom_70));
                        getAppBarTitle().setVisibility(View.GONE);

                    } else {

                        getAppBar().setBackgroundColor(getColor(R.color.colorPrimary));
                        getAppBarTitle().setVisibility(View.VISIBLE);

                    }


                    if (scrollY < (finalHeight1 / 2)) {
                        if (viewWithAlpha != null) {
                            viewWithAlpha.animate().alpha(1.0f).setDuration(200);
                        }
                    } else {
                        if (viewWithAlpha != null) {
                            //viewWithAlpha.setVisibility(View.GONE);
                            viewWithAlpha.animate().alpha(0.0f).setDuration(200);
                        }
                    }

                }
            });
        }
    }


    public static class SimpleHeaderSize {
        public static int FULL = 1;
        public static int HALF = 2;
        public static int SMALL = 3;
    }


}




