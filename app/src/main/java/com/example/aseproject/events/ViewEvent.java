package com.example.aseproject.events;

import static com.example.aseproject.events.Calendar.CalendarUtils.daysInMonthArray;
import static com.example.aseproject.events.Calendar.CalendarUtils.monthYearFromDate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aseproject.R;
import com.example.aseproject.events.Calendar.CalendarAdapter;
import com.example.aseproject.events.Calendar.CalendarUtils;
import com.example.aseproject.events.model.Event;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ViewEvent extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    RecyclerView eventLists;
    FirebaseFirestore fStore;
    FirestoreRecyclerAdapter<Event,EventViewHolder> eventAdapter;
    FirebaseUser user;
    FirebaseAuth fAuth;
    TextView monthYearText;
    RecyclerView calendarRecyclerView;
    String localDate, localMonth, localYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        initWidgets();
        Bundle extras = getIntent().getExtras();
        localDate = extras.getString("date");
        localMonth = extras.getString("month");
        localYear = extras.getString("year");
        LocalDate ld = LocalDate.of(Integer.valueOf(localYear), Integer.valueOf(localMonth), Integer.valueOf(localDate));
        CalendarUtils.selectedDate = ld;
        setMonthView();
        setEvents(localDate, localMonth, localYear);
    }

    private void setEvents(String date, String month, String year){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        Query query = fStore.collection("Events").document(user.getUid()).collection("myEvents")
                .whereEqualTo("date", date)
                .whereEqualTo("month", month)
                .whereEqualTo("year", year)
                .orderBy("hour", Query.Direction.ASCENDING)
                .orderBy("minute", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Event> allEvents = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query,Event.class)
                .build();

        eventAdapter = new FirestoreRecyclerAdapter<Event, EventViewHolder>(allEvents) {
            @Override
            protected void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, final int i, @NonNull final Event event) {
                eventViewHolder.eventTitle.setText(event.getTitle());
                eventViewHolder.eventDescription.setText(event.getDescription());
                final int code = getRandomColor();
                eventViewHolder.eCardView.setCardBackgroundColor(eventViewHolder.view.getResources().getColor(code,null));
                eventViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), EventDetails.class);
                        i.putExtra("title",event.getTitle());
                        i.putExtra("location",event.getLocation());
                        //i.putExtra("time",event.getTime());
                        //i.putExtra("date",event.getDate());
                        i.putExtra("description",event.getDescription());
                        i.putExtra("eventId",event.getId());
                        v.getContext().startActivity(i);
                    }
                });
            }

            @NonNull
            @Override
            public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_view_layout,parent,false);
                return new EventViewHolder(view);
            }
        };

        eventLists = findViewById(R.id.eventlist);
        eventLists.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        eventLists.setAdapter(eventAdapter);
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{
        TextView eventTitle,eventDescription;
        View view;
        CardView eCardView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.titles);
            eventDescription = itemView.findViewById(R.id.description);
            eCardView = itemView.findViewById(R.id.eventCard);
            view = itemView;
        }
    }

    private int getRandomColor() {

        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.red);
        colorCode.add(R.color.orange);
        colorCode.add(R.color.yellow);
        colorCode.add(R.color.green);
        colorCode.add(R.color.skyblue);
        colorCode.add(R.color.blue);
        colorCode.add(R.color.lightPurple);
        colorCode.add(R.color.purple);
        colorCode.add(R.color.lightGreen);
        colorCode.add(R.color.pink);

        Random randomColor = new Random();
        int number = randomColor.nextInt(colorCode.size());
        return colorCode.get(number);

    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray();
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        //setMonthView();
        changeDate(CalendarUtils.selectedDate);
    }

    public void nextMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        //setMonthView();
        changeDate(CalendarUtils.selectedDate);
    }
    private void changeDate(LocalDate date){
        localDate = String.valueOf(date.getDayOfMonth());
        localMonth = String.valueOf(date.getMonth().getValue());
        localYear= String.valueOf(date.getYear());
        Intent intent = new Intent(ViewEvent.this, ViewEvent.class)
                .putExtra("date", localDate)
                .putExtra("month", localMonth)
                .putExtra("year", localYear);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(intent);
    }
    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null) {
            /*CalendarUtils.selectedDate = date;
            setMonthView();*/
            changeDate(date);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        eventAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (eventAdapter != null) {
            eventAdapter.stopListening();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewEvent.this, EventManagement.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}

