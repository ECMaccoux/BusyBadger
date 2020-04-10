package com.maccoux.busybadger;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Settings.this, HomeView.class);
                myIntent.putExtra("settings", ""); //Optional parameters
                Settings.this.startActivity(myIntent);
            }
        });
    }

    public void onEditEventButton(View v) {
        Toast.makeText(this, "Edit events button clicked!", Toast.LENGTH_SHORT).show();
    }
    public void onEditListButton(View v) {
        Toast.makeText(this, "Edit class list button clicked!", Toast.LENGTH_SHORT).show();
    }
    public void onExportButton(View v) {
        Toast.makeText(this, "Google Calendar export button clicked!", Toast.LENGTH_SHORT).show();
    }
    public void onResetButton(View v) {
        Toast.makeText(this, "Reset button clicked!", Toast.LENGTH_SHORT).show();
    }
}
