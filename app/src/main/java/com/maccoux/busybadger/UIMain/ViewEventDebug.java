package com.maccoux.busybadger.UIMain;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.maccoux.busybadger.AlarmReceiver;
import com.maccoux.busybadger.R;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Event;

import java.util.Calendar;
import java.util.Locale;
public class ViewEventDebug extends AppCompatActivity {
    View view;
    int eID;
    static AppDatabase db;
    Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.fragment_event_view);

       Intent intent = getIntent();
        eID = intent.getIntExtra("eID",0);
        db = AppDatabase.getAppDatabase(this);
        event = db.eventDao().loadById(eID);

        TextView title = (TextView) findViewById(R.id.textTitle);
        title.setText(event.getName());

        TextView date = (TextView) findViewById(R.id.textDate);
        Calendar c = Calendar.getInstance();
        c.setTime(event.getDate());
        String newText = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + c.get(Calendar.DAY_OF_WEEK)
                + ", " + c.get(Calendar.YEAR) + ", " + c.get(Calendar.HOUR)
                + ":" + String.format("%02d", c.get(Calendar.MINUTE)) + " " + c.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
        date.setText(newText);

    }
    public void onCancel(View v) {
        int id = event.getNotificationID();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), id, intent, 0);
        alarmManager.cancel(alarmIntent);
        Toast.makeText(this,"Notification Silenced!", Toast.LENGTH_SHORT).show();
    }
}
