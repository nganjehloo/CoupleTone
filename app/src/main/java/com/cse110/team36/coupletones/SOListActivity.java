package com.cse110.team36.coupletones;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.cse110.team36.coupletones.FireBase.LocationFB;
import com.cse110.team36.coupletones.GCM.SOConfig;
import com.cse110.team36.coupletones.Managers.FaveLocationManager;
import com.cse110.team36.coupletones.Managers.FileManager;
import com.cse110.team36.coupletones.Managers.SOFaveLocManager;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;

import com.cse110.team36.coupletones.Managers.FileManager;

import java.util.ArrayList;

public class SOListActivity extends AppCompatActivity{
    FBListAdapter fbListAdapter;

    LocationFB locFB = new LocationFB();
    //mocked data, please edit as needed
    OurFaveLoc faveLocation = new OurFaveLoc("land", new LatLng(32.88182224246528, 163.9531598612666));
    ArrayList<FaveLocation> soList = new ArrayList<FaveLocation>();
    //SOFaveLocManager.addLocation()

    // reference to firebase
    Firebase myFirebaseRef;

    protected void onStart(){
        super.onStart();

        myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/debugList");

        //Query queryRef = myFirebaseRef.orderByChild("studentId").equalTo(idToSearch);
        myFirebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot == null || snapshot.getValue() == null)
                    Toast.makeText(SOListActivity.this, "No record found", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SOListActivity.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_list);

        initializeListViewAdapter();
        initializeButtons();
        soList.add(faveLocation);

        //start a FirebaseService, that has all the listeners
        Intent intent = new Intent(SOListActivity.this, FirebaseService.class);
        startService(intent);

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
        fbListAdapter = new FBListAdapter(soList, this, getFragmentManager());

        ListView listView = (ListView) findViewById(R.id.listView);
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
        startActivity(new Intent(SOListActivity.this, SOMapActivity.class));
    }

    public void toVisitedList(View view) {
        startActivity(new Intent(SOListActivity.this, SOVisitedActivity.class));
    }

}
