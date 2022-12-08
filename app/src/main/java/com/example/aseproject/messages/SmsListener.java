package com.example.aseproject.messages;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.aseproject.MainActivity;
import com.example.aseproject.R;
import com.example.aseproject.notes.Note;
import com.example.aseproject.notes.NotesManagement;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SmsListener extends BroadcastReceiver {

    private SharedPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    Log.d("sagar: ", message);
                    Log.d("sender: ", senderNum);


                    // -----------------------------------------------
                    // read preferences

                    // Retrieving the value using its keys the file name
// must be same in both saving and retrieving the data
                    SharedPreferences sh = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show
//                    String s1 = sh.getString("school_number", "");
                    String s1 = "+15199928538";
                    //      int a = sh.getInt("age", 0);

// We can then use the data
                    Log.d("s1: ", s1);




                    // ------------------------------------------------

                    if (senderNum.equals(s1))
                    {
                        insertMessage(message);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "0")
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("My notification")
                                .setContentText(message)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText("Much longer text that cannot fit one line..."))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int for each notification that you must define
                        notificationManager.notify(126, builder.build());
                    }




                    try {
                        if (senderNum.contains("MOB_NUMBER")) {
                            Toast.makeText(context,"",Toast.LENGTH_SHORT).show();

                            Intent intentCall = new Intent(context, MainActivity.class);
                            intentCall.putExtra("message", currentMessage.getMessageBody());

                            PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intentCall, PendingIntent.FLAG_UPDATE_CURRENT);
                            pendingIntent.send();
                        }
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public void insertMessage(String message) {

        ArrayList<String> messageWords = new ArrayList<>();
        String messageArray[] = message.split(" ");
        for (int i=0; i<messageArray.length; i++)
        {
            messageWords.add(messageArray[i].toLowerCase());
        }

        String messageType = "positive";
        String negativeWords[] = {"sorry", "regret", "not", "bad", "worse", "failure"};

        for (int i=0; i<negativeWords.length; i++)
        {
            if (messageWords.contains(negativeWords[i]))
            {
                messageType = "negative";
            }
        }

        Log.d("message type: ", messageType);
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // save note
        CollectionReference colref = fStore.collection("Messages").document(user.getUid()).collection("myMessages");
        String eId = colref.document().getId();
        String title = messageArray[0] + " " + messageArray[1] + " " + messageArray[2];
        Message saveMessage = new Message(eId, title, message, messageType);
        colref.document(eId).set(saveMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

//                Intent i = new Intent(view.getContext(), ViewEvent.class);
//                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("message: ", "failed");
            }
        });
    }


}
