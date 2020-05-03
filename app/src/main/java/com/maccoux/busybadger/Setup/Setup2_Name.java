package com.maccoux.busybadger.Setup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.maccoux.busybadger.R;

public class Setup2_Name extends AppCompatActivity {

    EditText editFirstName;
    EditText editLastName;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        editFirstName = (EditText)findViewById(R.id.editFirstName);
        editLastName = (EditText)findViewById(R.id.editLastName);

        prefs = this.getSharedPreferences("com.maccoux.busybadger", Context.MODE_PRIVATE);
        if(!prefs.getString("firstName", "FIRST").equals("FIRST")) {
            editFirstName.setText(prefs.getString("firstName", "FIRST"));
            editLastName.setText(prefs.getString("lastName", "LAST"));
        }

        Button buttonSetName = (Button)findViewById(R.id.buttonSetName);
        buttonSetName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editFirstName.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter a first name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(editLastName.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter a last name", Toast.LENGTH_SHORT).show();
                    return;
                }

                prefs.edit().putString("firstName", editFirstName.getText().toString()).commit();
                prefs.edit().putString("lastName", editLastName.getText().toString()).commit();

                Intent intent = new Intent(getApplicationContext(), Setup3_Class.class);
                //Intent intent = new Intent(getApplicationContext(), Setup4_Finish.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

}
