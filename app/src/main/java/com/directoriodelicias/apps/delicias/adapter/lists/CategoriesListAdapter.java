package com.directoriodelicias.apps.delicias.adapter.lists;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.directoriodelicias.apps.delicias.AppController;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.animation.ImageLoaderAnimation;
import com.directoriodelicias.apps.delicias.classes.Category;
import com.directoriodelicias.apps.delicias.classes.Images;
import com.directoriodelicias.apps.delicias.utils.Utils;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;


public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.mViewHolder> {


    private LayoutInflater infalter;
    private List<Category> data;
    private Context context;
    private ClickListener clickListener;
    private boolean rectCategoryView = false;
    private Map<String, Object> optionalParams;
    private int selectedPos = RecyclerView.NO_POSITION;
    // Define an array like the following in your adapter
    private float width = 0, height = 0;
    private boolean selectedAfterAction;


    public CategoriesListAdapter(Context context, List<Category> data, boolean rectCategoryView, Map<String, Object> optionalParams, float width, float height, boolean selectedAfterAction) {
        this.data = data;
        this.infalter = LayoutInflater.from(context);
        this.context = context;
        this.rectCategoryView = rectCategoryView;
        this.optionalParams = optionalParams;
        this.width = width;
        this.height = height;
        this.selectedAfterAction = selectedAfterAction;
    }

    @Override
    public CategoriesListAdapter.mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = null;
        if (rectCategoryView) {
            rootView = infalter.inflate(R.layout.v2_item_category_rect, parent, false);
        } else {
            rootView = infalter.inflate(R.layout.v2_item_category, parent, false);
        }

        mViewHolder holder = new mViewHolder(rootView);


        return holder;
    }


    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(final mViewHolder holder, final int position) {


        //resize image frame
        if (height > 0 && width > 0) {
            //set set the dp dimension
            int dp1 = Utils.dip2pix(context, 1);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) width, (int) height);
            params.setMargins((5 * dp1), (5 * dp1), (5 * dp1), (5 * dp1));
            // holder.frameImage.setLayoutParams(params);
        }


        String category_name = data.get(position).getNameCat();


        if (optionalParams != null && optionalParams.containsKey("displayCatTitle") && !((Boolean) optionalParams.get("displayCatTitle"))) {
            holder.name.setVisibility(View.GONE);

            holder.transparency_rec_filter.setVisibility(View.VISIBLE);
            holder.transparency_rec_filter_text.setVisibility(View.VISIBLE);
            holder.transparency_rec_filter_text.setText(data.get(position).getNameCat());

        } else if (rectCategoryView) {

            holder.transparency_rec_filter.setVisibility(View.GONE);
            holder.transparency_rec_filter_text.setVisibility(View.GONE);

            category_name = category_name.replaceAll(" ", "\n") ;

        } else {

            holder.name.setVisibility(View.VISIBLE);
        }

        holder.name.setText(category_name);



        Images mainImg = null;

        if (rectCategoryView && data.get(position).getLogo() != null && !data.get(position).getLogo().equals("")) {


            if (optionalParams != null && optionalParams.containsKey("displayCatTitle") && !((Boolean) optionalParams.get("displayCatTitle"))) {
                mainImg = data.get(position).getImages();
            }else{
                mainImg = data.get(position).getLogo();
            }


            if (data.get(position).getColor() != null && !data.get(position).getColor().equals("null")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.colorImgFilter.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(data.get(position).getColor())));
                } else {
                    holder.colorImgFilter.setBackgroundColor(Color.parseColor(data.get(position).getColor()));
                }
            } else {
                holder.colorImgFilter.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.colorPrimary, null));
            }

        } else if (data.get(position).getImages() != null) {

            mainImg = data.get(position).getImages();

        }


        if (mainImg != null)
            Glide.with(context)
                    .asBitmap()
                    .load(mainImg.getUrl500_500())
                    .placeholder(ImageLoaderAnimation.glideLoader(context))
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                            if (AppController.isRTL()) {
                                resource = Utils.flip(resource);
                            }

                            holder.image.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                            holder.image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.def_logo, null));

                        }
                    });
        else
            holder.image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.def_logo, null));


        if (optionalParams != null && optionalParams.containsKey("displayStoreNumber") && !((Boolean) optionalParams.get("displayStoreNumber"))) {
            holder.stores.setVisibility(View.GONE);
        } else {

            holder.stores.setVisibility(View.VISIBLE);

            if (!rectCategoryView) {

                Drawable storeDrawable = new IconicsDrawable(context)
                        .icon(CommunityMaterial.Icon.cmd_map_marker)
                        .color(ResourcesCompat.getColor(context.getResources(), R.color.white, null))
                        .sizeDp(12);

                if (AppController.isRTL()) {
                    holder.stores.setCompoundDrawables(null, null, storeDrawable, null);
                    holder.stores.setCompoundDrawablePadding(10);
                } else {
                    holder.stores.setCompoundDrawables(storeDrawable, null, null, null);
                    holder.stores.setCompoundDrawablePadding(10);
                }

            }

            holder.stores.setText(String.format(
                    context.getString(R.string.nbr_stores_message),
                    data.get(position).getNbr_stores()
            ));

        }


        if (!selectedAfterAction && rectCategoryView) {
            holder.frameImage.setSelected(selectedPos == position);
        }


    }


    public void setSelectedPos(final int pos) {
        if (rectCategoryView) {
            selectedPos = pos;
            notifyItemChanged(selectedPos);
        }

    }


    public Category getItem(int position) {

        try {
            return data.get(position);
        } catch (Exception e) {
            return null;
        }

    }


    public void clear() {

        data = new ArrayList<Category>();
        notifyDataSetChanged();

    }

    public void addItem(Category item) {

        int index = (data.size());
        data.add(item);
        notifyItemInserted(index);
    }

    public void addAllItems(RealmList<Category> listCats) {

        data.addAll(listCats);
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

    public class mViewHolder extends RecyclerView.ViewHolder {


        public TextView name;
        public ImageView image;
        public TextView stores;
        public View mainLayout;
        public View colorImgFilter;
        public View frameImage;


        public LinearLayout transparency_rec_filter;
        public TextView transparency_rec_filter_text;


        public mViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cat_name);
            image = itemView.findViewById(R.id.image);
            stores = itemView.findViewById(R.id.stores);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            colorImgFilter = itemView.findViewById(R.id.colorImgFilter);
            frameImage = itemView.findViewById(R.id.frame_image);

            transparency_rec_filter = itemView.findViewById(R.id.transparency_rec_filter);
            transparency_rec_filter_text = itemView.findViewById(R.id.transparency_rec_filter_text);

            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.itemClicked(view, getLayoutPosition());

                        if (rectCategoryView) {
                            notifyItemChanged(selectedPos);
                            selectedPos = getLayoutPosition();
                            notifyItemChanged(selectedPos);
                        }
                    }
                }
            });
        }


    }


}