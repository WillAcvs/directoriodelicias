package com.directoriodelicias.apps.delicias.adapter.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.classes.Review;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.List;


public class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsListAdapter.mViewHolder> {


    private LayoutInflater infalter;
    private List<Review> data;
    private Context context;
    private ClickListener clickListener;


    public ReviewsListAdapter(Context context, List<Review> data) {
        this.data = data;
        this.infalter = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ReviewsListAdapter.mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = infalter.inflate(R.layout.item_store_review, parent, false);
        //
        mViewHolder holder = new mViewHolder(rootView);

        return holder;
    }


    @Override
    public void onBindViewHolder(ReviewsListAdapter.mViewHolder holder, int position) {

        Review mReview = data.get(position);

        holder.title.setText(mReview.getPseudo());
        holder.detail.setText(mReview.getReview());

        Glide.with(context).load(mReview.getImage())
                .placeholder(R.drawable.profile_placeholder).centerCrop().into(holder.image);

        holder.mRatingBar.setRating((float) mReview.getRate());

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


    public void addItem(Review item) {

        int index = (data.size());
        data.add(item);
        notifyItemInserted(index);
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


        public TextView title;
        public TextView detail;
        public CircularImageView image;
        public RatingBar mRatingBar;

        public mViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.name);
            detail = itemView.findViewById(R.id.detail);
            mRatingBar = itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }
        }
    }


}
