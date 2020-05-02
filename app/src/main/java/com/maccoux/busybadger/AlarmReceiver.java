package com.maccoux.busybadger;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.maccoux.busybadger.UIMain.ViewEventDebug;

public class AlarmReceiver extends BroadcastReceiver {
    public static String NOT_ID = "notification-id";
    public static String NOT="notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = intent.getParcelableExtra(NOT);
        int ID = intent.getIntExtra(NOT_ID,0);

        notificationManager.notify(ID,notification);
    }

}
