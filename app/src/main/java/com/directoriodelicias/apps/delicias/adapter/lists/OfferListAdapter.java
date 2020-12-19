package com.directoriodelicias.apps.delicias.adapter.lists;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.directoriodelicias.apps.delicias.AppController;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.animation.ImageLoaderAnimation;
import com.directoriodelicias.apps.delicias.classes.Offer;
import com.directoriodelicias.apps.delicias.utils.OfferUtils;
import com.directoriodelicias.apps.delicias.utils.Utils;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.text.DecimalFormat;
import java.util.List;

import io.realm.RealmList;


public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.mViewHolder> {


    private LayoutInflater infalter;
    private List<Offer> data;
    private Context context;
    private ClickListener clickListener;
    private boolean isHorizontalList = false;
    private float width = 0, height = 0;

    public OfferListAdapter(Context context, List<Offer> data, boolean isHorizontalList) {
        this.data = data;
        this.infalter = LayoutInflater.from(context);
        this.context = context;
        this.isHorizontalList = isHorizontalList;
    }

    public OfferListAdapter(Context context, List<Offer> data, boolean isHorizontalList, float width, float height) {
        this.data = data;
        this.infalter = LayoutInflater.from(context);
        this.context = context;
        this.isHorizontalList = isHorizontalList;
        this.width = width;
        this.height = height;
    }

    @Override
    public OfferListAdapter.mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = null;
        if (isHorizontalList) rootView = infalter.inflate(R.layout.v2_item_offer, parent, false);
        else rootView = infalter.inflate(R.layout.fragment_custom_item_offer, parent, false);


        mViewHolder holder = new mViewHolder(rootView);

        return holder;
    }


    @Override
    public void onBindViewHolder(OfferListAdapter.mViewHolder holder, int position) {

        if (!isHorizontalList) {
          /*  int size = (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);

            if (Configuration.SCREENLAYOUT_SIZE_XLARGE == size) {
                holder.image.getLayoutParams().height = (int) (MainActivity.width / 3);
            } else {
                holder.image.getLayoutParams().height = (int) (MainActivity.width / 2);
            }*/
        }

        /*if (isHorizontalList) {

            Drawable locationDrawable = new IconicsDrawable(context)
                    .icon(CommunityMaterial.Icon.cmd_pin)
                    .color(ResourcesCompat.getColor(context.getResources(), R.color.white, null))
                    .sizeDp(10);

            holder.featured.setText("");
            holder.featured.setCompoundDrawables(locationDrawable, null, null, null);

        } else {
            holder.featured.setText(context.getString(R.string.featured));
            holder.featured.setCompoundDrawables(null, null, null, null);
        }*/


        if (height > 0 && width > 0) {
            //set set the dp dimention
            int dp1 = Utils.dip2pix(context, 1);
            CardView.LayoutParams params = new CardView.LayoutParams((int) width, (int) height);
            params.setMargins((5 * dp1), (5 * dp1), (5 * dp1), (5 * dp1));
            holder.itemView.setLayoutParams(params);
        }


        if (data.get(position).getValue_type() != null && !data.get(position).getValue_type().equals("")) {
            if (data.get(position).getValue_type().equalsIgnoreCase("Percent") && (data.get(position).getOffer_value() > 0 || data.get(position).getOffer_value() < 0)) {
                DecimalFormat decimalFormat = new DecimalFormat("#0");
                holder.offer.setText(decimalFormat.format(data.get(position).getOffer_value()) + "%");
            } else {
                if (data.get(position).getValue_type().equalsIgnoreCase("Price") && data.get(position).getOffer_value() != 0) {

                    holder.offer.setText(OfferUtils.parseCurrencyFormat(
                            data.get(position).getOffer_value(),
                            data.get(position).getCurrency()));

                } else {
                    holder.offer.setText(context.getString(R.string.promo));
                }
            }
        }

        if (data.get(position).getValue_type().equalsIgnoreCase("unspecifie")) {
            holder.offer.setText(context.getString(R.string.promo));

        }


        //disable distance indicator

        /*if (data.get(position).getDistance() != null) {
            String distanceFormated = "N/A";
            if (data.get(position).getDistance() > 0) {

                String symbole = com.directoriodelicias.apps.delicias.utils.Utils.getDistanceBy(
                        data.get(position).getDistance()
                );
                String distance = com.directoriodelicias.apps.delicias.utils.Utils.preparDistance(
                        data.get(position).getDistance()
                );


                distanceFormated = String.format(context.getString(R.string.offerIn), distance + " " + symbole.toUpperCase());
            }
            if (!isHorizontalList) holder.distance.setText(String.format(distanceFormated));
        }*/

        holder.name.setText(data.get(position).getName());
        holder.description.setText(data.get(position).getStore_name());

        Drawable locationDrawable = new IconicsDrawable(context)
                .icon(CommunityMaterial.Icon.cmd_map_marker)
                .color(ResourcesCompat.getColor(context.getResources(), R.color.white, null))
                .sizeDp(12);

        if (isHorizontalList) {
            holder.description.setCompoundDrawables(null, null, null, null);
        } else {
            if (!AppController.isRTL())
                holder.description.setCompoundDrawables(locationDrawable, null, null, null);
            else
                holder.description.setCompoundDrawables(null, null, locationDrawable, null);

        }

        holder.description.setCompoundDrawablePadding(14);

        if (data.get(position).getImages() != null) {
            Glide.with(context).load(data.get(position).getImages().getUrl500_500())
                    .placeholder(ImageLoaderAnimation.glideLoader(context))
                    .dontTransform()
                    .into(holder.image);
        } else {

            Glide.with(context).load(R.drawable.def_logo)
                    .into(holder.image);
        }


        if (data.get(position).getFeatured() == 0) {
            holder.featured.setVisibility(View.GONE);
        } else {
            holder.featured.setVisibility(View.VISIBLE);
        }

    }


    public void addAllItems(RealmList<Offer> list) {

        data.addAll(list);
        notifyDataSetChanged();

    }


    public void removeAll() {
        int size = this.data.size();

        if (size > 0) {

            for (int i = 0; i < size; i++) {
                this.data.remove(0);
            }
            if (size > 0)
                this.notifyItemRangeRemoved(0, size);

        }

    }

    public Offer getItem(int position) {

        try {
            return data.get(position);
        } catch (Exception e) {
            return null;
        }

    }


    public void addItem(Offer item) {

        int index = (data.size());
        data.add(item);
        notifyDataSetChanged();
        //notifyItemInserted(index);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(ClickListener clicklistener) {

        this.clickListener = clicklistener;

    }


    public interface ClickListener {
        void itemClicked(View view, int position);

    }

    public class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ImageView image;
        public TextView name;
        public TextView description;
        //public TextView distance;
        public TextView offer;
        public ImageView featured;


        public mViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.address);
            //distance = (TextView) itemView.findViewById(R.id.distance);
            offer = itemView.findViewById(R.id.offer);
            featured = itemView.findViewById(R.id.featured);

            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {


            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }

            //delete(getPosition());


        }
    }


}
