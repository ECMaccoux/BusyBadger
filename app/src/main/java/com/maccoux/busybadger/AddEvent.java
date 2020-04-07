package com.maccoux.busybadger;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.room.ColumnInfo;
import androidx.room.Room;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Calendar c;
    int eID;
    String name;
    String description;
    Date dateTime;
    Location location;
    boolean datePicked;

    AppDatabase db;

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
                Intent myIntent = new Intent(AddEvent.this, MainActivity.class);
                myIntent.putExtra("event", ""); //Optional parameters
                AddEvent.this.startActivity(myIntent);
            }
        });

        //TODO: add code for launching the calendar picker for date/time
        Button dateButton = (Button) findViewById(R.id.dateButton);
        Button timeButton = (Button) findViewById(R.id.timeButton);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        //TODO: add code for launching the Google Maps picker for location


        //code for clicking the actual "+" button
        ImageButton button = (ImageButton) findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCompleteEvent();
            }
        });
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


        return event;
    }
}
