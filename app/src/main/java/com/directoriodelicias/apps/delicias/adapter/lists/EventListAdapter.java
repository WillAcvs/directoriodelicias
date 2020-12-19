package com.directoriodelicias.apps.delicias.adapter.lists;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
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
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.classes.Event;
import com.directoriodelicias.apps.delicias.utils.DateUtils;
import com.directoriodelicias.apps.delicias.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.List;

import io.realm.RealmList;


public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.mViewHolder> {


    private LayoutInflater infalter;
    private List<Event> data;
    private Context context;
    private ClickListener clickListener;
    private boolean isHorizontalList = false;
    private float width = 0, height = 0;


    public EventListAdapter(Context context, List<Event> data, boolean isHorizontalList) {
        this.data = data;
        this.infalter = LayoutInflater.from(context);
        this.context = context;
        this.isHorizontalList = isHorizontalList;
    }

    public EventListAdapter(Context context, List<Event> data, boolean isHorizontalList, float width, float height) {
        this.data = data;
        this.infalter = LayoutInflater.from(context);
        this.context = context;
        this.isHorizontalList = isHorizontalList;
        this.width = width;
        this.height = height;
    }

    @Override
    public EventListAdapter.mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = null;
        if (isHorizontalList) rootView = infalter.inflate(R.layout.v2_item_event, parent, false);
        else rootView = infalter.inflate(R.layout.fragment_custom_item_event, parent, false);

        mViewHolder holder = new mViewHolder(rootView);

        return holder;
    }


    @SuppressLint("StringFormatInvalid")
    @Override
    public void onBindViewHolder(EventListAdapter.mViewHolder holder, int position) {


       /* int size = (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);

        if (Configuration.SCREENLAYOUT_SIZE_XLARGE == size) {

            holder.image.getLayoutParams().height = (int) (MainActivity.width / 2.5);
        } else {
            holder.image.getLayoutParams().height = (int) (MainActivity.width / 1.5);
        }*/


        if (height > 0 && width > 0) {
            //set set the dp dimention
            int dp1 = Utils.dip2pix(context, 1);
            CardView.LayoutParams params = new CardView.LayoutParams((int) width, (int) height);
            params.setMargins((5 * dp1), (5 * dp1), (5 * dp1), (5 * dp1));
            holder.itemView.setLayoutParams(params);
        }


        if (this.data.get(position).getListImages() != null && data.get(position).getListImages().size() > 0) {

            if (AppConfig.APP_DEBUG) {
                Log.i("image", data.get(position).getListImages()
                        .get(0).getUrl500_500());

            }

            Glide.with(context)
                    .load(this.data.get(position).getListImages()
                            .get(0).getUrl500_500())
                    .dontTransform()
                    .placeholder(ImageLoaderAnimation.glideLoader(context))
                    .into(holder.image);

        } else {
            Glide.with(context).load(R.drawable.def_logo).into(holder.image);
        }

        if (this.data.get(position).getListImages() == null)
            if (data.get(position).getType() == 1 && data.get(position).getType() == 2) {
                holder.image.setImageResource(R.drawable.def_logo);
            } else if (data.get(position).getType() == 3) {
                holder.image.setImageResource(R.drawable.def_logo);
            }


        if (this.data.get(position).getListImages().size() > 0) {

            Glide.with(context)
                    .load(this.data.get(position).getListImages().get(0).getUrl500_500())
                    .into(holder.image);
        }

        if (DateUtils.isLessThan24(this.data.get(position).getDateB(), null)) {
            holder.upcoming.setVisibility(View.VISIBLE);
        } else {
            holder.upcoming.setVisibility(View.GONE);
        }


        holder.name.setText(data.get(position).getName());

        Drawable locationDrawable = new IconicsDrawable(context)
                .icon(CommunityMaterial.Icon.cmd_map_marker)
                .color(ResourcesCompat.getColor(context.getResources(), R.color.colorGrayDefault, null))
                .sizeDp(12);

        holder.address.setCompoundDrawablePadding(10);


        if (AppController.isRTL()) {
            holder.address.setCompoundDrawables(null, null, locationDrawable, null);
        } else {
            holder.address.setCompoundDrawables(locationDrawable, null, null, null);
        }

        holder.address.setText(data.get(position).getAddress());


        String symbole = com.directoriodelicias.apps.delicias.utils.Utils.getDistanceBy(
                data.get(position).getDistance()
        );
        if (data.get(position).getDistance() != null) {
            String distanceFormated = "N/A";
            if (data.get(position).getDistance() > 0) {
                String distance = com.directoriodelicias.apps.delicias.utils.Utils.preparDistance(
                        data.get(position).getDistance()
                );
                distanceFormated = distance + " " + symbole.toUpperCase();
            }
            holder.distance.setText(distanceFormated);
        }


        //


        if (data.get(position).getFeatured() == 0) {
            holder.featured.setVisibility(View.GONE);
        } else {
            holder.featured.setVisibility(View.VISIBLE);
        }

        if (data.get(position).getDateB() != null && !data.get(position).getDateB().equals("")) {
            holder.day_calendar.setText(DateUtils.getDateByTimeZone(data.get(position).getDateB(), "dd"));
            holder.month_calendar.setText(DateUtils.getDateByTimeZone(data.get(position).getDateB(), "MMM"));
        }

        if (holder.join_button != null && holder.joined_button != null) {
            if (data.get(position).getSaved() == 0) {
                holder.join_button.setVisibility(View.VISIBLE);
                holder.joined_button.setVisibility(View.GONE);
            } else {
                holder.join_button.setVisibility(View.GONE);
                holder.joined_button.setVisibility(View.VISIBLE);
            }
        }


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

    public Event getItem(int position) {

        try {
            return data.get(position);
        } catch (Exception e) {
            return null;
        }

    }

    public void addAllItems(RealmList<Event> list) {

        data.addAll(list);
        notifyDataSetChanged();

    }


    public void addItem(Event item) {

        int index = (data.size());
        data.add(item);
        notifyDataSetChanged();
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
        public TextView address;
        public TextView distance;
        //public ImageView location;
        public ImageView featured;
        public TextView upcoming;
        public TextView day_calendar, month_calendar;
        public FloatingActionButton joined_button, join_button;


        public mViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            //location = (ImageView) itemView.findViewById(R.id.location);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            distance = itemView.findViewById(R.id.distance);
            featured = itemView.findViewById(R.id.featured);
            upcoming = itemView.findViewById(R.id.upcoming);
            day_calendar = itemView.findViewById(R.id.day_calendar);
            month_calendar = itemView.findViewById(R.id.month_calendar);
            joined_button = itemView.findViewById(R.id.joined_button);
            join_button = itemView.findViewById(R.id.join_button);


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
