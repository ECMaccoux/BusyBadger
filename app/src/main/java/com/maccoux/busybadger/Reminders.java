package com.maccoux.busybadger;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Random;
import com.maccoux.busybadger.Room.Event;

public class Reminders {
    Calendar EventDate;
    boolean[] ReminderTime;
    Event event;
    Context context;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    public Reminders(Calendar EventDate,boolean[] ReminderTime,Event event,Context context) {
        this.EventDate = EventDate;
        this.ReminderTime = ReminderTime;
        this.event = event;
        this.context = context;
    }
    public void setAlarm() {
        Notification notification = buildNotification("This is timed",("Alarm set for "+EventDate.getTime()),context).build();
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        // TODO: get system to replace notification ID so that they dont overlap
        int id = new Random().nextInt(61) + 100;
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
                            Toast.makeText(context, ("Reminder time for 15 mins ="+EventDate.getTime()), Toast.LENGTH_SHORT).show();

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
        // Set the alarm to start at 3:00 p.m.
        //calendar.set(Calendar.HOUR_OF_DAY, 17);
        //calendar.set(Calendar.MINUTE, 13);
        //calendar.set(Calendar.MONTH,4);

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.

    }
    public void setAlarmRepeat() {
        Calendar calendar = Calendar.getInstance();
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, alarmIntent);
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
