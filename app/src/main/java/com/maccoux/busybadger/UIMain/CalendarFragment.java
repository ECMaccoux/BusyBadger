package com.maccoux.busybadger.UIMain;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.maccoux.busybadger.R;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Event;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class CalendarFragment extends Fragment {

    View view;
    MaterialCalendarView calendarView;

    public static AppDatabase db;
    ArrayList<Event> events;

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        db = AppDatabase.getAppDatabase(getContext());

        calendarView = (MaterialCalendarView)view.findViewById(R.id.calendarView);
        calendarView.getLeftArrow().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        calendarView.getRightArrow().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        EventDecorator eventDecorator = new EventDecorator(Color.WHITE, db.eventDao().getAllDates());
        calendarView.addDecorator(eventDecorator);

        return view;
    }

}
