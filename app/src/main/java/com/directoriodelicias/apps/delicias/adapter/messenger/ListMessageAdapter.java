package com.directoriodelicias.apps.delicias.adapter.messenger;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.MainActivity;
import com.directoriodelicias.apps.delicias.classes.Message;
import com.directoriodelicias.apps.delicias.utils.DateUtils;
import com.directoriodelicias.apps.delicias.utils.Translator;
import com.directoriodelicias.apps.delicias.utils.Utils;
import com.wuadam.awesomewebview.AwesomeWebView;

import org.bluecabin.textoo.LinksHandler;
import org.bluecabin.textoo.Textoo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Directorio on 1/5/2016.
 */

public class ListMessageAdapter extends RecyclerView.Adapter<ListMessageAdapter.MessengerViewHolder> {


    private LayoutInflater infalter;
    private List<Message> data;
    private Context context;
    private ClickListener clickListener;
    private LoadEarlierMessages mLoadEarlierMessages;
    private boolean isLoadEarlierMsgs;


    public ListMessageAdapter(Context context, List<Message> data) {
        this.data = data;
        this.infalter = LayoutInflater.from(context);
        this.context = context;

    }

    public void setmLoadEarlierMessagesListener(LoadEarlierMessages mLoadEarlierMessages) {
        this.mLoadEarlierMessages = mLoadEarlierMessages;
    }

    public void setLoadEarlierMsgs(boolean isLoadEarlierMsgs) {
        this.isLoadEarlierMsgs = isLoadEarlierMsgs;
    }

    @Override
    public MessengerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView;
        MessengerViewHolder holder;

        if (viewType == Message.SENDER_VIEW) {

            rootView = infalter.inflate(R.layout.custom_message_sender, parent, false);

            holder = new mViewHolder(rootView);

        } else if (viewType == Message.RECEIVER_VIEW) {

            rootView = infalter.inflate(R.layout.custom_message_receiver, parent, false);

            holder = new mViewHolder(rootView);

        } else {

            rootView = infalter.inflate(R.layout.custom_message_loading, parent, false);

            holder = new mViewLoadingHolder(rootView);
        }

        return holder;
    }

    public void sendMessage(Message dis) {


        SimpleDateFormat inputPattern = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String dateOfLastMessage = "0000-00-00 00:00";
        int lastPosition = 0;

        for (int i = data.size() - 1; i >= 0; i--) {

            if (dis.getType() == data.get(i).getType()) {

                try {
                    dateOfLastMessage = inputPattern.format(inputPattern.parse(data.get(i).getDate()));
                    lastPosition = i;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;
            }
        }


        //add date if today
        try {

            String postDateAndTime = inputPattern.format(inputPattern.parse(dis.getDate()));


            if (dateOfLastMessage.equals(postDateAndTime)) {
                //Hour and minute like this 00:00
                if (data.size() > 0) {

                    try {

                        Log.e("Type", dis.getType() + "==" + data.get(lastPosition).getType());
                        if (data.get(lastPosition).getType() == dis.getType()) {
                            updateLastMessage(dis, lastPosition);
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                        addMessage(dis);
                    }

                } else {
                    addMessage(dis);
                }

            } else {
                //Year, month and Day , hour 2012/02/20 00:00
                addMessage(dis);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void addMessage(Message msg) {

        //check if message already exists
        for (int i = 0; i < data.size(); i++) {
            if (msg.getMessageid().equals(data.get(i).getMessageid())) {
                return;
            }
        }

        try {
            data.add(msg);
            notifyItemInserted(getItemCount() - 1);
        } catch (Exception e) {
            data.add(msg);
        }
    }

    public void addMessage(int pos, Message msg) {

        try {
            data.add(pos, msg);
            notifyDataSetChanged();
        } catch (Exception e) {
            data.add(pos, msg);
        }
    }

    public void updateLastMessage(Message msg, int position) {


        /*if(position==(getItemCount() - 1)){

            data.get(position).setMessage(

                    data.get(position).getMessage().trim() + "\n" +
                            msg.getMessage().trim()

            );

            notifyItemChanged(position);


        }else{
            addMessage(msg);
        }*/

        addMessage(msg);
    }

    public List<Message> getData() {
        return data;
    }

    @Override
    public void onBindViewHolder(ListMessageAdapter.MessengerViewHolder dholder, final int position) {

        if (dholder instanceof mViewHolder) {

            mViewHolder holder = (mViewHolder) dholder;

            int size = (int) (MainActivity.width * 0.6);

            holder.message.setText(Translator.getString(data.get(position).getMessage()));

            Textoo.config(holder.message)
                    .linkifyWebUrls()  // or just .linkifyAll()
                    .addLinksHandler(new LinksHandler() {
                        @Override
                        public boolean onClick(View view, String url) {

                            String patternString = ".*" + context.getString(R.string.DP_HOST_NAME) + ".*";
                            Pattern pattern = Pattern.compile(patternString);
                            Matcher matcher = pattern.matcher(url);
                            boolean matches = matcher.matches();

                            if (Utils.isValidURL(url) && !matches) {
                                new AwesomeWebView.Builder(context)
                                        .titleFont("ubuntu/Ubuntu-R.ttf")
                                        .urlFont("ubuntu/Ubuntu-R.ttf")
                                        .menuTextFont("ubuntu/Ubuntu-R.ttf")
                                        .showMenuOpenWith(false)
                                        .titleColor(
                                                ResourcesCompat.getColor(context.getResources(), R.color.white, null)
                                        ).urlColor(
                                        ResourcesCompat.getColor(context.getResources(), R.color.white, null)
                                ).show(url);
                                return true;
                            } else {
                                return false;
                            }


                        }
                    }).apply();


            //fixing bug related to timezone converter
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = df.parse(data.get(position).getDate());
                df.setTimeZone(TimeZone.getDefault());
                String formattedDate = df.format(date);
                holder.date.setText(
                        DateUtils.prepareOutputDate(formattedDate, null, context)
                );

                holder.date.setVisibility(View.VISIBLE);


            } catch (ParseException e) {

                holder.date.setText(
                        DateUtils.prepareOutputDate(data.get(position).getDate(), null, context)
                );

                e.printStackTrace();

                holder.date.setVisibility(View.VISIBLE);
            }


            if (data.get(position).getType() == Message.SENDER_VIEW) {

                if (data.get(position).getStatus() == Message.NEW || data.get(position).getStatus() == Message.NO_SENT) {
                    holder.message.setTextColor(ContextCompat.getColor(context, R.color.colorGrayDefault));
                    holder.date.setVisibility(View.INVISIBLE);
                } else if (data.get(position).getStatus() == Message.SENT) {

                    holder.date.setVisibility(View.VISIBLE);
                    holder.message.setTextColor(ContextCompat.getColor(context, R.color.white));
                }


                if (data.get(position).getStatus() == Message.ERROR) {
                    holder.warr.setVisibility(View.VISIBLE);
                } else {
                    holder.warr.setVisibility(View.GONE);
                }

            } else {
                holder.warr.setVisibility(View.GONE);
            }


            holder.message.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    ClipboardManager cManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData cData = ClipData.newPlainText("text", data.get(position).getMessage());
                    cManager.setPrimaryClip(cData);
                    Toast.makeText(context, Translator.print("Copied to clipboard", null), Toast.LENGTH_LONG).show();
                    return true;
                }
            });


        } else if (dholder instanceof mViewLoadingHolder) {

            final mViewLoadingHolder loadEarlierMsgsViewHolder = (mViewLoadingHolder) dholder;
            if (isLoadEarlierMsgs) {

                loadEarlierMsgsViewHolder.loadMore.setVisibility(View.VISIBLE);
                loadEarlierMsgsViewHolder.progressBar.setVisibility(View.GONE);

                loadEarlierMsgsViewHolder.loadMore
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (mLoadEarlierMessages != null) {

                                    mLoadEarlierMessages.onLoadEarlierMessages(
                                            loadEarlierMsgsViewHolder.progressBar,
                                            loadEarlierMsgsViewHolder.loadMore
                                    );
                                }
                            }
                        });
            } else {

                loadEarlierMsgsViewHolder.loadMore.setVisibility(View.GONE);
                loadEarlierMsgsViewHolder.progressBar.setVisibility(View.GONE);

            }


        }


    }

    public Message getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemViewType(int position) {

        return data.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(ClickListener clicklistener) {

        this.clickListener = clicklistener;

    }


    public interface LoadEarlierMessages {
        void onLoadEarlierMessages(ProgressBar mProgressBar, Button mButton);
    }

    public interface ClickListener {
        void itemClicked(View view, int position);
    }


    public interface TouchListener {
    }

    static class MessengerViewHolder extends RecyclerView.ViewHolder {

        public MessengerViewHolder(View itemView) {
            super(itemView);
        }


    }

    public class mViewLoadingHolder extends MessengerViewHolder {

        public ProgressBar progressBar;
        public Button loadMore;

        public mViewLoadingHolder(View itemView) {

            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
            loadMore = itemView.findViewById(R.id.loadmore);
        }


    }

    public class mViewHolder extends MessengerViewHolder implements View.OnClickListener, View.OnTouchListener {


        public TextView message;
        public TextView date;
        public ImageView warr;

        public mViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);


            warr = itemView.findViewById(R.id.warr);
            message = itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.date);

        }


        @Override
        public void onClick(View v) {


            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }

            //delete(getPosition());


        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            return false;
        }
    }


}
