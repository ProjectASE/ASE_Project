package com.example.aseproject.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aseproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ShowTodo extends AppCompatActivity {

    EditText noteField;
    Button saveButton;
    FirebaseFirestore fStore;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_todo);

        fStore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        noteField = findViewById(R.id.noteField);
        saveButton = findViewById(R.id.saveButton);

        String id = getIntent().getStringExtra("id");
        final String title;
        final String note;
        if (id != null) {
            title = getIntent().getStringExtra("title");
            note = getIntent().getStringExtra("note");
            noteField.setText(note);
        }



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == null)
                {
                    addNote(noteField.getText().toString());
                }

                else {
                    updateNote(user.getUid(), getIntent().getStringExtra("id"), getIntent().getStringExtra("title"), noteField.getText().toString());
                }
            }
        });




    }

    private void updateNote(String uid, String id, String title, String note)
    {
        Toast.makeText(ShowTodo.this, "update", Toast.LENGTH_SHORT).show();

        Log.d("uid " , uid);
        Log.d("id " , id);
        Log.d("title " , title);
        Log.d("note " , note);

        if (note.split(" ")[0] != null)
        {
            title = note.split(" ")[0];
        }
        Map<String, Object> userMap = new HashMap<>();
        userMap = new HashMap<>();
        userMap.put("id", id);
        userMap.put("note", note);
        userMap.put("title", title);

        fStore.collection("Notes").document(uid).collection("myNotes").document(id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("data " , "updated");
                startActivity(new Intent(ShowTodo.this, TodoManagement.class));

            }


        });

    }

    private void addNote(String note)
    {
        Toast.makeText(ShowTodo.this, "add", Toast.LENGTH_SHORT).show();

        // save note
        CollectionReference colref = fStore.collection("Notes").document(user.getUid()).collection("myNotes");
        String eId = colref.document().getId();
        String title = note.split(" ")[0];
        if (title == null)
        {
            title = "note";
        }
        Todo newNote = new Todo(eId, title, note);
        colref.document(eId).set(newNote).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ShowTodo.this, "Todo Added.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ShowTodo.this, TodoManagement.class));

//                Intent i = new Intent(view.getContext(), ViewEvent.class);
//                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ShowTodo.this, "Error, Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}