package com.maccoux.busybadger;

import android.app.Activity;
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

        /*//TODO: add code for launching the calendar picker for date/time
        Button dateButton = (Button) findViewById(R.id.dateButton);
        Button timeButton = (Button) findViewById(R.id.timeButton);
        //Button repeatButton = (Button) findViewById(R.id.repeatButton);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setStyle(DialogFragment.STYLE_NORMAL, R.style.DatePicker);
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment TimePicker = new AddEvent.TimePickerFragment();
        -        TimePicker.show(getSupportFragmentManager(), "time picker");
            }
        });*/
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


        //code for clicking the actual "+" button
        /* button = (ImageButton) findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCompleteEvent();
            }
        });*/
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

        //TODO: add code for setting the date/time and location
        /*if (datePicked) {
            dateTime = c.getTime();
        }*/
        // Notification Functionality
        CheckBox check15 = findViewById(R.id.checkBox15min2);
        CheckBox check1hour = findViewById(R.id.checkBox1hour2);
        CheckBox check1day = findViewById(R.id.checkBox1day2);
        CheckBox check1week = findViewById(R.id.checkBox1week2);
        checkOptions = new boolean[]{check15.isChecked(), check1hour.isChecked(), check1day.isChecked(), check1week.isChecked()};
        Reminders reminder = new Reminders(c,checkOptions,event,this);
        reminder.setAlarm();
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

                setDateTimeText();
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }

    /*public void timeSet(Context context, Calendar eventTime, Event event) {
        c.set(Calendar.HOUR_OF_DAY,eventTime.get(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE,eventTime.get(Calendar.MINUTE));
        c.set(Calendar.SECOND,0);
        Toast.makeText(context, ("Time SET!"+c.getTime()), Toast.LENGTH_SHORT).show();
        Reminders reminder = new Reminders(c,null,event,context);
        reminder.setAlarm();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        //this pulls the actual Date data
        String dateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
    }

    // Time Picker dialog
    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener  {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            TimePickerDialog dialog = new TimePickerDialog(getActivity(),R.style.TimePicker,this, hour, minute,
                    android.text.format.DateFormat.is24HourFormat(getActivity()));
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
//            Toast.makeText(getActivity(), ("Time SET!"+eventTime.getTime()), Toast.LENGTH_SHORT).show();
//            Reminders reminder = new Reminders(eventTime,null,test,getActivity());
//            reminder.setAlarm();
            ((AddEvent)getActivity()).timeSet(getActivity(),eventTime,test);
        }


    }*/

    // This AsyncTask class inserts new events into the Room database in a background thread
    private static class InsertEventAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private String name;
        private String description;
        private Calendar c;

        public InsertEventAsyncTask(Activity activity, String name, String description, Calendar c) {
            weakActivity = new WeakReference<>(activity);
            this.name = name;
            this.description = description;
            this.c = c;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            db.eventDao().insert(new Event(name, description, c));
            return 0;
        }
    }

}

