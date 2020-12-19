package com.directoriodelicias.apps.delicias.load_manager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;


public class ViewManager {

    private View error;
    private View loading;
    private View content;
    private View empty;
    private CustomView mCustomView;
    private int shortAnimationDuration = 1000;
    private CallViewListener cvl;

    public ViewManager(Context context) {
    }

    public View getError() {
        return error;
    }

    public View getLoading() {
        return loading;
    }

    public View getContent() {
        return content;
    }

    public View getEmpty() {
        return empty;
    }

    public void setEmptyView(View empty) {
        this.empty = empty;
    }

    public void showError() {

        if (checkAll()) {
            this.empty.setVisibility(View.GONE);
            this.content.setVisibility(View.GONE);
            this.loading.setVisibility(View.GONE);

            if (!this.error.isShown())
                this.error.animate().alpha(1f).setDuration(shortAnimationDuration)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                error.setVisibility(View.VISIBLE);
                            }
                        }).start();


            if (cvl != null)
                cvl.onErrorShown();
        }


    }

    public void showContent() {
        if (checkAll()) {

            if (!this.content.isShown())
                this.content.setVisibility(View.VISIBLE);

            this.loading.animate().alpha(0f).setDuration(shortAnimationDuration / 2)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            loading.setVisibility(View.GONE);
                        }
                    }).start();

            this.error.setVisibility(View.GONE);
            this.empty.setVisibility(View.GONE);

            if (cvl != null)
                cvl.onContentShown();
        }
    }

    public void showLoading() {
        if (checkAll()) {


            this.content.setVisibility(View.GONE);

            if (!this.loading.isShown())
                this.loading.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loading.setVisibility(View.VISIBLE);
                    }
                });
            this.error.setVisibility(View.GONE);
            this.empty.setVisibility(View.GONE);

            if (cvl != null)
                cvl.onLoadingShown();
        }
    }

    public void showEmpty() {
        if (checkAll()) {
            this.content.setVisibility(View.GONE);
            this.loading.setVisibility(View.GONE);
            this.error.setVisibility(View.GONE);

            if (!this.empty.isShown())
                this.empty.animate()
                        .alpha(1f)
                        .setDuration(shortAnimationDuration)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                empty.setVisibility(View.VISIBLE);
                            }
                        });

            if (cvl != null)
                cvl.onEmptyShown();
        }
    }

    private boolean checkAll() {

        if (error == null) {

            new ManagerException("View showError is null");
            return false;
        } else if (loading == null) {


            new ManagerException("View showLoading is null");
            return false;
        } else if (content == null) {


            new ManagerException("View noloading is null");

            return false;
        } else if (empty == null) {

            new ManagerException("View showEmpty is null");
            return false;
        }


        return true;
    }

    public void setListener(CallViewListener cvl) {
        this.cvl = cvl;
    }


    public void setErrorView(View error) {
        this.error = error;
        //if(this.error!=null)
        // this.error.animate().alpha(0.0f).start();
    }

    public void setLoadingView(View loading) {
        this.loading = loading;
        // if(this.loading!=null)
        //this.loading.animate().alpha(0.0f).start();
    }

    public void setContentView(View noLoading) {
        this.content = noLoading;

        // if(this.content!=null)
        //this.content.animate().alpha(0.0f).start();
    }


    public void setCustumizeView(CustomView cv) {
        mCustomView = cv;
        if (checkAll()) {
            if (mCustomView != null) {
                mCustomView.customErrorView(this.error);
                mCustomView.customLoadingView(this.loading);
                mCustomView.customEmptyView(this.empty);
            }
        }

    }


    public interface CustomView {
        void customErrorView(View v);

        void customLoadingView(View v);

        void customEmptyView(View v);
    }


    public interface CallViewListener {
        void onContentShown();

        void onErrorShown();

        void onEmptyShown();

        void onLoadingShown();
    }

    class ManagerException extends Exception {
        public ManagerException(String msg) {
            super(msg);
        }
    }
}
