/*
 * Class name: HomeScreen.java
 * Purpose: This is the activity that is called when the middle button is pressed.
 * It displays a list view of the places that the user's SO has visited for the day.
 */

package com.cse110.team36.coupletones.lists;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cse110.team36.coupletones.FaveLocations.SOFaveLoc;
import com.cse110.team36.coupletones.FireBase.FireBaseManager;
import com.cse110.team36.coupletones.FireBase.SOConfig;
import com.cse110.team36.coupletones.Dialogs.LocationDialog;
import com.cse110.team36.coupletones.Managers.FaveLocationManager;
import com.cse110.team36.coupletones.Managers.FileManager;
import com.cse110.team36.coupletones.Managers.SOFaveLocManager;
import com.cse110.team36.coupletones.maps.MapsActivity;
import com.cse110.team36.coupletones.R;
import com.cse110.team36.coupletones.maps.SOMapsActivity;
import com.cse110.team36.coupletones.lists.adapters.FBListAdapter;
import com.cse110.team36.coupletones.lists.adapters.SOVisitedAdapter;
import com.google.android.gms.maps.model.LatLng;

/* NOTE: This is actually the location page (middle button) */

public class SOVisitedActivity extends AppCompatActivity{
    SOVisitedAdapter myCustomAdapter;
    FBListAdapter fbListAdapter;
    public SharedPreferences sharedPreferences;
    /*
     * Preparing the list data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        FireBaseManager fireBaseManager = new FireBaseManager(sharedPreferences);
        fireBaseManager.loadSOVisited();
        Log.d("SOVISITED", ((Integer) SOFaveLocManager.visList.size()).toString());
        //Log.d("SOVISITED", SOFaveLocManager.visList.peek().getName());
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
        myCustomAdapter = new SOVisitedAdapter(SOFaveLocManager.visList, this, getFragmentManager());

        ListView listView = (ListView) findViewById(R.id.listView_visited);
        listView.setAdapter(myCustomAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop", "On Stop .....");

        (new FileManager(this)).exportSavedFavLocs();
        overridePendingTransition(0, 0);
    }

    public void toSOMap(View view) {
        finish();
        startActivity(new Intent(SOVisitedActivity.this, SOMapsActivity.class));
    }

    public void toSOList(View view) {
        finish();
        startActivity(new Intent(SOVisitedActivity.this, SOListActivity.class));
    }
}
