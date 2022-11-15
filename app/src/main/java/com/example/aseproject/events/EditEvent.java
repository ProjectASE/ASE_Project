package com.example.aseproject.events;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditEvent extends AppCompatActivity {
    Intent data;
    FirebaseFirestore fStore;
    EditText editEventTitle, editEventLocation, editEventDescription;
    FirebaseUser user;
    Calendar calendarIns;
    String eHour, eMinute, eDate, eMonth, eYear;
    TextView timeTextView,dateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        fStore = fStore.getInstance();
        editEventTitle = findViewById(R.id.editEventTitle);
        editEventLocation = findViewById(R.id.editEventLocation);
        timeTextView = findViewById(R.id.editEventTime);
        dateTextView = findViewById(R.id.editEventDate);
        editEventDescription = findViewById(R.id.editEventDescription);

        user = FirebaseAuth.getInstance().getCurrentUser();
        data = getIntent();

        calendarIns = Calendar.getInstance();
        calendarIns.set(Calendar.YEAR, Integer.valueOf(data.getStringExtra("year")));
        calendarIns.set(Calendar.MONTH, Integer.valueOf(data.getStringExtra("month")));
        calendarIns.set(Calendar.DATE, Integer.valueOf(data.getStringExtra("date")));
        calendarIns.set(Calendar.HOUR, Integer.valueOf(data.getStringExtra("hour")));
        calendarIns.set(Calendar.MINUTE, Integer.valueOf(data.getStringExtra("minute")));
        String eventTime = DateFormat.format("EEEE, MMM d, yyyy", calendarIns).toString();
        String eventDate = DateFormat.format("h:mm a", calendarIns).toString();

        String eventTitle = data.getStringExtra("title");
        String eventLocation = data.getStringExtra("location");
        String eventDescription = data.getStringExtra("description");

        editEventTitle.setText(eventTitle);
        editEventLocation.setText(eventLocation);
        timeTextView.setText(eventTime);
        dateTextView.setText(eventDate);
        editEventDescription.setText(eventDescription);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String eTitle = editEventTitle.getText().toString();
                String eLocation = editEventLocation.getText().toString();
                String eTime = timeTextView.getText().toString();
                String eDate = dateTextView.getText().toString();
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
    public void onDateClick(View view) {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                eDate = String.valueOf(date);
                eMonth = String.valueOf(month+1);
                eYear = String.valueOf(year);
                calendarIns.set(Calendar.YEAR, year);
                calendarIns.set(Calendar.MONTH, month);
                calendarIns.set(Calendar.DATE, date);
                String dateText = DateFormat.format("EEEE, MMM d, yyyy", calendarIns).toString();
                dateTextView.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();

    }

    public void onTimeClick(View view) {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                eHour = String.valueOf(hour);
                eMinute = String.valueOf(minute);
                calendarIns.set(Calendar.HOUR, hour);
                calendarIns.set(Calendar.MINUTE, minute);
                String dateText = DateFormat.format("h:mm a", calendarIns).toString();
                timeTextView.setText(String.valueOf(dateText));
            }
        }, HOUR, MINUTE, is24HourFormat);

        timePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EditEvent.this, EventManagement.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
