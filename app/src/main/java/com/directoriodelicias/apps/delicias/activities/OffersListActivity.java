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
import com.directoriodelicias.apps.delicias.classes.Store;
import com.directoriodelicias.apps.delicias.controllers.stores.StoreController;
import com.directoriodelicias.apps.delicias.fragments.ListOffersFragment;

import java.util.Objects;


public class OffersListActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TextView APP_TITLE_VIEW = null;
    private TextView APP_DESC_VIEW = null;

    private int store_id = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_store_favortie);
        initToolbar();

        Intent intent = getIntent();
        Bundle b = new Bundle();


        try {

            if (intent.hasExtra("store_id")) {
                b.putInt("store_id", Objects.requireNonNull(intent.getExtras()).getInt("store_id"));
                store_id = intent.getExtras().getInt("store_id");
                Store store = StoreController.findStoreById(store_id);
                APP_DESC_VIEW.setText(store.getName());
                APP_DESC_VIEW.setVisibility(View.VISIBLE);
                APP_TITLE_VIEW.setText(R.string.offers);
            } else {
                APP_TITLE_VIEW.setText(R.string.offers_nearby);
                APP_DESC_VIEW.setVisibility(View.GONE);
            }

            if (intent.getExtras().containsKey("searchParams")) {
                APP_TITLE_VIEW.setText(R.string.offers_result);
                b.putSerializable("searchParams", intent.getExtras().getSerializable("searchParams"));
            }

        } catch (Exception e) {

        }


        ListOffersFragment fragment = new ListOffersFragment();
        fragment.setArguments(b);


        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction()
                .replace(R.id.store_content, fragment)
                .commit();


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
            intent.putExtra("selected_module", "offer");
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }


    public void initToolbar() {

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setSubtitle("E-shop");
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_36dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        APP_TITLE_VIEW = toolbar.findViewById(R.id.toolbar_title);
        APP_DESC_VIEW = toolbar.findViewById(R.id.toolbar_subtitle);


        APP_DESC_VIEW.setVisibility(View.GONE);

    }
}
