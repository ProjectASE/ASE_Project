package com.example.aseproject.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.example.aseproject.events.model.Event;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ViewEvent extends AppCompatActivity {
    DrawerLayout drawerLayout;
    RecyclerView eventLists;
    FirebaseFirestore fStore;
    FirestoreRecyclerAdapter<Event,EventViewHolder> eventAdapter;
    FirebaseUser user;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();


        Query query = fStore.collection("Events").document(user.getUid()).collection("myEvents").orderBy("title", Query.Direction.DESCENDING);;

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
                final String docId = eventAdapter.getSnapshots().getSnapshot(i).getId();

                eventViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), EventDetails.class);
                        i.putExtra("title",event.getTitle());
                        i.putExtra("location",event.getLocation());
                        i.putExtra("time",event.getTime());
                        i.putExtra("date",event.getDate());
                        i.putExtra("description",event.getDescription());
                        i.putExtra("eventId",docId);
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
        drawerLayout = findViewById(R.id.drawer);

        eventLists.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
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
