package com.example.aseproject.events;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    String messageTitle, messageBody;

    @Override
    public void onReceive(Context context, Intent intent){
        Bundle extras = intent.getExtras();
        messageTitle = extras.getString("title");
        messageBody = extras.getString("body");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify")
                .setContentTitle(messageTitle)
                .setContentText(messageBody);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200,builder.build());
    }
}

