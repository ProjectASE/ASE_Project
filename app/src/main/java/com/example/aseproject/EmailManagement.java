package com.example.aseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EmailManagement extends AppCompatActivity {

    TextView googleManageView;
    TextView yahooManageView;
    TextView outlookManageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_management);

        googleManageView = (TextView) findViewById(R.id.googleManage);
        yahooManageView = (TextView) findViewById(R.id.yahooManage);
        outlookManageView = (TextView) findViewById(R.id.outlookManage);

        googleManageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(EmailManagement.this, EmailManagementView.class);
                myIntent.putExtra("platform", "google");
                startActivity(myIntent);
            }
        });

        yahooManageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(EmailManagement.this, EmailManagementView.class);
                myIntent.putExtra("platform", "yahoo");
                startActivity(myIntent);
            }
        });

        outlookManageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(EmailManagement.this, EmailManagementView.class);
                myIntent.putExtra("platform", "outlook");
                startActivity(myIntent);
            }
        });
    }

    public void signUserOut()
    {
        FirebaseAuth.getInstance().signOut();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent myIntent = new Intent(EmailManagement.this, MainActivity.class);
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