package com.mountreachsolution.e_learningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mountreachsolution.e_learningapp.HomeActivity.HomeActivity;

public class CompilerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compiler);

        setTitle("E-Learning App Compiler");

        // Find the WebView by its unique ID
        WebView webView = findViewById(R.id.web);

        // loading https://www.geeksforgeeks.org url in the WebView.
        webView.loadUrl("https://onecompiler.com/");

        // this will enable the javascript.
        webView.getSettings().setJavaScriptEnabled(true);

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CompilerActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}