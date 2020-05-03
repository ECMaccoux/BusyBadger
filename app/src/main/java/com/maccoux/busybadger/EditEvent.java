package com.maccoux.busybadger;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Event;
import com.maccoux.busybadger.Room.Class;
import com.maccoux.busybadger.UIMain.ClassListFragment;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

//public class AddEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
public class EditEvent extends AppCompatActivity {

    final Calendar c = Calendar.getInstance();
    int eID;
    int eventType;
    String name;
    String description;
    LatLng location;
    int classID;
    Event event;
    EditText nameText;
    EditText descriptionText;

    boolean datePicked;
    boolean locationPicked;
    boolean[]  checkOptions;

    public static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Address> addresses;
        String city = "";
        String state = "";
        String country = "";

        Bundle bundle = getIntent().getExtras();
        db = AppDatabase.getAppDatabase(this);
        event = db.eventDao().loadById(bundle.getInt("eID",0));
        if (eventType == 0) {
            setContentView(R.layout.activity_edit_event);
        } else {
            setContentView(R.layout.activity_add_assignment);
            eventType = 1;
        }

        // Setting current values
        nameText = (EditText) findViewById(R.id.eventNameText);
        descriptionText = (EditText) findViewById(R.id.eventDescriptionText);
        nameText.setText(event.getName());
        descriptionText.setText(event.getDescription());
        // Set Default Date/Time
        TextView dateTimeText = (TextView)findViewById(R.id.setDateText);
        String newText = event.getDate().toString();
        dateTimeText.setText(newText);
        // Setting Current Location
        if(event.getLocation() != null) {
            TextView locText = (TextView) findViewById(R.id.setLocationText);
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(event.getLatitude(), event.getLongitude(), 1);
                city = addresses.get(0).getLocality();
                state = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
            } catch (Exception e) {

            }
            locText.setText(city + ", " + state + ", " + country);
        }
        if(bundle.getInt("type") != -1) {
            eventType = bundle.getInt("type");
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar

        // Gets reference to Room database
        datePicked = true;
        locationPicked = false;

        // implement setNavigationOnClickListener event
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        FloatingActionButton addEventButton = (FloatingActionButton)findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finishEditEvent(v); }
        });

    }
    public void finishEditEvent(View view) {


        name = nameText.getText().toString();
        if(name.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter a name for this event", Toast.LENGTH_SHORT).show();
            return;
        }
        description = descriptionText.getText().toString();
        event.setName(name);

        event.setDescription(description);

// Update Time
        if(c != null) {
            event.setDate(c.getTime());
            onCancel(view);
            CheckBox check15 = findViewById(R.id.checkBox15min2);
            CheckBox check1hour = findViewById(R.id.checkBox1hour2);
            CheckBox check1day = findViewById(R.id.checkBox1day2);
            CheckBox check1week = findViewById(R.id.checkBox1week2);
            checkOptions = new boolean[]{check15.isChecked(), check1hour.isChecked(), check1day.isChecked(), check1week.isChecked()};
            Reminders reminder = new Reminders(c,checkOptions,event,this);
            reminder.setAlarm();
        }

        if(eventType == 0) {
            //event
            if(locationPicked) {
                event.setLocation(location);
            }
        }
        //assignment
        if(eventType == 1) {
            event.setClassID(classID);
        }


        // Notification Functionality


        new InsertEventAsyncTask(this, event).execute();

        //Toast.makeText(getApplicationContext(), "Event added!", Toast.LENGTH_SHORT).show();

        finish();


    }

    /** Must cancel current notifications if time/date changed
     *
     * @param v
     */
    public void onCancel(View v) {
        int id = event.getNotificationID();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), id, intent, 0);
        alarmManager.cancel(alarmIntent);
        notificationManager.cancel(id);

    }
    public void onDateButtonClicked(View view) {
        datePicker();
    }

    private void setDateTimeText() {
        TextView dateTimeText = (TextView)findViewById(R.id.setDateText);
        String newText = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + c.get(Calendar.DAY_OF_MONTH)
                + ", " + c.get(Calendar.YEAR) + "\n" + c.get(Calendar.HOUR)
                + ":" + String.format("%02d", c.get(Calendar.MINUTE)) + " " + c.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());

        dateTimeText.setText(newText);
    }

    private void datePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                c.set(year, month, day);
                timePicker();
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void timePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.TimePicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, minute);

                datePicked = true;
                setDateTimeText();
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }

    public void onAddLocationButton(View view) {
        Intent intent = new Intent(this, LocationPicker.class);
        startActivityForResult(intent, 1);
    }

    public void onAddClassButton(View v) {
        Intent intent = new Intent(this, PickClass.class);
        startActivityForResult(intent, 2);
    }

    // This AsyncTask class inserts new events into the Room database in a background thread
    private static class InsertEventAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private Event event;

        public InsertEventAsyncTask(Activity activity, Event event) {
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                location = new LatLng(data.getDoubleExtra("latitude", 0), data.getDoubleExtra("longitude", 0));

                TextView view = (TextView)findViewById(R.id.setLocationText);
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());

                List<Address> addresses = new ArrayList<>();
                try {
                    addresses = geocoder.getFromLocation(location.latitude, location.longitude,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                android.location.Address address = addresses.get(0);

                if (address != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(address.getAddressLine(0));
                    for (int i = 1; i <= address.getMaxAddressLineIndex(); i++){
                        sb.append("\n" + address.getAddressLine(i));
                    }
                    view.setText(sb.toString());
                }

                locationPicked = true;

            }
        } else if (requestCode == 2) {
            try {
                if (resultCode == RESULT_OK) {
                    classID = data.getIntExtra("cid", 0);
                }
            } catch (NullPointerException e) {

            }
        }
    }

}

