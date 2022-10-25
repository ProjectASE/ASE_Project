package com.example.aseproject.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.aseproject.R;
import com.example.aseproject.UserHomepage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditEvent extends AppCompatActivity {
    Intent data;
    FirebaseFirestore fStore;
    EditText editEventTitle, editEventLocation, editEventTime, editEventDate, editEventDescription;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);

        fStore = fStore.getInstance();
        editEventTitle = findViewById(R.id.editEventTitle);
        editEventLocation = findViewById(R.id.editEventLocation);
        editEventTime = findViewById(R.id.editEventTime);
        editEventDate = findViewById(R.id.editEventDate);
        editEventDescription = findViewById(R.id.editEventDescription);

        user = FirebaseAuth.getInstance().getCurrentUser();
        data = getIntent();

        String eventTitle = data.getStringExtra("title");
        String eventLocation = data.getStringExtra("location");
        String eventTime = data.getStringExtra("time");
        String eventDate = data.getStringExtra("date");
        String eventDescription = data.getStringExtra("description");

        editEventTitle.setText(eventTitle);
        editEventLocation.setText(eventLocation);
        editEventTime.setText(eventTime);
        editEventDate.setText(eventDate);
        editEventDescription.setText(eventDescription);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String eTitle = editEventTitle.getText().toString();
                String eLocation = editEventLocation.getText().toString();
                String eTime = editEventTime.getText().toString();
                String eDate = editEventDate.getText().toString();
                String eDescription = editEventDescription.getText().toString();

                if(eDescription.isEmpty() || eTime.isEmpty() || eDate.isEmpty()){
                    Toast.makeText(EditEvent.this, "Can not Save Event with Empty Field.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DocumentReference docref = fStore.collection("Events").document(user.getUid()).collection("myEvents").document(data.getStringExtra("eventId"));
                Map<String,Object> event = new HashMap<>();
                event.put("title",eTitle);
                event.put("location",eLocation);
                event.put("time",eTime);
                event.put("date",eDate);
                event.put("description",eDescription);

                docref.update(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditEvent.this, "Event Saved.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ViewEvent.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditEvent.this, "Error, Try again.", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference docref = fStore.collection("Events").document(user.getUid()).collection("myEvents").document(data.getStringExtra("eventId"));
                docref.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditEvent.this, "Event Deleted.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ViewEvent.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditEvent.this, "Error, Try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EditEvent.this, ViewEvent.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
