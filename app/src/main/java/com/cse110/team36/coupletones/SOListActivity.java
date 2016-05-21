package com.cse110.team36.coupletones;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cse110.team36.coupletones.GCM.SOConfig;
import com.cse110.team36.coupletones.Managers.FaveLocationManager;
import com.cse110.team36.coupletones.Managers.FileManager;
import com.firebase.client.Firebase;
import com.google.android.gms.maps.model.LatLng;

import com.cse110.team36.coupletones.Managers.FileManager;

public class SOListActivity extends AppCompatActivity implements LocationDialog.LocationDialogListener{
    MyCustomAdapter myCustomAdapter;
    FBListAdapter fbListAdapter;

    @Override
    public void onDialogPositiveClick(String name, LatLng loc, int position) {
        String originalName = FaveLocationManager.locList.get(position).getName();
        double lat = FaveLocationManager.locList.get(position).getLat();
        double Long = FaveLocationManager.locList.get(position).getLng();

        FaveLocationManager.locList.get(position).setName(name);
        myCustomAdapter.notifyDataSetChanged();

        //Remove from Firebase and add again (Rename)
        //Firebase myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/MyLoc");
        Firebase myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/debugList");
        LocationFB locFB = new LocationFB();
        locFB.setName(originalName);
        myFirebaseRef.child(locFB.getName()).removeValue();
        locFB.setName(name);
        locFB.setLat(lat);
        locFB.setLong(Long);
        myFirebaseRef.child(locFB.getName()).setValue(locFB);
    }

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
    myCustomAdapter = new MyCustomAdapter(FaveLocationManager.locList, this, getFragmentManager());

    ListView listView = (ListView) findViewById(R.id.listView);
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
        startActivity(new Intent(SOVisitedActivity.this, SOMapActivity.class));
    }

    public void toSOList(View view) {
        startActivity(new Intent(SOVisitedActivity.this, SOListActivity.class));
    }
}
