package com.directoriodelicias.apps.delicias.adapter.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.animation.ImageLoaderAnimation;
import com.directoriodelicias.apps.delicias.classes.Images;

import java.util.List;


public class ImagesListAdapter extends RecyclerView.Adapter<ImagesListAdapter.mViewHolder> {


    private LayoutInflater infalter;
    private List<Images> data;
    private Context context;
    private ClickListener clickListener;
    private boolean isHorizontalList = false;


    private int parent_width = 0;
    private int rest = 0;

    public ImagesListAdapter(Context context, List<Images> data, boolean isHorizontalList) {
        this.data = data;
        this.infalter = LayoutInflater.from(context);
        this.context = context;
        this.isHorizontalList = isHorizontalList;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public int getParent_width() {
        return parent_width;
    }

    public void setParent_width(int parent_width) {
        this.parent_width = parent_width;
    }

    public List<Images> getData() {
        return data;
    }

    @Override
    public ImagesListAdapter.mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View rootView = null;
        if (isHorizontalList)
            rootView = infalter.inflate(R.layout.v2_store_gallery_horizontal, parent, false);
        else rootView = infalter.inflate(R.layout.item_custom_gallery, parent, false);

        //
        mViewHolder holder = new mViewHolder(rootView);

        return holder;
    }


    @Override
    public void onBindViewHolder(ImagesListAdapter.mViewHolder holder, int position) {


        if (parent_width > 0) {

            int size = parent_width / context.getResources().getInteger(R.integer.nbr_gallery_cols);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(size, size);
            holder.main.setLayoutParams(lp);

        }

        Images image = data.get(position);

        if (image.getUrlFull() != null) {

            Glide.with(context).load(image.getUrl200_200())
                    .placeholder(ImageLoaderAnimation.glideLoader(context))
                    .centerCrop().into(holder.image);
            holder.see_more_layout.setVisibility(View.GONE);

        } else {

            Glide.with(context).load(image.getUrl200_200())
                    .placeholder(R.drawable.def_logo)
                    .centerCrop().into(holder.image);

            holder.see_more.setText("+" + rest);
            holder.see_more_layout.setVisibility(View.VISIBLE);


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


    public void addItem(Images item) {

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

        void seeMoreClicked(View view, int position);
    }

    public class mViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public FrameLayout main;
        public LinearLayout see_more_layout;
        public TextView see_more;


        public mViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            main = itemView.findViewById(R.id.main);
            see_more_layout = itemView.findViewById(R.id.see_more_layout);
            see_more = itemView.findViewById(R.id.see_more);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.itemClicked(v, getPosition());
                    }
                }
            });

            see_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.seeMoreClicked(v, getPosition());
                    }
                }
            });
        }


    }


}
