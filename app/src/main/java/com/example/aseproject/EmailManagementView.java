package com.example.aseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.auth.FirebaseAuth;

public class EmailManagementView extends AppCompatActivity {

    WebView browser;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_management_view);

        Intent intent = getIntent();
        String platform = intent.getStringExtra("platform").toString();

        if (platform.equals("google"))
        {
            // this is for google
            url = "https://www.google.com/gmail/about/";
            url = "https://mail.google.com";
        }

        else if (platform.equals("yahoo")) {
            // this is for yahoo
            url = "https://mail.yahoo.com";
        }

        else {
            // this is outlook
            url = "https://outlook.live.com/owa/";
        }

        browser = (WebView) findViewById(R.id.webview);
        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        browser.setWebViewClient(new WebViewClient());

        browser.loadUrl(url);
    }

    public void signUserOut()
    {
        FirebaseAuth.getInstance().signOut();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent myIntent = new Intent(EmailManagementView.this, MainActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
                finish();
            }
        }, 3000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            // sign user out
            signUserOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}