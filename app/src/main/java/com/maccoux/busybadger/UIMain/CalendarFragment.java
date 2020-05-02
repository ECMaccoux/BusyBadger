package com.maccoux.busybadger.UIMain;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.maccoux.busybadger.R;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Event;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;

import java.util.Date;
import java.util.ArrayList;

public class CalendarFragment extends Fragment {

    View view;
    MaterialCalendarView calendarView;

    public static AppDatabase db;
    ArrayList<Event> events;

    CalendarDataListener mDataListener;

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

        calendarView.setOnDateLongClickListener(new OnDateLongClickListener() {
            @Override
            public void onDateLongClick(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {
                Date selectedDate = new Date(date.getYear(), date.getMonth(), date.getDay());
                mDataListener.onDataReceived(selectedDate);

                ViewPager viewPager = getActivity().findViewById(R.id.view_pager);
                viewPager.setCurrentItem(1);
            }
        });

        EventDecorator eventDecorator = new EventDecorator(Color.WHITE, db.eventDao().getAllDates());
        calendarView.addDecorator(eventDecorator);

        return view;
    }

    public interface CalendarDataListener {
        public void onDataReceived(Date selectedDate);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mDataListener = (CalendarDataListener)activity;
        } catch (ClassCastException e) {

        }
    }
}
