package com.maccoux.busybadger;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Class;
import com.maccoux.busybadger.Room.Event;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//public class AddEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
public class AddEvent extends AppCompatActivity {

    final Calendar c = Calendar.getInstance();
    int eID;
    int eventType;
    String name;
    String description;
    LatLng location;
    int classID;

    boolean datePicked;
    boolean locationPicked;
    boolean[]  checkOptions;

    public static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();

        if(bundle.getInt("type") != -1) {
            eventType = bundle.getInt("type");
        }

        if (eventType == 0) {
            setContentView(R.layout.activity_add_event);
        } else {
            setContentView(R.layout.activity_add_assignment);
            eventType = 1;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar

        // Gets reference to Room database
        db = AppDatabase.getAppDatabase(this);
        datePicked = false;
        locationPicked = false;

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
    }

    public void addCompleteEvent(View view) {
        //Repeat options
        CheckBox checkDaily = findViewById(R.id.checkBoxDaily);
        CheckBox checkWeekly = findViewById(R.id.checkBoxWeekly);
        CheckBox checkMonthly = findViewById(R.id.checkBoxMonthly);
        
        boolean [] checkRepeats = {checkDaily.isChecked(), checkWeekly.isChecked(), checkMonthly.isChecked()};

        if (checkRepeats[0] == true) { //daily

            for (int i = 0; i <= 365; i++) {
                Add(c.getTime());
                c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
            }

        } else if (checkRepeats[1] == true) { //weekly

            for (int i = 0; i <= 52; i++) {
                Add(c.getTime());
                c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 7);
            }

        } else if (checkRepeats[2] == true) { //monthly

            for (int i = 0; i <= 12; i++) {
                Add(c.getTime());
                c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
            }

        } else { //no repeats

            Add(c.getTime());

            /*
            Event event = new Event();

            EditText nameText = (EditText) findViewById(R.id.eventNameText);
            name = nameText.getText().toString();
            if (name.equals("")) {
                Toast.makeText(getApplicationContext(), "Please enter a name for this event", Toast.LENGTH_SHORT).show();
                return;
            }
            event.setName(name);

            EditText descriptionText = (EditText) findViewById(R.id.eventDescriptionText);
            description = descriptionText.getText().toString();
            event.setDescription(description);


            if (!datePicked) {
                Toast.makeText(getApplicationContext(), "Please select a date/time for this event", Toast.LENGTH_SHORT).show();
                return;
            }
            event.setDate(c.getTime());
            event.setEventType(eventType);

            if (eventType == 0) {
                //event
                if (locationPicked) {
                    event.setLocation(location);
                }
            }
            //assignment
            if (eventType == 1) {
                event.setClassID(classID);
            }

            //event.setEventType(1);

            // Notification Functionality
            CheckBox check15 = findViewById(R.id.checkBox15min2);
            CheckBox check1hour = findViewById(R.id.checkBox1hour2);
            CheckBox check1day = findViewById(R.id.checkBox1day2);
            CheckBox check1week = findViewById(R.id.checkBox1week2);
            checkOptions = new boolean[]{check15.isChecked(), check1hour.isChecked(), check1day.isChecked(), check1week.isChecked()};
            Reminders reminder = new Reminders(c, checkOptions, event, this);
            reminder.setAlarm();

            new InsertEventAsyncTask(this, event).execute();

            Toast.makeText(getApplicationContext(), "Event added!", Toast.LENGTH_SHORT).show();

            finish();*/
        }


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
            db.eventDao().insert(event);
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

                    TextView view = (TextView)findViewById(R.id.setLocationText);
                    Class cl = db.classDao().loadById(classID);

                    if(cl != null) {
                        view.setText(cl.getName());
                    } else {
                        view.setText("Class has been deleted");
                    }
                }
            } catch (NullPointerException e) {

            }
        }
    }

    public void Add(Date specDate) {
        //no repeats

            Event event = new Event();

            EditText nameText = (EditText) findViewById(R.id.eventNameText);
            name = nameText.getText().toString();
            if (name.equals("")) {
                Toast.makeText(getApplicationContext(), "Please enter a name for this event", Toast.LENGTH_SHORT).show();
                return;
            }
            event.setName(name);

            EditText descriptionText = (EditText) findViewById(R.id.eventDescriptionText);
            description = descriptionText.getText().toString();
            event.setDescription(description);


            if (!datePicked) {
                Toast.makeText(getApplicationContext(), "Please select a date/time for this event", Toast.LENGTH_SHORT).show();
                return;
            }
            //event.setDate(c.getTime());
            event.setDate(specDate);

            event.setEventType(eventType);

            if (eventType == 0) {
                //event
                if (locationPicked) {
                    event.setLocation(location);
                }
            }
            //assignment
            if (eventType == 1) {
                event.setClassID(classID);
                event.setProgress(0);
            }

            //event.setEventType(1);

            // Notification Functionality
            CheckBox check15 = findViewById(R.id.checkBox15min2);
            CheckBox check1hour = findViewById(R.id.checkBox1hour2);
            CheckBox check1day = findViewById(R.id.checkBox1day2);
            CheckBox check1week = findViewById(R.id.checkBox1week2);
            checkOptions = new boolean[]{check15.isChecked(), check1hour.isChecked(), check1day.isChecked(), check1week.isChecked()};
            Reminders reminder = new Reminders(c, checkOptions, event, this);
            reminder.setAlarm();

            new InsertEventAsyncTask(this, event).execute();

            Toast.makeText(getApplicationContext(), "Event added!", Toast.LENGTH_SHORT).show();

            finish();
    }

}

