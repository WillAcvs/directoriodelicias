package com.directoriodelicias.apps.delicias.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.ReviewsActivity;
import com.directoriodelicias.apps.delicias.animation.ImageLoaderAnimation;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.classes.Images;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.List;

/**
 * Created by Directorio on 11/12/2017.
 */

public class GalleryAdapter {

    private Context context;
    private List<Images> list;
    private int resLayout;
    private LayoutInflater inflater;

    private int int_id;
    private String type;

    public GalleryAdapter(Context context) {
        this.context = context;
        try {
            inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
        }
    }

    public static GalleryAdapter newInstance(Context context) {
        return new GalleryAdapter(context);
    }

    public GalleryAdapter load(List<Images> list) {
        this.list = list;
        return this;
    }

    public GalleryAdapter inflate(int resLayout) {

        this.resLayout = resLayout;

        return this;
    }


    public GalleryAdapter into(LinearLayout rootView) {
        loop(rootView);
        return this;
    }

    public GalleryAdapter setType(String type) {
        this.type = type;
        return this;
    }

    public GalleryAdapter setId(int id) {
        this.int_id = id;
        return this;
    }

    private View prepareView(View layout, Images mImage) {

        CircularImageView image = layout.findViewById(R.id.image);
        Glide.with(context).load(mImage.getUrl200_200()).placeholder(ImageLoaderAnimation.glideLoader(context)).centerCrop().into(image);

        return layout;
    }

    public void loop(LinearLayout rootView) {

        rootView.removeAllViews();

        if (inflater != null) {

            for (int i = 0; i < list.size(); i++) {

                if (AppConfig.APP_DEBUG)
                    Log.i("StoreReviewAdapter", "Put it " + list.get(i).getUrl200_200());

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
                        intent.putExtra("int_id", int_id);
                        intent.putExtra("type", type);
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
