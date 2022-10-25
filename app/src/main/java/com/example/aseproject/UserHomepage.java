package com.example.aseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.aseproject.events.EventManagement;
import com.google.firebase.auth.FirebaseAuth;

public class UserHomepage extends AppCompatActivity {

    TextView emailManagementView;
    TextView eventManagementView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);

        emailManagementView = findViewById(R.id.emailManagement);
        emailManagementView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEmailManagement();
            }
        });

        eventManagementView = findViewById(R.id.eventManagement);
        eventManagementView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEventManagement();
            }
        });

    }

    private void openEmailManagement() {
        startActivity(new Intent(UserHomepage.this, EmailManagement.class));
    }

    private void openEventManagement() {
        startActivity(new Intent(UserHomepage.this, EventManagement.class));
    }

    public void signUserOut() {
        FirebaseAuth.getInstance().signOut();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent myIntent = new Intent(UserHomepage.this, MainActivity.class);
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