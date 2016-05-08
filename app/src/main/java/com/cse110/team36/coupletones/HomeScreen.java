package com.cse110.team36.coupletones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import com.cse110.team36.coupletones.GCM.SOConfig;


/* NOTE: This is actually the location page (middle button) */

public class HomeScreen extends AppCompatActivity {
    /*
     * Preparing the list data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        initializeListViewAdapter();
        initializeButtons();
    }

    void initializeButtons() {
        final ImageButton mapButton = (ImageButton) findViewById(R.id.mapButton);
        final ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);

        mapButton.setBackgroundColor(0xFFFFFF);
        (findViewById(R.id.myLocButton)).setBackgroundResource(R.color.colorButtonDepressed);
        settingsButton.setBackgroundColor(0xFFFFFF);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(HomeScreen.this, MapsActivity.class));
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(HomeScreen.this, SOConfig.class));
            }
        });
    }

    void initializeListViewAdapter() {
        MyCustomAdapter myCustomAdapter = new MyCustomAdapter(FaveLocationManager.locList, this);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(myCustomAdapter);
    }
}
