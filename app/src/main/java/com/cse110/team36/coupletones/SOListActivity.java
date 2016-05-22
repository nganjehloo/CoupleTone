package com.cse110.team36.coupletones;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;


import com.cse110.team36.coupletones.FireBase.LocationFB;
import com.cse110.team36.coupletones.FireBase.SOConfig;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class SOListActivity extends AppCompatActivity{
    FBListAdapter fbListAdapter;


    LocationFB locFB = new LocationFB();
    //mocked data, please edit as needed
    OurFaveLoc faveLocation = new OurFaveLoc("land", new LatLng(32.88182224246528, 163.9531598612666));
    ArrayList<FaveLocation> soList = new ArrayList<FaveLocation>();

    /*
     * Preparing the list data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_list);

        initializeListViewAdapter();
        initializeButtons();
        soList.add(faveLocation);

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
