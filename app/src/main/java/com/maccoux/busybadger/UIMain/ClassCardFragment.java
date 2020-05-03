package com.maccoux.busybadger.UIMain;

import android.content.Intent;
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
import com.maccoux.busybadger.Room.Event;

import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class ClassCardFragment extends Fragment {

    View view;
    int cID;
    static AppDatabase db;

    public static ClassCardFragment newInstance() {
        ClassCardFragment fragment = new ClassCardFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_event_card, container, false);
        cID = getArguments().getInt("cid");
        db = AppDatabase.getAppDatabase(getContext());
        Class currClass = db.classDao().loadById(cID);

        if(currClass == null) {
            return null;
        }
        TextView title = (TextView)view.findViewById(R.id.textTitle);
        title.setText(currClass.getName());

        /*TextView date = (TextView)view.findViewById(R.id.textDate);
        Calendar c = Calendar.getInstance();
        c.setTime(event.getDate());
        String newText = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + c.get(Calendar.DAY_OF_MONTH)
                + ", " + c.get(Calendar.YEAR) + ", " + c.get(Calendar.HOUR)
                + ":" + String.format("%02d", c.get(Calendar.MINUTE)) + " " + c.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
        date.setText(newText);

        TextView description = (TextView)view.findViewById(R.id.textDescription);
        description.setText(event.getDescription());
        */
        View cardview = view.findViewById(R.id.classCardView);
        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddEvent.class);
                intent.putExtra("cID",cID);
                getActivity().setResult(RESULT_OK, intent);
                getActivity().finish();
                startActivity(intent);

            }
        });

        return view;
    }

}
