package com.directoriodelicias.apps.delicias.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.MainActivity;
import com.directoriodelicias.apps.delicias.appconfig.AppContext;
import com.directoriodelicias.apps.delicias.classes.Images;
import com.directoriodelicias.apps.delicias.classes.SimpleImage;
import com.directoriodelicias.apps.delicias.views.Blur;
import com.directoriodelicias.apps.delicias.views.BlurImageView;
import com.directoriodelicias.apps.delicias.views.HackyViewPager;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SlideshowDialogFragment extends DialogFragment {
    public static String TYPE_ANY = "any";
    @BindView(R.id.menu)
    ImageButton menu;
    Toolbar toolbar;
    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private ArrayList<SimpleImage> images;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount, lblTitle, lblDate;
    private int selectedPosition = 0;
    private int currentPosition = 0;
    //	page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {


        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
            currentPosition = position;

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private TextView APP_TITLE_VIEW = null;
    private TextView APP_DESC_VIEW = null;

    public static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    public SlideshowDialogFragment show(FragmentActivity activity, List<Images> imagesList) {

        showDialog(activity, imagesList, 0, TYPE_ANY);
        return this;
    }

    public SlideshowDialogFragment show(FragmentActivity activity, List<Images> imagesList, int position) {
        showDialog(activity, imagesList, position, TYPE_ANY);
        return this;
    }

    public SlideshowDialogFragment show(FragmentActivity activity, List<Images> imagesList, int position, String type) {

        showDialog(activity, imagesList, position, type);
        return this;
    }

    public void showDialog(FragmentActivity activity, List<Images> imagesList, int position, String type) {

        ArrayList<SimpleImage> list =
                SimpleImage.convertToSimpleImage(imagesList);

        Bundle bundle = new Bundle();
        bundle.putSerializable("images", list);
        bundle.putInt("position", position);

        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        setArguments(bundle);
        show(ft, "slideshow");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
        ButterKnife.bind(this, v);


        // setupToolbar(v);

        viewPager = (HackyViewPager) v.findViewById(R.id.viewpager);
        lblCount = v.findViewById(R.id.lbl_count);
        lblTitle = v.findViewById(R.id.title);
        lblDate = v.findViewById(R.id.date);

        images = (ArrayList<SimpleImage>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");

        if (AppContext.DEBUG) {
            Log.e(TAG, "position: " + selectedPosition);
            Log.e(TAG, "images size: " + images.size());
        }


        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        if (images.size() > 1)
            setCurrentItem(selectedPosition);
        else {
            lblCount.setVisibility(View.GONE);
            setCurrentItem(0);
        }

        return v;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    private void displayMetaInfo(int position) {
        lblCount.setText((position + 1) + "/" + images.size());

        //lblTitle.setText(image.getName());
        // lblDate.setText(image.getTimestamp());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    public void setupToolbar(View rootView) {

        toolbar = rootView.findViewById(R.id.app_bar);
        APP_TITLE_VIEW = toolbar.findViewById(R.id.toolbar_title);
        APP_DESC_VIEW = toolbar.findViewById(R.id.toolbar_subtitle);


        APP_DESC_VIEW.setVisibility(View.GONE);
        APP_TITLE_VIEW.setVisibility(View.GONE);

        int baseColor = ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null);
        toolbar.getBackground().setAlpha(0);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle the menu item


                return true;
            }
        });
        //toolbar.inflateMenu(R.menu.slider_menu);

    }

    //	adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);

            final ImageView imageViewPreview = view.findViewById(R.id.image_preview);
            final BlurImageView mBlurImageView = view.findViewById(R.id.placeholder);
            final ProgressBar mProgressBar = view.findViewById(R.id.pBar);

            imageViewPreview.setVisibility(View.INVISIBLE);
            mBlurImageView.setVisibility(View.VISIBLE);

            SimpleImage image = images.get(position);


            int width = image.getWidth();
            int height = image.getHeight();
            float ratio = (float) height / (float) width;
            float heightFloat = ((float) MainActivity.width) * ratio;

            mBlurImageView.getLayoutParams().height = (int) heightFloat;
            mBlurImageView.getLayoutParams().width = MainActivity.width;

            Glide.with(getActivity()).load(image.getSmallUrl()).transform(new Blur(getActivity(), 20)).into(mBlurImageView);


            //mBlurImageView.setVisibility(View.GONE);

            final PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageViewPreview);
            Glide.with(getActivity())
                    .load(image.getLargeUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            mProgressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Error showLoading pictures", Toast.LENGTH_LONG).show();

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            mBlurImageView.setVisibility(View.INVISIBLE);
                            imageViewPreview.setVisibility(View.VISIBLE);
                            photoViewAttacher.update();
                            mProgressBar.setVisibility(View.GONE);

                            (new Handler()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mBlurImageView.setVisibility(View.INVISIBLE);
                                }
                            }, 500);
                            return false;
                        }

                    })
                    .into(imageViewPreview/*, new Callback() {
                        @Override
                        public void onSuccess() {
                            mBlurImageView.setVisibility(View.INVISIBLE);
                            imageViewPreview.setVisibility(View.VISIBLE);
                            photoViewAttacher.update();
                            mProgressBar.setVisibility(View.GONE);

                            (new Handler()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mBlurImageView.setVisibility(View.INVISIBLE);
                                }
                            },500);
                        }

                        @Override
                        public void onError() {
                            mProgressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(),"Error showLoading pictures",Toast.LENGTH_LONG).show();
                        }
                    }*/);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
