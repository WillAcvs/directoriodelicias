package com.directoriodelicias.apps.delicias.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.fragments.ListEventFragment;

import java.util.Objects;


public class EventsListActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TextView APP_TITLE_VIEW = null;
    private TextView APP_DESC_VIEW = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_liked);

        initToolbar();
        Intent intent = getIntent();
        Bundle b = new Bundle();


        try {

            if (intent.hasExtra("upcomingEventsRequest")) {
                boolean upcomingEventsRequest = intent.getExtras().getBoolean("upcomingEventsRequest", false);
                if (upcomingEventsRequest) {
                    b.putInt("isLiked", 2);
                    APP_TITLE_VIEW.setText(getString(R.string.myUpComingEvents));
                }
            } else if (intent.hasExtra("isLiked")) {
                b.putInt("isLiked", Objects.requireNonNull(intent.getExtras()).getInt("isLiked"));
                APP_TITLE_VIEW.setText(getString(R.string.my_events));
            } else {
                APP_TITLE_VIEW.setText(getString(R.string.events_nearby));

            }

            if (intent.getExtras().containsKey("searchParams")) {
                APP_TITLE_VIEW.setText("Event result");
                b.putSerializable("searchParams", intent.getExtras().getSerializable("searchParams"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        ListEventFragment fragment = new ListEventFragment();
        fragment.setArguments(b);


        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction()
                .replace(R.id.event_content, fragment)
                .commit();

        String liked = intent.getStringExtra("eventNotified");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);

        menu.findItem(R.id.search_icon).setVisible(true);
        menu.findItem(R.id.share_post).setVisible(false);
        menu.findItem(R.id.map_action).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (android.R.id.home == item.getItemId()) {
            finish();
        } else if (item.getItemId() == R.id.search_icon) {

            Intent intent = new Intent(this, CustomSearchActivity.class);
            intent.putExtra("selected_module", "event");
            startActivity(intent);

        }


        return super.onOptionsItemSelected(item);
    }

    public void initToolbar() {

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        APP_TITLE_VIEW = toolbar.findViewById(R.id.toolbar_title);
        APP_DESC_VIEW = toolbar.findViewById(R.id.toolbar_subtitle);
        APP_DESC_VIEW.setVisibility(View.GONE);

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


}
