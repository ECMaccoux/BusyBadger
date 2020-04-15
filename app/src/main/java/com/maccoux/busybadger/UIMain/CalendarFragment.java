package com.maccoux.busybadger.UIMain;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.maccoux.busybadger.R;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class CalendarFragment extends Fragment {

    View view;
    MaterialCalendarView calendarView;

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = (MaterialCalendarView)view.findViewById(R.id.calendarView);
        calendarView.getLeftArrow().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        calendarView.getRightArrow().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);

        return view;
    }

}
