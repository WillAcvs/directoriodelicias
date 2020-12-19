package com.directoriodelicias.apps.delicias.fragments;

import android.app.Dialog;
import android.content.Context;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.directoriodelicias.apps.delicias.R;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;

/**
 * Created by Directorio on 11/18/2017.
 */

public class SearchDialog extends Dialog {

    private static int mOldDistance = -1;
    private static String mOldValue = "";
    private TextView doSearch;
    private SeekBar mDistanceRange;
    private TextView mDistanceText;
    private MaterialAutoCompleteTextView searchEditText;
    private TextView searchBy;
    private Listener mListener;

    public SearchDialog(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_search);

        doSearch = findViewById(R.id.doSearch);
        mDistanceRange = findViewById(R.id.distance);
        mDistanceText = findViewById(R.id.md);
        searchEditText = findViewById(R.id.search);
        searchBy = findViewById(R.id.searchBy);


        if (mOldDistance == -1) {
            int radius = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("distance_value", 100);
            mOldDistance = radius;
        }

        String val = String.valueOf(mOldDistance);
        if (mOldDistance == 100) {
            val = "+" + mOldDistance;
        }

        String msg = String.format(getContext().getString(R.string.settings_notification_distance_dis), val);
        mDistanceText.setText(msg);
        mDistanceRange.setProgress(mOldDistance);
        searchEditText.setText(mOldValue);

        mDistanceRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                String val = String.valueOf(progress);
                if (progress == 100) {
                    val = "+" + progress;
                }

                String msg = String.format(getContext().getString(R.string.settings_notification_distance_dis), val);
                mDistanceText.setText(msg);
                mOldDistance = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        doSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onSearchClicked(SearchDialog.this, searchEditText.getText().toString(), mOldDistance);
                }
            }
        });

    }

    public static SearchDialog newInstance(Context context) {
        return new SearchDialog(context);
    }

    public SearchDialog setHeader(String text) {
        searchBy.setText(text);
        return this;
    }

    public SearchDialog setOnSearchListener(Listener l) {
        if (mListener == null) {
            mListener = l;
        }

        return this;
    }

    public void showDialog() {
        if (!isShowing())
            show();
    }


//    public void dismissDialog(){
//
//        try {
//            if(isShowing())
//                dismiss();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }

    public interface Listener {
        void onSearchClicked(SearchDialog mSearchDialog, String value, int radius);
    }


}
