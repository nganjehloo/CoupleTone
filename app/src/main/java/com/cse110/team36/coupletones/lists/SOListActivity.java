package com.cse110.team36.coupletones.lists;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;


import com.cse110.team36.coupletones.FaveLocations.FaveLocation;
import com.cse110.team36.coupletones.FireBase.FireBaseManager;
import com.cse110.team36.coupletones.FireBase.LocationFB;
import com.cse110.team36.coupletones.FireBase.SOConfig;
import com.cse110.team36.coupletones.Managers.SOFaveLocManager;
import com.cse110.team36.coupletones.maps.MapsActivity;
import com.cse110.team36.coupletones.FaveLocations.OurFaveLoc;
import com.cse110.team36.coupletones.R;
import com.cse110.team36.coupletones.maps.SOMapsActivity;
import com.cse110.team36.coupletones.lists.adapters.FBListAdapter;
import com.firebase.client.Firebase;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class SOListActivity extends AppCompatActivity{
    FBListAdapter fbListAdapter;

    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        FireBaseManager fireBaseManager = new FireBaseManager(sharedPreferences);
        fireBaseManager.loadSOLocs();
        setContentView(R.layout.activity_so_list);

        initializeListViewAdapter();
        initializeButtons();

        //start a FirebaseService, that has all the listeners
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
                startActivity(new Intent(SOListActivity.this, MapsActivity.class));
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SOListActivity.this, SOConfig.class));
            }
        });
    }

    void initializeListViewAdapter() {
        fbListAdapter = new FBListAdapter(SOFaveLocManager.locList, this, getFragmentManager(),this);

        ListView listView = (ListView) findViewById(R.id.listView_solist);
        listView.setAdapter(fbListAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.i("onStop", "On Stop .....");

        //(new FileManager(this)).exportSavedFavLocs();
        //overridePendingTransition(0, 0);
    }

    public void toSOMap(View view) {
        finish();
        startActivity(new Intent(SOListActivity.this, SOMapsActivity.class));
    }

    public void toVisitedList(View view) {
        finish();
        startActivity(new Intent(SOListActivity.this, SOVisitedActivity.class));
    }

}
