package com.maccoux.busybadger.UIMain;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.maccoux.busybadger.R;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Class;
import com.maccoux.busybadger.Setup.Setup3_Class;

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
        view =  inflater.inflate(R.layout.fragment_setup_class_card, container, false);
        cID = getArguments().getInt("cid");
        db = AppDatabase.getAppDatabase(getContext());
        final Class currClass = db.classDao().loadById(cID);

        TextView classTitle = (TextView)view.findViewById(R.id.textTitle);
        classTitle.setText(currClass.getName());

        CardView cardView = (CardView)view.findViewById(R.id.classCardView);
        cardView.setCardBackgroundColor(Color.parseColor(currClass.getColor()));

        Button deleteButton = (Button)view.findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.classDao().delete(currClass);
                Toast.makeText(getContext(), "Class deleted", Toast.LENGTH_SHORT).show();
                //getActivity().recreate();
                ((Setup3_Class)getActivity()).removeCards();
                ((Setup3_Class)getActivity()).addCards();
            }
        });

        return view;
    }

}
