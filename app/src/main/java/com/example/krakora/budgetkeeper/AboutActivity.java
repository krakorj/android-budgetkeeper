package com.example.krakora.budgetkeeper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Fill the webview by About.html
        WebView webview = (WebView) findViewById(R.id.about_webview);
        WebSettings webSettings =
              webview.getSettings();
        webview.setWebChromeClient(
                 new WebChromeClient());
        webview.loadUrl("file:///android_asset/html/about.html");

        // Home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
