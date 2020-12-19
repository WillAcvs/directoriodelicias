package com.directoriodelicias.apps.delicias.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.OffersListActivity;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.classes.Offer;
import com.directoriodelicias.apps.delicias.utils.OfferUtils;

import java.util.List;

/**
 * Created by Droideve on 11/12/2017.
 */

public class StoreOfferAdapter {

    private Context context;
    private List<Offer> list;
    private int resLayout;
    private LayoutInflater inflater;
    private Listener mListener;

    public StoreOfferAdapter(Context context) {
        this.context = context;
        try {
            inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
        }

    }

    public static StoreOfferAdapter newInstance(Context context) {
        return new StoreOfferAdapter(context);
    }

    public StoreOfferAdapter load(List<Offer> list) {
        this.list = list;
        return this;
    }

    public StoreOfferAdapter inflate(int resLayout) {

        this.resLayout = resLayout;

        return this;
    }

    public StoreOfferAdapter into(LinearLayout rootView) {
        loop(rootView);
        return this;
    }

    private View prepareView(View layout, Offer offer) {

        ImageView image = layout.findViewById(R.id.image);
        TextView title = layout.findViewById(R.id.name);
        TextView detail = layout.findViewById(R.id.detail);
        TextView price = layout.findViewById(R.id.price);

        title.setText(offer.getName());
        detail.setText(Html.fromHtml(offer.getDescription()));

        if (offer.getOffer_value() >= 0) {
            price.setText(
                    OfferUtils.parseCurrencyFormat(
                            offer.getOffer_value(),
                            offer.getCurrency()

                    ));
        } else {
            price.setText(offer.getOffer_value() + "%");
        }

        if (offer.getOffer_value() == 0 || offer.getOffer_value() == 0) {
            price.setText(context.getString(R.string.promo));
        }

        try {

            Glide.with(context).load(offer.getImages()
                    .getUrl200_200()).centerCrop()
                    .into(image);

        } catch (Exception e) {

        }


        return layout;
    }

    public void loop(LinearLayout rootView) {

        rootView.removeAllViews();
        if (inflater != null) {

            for (int i = 0; i < list.size(); i++) {

                View layout = inflater.inflate(resLayout, null);

                final int finalI = i;
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null)
                            mListener
                                    .onOfferClicked(finalI);
                    }
                });

                rootView.addView(prepareView(layout, list.get(i)));

                if (i == 5)
                    break;
            }
        }

        if (list.size() >= 7) {

            try {

                View layout = inflater.inflate(R.layout.item_store_review_load_more, null);
                Button button = layout.findViewById(R.id.loadMore);
                button.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, OffersListActivity.class);
                        intent.putExtra("store_id", list.get(0).getStore_id());
                        context.startActivity(intent);

                    }
                });

                rootView.addView(layout);

            } catch (Exception e) {
                if (AppConfig.APP_DEBUG)
                    e.printStackTrace();
            }


        }

    }

    public void setOnistener(Listener l) {
        mListener = l;
    }

    public interface Listener {
        void onOfferClicked(int position);
    }
}
