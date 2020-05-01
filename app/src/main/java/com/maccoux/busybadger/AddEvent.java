package com.maccoux.busybadger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Event;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

//public class AddEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
public class AddEvent extends AppCompatActivity {

    final Calendar c = Calendar.getInstance();
    int eID;
    String name;
    String description;
    Location location;
    boolean datePicked;
    boolean[]  checkOptions;

    public static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar

        // Gets reference to Room database
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database").build();
        datePicked = false;

        Event event = new Event();

        // implement setNavigationOnClickListener event
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        FloatingActionButton addEventButton = (FloatingActionButton)findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCompleteEvent(v); }
        });

// TODO: FIX CODE FOR POPUP REMINDER DIALOG
//        repeatButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ReminderFragment repeatPicker = new ReminderFragment();
//                repeatPicker.show(getSupportFragmentManager(), "repeat picker");
//
//            }
//        });

        //TODO: add code for launching the Google Maps picker for location
    }

    public void addCompleteEvent(View view) {

        Event event = new Event();

        EditText nameText = (EditText) findViewById(R.id.eventNameText);
        name = nameText.getText().toString();
        if(name.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter a name for this event", Toast.LENGTH_SHORT).show();
            return;
        }
        event.setName(name);

        EditText descriptionText = (EditText) findViewById(R.id.eventDescriptionText);
        description = descriptionText.getText().toString();
        event.setDescription(description);

        if(!datePicked) {
            Toast.makeText(getApplicationContext(), "Please select a date/time for this event", Toast.LENGTH_SHORT).show();
            return;
        }
        event.setCalendar(c);

        // Notification Functionality
        CheckBox check15 = findViewById(R.id.checkBox15min2);
        CheckBox check1hour = findViewById(R.id.checkBox1hour2);
        CheckBox check1day = findViewById(R.id.checkBox1day2);
        CheckBox check1week = findViewById(R.id.checkBox1week2);
        checkOptions = new boolean[]{check15.isChecked(), check1hour.isChecked(), check1day.isChecked(), check1week.isChecked()};
        Reminders reminder = new Reminders(c,checkOptions,event,this);
        reminder.setAlarm();

        new InsertEventAsyncTask(this, event);

        Toast.makeText(getApplicationContext(), "Event added!", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void onDateButtonClicked(View view) {
        datePicker();
    }

    private void setDateTimeText() {
        TextView dateTimeText = (TextView)findViewById(R.id.setDateText);
        String newText = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + c.get(Calendar.DAY_OF_WEEK)
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
            db.eventDao().insert(event);
            return 0;
        }
    }

}

