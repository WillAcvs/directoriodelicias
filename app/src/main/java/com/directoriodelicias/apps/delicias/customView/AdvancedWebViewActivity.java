package com.directoriodelicias.apps.delicias.customView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.directoriodelicias.apps.delicias.R;
import com.google.gson.Gson;

import java.util.HashMap;

import im.delight.android.webview.AdvancedWebView;


public class AdvancedWebViewActivity extends AppCompatActivity implements AdvancedWebView.Listener {

    private AdvancedWebView mWebView;
    private String link;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private TextView toolbar_desc;
    private String TAG = "AdvancedWebViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advanced_web_view);


        initToolbar();

        mWebView = findViewById(R.id.webview);
        mWebView.setListener(this, this);

        if (getIntent().hasExtra("BMLink")) {
            mWebView.loadUrl(getIntent().getExtras().getString("BMLink"));
        } else {
            Toast.makeText(this, getString(R.string.error_try_later), Toast.LENGTH_SHORT).show();
            finish();
        }

    }


    public void initToolbar() {
        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);

        toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.business_manager) + " - " + getString(R.string.app_name));
        toolbar_title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        toolbar_desc = toolbar.findViewById(R.id.toolbar_subtitle);
        toolbar_desc.setVisibility(View.GONE);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.business_manager_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (android.R.id.home == item.getItemId()) {
            finish();
            overridePendingTransition(R.anim.righttoleft_enter, R.anim.righttoleft_exit);
        } else if (R.id.manage_business_options == item.getItemId()) {

            HashMap<String, String> mb_fields = new HashMap<String, String>();
            mb_fields.put("action", "manage_business_options");
            mb_fields.put("device", "android");

            Gson gson = new Gson();
            String data = gson.toJson(mb_fields);

            callJavaScript(mWebView, "handle_device_events", data);
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        //show loading at the first page run
        toolbar.findViewById(R.id.progressLayout).setVisibility(View.VISIBLE);

        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) {
            return;
        }
        // ...
        super.onBackPressed();

        overridePendingTransition(R.anim.righttoleft_enter, R.anim.righttoleft_exit);

    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        toolbar.findViewById(R.id.progressLayout).setVisibility(View.VISIBLE);
    }


    @Override
    public void onPageFinished(String url) {
        toolbar.findViewById(R.id.progressLayout).setVisibility(View.GONE);
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
    }

    @Override
    public void onExternalPageRequest(String url) {
    }


    private void callJavaScript(WebView view, String methodName, String param) {

        view.getSettings().setJavaScriptEnabled(true);
        //view.loadUrl("file:///views/backend/footer.php");


        StringBuilder stringBuilder = new StringBuilder();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            stringBuilder.append("try{");
        } else {
            stringBuilder.append("javascript:try{");
        }
        stringBuilder.append(methodName);
        stringBuilder.append("(");
        stringBuilder.append(param);

       /* String separator = "";
        stringBuilder.append(separator);
        stringBuilder.append("'");
        stringBuilder.append(param.toString().replace("'", "\\'"));
        stringBuilder.append("'");*/

        stringBuilder.append(")}catch(error){console.error(error.message);}");
        final String call = stringBuilder.toString();
        Log.i(TAG, methodName + " : call=" + call);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            view.evaluateJavascript(call, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.i(TAG, methodName + " : callback=" + s);
                }
            });
        } else {
            view.loadUrl(call);
        }
    }
}
