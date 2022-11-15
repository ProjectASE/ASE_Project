package com.example.aseproject.events;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import com.example.aseproject.R;
import com.example.aseproject.UserHomepage;
import com.example.aseproject.events.model.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AddEvent extends AppCompatActivity {
    FirebaseFirestore fStore;
    EditText eventTitle, eventLocation, eventDescription;
    TextView timeTextView,dateTextView;
    FirebaseUser user;
    Calendar calendarIns;
    String eHour, eMinute, eDate, eMonth, eYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        calendarIns = Calendar.getInstance();

        fStore = FirebaseFirestore.getInstance();

        eventTitle = findViewById(R.id.eventTitle);
        eventLocation = findViewById(R.id.eventLocation);
        eventDescription = findViewById(R.id.eventDescription);

        timeTextView = findViewById(R.id.timeTextView);
        dateTextView = findViewById(R.id.dateTextView);

        user = FirebaseAuth.getInstance().getCurrentUser();

        Button btn = findViewById(R.id.btnCreateEvent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eTitle = eventTitle.getText().toString();
                String eLocation = eventLocation.getText().toString();
                String eDescription = eventDescription.getText().toString();

                if(eDescription.isEmpty()){
                    Toast.makeText(AddEvent.this, "Can not Save Event with Empty Field.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // save event
                CollectionReference colref = fStore.collection("Events").document(user.getUid()).collection("myEvents");
                String eId = colref.document().getId();
                Event event = new Event(eId,eTitle, eLocation, eHour, eMinute, eDate, eMonth, eYear, eDescription);
                colref.document(eId).set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddEvent.this, "Event Added.", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(view.getContext(),EventManagement.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddEvent.this, "Error, Try again.", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(AddEvent.this, ReminderBroadcast.class);
                intent.putExtra("title",eTitle).putExtra("body",eDescription);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddEvent.this,0,intent,0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                long timeatButtonClick = System.currentTimeMillis();
                long timeafterButtonClick = 1000 * 5;
                alarmManager.set(AlarmManager.RTC_WAKEUP,timeatButtonClick + timeafterButtonClick,pendingIntent);

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

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "ReminderChannel";
            String desc = "Channel for Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify",name,importance);
            channel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
