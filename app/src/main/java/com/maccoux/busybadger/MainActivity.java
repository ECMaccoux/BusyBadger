package com.maccoux.busybadger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reqPermissions();
        createNotificationChannel();


    }

    /** This function asks for the permissions required
     *
     */
    public void reqPermissions() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.SET_ALARM,Manifest.permission.ACCESS_FINE_LOCATION};
        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

    }

    /** This function checks if the application has the needed permissions
     *
     * @param context
     * @param permissions string array of Manifest.permission.XXXXX
     * @return true/false if a needed permission is missing
     */
    public boolean hasPermissions(Context context, String[] permissions) {

        if (android.os.Build.VERSION.SDK_INT >= 23 && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Taken from https://developer.android.com/training/notify-user/build-notification
     *
     */
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "BusyBadger";
            String description = "Notifications for Assignments or other School Events";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /** This is the Onclick function to test Notifications
     *
     * @param v
     */
  public void onTestNotify(View v) {
      Event test = new Event();
      Reminders reminder = new Reminders(Calendar.getInstance(),60,test,this);
      reminder.setAlarm();
  }
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /** This is the Onclick function to go to the views' screens
     *
     * @param v
     */
    public void onViewButton(View v) {
        Intent intent = new Intent(this, HomeView.class);
        startActivity(intent);
    }

    public void onAddEventButton(View v) {
        Intent intent = new Intent(this, AddEvent.class);
        startActivity(intent);
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener  {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            TimePickerDialog dialog = new TimePickerDialog(getActivity(),R.style.TimePicker,this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
            // Create a new instance of TimePickerDialog and return it
            return dialog;
        }
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar eventTime = Calendar.getInstance(TimeZone.getDefault());
            eventTime.setTimeInMillis(System.currentTimeMillis());
            eventTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
            eventTime.set(Calendar.MINUTE,minute);
            eventTime.set(Calendar.SECOND,0);
            Event test = new Event();
            Toast.makeText(getActivity(), ("Time SET!"+eventTime.getTime()), Toast.LENGTH_SHORT).show();
            Reminders reminder = new Reminders(eventTime,60,test,getActivity());
            reminder.setAlarm();
        }


    }

}

