package com.directoriodelicias.apps.delicias.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.classes.Category;
import com.directoriodelicias.apps.delicias.fragments.AuthenticationFragment;
import com.directoriodelicias.apps.delicias.fragments.CustomSearchFragment;
import com.directoriodelicias.apps.delicias.fragments.ListEventFragment;
import com.directoriodelicias.apps.delicias.fragments.ListOffersFragment;
import com.directoriodelicias.apps.delicias.fragments.ListStoresFragment;

import java.util.HashMap;


public class CustomSearchActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TextView APP_TITLE_VIEW = null;
    private TextView APP_DESC_VIEW = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_custom);

        initToolbar();

        APP_TITLE_VIEW.setText(getString(R.string.search));

        CustomSearchFragment fragment = new CustomSearchFragment();

        Bundle bundle = new Bundle();

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("useCacheFields")) {
            bundle.putString("useCacheFields", intent.getStringExtra("useCacheFields"));
        }

        if (intent != null && intent.hasExtra("searchParams")) {
            bundle.putSerializable("searchParams", intent.getSerializableExtra("searchParams"));
        }

        if (intent != null && intent.hasExtra("selected_module")) {
            bundle.putString("selected_module", intent.getStringExtra("selected_module"));
        }


        fragment.setArguments(bundle);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.search_content, fragment)
                .commit();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);

        menu.findItem(R.id.search_icon).setVisible(false);
        menu.findItem(R.id.share_post).setVisible(false);
        menu.findItem(R.id.map_action).setVisible(false);

        return true;
    }


    public void initToolbar() {

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (android.R.id.home == item.getItemId()) {
            finish();


        }

        return super.onOptionsItemSelected(item);
    }


    public static class LoginActivityV2 extends AppCompatActivity {



        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.fragment_authentication);


            AuthenticationFragment fragment = new AuthenticationFragment();
            FragmentManager manager = getSupportFragmentManager();

            manager.beginTransaction()
                    .replace(R.id.authentication_content, fragment)
                    .commit();


        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.home_menu, menu);

            menu.findItem(R.id.search_icon).setVisible(false);
            menu.findItem(R.id.share_post).setVisible(false);
            menu.findItem(R.id.map_action).setVisible(false);

            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            if (android.R.id.home == item.getItemId()) {
                finish();
            }

            return super.onOptionsItemSelected(item);
        }


    }

    public static class ResultFilterActivity extends AppCompatActivity {

        Toolbar toolbar;
        private TextView APP_TITLE_VIEW = null;
        private TextView APP_DESC_VIEW = null;

        private Category mCat = null;
        private int CatId, userId;
        private HashMap<String, Object> searchParams;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.fragment_result_filter);

            initToolbar();


            if (getIntent().getExtras().containsKey("searchParams"))
                handleResultSearchByModule((HashMap<String, Object>) getIntent().getExtras().getSerializable("searchParams"));
            else
                onBackPressed();

        }


        private void handleResultSearchByModule(HashMap<String, Object> _searchParams) {

            //fill params
            searchParams = _searchParams;

            Bundle b = new Bundle();
            b.putSerializable("searchParams", _searchParams);
            Fragment fragResult = null;
            if (_searchParams.containsKey("module") && _searchParams.get("module").equals("store")) {
                APP_TITLE_VIEW.setText(getString(R.string.stores_result));
                fragResult = new ListStoresFragment();
            } else if (_searchParams.containsKey("module") && _searchParams.get("module").equals("event")) {
                APP_TITLE_VIEW.setText(getString(R.string.events_result));
                fragResult = new ListEventFragment();
            } else if (_searchParams.containsKey("module") && _searchParams.get("module").equals("offer")) {
                APP_TITLE_VIEW.setText(getString(R.string.offers_result));
                fragResult = new ListOffersFragment();
            }

            FragmentManager manager = getSupportFragmentManager();
            if (fragResult != null) {
                fragResult.setArguments(b);
                manager.beginTransaction()
                        .replace(R.id.result_layout, fragResult)
                        .commit();
            } else {
                onBackPressed();
            }


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
                intent.putExtra("useCacheFields", "enabled");
                intent.putExtra("searchParams", searchParams);
                startActivityForResult(intent, 50505);
                //finish();
            }

            return super.onOptionsItemSelected(item);
        }


        public void initToolbar() {

            toolbar = findViewById(R.id.app_bar);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            APP_TITLE_VIEW = toolbar.findViewById(R.id.toolbar_title);
            APP_DESC_VIEW = toolbar.findViewById(R.id.toolbar_subtitle);

            APP_DESC_VIEW.setVisibility(View.GONE);

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (50505 == requestCode && resultCode == Activity.RESULT_OK) {
                searchParams = (HashMap<String, Object>) data.getExtras().getSerializable("searchParams");
                handleResultSearchByModule(searchParams);
            }
        }


    }
}
