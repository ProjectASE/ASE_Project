package com.example.aseproject.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aseproject.MainActivity;
import com.example.aseproject.R;
import com.example.aseproject.UserHomepage;

import java.time.LocalDate;

public class EventManagement extends AppCompatActivity {

    public String localDate,localMonth,localYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);
        localDate = String.valueOf(LocalDate.now().getDayOfMonth());
        localMonth = String.valueOf(LocalDate.now().getMonth().getValue());
        localYear= String.valueOf(LocalDate.now().getYear());
    }

    public void onCreateEventCardViewClick(View view) {
        Intent intent = new Intent(EventManagement.this, AddEvent.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void onViewCreatedEventCardViewClick(View view) {
        Intent intent = new Intent(EventManagement.this, ViewEvent.class)
                .putExtra("date", localDate)
                .putExtra("month", localMonth)
                .putExtra("year", localYear);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EventManagement.this, UserHomepage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
