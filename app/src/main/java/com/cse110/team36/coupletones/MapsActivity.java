package com.cse110.team36.coupletones;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.cse110.team36.coupletones.GCM.SOActivity;
import com.cse110.team36.coupletones.GCM.SOConfig;
import com.cse110.team36.coupletones.Managers.FaveLocationManager;
import com.cse110.team36.coupletones.Managers.FileManager;
import com.cse110.team36.coupletones.Managers.MapManager;
import com.cse110.team36.coupletones.Managers.MarkerManager;
import com.firebase.client.Firebase;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener,
                                                                OnMapReadyCallback,
                                                                LocationDialog.LocationDialogListener,
                                                                Constants {
    // File vars
    static boolean firstOpen = true;

    // Map vars
    private GoogleMap mMap;
    public MapManager mapManager;
    boolean dropMarker = true;


    // SO vars
    private static String SOKey;
    private static String message;
    SharedPreferences sharedPreferences;

    VibeToneFactory v;
    FileManager fileManager;
    MarkerManager markerManager;

//    LocationChangeListener locationListener = new LocationChangeListener(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        v = new VibeToneFactory(this);
        fileManager = new FileManager(this);

        if (firstOpen) {
            fileManager.importSavedFavLocs();
            firstOpen = false;
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean ran_once = sharedPreferences.getBoolean("RAN_ONCE", false);

        if (!ran_once) {
            sharedPreferences.edit().putBoolean("RAN_ONCE",true).apply();
            startActivity(new Intent(MapsActivity.this, SOActivity.class));
        } else {

            setContentView(R.layout.activity_maps);

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            initializeButtons();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("onStart", "On Start .....");

        overridePendingTransition(0, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop", "On Stop .....");

        fileManager.exportSavedFavLocs();
        overridePendingTransition(0, 0);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        markerManager = new MarkerManager(this, mMap);
        initializeGMapUISettings();
        mMap.setOnMapLongClickListener(this);   // LISTENER FOR THE LONG-CLICK SO MARKER DROPS ON HELD LOCATION



        //GPS
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mapManager = new MapManager(locationManager);
        String locationProvider = LocationManager.GPS_PROVIDER;

        mapManager.populateMap(fileManager, markerManager);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
            Log.d("test1", "ins");
            return;
        } else if(mMap != null) {
            Log.d("test2", "outs");
            mMap.setMyLocationEnabled(true);

            // Create a criteria object to retrieve provider
            mapManager.firstLocationSet(mMap);
        }
        locationManager.requestLocationUpdates(locationProvider, 0, 0, new LocationChangeListener(this));
    }

    /***** DROPPING OF MAP MARKER ON MAP LONG-CLICK
     *          ADDED BY: MrSwirlyEyes
     *                      4/25
     *
     *****/
    public void onMapLongClick(LatLng point) {
        v.vibrate();

        dropMarker = true;  //Always assume we can drop a marker (and show we cannot, if we cannot)

        // If the list already has ANY point (if we already have saved favorite locations
            //TODO: REPLACE WITH DATABASE WHEN TEAM GETS IT
        if (FaveLocationManager.locList.size() > 0) {
            dropMarker = mapManager.checkValidDrop(FaveLocationManager.locList, point);
        }

        //Here, we know there was either:
            // No markers/saved-locs to begin with
            // OR, all the other markers are outside of 2*ONE_TENTH_MILE rule
        // Thus we wish to drop a marker, and we can!
            if (dropMarker) { mapManager.showLocationDialog(point, getFragmentManager()); }
            else { Toast.makeText(getBaseContext(), "CANNOT drop FavLoc here\nToo Close to another FavLoc", Toast.LENGTH_SHORT).show(); }
    }

//    void populateMap() {
//        String savedLocArr[] = fileManager.getSavedLocs().split("\n");
//
//        for ( int i = 0 ; i < savedLocArr.length - 1 ; i+=3 )
//            FaveLocationManager.locList.add(new FaveLocation(new String(savedLocArr[i]),new LatLng(Double.valueOf(savedLocArr[i+1]),Double.valueOf(savedLocArr[i+2]))));
//
//        for ( int i = 0 ; i < FaveLocationManager.locList.size() ; i++ )
//            markerManager.dropFavLocMarker(FaveLocationManager.locList.get(i).getName(), FaveLocationManager.locList.get(i).getCoords());
//    }



    /* ----------------------------------------------------
     * Allows us to communicate with the LocationDialog
     * Saves new location
     * ---------------------------------------------------- */
    @Override
    public void onDialogPositiveClick(String name, LatLng loc, int pos) {
        /* NOTE (Aimed toward locations team)
         * This implementation is temporary - I was testing that the information gets here
         * Use this method to save the new location
         */
        double lat = loc.latitude;
        double Long = loc.longitude;

        boolean addSuccess = FaveLocationManager.addLocation(name, loc);
        if (!addSuccess) {
            Toast.makeText(getBaseContext(), "You have to input a unique name! Try again.", Toast.LENGTH_SHORT).show();
        }
        markerManager.dropFavLocMarker(name, loc);

        //TODO: UNIQUE ID FOR THE LINK

        //Add to Firebase
        //Firebase myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/myLoc");
        Firebase myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/debugList");
        LocationFB locFB = new LocationFB();
        locFB.setName(name);
        locFB.setLat(lat);
        locFB.setLong(Long);
        myFirebaseRef.child(locFB.getName()).setValue(locFB);


    }

    /**
     * Creates these button listeners on the Maps GUI to open other Activities:
     *      + Open HomeScreen [MyLocList]
     *      + Open SOConfig [SO Page]
     *
     */
    void initializeButtons() {

        (findViewById(R.id.mapButton)).setBackgroundResource(R.color.colorButtonDepressed);

        ImageButton myLocButton = (ImageButton) findViewById(R.id.myLocButton);
        myLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MapsActivity.this, HomeScreen.class));
            }
        });

        //SO Page (Right button)
        ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MapsActivity.this, SOConfig.class));
            }
        });
    }

    /**
     * Sets the googleMap UI Settings for:
     *      + zoomControl
     *      + MapToolbar
     *      + LocationButtonEnable
     */
    void initializeGMapUISettings() {
        mMap.getUiSettings().setZoomControlsEnabled(false); //Disable zoom toolbar
        mMap.getUiSettings().setMapToolbarEnabled(false);   //Disable (useless) map toolbar (literally is garbage)
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
    }

    public void runVibe(View view) {
//        FloatingActionButton vibe = (FloatingActionButton) findViewById(R.id.vibe);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(MapsActivity.this, NotifSettings.class));

    }

    public void runOurList(View view) {
        startActivity(new Intent(MapsActivity.this, HomeScreen.class));
    }
}