package com.maccoux.busybadger.UIMain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.maccoux.busybadger.R;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Class;

public class ClassCardSetupFragment extends Fragment {

    View view;
    int cID;
    static AppDatabase db;

    public static ClassCardSetupFragment newInstance() {
        ClassCardSetupFragment fragment = new ClassCardSetupFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_event_card, container, false);
        cID = getArguments().getInt("cid");
        db = AppDatabase.getAppDatabase(getContext());
        final Class currClass = db.classDao().loadById(cID);

        Button deleteButton = (Button)view.findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.classDao().delete(currClass);
                getActivity().recreate();
            }
        });

        return view;
    }

}
