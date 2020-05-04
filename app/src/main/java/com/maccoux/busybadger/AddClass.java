package com.maccoux.busybadger;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Class;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;

public class AddClass extends AppCompatActivity {

    static AppDatabase db;

    Date mon;
    boolean monSet = false;
    Date tues;
    boolean tuesSet = false;
    Date wed;
    boolean wedSet = false;
    Date thurs;
    boolean thursSet = false;
    Date fri;
    boolean friSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        db = AppDatabase.getAppDatabase(this);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.addClassButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCompleteClass(v);
            }
        });

        Button buttonMon = (Button)findViewById(R.id.buttonMonStart);
        buttonMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(monSet) {
                    TimePickerDialog dialog = new TimePickerDialog(AddClass.this, R.style.TimePicker, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            mon.setHours(hour);
                            mon.setMinutes(minute);

                            monSet = true;
                        }
                    }, mon.getHours(), mon.getMinutes(), false);
                    dialog.show();
                } else {
                    Date date = new Date();
                    TimePickerDialog dialog = new TimePickerDialog(AddClass.this, R.style.TimePicker, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            mon = new Date();
                            mon.setTime(0);
                            mon.setHours(hour);
                            mon.setMinutes(minute);
                            mon.setSeconds(0);

                            monSet = true;
                        }
                    }, date.getHours(), date.getMinutes(), false);
                    dialog.show();
                }
            }
        });

        Button buttonTues = (Button)findViewById(R.id.buttonTuesStart);
        buttonTues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tuesSet) {
                    TimePickerDialog dialog = new TimePickerDialog(AddClass.this, R.style.TimePicker, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            tues.setHours(hour);
                            tues.setMinutes(minute);

                            tuesSet = true;
                        }
                    }, tues.getHours(), tues.getMinutes(), false);
                    dialog.show();
                } else {
                    Date date = new Date();
                    TimePickerDialog dialog = new TimePickerDialog(AddClass.this, R.style.TimePicker, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            tues = new Date();
                            tues.setTime(0);
                            tues.setHours(hour);
                            tues.setMinutes(minute);
                            tues.setSeconds(0);

                            tuesSet = true;
                        }
                    }, date.getHours(), date.getMinutes(), false);
                    dialog.show();
                }
            }
        });

        Button buttonWed = (Button)findViewById(R.id.buttonWedStart);
        buttonWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wedSet) {
                    TimePickerDialog dialog = new TimePickerDialog(AddClass.this, R.style.TimePicker, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            wed.setHours(hour);
                            wed.setMinutes(minute);

                            wedSet = true;
                        }
                    }, wed.getHours(), wed.getMinutes(), false);
                    dialog.show();
                } else {
                    Date date = new Date();
                    TimePickerDialog dialog = new TimePickerDialog(AddClass.this, R.style.TimePicker, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            wed = new Date();
                            wed.setTime(0);
                            wed.setHours(hour);
                            wed.setMinutes(minute);
                            wed.setSeconds(0);

                            wedSet = true;
                        }
                    }, date.getHours(), date.getMinutes(), false);
                    dialog.show();
                }
            }
        });

        Button buttonThurs = (Button)findViewById(R.id.buttonThursStart);
        buttonThurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thursSet) {
                    TimePickerDialog dialog = new TimePickerDialog(AddClass.this, R.style.TimePicker, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            thurs.setHours(hour);
                            thurs.setMinutes(minute);

                            thursSet = true;
                        }
                    }, thurs.getHours(), thurs.getMinutes(), false);
                    dialog.show();
                } else {
                    Date date = new Date();
                    TimePickerDialog dialog = new TimePickerDialog(AddClass.this, R.style.TimePicker, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            thurs = new Date();
                            thurs.setTime(0);
                            thurs.setHours(hour);
                            thurs.setMinutes(minute);
                            thurs.setSeconds(0);

                            thursSet = true;
                        }
                    }, date.getHours(), date.getMinutes(), false);
                    dialog.show();
                }
            }
        });

        Button buttonFri = (Button)findViewById(R.id.buttonFriStart);
        buttonFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(friSet) {
                    TimePickerDialog dialog = new TimePickerDialog(AddClass.this, R.style.TimePicker, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            fri.setHours(hour);
                            fri.setMinutes(minute);

                            friSet = true;
                        }
                    }, fri.getHours(), fri.getMinutes(), false);
                    dialog.show();
                } else {
                    Date date = new Date();
                    TimePickerDialog dialog = new TimePickerDialog(AddClass.this, R.style.TimePicker, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            fri = new Date();
                            fri.setTime(0);
                            fri.setHours(hour);
                            fri.setMinutes(minute);
                            fri.setSeconds(0);

                            friSet = true;
                        }
                    }, date.getHours(), date.getMinutes(), false);
                    dialog.show();
                }
            }
        });
    }

    public void addCompleteClass(View v) {

        Class c = new Class();

        EditText nameText = (EditText) findViewById(R.id.classNameText);
        String name = nameText.getText().toString();
        if(name.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter a name for this class", Toast.LENGTH_SHORT).show();
            return;
        }
        Date begin = new Date();
        begin.setHours(0);
        begin.setMinutes(0);
        begin.setSeconds(0);
        c.setBegin(begin);
        c.setName(name);
        boolean[] checkOptions = new boolean[]{false,true,false,false};
        Calendar cal = Calendar.getInstance();
        cal.setTime(c.getBegin());

        if(monSet) {
            c.setMonday(mon);
            cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        }
        if(tuesSet) {
            c.setTuesday(tues);
            cal.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
        }
        if(wedSet) {
            c.setWednesday(wed);
            cal.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
        }
        if(thursSet) {
            c.setThursday(thurs);
            cal.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
        }
        if(friSet) {
            c.setFriday(fri);
            cal.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
        }


        EditText editColorCode = (EditText)findViewById(R.id.editColorCode);
        String colorCode;
        if(!editColorCode.getText().toString().equals("")) {
            colorCode = editColorCode.getText().toString();

            try {
                int color = Color.parseColor(colorCode);
            } catch (IllegalArgumentException iae) {
                Toast.makeText(getApplicationContext(), "Invalid color code entered", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else {
            colorCode = "#C5050C";
        }
        c.setColor(colorCode);

        new InsertClassAsyncTask(this, c).execute();


        RemindersClass reminder = new RemindersClass(cal, checkOptions,c, this);
        reminder.setAlarmRepeat(604800000); // one week
        //reminder.setAlarm();
        Toast.makeText(getApplicationContext(), "Class added!", Toast.LENGTH_SHORT).show();

        finish();
    }

    private static class InsertClassAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private Class c;

        public InsertClassAsyncTask(Activity activity, Class c) {
            weakActivity = new WeakReference<>(activity);
            this.c = c;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            //android.os.Debug.waitForDebugger();
            db.classDao().insert(c);
            return 0;
        }
    }

}
