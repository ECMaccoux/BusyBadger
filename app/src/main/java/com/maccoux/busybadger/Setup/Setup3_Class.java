package com.maccoux.busybadger.Setup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.maccoux.busybadger.R;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Class;
import com.maccoux.busybadger.UIMain.ClassCardFragment;
import com.maccoux.busybadger.UIMain.ClassCardSetupFragment;

import java.util.List;

public class Setup3_Class extends AppCompatActivity {

    static AppDatabase db;
    List<Class> classes;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        Button continueButton = (Button)findViewById(R.id.buttonContinue);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Setup4_Finish.class);
                startActivity(intent);
            }
        });

        db = AppDatabase.getAppDatabase(this);

        addCards();
    }

    public void addCards() {
        classes = db.classDao().getAll();

        TextView noClasses = (TextView)findViewById(R.id.textNoClasses);
        if(classes.size() != 0) {
            noClasses.setAlpha(0.0f);
        }
        else {
            noClasses.setAlpha(0.5f);
        }

        manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        for (Class current : classes) {
            ClassCardSetupFragment tempFrag = (ClassCardSetupFragment)manager.findFragmentByTag(Integer.toString(current.cID));
            if(tempFrag != null) {
                continue;
            }

            ClassCardSetupFragment fragment = ClassCardSetupFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putInt("cid", current.cID);
            fragment.setArguments(bundle);
            ft.add(R.id.addClassScrollLayout, fragment, Integer.toString(current.cID));
        }

        ft.commit();
    }

}
