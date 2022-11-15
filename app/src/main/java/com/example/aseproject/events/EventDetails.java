package com.example.aseproject.events;

import android.content.Intent;
import android.os.Bundle;

import com.example.aseproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.format.DateFormat;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;


public class EventDetails extends AppCompatActivity {
    Intent data;
    Calendar calendarIns;
    String eDate,eMonth,eYear,eHour,eMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        data = getIntent();

        TextView title = findViewById(R.id.eventDetailsTitle);
        TextView location = findViewById(R.id.eventDetailsLocation);
        TextView time = findViewById(R.id.eventDetailsTime);
        TextView date = findViewById(R.id.eventDetailsDate);
        TextView description = findViewById(R.id.eventDetailsDescription);
        description.setMovementMethod(new ScrollingMovementMethod());


        calendarIns = Calendar.getInstance();
        calendarIns.set(Calendar.YEAR, Integer.valueOf(data.getStringExtra("year")));
        calendarIns.set(Calendar.MONTH, Integer.valueOf(data.getStringExtra("month")));
        calendarIns.set(Calendar.DATE, Integer.valueOf(data.getStringExtra("date")));
        calendarIns.set(Calendar.HOUR, Integer.valueOf(data.getStringExtra("hour")));
        calendarIns.set(Calendar.MINUTE, Integer.valueOf(data.getStringExtra("minute")));
        String dateText = DateFormat.format("EEEE, MMM d, yyyy", calendarIns).toString();
        String timeText = DateFormat.format("h:mm a", calendarIns).toString();

        title.setText(data.getStringExtra("title"));
        location.setText(data.getStringExtra("location"));
        time.setText(timeText);
        date.setText(dateText);
        description.setText(data.getStringExtra("description"));

        Button btn = findViewById(R.id.btnEdit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),EditEvent.class);
                i.putExtra("title",data.getStringExtra("title"));
                i.putExtra("location",data.getStringExtra("location"));
                i.putExtra("hour",data.getStringExtra("hour"));
                i.putExtra("minute",data.getStringExtra("minute"));
                i.putExtra("date",data.getStringExtra("date"));
                i.putExtra("month",data.getStringExtra("month"));
                i.putExtra("year",data.getStringExtra("year"));
                i.putExtra("description",data.getStringExtra("description"));
                i.putExtra("eventId",data.getStringExtra("eventId"));
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EventDetails.this, EventManagement.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
