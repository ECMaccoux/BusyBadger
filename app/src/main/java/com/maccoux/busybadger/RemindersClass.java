package com.maccoux.busybadger;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Random;

import com.maccoux.busybadger.Room.Class;
import com.maccoux.busybadger.UIMain.ViewEventDebug;

public class RemindersClass {
    Calendar EventDate;
    boolean[] ReminderTime;
    Class event;
    Context context;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    public RemindersClass(Calendar EventDate, boolean[] ReminderTime, Class event, Context context) {
        this.EventDate = EventDate;
        this.ReminderTime = ReminderTime;
        this.event = event;
        this.context = context;
    }
    public void setAlarm() {
        NotificationCompat.Builder builder = buildNotification("Event Reminder",(event.getName()+" set for "+EventDate.getTime()),context);

        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        // TODO: get system to replace notification ID so that they dont overlap
        int id = new Random().nextInt(61) + 100;
        // SETTING EVENT NOTIFY ID
        event.setNotificationID(id);
        //
        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(context, ViewEventDebug.class);
        resultIntent.putExtra("notID",id);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        Notification notification = builder.build();
        //
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.NOT_ID,id);
        intent.putExtra(AlarmReceiver.NOT,notification);
        alarmIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        if(ReminderTime != null) {
            for (int i = 0; i < ReminderTime.length; ++i) {
                if (ReminderTime[i]) {
                    switch (i) {
                        case 0: // 15min
                            EventDate.setTimeInMillis(EventDate.getTimeInMillis()-15*60*1000);
                            alarmMgr.set(AlarmManager.RTC_WAKEUP,EventDate.getTimeInMillis(), alarmIntent);
                            //Toast.makeText(context, ("Reminder time for 15 mins ="+EventDate.getTime()), Toast.LENGTH_SHORT).show();

                            break;
                        case 1: // 1 hour
                            alarmMgr.set(AlarmManager.RTC_WAKEUP, (EventDate.getTimeInMillis() - 60 * 60 * 1000), alarmIntent);
                            break;

                        case 2: // 1 day
                            alarmMgr.set(AlarmManager.RTC_WAKEUP, (EventDate.getTimeInMillis() - 24 * 60 * 60 * 1000), alarmIntent);
                            break;
                    }
                }
            }
        }
        else {
            alarmMgr.set(AlarmManager.RTC_WAKEUP,EventDate.getTimeInMillis(),alarmIntent);
        }

    }

    /** THis function sets a repeating alarm for class times and assignments
     *
     * @param interval time in milliseconds between events that you would like to notify for.
     */
    public void setAlarmRepeat(int interval) {
        Notification notification = buildNotification("Event Reminder",(event.getName()+" set for interval of "+interval),context).build();
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        // TODO: get system to replace notification ID so that they dont overlap
        int id = new Random().nextInt(61) + 100;
        // SETTING EVENT NOTIFY ID
        event.setNotificationID(id);
        //
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.NOT_ID,id);
        intent.putExtra(AlarmReceiver.NOT,notification);
        alarmIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        Calendar calendar = Calendar.getInstance();
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, EventDate.getTimeInMillis(),
                interval, alarmIntent);
    }
    public NotificationCompat.Builder buildNotification(String title, String body, Context context) {
        String textTitle = title;
        String textContent = body;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.ic_priority_high_black_24dp)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        return builder;
    }

}
