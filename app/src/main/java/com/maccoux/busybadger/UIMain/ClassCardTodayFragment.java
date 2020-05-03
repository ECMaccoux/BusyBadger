package com.maccoux.busybadger.UIMain;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.maccoux.busybadger.AddEvent;
import com.maccoux.busybadger.R;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Class;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class ClassCardTodayFragment extends Fragment {

    View view;
    int cID;
    Date curDate;
    static AppDatabase db;

    public static ClassCardTodayFragment newInstance() {
        ClassCardTodayFragment fragment = new ClassCardTodayFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_today_class_card, container, false);
        cID = getArguments().getInt("cid");
        curDate = new Date((long)getArguments().getLong("curDate")); //getArguments().get("curDate");
        db = AppDatabase.getAppDatabase(getContext());
        Class currClass = db.classDao().loadById(cID);

        if(currClass == null) {
            return null;
        }

        TextView title = (TextView)view.findViewById(R.id.textTitle);
        title.setText(currClass.getName());

        Date classDate = new Date(curDate.getYear(), curDate.getMonth(), curDate.getDate());
        if(classDate.getDay() == 1 && currClass.getMonday() != null) {
            classDate.setHours(currClass.getMonday().getHours());
            classDate.setMinutes(currClass.getMonday().getMinutes());
        }
        else if(classDate.getDay() == 2 && currClass.getTuesday() != null) {
            classDate.setHours(currClass.getTuesday().getHours());
            classDate.setMinutes(currClass.getTuesday().getMinutes());
        }
        else if(classDate.getDay() == 3 && currClass.getWednesday() != null) {
            classDate.setHours(currClass.getWednesday().getHours());
            classDate.setMinutes(currClass.getWednesday().getMinutes());
        }
        else if(classDate.getDay() == 4 && currClass.getThursday() != null) {
            classDate.setHours(currClass.getThursday().getHours());
            classDate.setMinutes(currClass.getThursday().getMinutes());
        }
        else if(classDate.getDay() == 5 && currClass.getFriday() != null) {
            classDate.setHours(currClass.getFriday().getHours());
            classDate.setMinutes(currClass.getFriday().getMinutes());
        }

        TextView date = (TextView)view.findViewById(R.id.textDate);
        Calendar c = Calendar.getInstance();
        c.setTime(classDate);
        String newText = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + c.get(Calendar.DAY_OF_MONTH)
                + ", " + c.get(Calendar.YEAR) + ", " + c.get(Calendar.HOUR)
                + ":" + String.format("%02d", c.get(Calendar.MINUTE)) + " " + c.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
        date.setText(newText);

        View cardview = view.findViewById(R.id.classCardView);
        cardview.setBackgroundColor(Color.parseColor(currClass.getColor()));
        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

}
