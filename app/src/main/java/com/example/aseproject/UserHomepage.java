package com.example.aseproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aseproject.events.EventManagement;
import com.example.aseproject.messages.MessageManagement;
import com.example.aseproject.notes.NotesManagement;
import com.example.aseproject.todo.TodoManagement;
import com.google.firebase.auth.FirebaseAuth;

public class UserHomepage extends AppCompatActivity {

    ImageView eventManagementView, notesManagementView, messageManagement, todoManagement, logout;

    static EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);

        createNotificationChannel();

        eventManagementView = findViewById(R.id.eventManagement);
        notesManagementView = findViewById(R.id.notesManagement);
        messageManagement = findViewById(R.id.messageManagement);
        todoManagement = findViewById(R.id.todoManagement);
        logout = findViewById(R.id.logout);

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

        messageManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMessageManagement();
            }
        });
        todoManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTodoManagement();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { signUserOut();}
        });

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("0", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void openEventManagement() {
        startActivity(new Intent(UserHomepage.this, EventManagement.class));
    }

    private void openNotesManagement() {
        startActivity(new Intent(UserHomepage.this, NotesManagement.class));
    }

    private void openMessageManagement() {
        startActivity(new Intent(UserHomepage.this, MessageManagement.class));
    }

    private void openTodoManagement() {
        startActivity(new Intent(UserHomepage.this, TodoManagement.class));
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


    private void getNumber() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserHomepage.this);
        builder.setTitle("Enter Number:");

// Set up the input
        input = new EditText(UserHomepage.this);
        input.setText(retrieveNumber());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveNumber(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void saveNumber(String number) {


        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
        myEdit.putString("school_number", number);
        //       myEdit.putInt("age", Integer.parseInt("20"));

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
        myEdit.commit();


    }

    private String retrieveNumber() {
        // read preferences

        // Retrieving the value using its keys the file name
// must be same in both saving and retrieving the data
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show
        String s1 = sh.getString("school_number", "");
        //      int a = sh.getInt("age", 0);

// We can then use the data
        Toast.makeText(UserHomepage.this, s1, Toast.LENGTH_LONG).show();

        return s1;
    }
}