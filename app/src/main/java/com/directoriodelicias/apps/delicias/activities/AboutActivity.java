package com.directoriodelicias.apps.delicias.activities;

import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.wuadam.awesomewebview.AwesomeWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {


    @BindView(R.id.about_app_version)
    TextView version;
    @BindView(R.id.about_description)
    TextView description;
    @BindView(R.id.about_mail)
    TextView mail;
    @BindView(R.id.about_version)
    TextView verion_content;
    @BindView(R.id.about_mail_content)
    TextView mail_content;
    @BindView(R.id.about_phone)
    TextView phone;
    @BindView(R.id.about_phone_content)
    TextView phone_content;
    @BindView(R.id.about_description_content)
    TextView description_content;
    @BindView(R.id.toolbar_title)
    TextView APP_TITLE_VIEW;
    @BindView(R.id.toolbar_subtitle)
    TextView APP_DESC_VIEW;
    @BindView(R.id.app_bar)
    Toolbar toolbar;

    @BindView(R.id.youtube)
    ImageView youtube;

    @BindView(R.id.facebook)
    ImageView facebook;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initToolbar();
        applyFrontText();


        APP_TITLE_VIEW.setText(getResources().getString(R.string.app_name));
        APP_TITLE_VIEW.setVisibility(View.VISIBLE);
        mail_content.setText(Constances.initConfig.AppInfos.ADDRESS_CONTACT);
        try {
            verion_content.setText(getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AwesomeWebView.Builder(AboutActivity.this)
                        .statusBarColorRes(R.color.colorAccent)
                        .theme(R.style.FinestWebViewAppTheme)
                        .show("https://youtu.be/IS1FNtggVTc");
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AwesomeWebView.Builder(AboutActivity.this)
                        .statusBarColorRes(R.color.colorAccent)
                        .theme(R.style.FinestWebViewAppTheme)
                        .show("https://www.facebook.com/droidevtech");
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.righttoleft_enter, R.anim.righttoleft_exit);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.righttoleft_enter, R.anim.righttoleft_exit);
    }

    public void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        APP_DESC_VIEW.setVisibility(View.GONE);


        String about = Constances.initConfig.AppInfos.ABOUT_CONTENT;
        APP_TITLE_VIEW.setText(R.string.About_us);
        description_content.setText(about);

        APP_DESC_VIEW.setVisibility(View.GONE);

    }

    public void applyFrontText() {
        //SET FONT TO THE TEXTVIEW

        //make tiles in bold
        description.setTypeface(description.getTypeface(), Typeface.BOLD);
        mail.setTypeface(mail.getTypeface(), Typeface.BOLD);
        version.setTypeface(version.getTypeface(), Typeface.BOLD);
        phone.setTypeface(phone.getTypeface(), Typeface.BOLD);
    }


}
