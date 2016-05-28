/*
 * Class name: HomeScreen.java
 * Purpose: This is the activity that is called when the middle button is pressed.
 * It displays a list view of the places that the user's SO has visited for the day.
 */

package com.cse110.team36.coupletones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cse110.team36.coupletones.FireBase.SOConfig;
import com.cse110.team36.coupletones.Managers.FaveLocationManager;
import com.cse110.team36.coupletones.Managers.FileManager;
import com.google.android.gms.maps.model.LatLng;

import java.sql.Time;

//import java.sql.Time;

/* NOTE: This is actually the location page (middle button) */

public class SOVisitedActivity extends AppCompatActivity {
    SOVisitedAdapter myCustomAdapter;
    //FBListAdapter fbListAdapter;

    VisitedLoc aLoc = new VisitedLoc("test arrive", new LatLng(0,0), true);
    VisitedLoc lLoc = new VisitedLoc("test leave", new LatLng(0,0), false);

    /*
    @Override
    public void onDialogPositiveClick(String name, LatLng loc, int position) {
        FaveLocationManager.locList.get(position).setName(name);
        myCustomAdapter.notifyDataSetChanged();
    }
    */

    /*
     * Preparing the list data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_visited);

        initializeListViewAdapter();
        initializeButtons();
    }

    void initializeButtons() {

        final ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        final ImageButton mapButton = (ImageButton) findViewById(R.id.mapButton);
        mapButton.setBackgroundColor(0xFFFFFF);
        (findViewById(R.id.myLocButton)).setBackgroundResource(R.color.colorButtonDepressed);
        settingsButton.setBackgroundColor(0xFFFFFF);

        initializeListViewAdapter();

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SOVisitedActivity.this, MapsActivity.class));
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SOVisitedActivity.this, SOConfig.class));
            }
        });
    }

    void initializeListViewAdapter() {
        myCustomAdapter = new SOVisitedAdapter(FaveLocationManager.locList, this, getFragmentManager());

        ListView listView = (ListView) findViewById(R.id.visitedList);
        listView.setAdapter(myCustomAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop", "On Stop .....");

        (new FileManager(this)).exportSavedFavLocs();
        overridePendingTransition(0, 0);
    }

//    public void toSOMap(View view) {
//        startActivity(new Intent(SOVisitedActivity.this, SOMapActivity.class));
//    }

    public void toSOList(View view) {
        startActivity(new Intent(SOVisitedActivity.this, SOListActivity.class));
    }
}
