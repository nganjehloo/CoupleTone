package com.cse110.team36.coupletones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.cse110.team36.coupletones.GCM.SOConfig;


/* NOTE: This is actually the location page (middle button) */

public class HomeScreen extends AppCompatActivity {
    ExpandableListView exview;
    public List<String> first = new ArrayList<String>();
    public List<String> second = new ArrayList<String>();
    public List<String> listDataHeader;
    public HashMap<String, List<String>> listDataChild;


    /*
     * Preparing the list data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setWindowAnimations(android.R.style.Animation);
        setContentView(R.layout.activity_home_screen);

        final ImageButton mapButton = (ImageButton) findViewById(R.id.mapButton);
        final ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);

        mapButton.setBackgroundColor(0xFFFFFF);
        (findViewById(R.id.myLocButton)).setBackgroundResource(R.color.colorButtonDepressed);
        settingsButton.setBackgroundColor(0xFFFFFF);

        ArrayList<String> list = new ArrayList<String>();

        for(int i = 0; i < FaveLocationManager.locList.size(); ++i){
            list.add(i, FaveLocationManager.locList.get(i).getName());
        }

        Log.d("BREH", Integer.toString(list.size()) );
        Log.d("BREH2", Integer.toString(FaveLocationManager.locList.size()) );

        MyCustomAdapter myCustomAdapter = new MyCustomAdapter(list, this);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(myCustomAdapter);
        /* ADDING THE LIST */
        // preparing list data

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



/*
    @Override
    protected void onStop() {
        super.onStop();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
