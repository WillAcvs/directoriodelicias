package com.directoriodelicias.apps.delicias.adapter.lists;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.animation.ImageLoaderAnimation;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.appconfig.AppContext;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.User;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.List;

/**
 * Created by Directorio on 5/23/2016.
 */
public class ListUsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_AD = 1;
    private LayoutInflater infalter;
    private List<User> data;
    private Context context;
    private ClickListener clickListener;
    private boolean isHorizontalList = false;

    public ListUsersAdapter(Context context, List<User> data, boolean isHorizontalList) {
        this.data = data;
        this.infalter = LayoutInflater.from(context);
        this.context = context;
        this.isHorizontalList = isHorizontalList;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (AppContext.DEBUG)
            Log.i("viewType", viewType + "");

        View rootView = null;
        if (isHorizontalList) rootView = infalter.inflate(R.layout.v2_item_user, parent, false);
        else rootView = infalter.inflate(R.layout.layout_user, parent, false);

        mViewHolder holder = new mViewHolder(rootView);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position) == null ? VIEW_TYPE_AD : VIEW_TYPE_ITEM;
    }


    //private Realm realm = Realm.getDefaultInstance();

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder mHolder, final int position) {
        final mViewHolder mviewHolder = (ListUsersAdapter.mViewHolder) mHolder;

        if (isHorizontalList) {
            String[] l = data.get(position).getName().split(" ");
            mviewHolder.name.setText(l[0]);
        } else {
            mviewHolder.name.setText(data.get(position).getName());
        }


        if (data.get(position).getImages() != null)
            Glide.with(context).load(data.get(position).getImages().getUrl200_200())
                    .placeholder(ImageLoaderAnimation.glideLoader(context))
                    .centerCrop().into(mviewHolder.userimage);

        mviewHolder.desc.setText("@" + data.get(position).getUsername());
        mviewHolder.desc.setVisibility(View.VISIBLE);


        if (AppConfig.APP_DEBUG)
            Log.i("user", position + " " + data.get(position).getUsername());


        Log.i("distance - " + data.get(position).getUsername() + " - " + data.get(position).getType(), String.valueOf(data.get(position).getDistance()));


        if (!isHorizontalList) {
            if (data.get(position).isWithHeader()) {

                try {
                    if (data.get(position).getDistance() < Constances.DISTANCE_CONST) {
                        mviewHolder.header.setText(context.getString(R.string.lessThan1km));
                    } else if (data.get(position).getDistance() > (Constances.DISTANCE_CONST * 2)) {

                        mviewHolder.header.setText(context.getString(R.string.MoreThan2km));
                    } else if (data.get(position).getDistance() > (Constances.DISTANCE_CONST * 5)) {

                        mviewHolder.header.setText(context.getString(R.string.MoreThan5km));
                    } else if (data.get(position).getDistance() > (Constances.DISTANCE_CONST * 10)) {
                        mviewHolder.header.setText(context.getString(R.string.MoreThan10km));
                    }

                } catch (Exception e) {
                    mviewHolder.header.setVisibility(View.GONE);
                }

                mviewHolder.header.setVisibility(View.VISIBLE);

            } else {
                mviewHolder.header.setVisibility(View.GONE);
            }
        }

        if (data.get(position).getImages() != null) {
            Glide.with(context).load(data.get(position).getImages()
                    .getUrl200_200()).placeholder(R.drawable.profile_placeholder).centerCrop().into(mviewHolder.userimage);
        } else
            Glide.with(context).load(R.drawable.profile_placeholder).centerCrop().into(mviewHolder.userimage);


        Drawable icon = new IconicsDrawable(context)
                .icon(CommunityMaterial.Icon.cmd_dots_vertical)
                .color(Color.parseColor("#000000"))
                .sizeDp(16);
        mviewHolder.optionIcon.setImageDrawable(icon);

        if (data.get(position).isBlocked()) {
            mviewHolder.name.setTextColor(context.getResources().getColor(R.color.gray));
        } else {
            mviewHolder.name.setTextColor(context.getResources().getColor(R.color.colorAccent));
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

    public void addItem(User item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public User getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(ClickListener clicklistener) {

        this.clickListener = clicklistener;

    }


    public interface ClickListener {
        void itemClicked(int position);

        void itemOptionsClicked(View view, int position);
    }

    public class mViewHolder extends RecyclerView.ViewHolder {


        public CircularImageView userimage;
        public TextView name;
        public TextView desc;
        public TextView header;
        public CardView mainLayout;
        public ImageView optionIcon;


        public mViewHolder(View itemView) {
            super(itemView);

            mainLayout = itemView.findViewById(R.id.mainLayout);
            userimage = itemView.findViewById(R.id.userimage);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);
            header = itemView.findViewById(R.id.textHeader);

            optionIcon = itemView.findViewById(R.id.option);

            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.itemClicked(getPosition());
                    }
                }
            });

            optionIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.itemOptionsClicked(optionIcon, getPosition());
                    }
                }
            });


        }


    }


}
