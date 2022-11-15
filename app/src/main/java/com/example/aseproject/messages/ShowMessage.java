package com.example.aseproject.messages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aseproject.R;
import com.example.aseproject.notes.NotesManagement;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShowMessage extends AppCompatActivity {

    TextView messageView;
    Button deleteButton;
    FirebaseFirestore fStore;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message);

        fStore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        messageView = findViewById(R.id.messageView);
        deleteButton = findViewById(R.id.deleteButton);

        String id = getIntent().getStringExtra("id");
        final String title;
        final String message;
        final String messageType;
        if (id != null) {
            title = getIntent().getStringExtra("title");
            message = getIntent().getStringExtra("message");
            messageType = getIntent().getStringExtra("messageType");
            messageView.setText(message);
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adb=new AlertDialog.Builder(ShowMessage.this);
                adb.setTitle("Delete Note");
                adb.setMessage("Do you want to delete?");
                adb.setNeutralButton("Escape", null);

                adb.setPositiveButton("Accept", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseFirestore.getInstance()
                                .collection("Messages").document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).collection("myMessages").document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity(new Intent(ShowMessage.this, MessageManagement.class));
                                    }

                                });
                    }});


                adb.show();
            }
        });
    }
}