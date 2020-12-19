package com.directoriodelicias.apps.delicias.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.classes.Category;
import com.directoriodelicias.apps.delicias.fragments.ListStoresFragment;

import io.realm.Realm;


public class ListStoresActivity extends AppCompatActivity {

    //Toolbar toolbar;
    private Category mCat = null;
    private int CatId, userId;
    //private TextView APP_TITLE_VIEW = null;
    //private TextView APP_DESC_VIEW = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_list_activity);

        Realm realm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        Bundle b = new Bundle();


        try {
            //user_id
            userId = intent.getExtras().getInt("user_id");
            //category name
            CatId = intent.getExtras().getInt("category");
            mCat = realm.where(Category.class).equalTo("numCat", CatId).findFirst();
            //if (mCat != null) APP_TITLE_VIEW.setText(mCat.getNameCat());

            if (intent.getExtras().containsKey("searchParams")) {
                // APP_TITLE_VIEW.setText("Store result");
                b.putSerializable("searchParams", intent.getExtras().getSerializable("searchParams"));
            }

        } catch (Exception ex) {
            if (AppConfig.APP_DEBUG)
                Toast.makeText(this, "Error @655", Toast.LENGTH_LONG).show();
            if (AppConfig.APP_DEBUG)
                ex.printStackTrace();

            finish();
        }


        ListStoresFragment listFrag = new ListStoresFragment();


        try {
            b.putInt("category", mCat.getNumCat());
        } catch (Exception e) {

        }

        b.putInt("user_id", userId);


        listFrag.setArguments(b);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.frame, listFrag);
        // transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (android.R.id.home == item.getItemId()) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cat_menu, menu);

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
