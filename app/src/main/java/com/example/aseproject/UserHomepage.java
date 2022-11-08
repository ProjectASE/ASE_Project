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
import com.example.aseproject.notes.NotesManagement;
import com.google.firebase.auth.FirebaseAuth;

public class UserHomepage extends AppCompatActivity {

    TextView emailManagementView;
    TextView eventManagementView;
    TextView notesManagementView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);

        emailManagementView = findViewById(R.id.emailManagement);
        eventManagementView = findViewById(R.id.eventManagement);
        notesManagementView = findViewById(R.id.notesManagement);


        emailManagementView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEmailManagement();
            }
        });

        eventManagementView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEventManagement();
            }
        });

        notesManagementView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotesManagement();
            }
        });

    }

    private void openEmailManagement() {
        startActivity(new Intent(UserHomepage.this, EmailManagement.class));
    }

    private void openEventManagement() {
        startActivity(new Intent(UserHomepage.this, EventManagement.class));
    }

    private void openNotesManagement() {
        startActivity(new Intent(UserHomepage.this, NotesManagement.class));
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