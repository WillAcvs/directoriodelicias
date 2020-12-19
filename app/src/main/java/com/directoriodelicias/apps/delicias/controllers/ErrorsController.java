package com.directoriodelicias.apps.delicias.controllers;

import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.directoriodelicias.apps.delicias.utils.MessageDialog;

/**
 * Created by Droideve on 2/5/2018.
 */

public class ErrorsController {


    public static void serverPermissionError(FragmentActivity activity) {

        if (MessageDialog.getInstance() != null) {
            if (!MessageDialog.getInstance().isShowen()) {

                MessageDialog.newDialog(activity).onCancelClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MessageDialog.getInstance().hide();
                    }
                }).onOkClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MessageDialog.getInstance().hide();
                    }
                }).setContent("You don't have permission to server! try to reinstall the app").show();

            }
        } else {

            MessageDialog.newDialog(activity).onCancelClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageDialog.getInstance().hide();
                }
            }).onOkClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MessageDialog.getInstance().hide();
                }
            }).setContent("You don't have permission to server! try to reinstall the app").show();

        }


    }
}
