package com.directoriodelicias.apps.delicias.animation;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

/**
 * Created by Directorio on 9/24/2017.
 */

public class Animation {


    public static View startZoomEffect(final View view) {

        view.setVisibility(View.INVISIBLE);

        AnimationSet animation = new AnimationSet(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.8f, 1.0f);

        ScaleAnimation scaleAnimation =
                new ScaleAnimation(0.5f, 1.1f, 0.5f, 1.1f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f);

        final ScaleAnimation defScaleAnimation =
                new ScaleAnimation(1.1f, 1f, 1.1f, 1f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f);

        animation.addAnimation(alphaAnimation);
        animation.addAnimation(scaleAnimation);

        animation.setDuration(400);
        view.startAnimation(animation);
        scaleAnimation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                defScaleAnimation.setDuration(100);
                view.startAnimation(defScaleAnimation);
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {

            }
        });

        return view;


    }


    public static View startCustomZoom(final View view) {

        view.setVisibility(View.INVISIBLE);

        AnimationSet animation = new AnimationSet(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.8f, 1.0f);

        ScaleAnimation scaleAnimation =
                new ScaleAnimation(0.5f, 1.1f, 0.5f, 1.1f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f);

        final ScaleAnimation defScaleAnimation =
                new ScaleAnimation(1.1f, 1f, 1.1f, 1f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f);

        animation.addAnimation(alphaAnimation);
        animation.addAnimation(scaleAnimation);

        animation.setDuration(200);
        view.startAnimation(animation);
        scaleAnimation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                defScaleAnimation.setDuration(100);
                view.startAnimation(defScaleAnimation);
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {

            }
        });

        return view;


    }


    public static View hideWithZoomEffect(final View view) {

        view.setVisibility(View.VISIBLE);

        AnimationSet animation = new AnimationSet(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.5f);

        ScaleAnimation scaleAnimation =
                new ScaleAnimation(1f, 0.6f, 1f, 0.6f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f);

        animation.addAnimation(alphaAnimation);
        animation.addAnimation(scaleAnimation);

        animation.setDuration(200);

        view.startAnimation(animation);
        scaleAnimation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {

            }
        });

        return view;

//        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1, 1.2f);
//        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1, 1.2f);
//        ObjectAnimator scaleAnimation = ObjectAnimator.ofPropertyValuesHolder(APP_ICON_VIEW_LEFT, pvhX, pvhY);
//
//        AnimatorSet setAnimation = new AnimatorSet();
//        setAnimation.play(scaleAnimation);
//        setAnimation.start();

    }


}
