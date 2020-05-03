package com.maccoux.busybadger.UIMain;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.maccoux.busybadger.AlarmReceiver;
import com.maccoux.busybadger.EditEvent;
import com.maccoux.busybadger.R;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Event;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.List;
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
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.classCardView);
        View cardView = (View) findViewById(R.id.classCardView);

        db = AppDatabase.getAppDatabase(this);
       Intent intent = getIntent();
        eID = intent.getIntExtra("eID",0);
        List<Address> addresses;
        String city = "";
        String state = "";
        String country = "";
        if(eID == 0) {
            event = db.eventDao().loadByNotID(intent.getIntExtra("notID",0));
        }
        else {
            event = db.eventDao().loadById(eID);
            if(event == null) {
                onCancel(view);
            }
        }
        SeekBar progressBar = (SeekBar) findViewById(R.id.seekBar);
        if(event.getEventType() == 1) {
            cardView.setBackgroundColor(Color.parseColor("#4287f5"));
        }
        if(event.getEventType() != 1) {
            viewGroup.removeView(findViewById(R.id.seekBar));
            viewGroup.removeView(findViewById(R.id.progress));
            viewGroup.removeView(findViewById(R.id.textDescription2));
            progressBar.setProgress(event.getProgress());
        }
        TextView title = (TextView) findViewById(R.id.textTitle);
        title.setText(event.getName());

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
           addresses = geocoder.getFromLocation(event.getLatitude(),event.getLongitude(), 1);
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
        }
        catch(Exception e) {

        }
        if(event.getEventType() != 1) {
            TextView loc = (TextView) findViewById(R.id.textDescription2);
            //
            loc.setText(city+", "+state+", "+country);
        }

        //
        TextView desc = (TextView) findViewById(R.id.textDescription);
        desc.setText(event.getDescription());
        TextView date = (TextView) findViewById(R.id.textDate);
        Calendar c = Calendar.getInstance();
        c.setTime(event.getDate());
        String newText = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + c.get(Calendar.DAY_OF_MONTH)
                + ", " + c.get(Calendar.YEAR) + ", " + c.get(Calendar.HOUR)
                + ":" + String.format("%02d", c.get(Calendar.MINUTE)) + " " + c.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
        date.setText(newText);

    }

    /** When user cancels notifications, this removes the notification from AlarmManager
     *
     * @param v
     */
    public void onCancel(View v) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int id = event.getNotificationID();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), id, intent, 0);
        alarmManager.cancel(alarmIntent);
        notificationManager.cancel(id);
        Toast.makeText(this,"Notification Silenced!", Toast.LENGTH_SHORT).show();
    }

    /** Exits View Event Acitivty
     *
     * @param v
     */
    public void onExit(View v) {
        SeekBar progressBar = (SeekBar) findViewById(R.id.seekBar);
        if(event.getEventType() == 1 && progressBar.getProgress() != event.getProgress()) {
            event.setProgress(progressBar.getProgress());
            new UpdateEventAsyncTask(this, event).execute();
        }

        finish();
    }
    public void onEdit(View v)  {
        Intent intent = new Intent(v.getContext(), EditEvent.class);
        intent.putExtra("eID", eID);
        startActivity(intent);
    }
    /** This function removes event from database and returns back to homebiew
     *
     * @param v View passed in by OnClick
     */
    public void onRemoveEvent(View v) {
        onCancel(v);

        db.eventDao().delete(event);
        finish();
    }

    private static class UpdateEventAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private Event event;

        public UpdateEventAsyncTask(Activity activity, Event event) {
            weakActivity = new WeakReference<>(activity);
            this.event = event;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            //android.os.Debug.waitForDebugger();
            db.eventDao().update(event);
            return 0;
        }
    }

}
