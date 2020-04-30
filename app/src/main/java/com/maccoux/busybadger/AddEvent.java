package com.maccoux.busybadger;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Event;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Calendar c;
    int eID;
    String name;
    String description;
    Date dateTime;
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
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database").build();

        // implement setNavigationOnClickListener event
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AddEvent.this, TestScreen.class);
                myIntent.putExtra("event", ""); //Optional parameters
                AddEvent.this.startActivity(myIntent);
            }
        });

        //TODO: add code for launching the calendar picker for date/time
        Button dateButton = (Button) findViewById(R.id.dateButton);
        Button timeButton = (Button) findViewById(R.id.timeButton);
        //Button repeatButton = (Button) findViewById(R.id.repeatButton);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment TimePicker = new AddEvent.TimePickerFragment();
                TimePicker.show(getSupportFragmentManager(), "time picker");
            }
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


        //code for clicking the actual "+" button
        ImageButton button = (ImageButton) findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addCompleteEvent();
                addButtonClick();
            }
        });
    }
    public void timeSet(Context context, Calendar eventTime, Event event) {
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

    private Event addCompleteEvent() {

        Event event = new Event();

        EditText nameText = (EditText) findViewById(R.id.eventNameText);
        name = nameText.getText().toString();
        event.setName(name);

        EditText descriptionText = (EditText) findViewById(R.id.eventDescriptionText);
        description = descriptionText.getText().toString();
        event.setDescription(description);

        //TODO: add code for setting the date/time and location
        if (datePicked) {
            dateTime = c.getTime();
        }
        // Notification Functionality
        CheckBox check15 = findViewById(R.id.checkBox15min2);
        CheckBox check1hour = findViewById(R.id.checkBox1hour2);
        CheckBox check1day = findViewById(R.id.checkBox1day2);
        CheckBox check1week = findViewById(R.id.checkBox1week2);
        checkOptions = new boolean[]{check15.isChecked(), check1hour.isChecked(), check1day.isChecked(), check1week.isChecked()};
        Reminders reminder = new Reminders(c,checkOptions,event,this);
        reminder.setAlarm();


        return event;
    }

    public void addButtonClick() {
        // TEST CODE
        new EventAsyncTask(this, "Test1", "Test event 1", new Date(0)).execute();
    }

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


    }

    private static class EventAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private String name;
        private String description;
        private Date dateTime;

        public EventAsyncTask(Activity activity, String name, String description, Date dateTime) {
            weakActivity = new WeakReference<>(activity);
            this.name = name;
            this.description = description;
            this.dateTime = dateTime;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            db.eventDao().insert(new Event(name, description, dateTime));
            return 0;
        }
    }

}

