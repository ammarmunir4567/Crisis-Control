package com.example.testing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testing.R;

public class APIWebClient extends AppCompatActivity {
    private WebView APIWebView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiweb_client);

        Intent intent=getIntent();
        url=intent.getStringExtra("url");
        APIWebView = findViewById(R.id.APIWebView);
        APIWebView.loadUrl(url);
        APIWebView.setWebViewClient(new WebViewClient());
        APIWebView.getSettings().setJavaScriptEnabled(true);

    }
}