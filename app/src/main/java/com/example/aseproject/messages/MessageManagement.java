package com.example.aseproject.messages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aseproject.R;
import com.example.aseproject.notes.Note;
import com.example.aseproject.notes.NotesManagement;
import com.example.aseproject.notes.ShowNote;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MessageManagement extends AppCompatActivity {

    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> messageArray;
    ArrayList<Message> allMessages;
    List<DocumentSnapshot> myListOfDocuments;

    FirebaseFirestore fStore;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_management);

        fStore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        messageArray = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, messageArray);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myIntent = new Intent(MessageManagement.this, ShowMessage.class);
                myIntent.putExtra("id", allMessages.get(i).getId().toString());
                myIntent.putExtra("title", allMessages.get(i).getTitle().toString());
                myIntent.putExtra("message", allMessages.get(i).getMessage().toString());
                myIntent.putExtra("messageType", allMessages.get(i).getMessageType().toString());
                startActivity(myIntent);
            }
        });


        viewMessages();


    }

    private void viewMessages() {
        FirebaseFirestore.getInstance()
                .collection("Messages").document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).collection("myMessages")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            myListOfDocuments = task.getResult().getDocuments();

                            allMessages = new ArrayList<>();

                            if (myListOfDocuments.size()==0)
                            {
                                messageArray.add("No Messages");
                            }

                            for (int i=0; i<myListOfDocuments.size(); i++)
                            {
                                String id = myListOfDocuments.get(i).getId().toString();
                                String title = myListOfDocuments.get(i).getString("title");
                                String message = myListOfDocuments.get(i).getString("message");
                                String messageType = myListOfDocuments.get(i).getString("messageType");

                                Message myMessage = new Message(id, title, message, messageType);
                                allMessages.add(myMessage);

                                messageArray.add(title + " " + messageType);


                            }

//                            Toast.makeText(Friends.this, "Items = " + items.size(), Toast.LENGTH_LONG).show();

                            adapter.notifyDataSetChanged();
                            listView.setAdapter(adapter);

                        }
                    }
                });
//        Toast.makeText(Friends.this, "FE = " + items.size(), Toast.LENGTH_LONG).show();
    }
}