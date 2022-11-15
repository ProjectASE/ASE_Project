package com.example.aseproject.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aseproject.MainActivity;
import com.example.aseproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotesManagement extends AppCompatActivity {

    Button add_notes;
    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> notesArray;
    ArrayList<Note> allNotes;
    List<DocumentSnapshot> myListOfDocuments;


    FirebaseFirestore fStore;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_management);

        fStore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        notesArray = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, notesArray);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        add_notes = findViewById(R.id.add_note);

        add_notes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(NotesManagement.this, ShowNote.class);
                startActivity(myIntent);
       //         insertNote();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myIntent = new Intent(NotesManagement.this, ShowNote.class);
                myIntent.putExtra("id", allNotes.get(i).getId().toString());
                myIntent.putExtra("title", allNotes.get(i).getTitle().toString());
                myIntent.putExtra("note", allNotes.get(i).getNote().toString());
                startActivity(myIntent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                deleteNote(i);
                return false;
            }
        });

        viewNotes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            // sign user out
            signUserOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void signUserOut() {
        FirebaseAuth.getInstance().signOut();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent myIntent = new Intent(NotesManagement.this, MainActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
                finish();
            }
        }, 3000);
    }

    public void insertNote() {

        Toast.makeText(NotesManagement.this, "Notes", Toast.LENGTH_SHORT).show();

        // save note
        CollectionReference colref = fStore.collection("Notes").document(user.getUid()).collection("myNotes");
        String eId = colref.document().getId();
        Note note = new Note(eId, "note", "new note");
        colref.document(eId).set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(NotesManagement.this, "Note Added.", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(view.getContext(), ViewEvent.class);
//                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NotesManagement.this, "Error, Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewNotes() {
        FirebaseFirestore.getInstance()
                .collection("Notes").document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).collection("myNotes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            myListOfDocuments = task.getResult().getDocuments();

                            allNotes = new ArrayList<>();

                            if (myListOfDocuments.size()==0)
                            {
                                notesArray.add("No Notes");
                            }

                            for (int i=0; i<myListOfDocuments.size(); i++)
                            {
                                String id = myListOfDocuments.get(i).getId().toString();
                                String title = myListOfDocuments.get(i).getString("title");
                                String note = myListOfDocuments.get(i).getString("note");

                                Note myNote = new Note(id, title, note);
                                allNotes.add(myNote);

                                notesArray.add(title);

                            }

//                            Toast.makeText(Friends.this, "Items = " + items.size(), Toast.LENGTH_LONG).show();

                            adapter.notifyDataSetChanged();
                            listView.setAdapter(adapter);

                        }
                    }
                });
//        Toast.makeText(Friends.this, "FE = " + items.size(), Toast.LENGTH_LONG).show();
    }

    private void deleteNote(int position)
    {
        AlertDialog.Builder adb=new AlertDialog.Builder(NotesManagement.this);
        adb.setTitle("Delete Note");
        adb.setMessage("Do you want to delete?");
        final int positionToRemove = position;
        adb.setNeutralButton("Escape", null);

        adb.setPositiveButton("Accept", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                FirebaseFirestore.getInstance()
                        .collection("Notes").document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).collection("myNotes").document(allNotes.get(position).getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                allNotes.remove(position);
                                notesArray.remove(position);
                                adapter.notifyDataSetChanged();
                            }

                        });
            }});


        adb.show();
    }




}