package com.maccoux.busybadger.UIMain;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.maccoux.busybadger.R;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Event;

import java.util.Calendar;
import java.util.Locale;
public class ViewEventDebugFragment extends Fragment {
    View view;
    int eID;
    static AppDatabase db;
    public static ViewEventDebugFragment newInstance() {
        ViewEventDebugFragment fragment = new ViewEventDebugFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_view, container, false);
        eID = getArguments().getInt("eid");
        db = AppDatabase.getAppDatabase(getContext());
        Event event = db.eventDao().loadById(eID);

        TextView title = (TextView) view.findViewById(R.id.textTitle);
        title.setText(event.getName());

        TextView date = (TextView) view.findViewById(R.id.textDate);
        Calendar c = Calendar.getInstance();
        c.setTime(event.getDate());
        String newText = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + c.get(Calendar.DAY_OF_WEEK)
                + ", " + c.get(Calendar.YEAR) + ", " + c.get(Calendar.HOUR)
                + ":" + String.format("%02d", c.get(Calendar.MINUTE)) + " " + c.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
        date.setText(newText);
        return view;
    }
}
