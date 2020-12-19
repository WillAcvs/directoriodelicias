package com.directoriodelicias.apps.delicias.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.ReviewsActivity;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.classes.Review;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.List;

/**
 * Created by Droideve on 11/12/2017.
 */

public class StoreReviewsAdapter {

    private Context context;
    private List<Review> list;
    private int resLayout;
    private LayoutInflater inflater;

    public StoreReviewsAdapter(Context context) {
        this.context = context;
        try {
            inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
        }
    }

    public static StoreReviewsAdapter newInstance(Context context) {
        return new StoreReviewsAdapter(context);
    }

    public StoreReviewsAdapter load(List<Review> list) {
        this.list = list;
        return this;
    }

    public StoreReviewsAdapter inflate(int resLayout) {

        this.resLayout = resLayout;

        return this;
    }


    public StoreReviewsAdapter into(LinearLayout rootView) {
        loop(rootView);
        return this;
    }


    private View prepareView(View layout, Review mReview) {

        CircularImageView image = layout.findViewById(R.id.image);
        TextView title = layout.findViewById(R.id.name);
        TextView detail = layout.findViewById(R.id.detail);
        RatingBar mRatingBar = layout.findViewById(R.id.ratingBar);

        title.setText(mReview.getPseudo());
        detail.setText(mReview.getReview());

        Glide.with(context).load(mReview.getImage()).placeholder(R.drawable.profile_placeholder).centerCrop().into(image);
        mRatingBar.setRating((float) mReview.getRate());

        return layout;
    }

    public void loop(LinearLayout rootView) {

        rootView.removeAllViews();

        if (inflater != null) {

            for (int i = 0; i < list.size(); i++) {

                if (AppConfig.APP_DEBUG)
                    Log.i("StoreReviewAdapter", "Put it " + list.get(i).getReview());

                View layout = inflater.inflate(resLayout, null);

                final int finalI = i;
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* if(mListener!=null)
                            mListener
                                    .onReviewClicked(finalI);*/
                    }
                });
                rootView.addView(prepareView(layout, list.get(i)));

                if (i == 5)
                    break;
            }


            if (list.size() >= 7) {

                View layout = inflater.inflate(R.layout.item_store_review_load_more, null);
                Button button = layout.findViewById(R.id.loadMore);
                button.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, ReviewsActivity.class);
                        intent.putExtra("store_id", list.get(0).getStore_id());
                        context.startActivity(intent);

                    }
                });

                rootView.addView(layout);

            }

        }


    }


    public interface Listener {
    }
}
