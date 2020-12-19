package com.directoriodelicias.apps.delicias.adapter.lists;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.classes.Bookmark;
import com.directoriodelicias.apps.delicias.controllers.BookmarkController;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class BookmarkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Bookmark> items = new ArrayList<>();
    private Context ctx;
    private ClickListener mClickListener;

    public BookmarkAdapter(Context context, List<Bookmark> items) {
        this.items = items;
        ctx = context;
    }

    public List<Bookmark> getItems() {
        return items;
    }

    public void setItems(List<Bookmark> items) {
        this.items = items;
    }

    public void setClickListener(final ClickListener mItemClickListener) {
        this.mClickListener = mItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        vh = new BookmarkViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BookmarkViewHolder) {
            BookmarkViewHolder view = (BookmarkViewHolder) holder;

            final Bookmark bookmark = items.get(position);
            if (bookmark.getModule() != null && !bookmark.getModule().equals("")) {

                view.label.setText(bookmark.getLabel());
                view.description.setText(bookmark.getLabel_description());

                @SuppressLint("ResourceType") String source = ctx.getString(R.string.tab_stores);
                if (bookmark.getModule().toLowerCase().equals("offer")) {
                    source = ctx.getString(R.string.tab_offers);
                } else if (bookmark.getModule().toLowerCase().equals("event")) {
                    source = ctx.getString(R.string.tab_events);
                }

                view.source.setText(source);

                if (bookmark.getImages() != null) {
                    Glide.with(ctx)
                            .load(bookmark.getImages().getUrl200_200())
                            .centerCrop().placeholder(R.mipmap.ic_launcher)
                            .into(view.image);
                } else {
                    Glide.with(ctx).load(R.drawable.def_logo)
                            .centerCrop().into(view.image);
                }


            }


        }
    }

    public void addItem(Bookmark item) {
        if (item != null) {
            if (items.add(item)) {
                BookmarkController.insertBookmark(item);
                notifyDataSetChanged();
            }
        }

    }

    public void updateItem(final int position, final Bookmark item) {
        if (item != null && position >= 0) {
            items.set(position, item);
            BookmarkController.updateBookmark(item);
            notifyDataSetChanged();
        }


    }

    public void removeItem(final int noti_id, final int position) {

        Bookmark item = BookmarkController.getBookmark(noti_id);
        if (item != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            items.remove(item);
            BookmarkController.removeBookmark(item, realm);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, items.size());
            realm.commitTransaction();
        }

    }

    public void addAll(final List<Bookmark> productList) {
        items.addAll(productList);
        notifyDataSetChanged();
    }

    public void removeAll() {
        items.clear();

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public interface ClickListener {
        void onItemClick(View view, int pos);

        void onMoreButtonClick(View view, int position);

    }

    public class BookmarkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView bookmarkCV;
        LinearLayout notif_layout;
        ImageView image;
        TextView label;
        TextView description;
        TextView source;
        ImageView more;


        public BookmarkViewHolder(View v) {
            super(v);

            bookmarkCV = v.findViewById(R.id.notification_cv);
            notif_layout = v.findViewById(R.id.notif_layout);
            image = v.findViewById(R.id.image);
            label = v.findViewById(R.id.notif_label);
            description = v.findViewById(R.id.description);
            source = v.findViewById(R.id.source);
            more = v.findViewById(R.id.notif_menu);


            //set click listeners
            bookmarkCV.setOnClickListener(this);
            more.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.notification_cv) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(view, getPosition());
                }
            } else if (view.getId() == R.id.notif_menu) {
                if (mClickListener != null) {
                    mClickListener.onMoreButtonClick(view, getPosition());
                }
            }

        }
    }


}