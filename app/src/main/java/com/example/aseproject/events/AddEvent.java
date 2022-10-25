package com.example.aseproject.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.aseproject.R;
import com.example.aseproject.UserHomepage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddEvent extends AppCompatActivity {
    FirebaseFirestore fStore;
    EditText eventTitle, eventLocation, eventTime, eventDate, eventDescription;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);
        Toolbar toolbar = findViewById(R.id.toolbar);

        fStore = FirebaseFirestore.getInstance();

        eventTitle = findViewById(R.id.eventTitle);
        eventLocation = findViewById(R.id.eventLocation);
        eventTime = findViewById(R.id.eventTime);
        eventDate = findViewById(R.id.eventDate);
        eventDescription = findViewById(R.id.eventDescription);


        user = FirebaseAuth.getInstance().getCurrentUser();


        Button btn = findViewById(R.id.btnCreateEvent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eTitle = eventTitle.getText().toString();
                String eLocation = eventLocation.getText().toString();
                String eTime = eventTime.getText().toString();
                String eDate = eventDate.getText().toString();
                String eDescription = eventDescription.getText().toString();

                if(eDescription.isEmpty() || eTime.isEmpty() || eDate.isEmpty()){
                    Toast.makeText(AddEvent.this, "Can not Save Event with Empty Field.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // save event
                DocumentReference docref = fStore.collection("Events").document(user.getUid()).collection("myEvents").document();
                Map<String,Object> event = new HashMap<>();
                event.put("title",eTitle);
                event.put("location",eLocation);
                event.put("time",eTime);
                event.put("date",eDate);
                event.put("description",eDescription);
                docref.set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddEvent.this, "Event Added.", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(view.getContext(),ViewEvent.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddEvent.this, "Error, Try again.", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddEvent.this, EventManagement.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
