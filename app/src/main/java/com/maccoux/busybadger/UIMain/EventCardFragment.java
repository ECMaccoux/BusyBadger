package com.maccoux.busybadger.UIMain;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.maccoux.busybadger.R;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Class;
import com.maccoux.busybadger.Room.Event;

import java.util.Calendar;
import java.util.Locale;

public class EventCardFragment extends Fragment {

    View view;
    int eID;
    static AppDatabase db;

    public static EventCardFragment newInstance() {
        EventCardFragment fragment = new EventCardFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_event_card, container, false);
        eID = getArguments().getInt("eid");
        db = AppDatabase.getAppDatabase(getContext());
        Event event = db.eventDao().loadById(eID);

        if(event == null) {
            return null;
        }
        TextView title = (TextView)view.findViewById(R.id.textTitle);
        title.setText(event.getName());

        TextView date = (TextView)view.findViewById(R.id.textDate);
        Calendar c = Calendar.getInstance();
        c.setTime(event.getDate());
        int hour = c.get(Calendar.HOUR);
        if(hour == 0) {
            hour = 12;
        }
        String newText = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + c.get(Calendar.DAY_OF_MONTH)
                + ", " + c.get(Calendar.YEAR) + ", " + hour
                + ":" + String.format("%02d", c.get(Calendar.MINUTE)) + " " + c.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
        date.setText(newText);

        TextView description = (TextView)view.findViewById(R.id.textDescription);
        description.setText(event.getDescription());

        View cardview = view.findViewById(R.id.eventCardView);
        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewEventDebug.class);
                intent.putExtra("eID", eID);
                startActivity(intent);
            }
        });

        if(event.getEventType() == 1) {
            cardview.setBackgroundColor(Color.parseColor("#4287f5"));

            TextView textClass = (TextView)view.findViewById(R.id.textClass);
            if(event.getClassID() > 0) {
                Class cl = db.classDao().loadById(event.getClassID());
                if(cl != null) {
                    textClass.setText(cl.getName());
                    textClass.setAlpha(1.0f);
                }
            }
        }

        return view;
    }

}
