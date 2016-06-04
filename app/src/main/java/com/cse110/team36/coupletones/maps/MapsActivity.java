package com.cse110.team36.coupletones.maps;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cse110.team36.coupletones.AlarmReset;
import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.FireBase.FireBaseManager;
import com.cse110.team36.coupletones.FireBase.FirebaseService;
import com.cse110.team36.coupletones.FireBase.LocationFB;
import com.cse110.team36.coupletones.FireBase.MyFireBaseRegistration;
import com.cse110.team36.coupletones.FireBase.SOConfig;
import com.cse110.team36.coupletones.Dialogs.LocationDialog;
import com.cse110.team36.coupletones.Managers.FaveLocationManager;
import com.cse110.team36.coupletones.Managers.FileManager;
import com.cse110.team36.coupletones.Managers.MapManager;
import com.cse110.team36.coupletones.Managers.MarkerManager;
import com.cse110.team36.coupletones.NotifSettings;
import com.cse110.team36.coupletones.R;
import com.cse110.team36.coupletones.VibeToneFactory;
import com.cse110.team36.coupletones.listeners.LocationChangeListener;
import com.cse110.team36.coupletones.lists.HomeScreen;
import com.cse110.team36.coupletones.lists.SOVisitedActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener,
        OnMapReadyCallback,
        LocationDialog.LocationDialogListener,
        Constants {

    // Map vars
    private GoogleMap mMap;
    public MapManager mapManager;
    boolean dropMarker = true;


    // SO vars

    private static String SOKey;
    private static String message;
    public SharedPreferences sharedPreferences;


    VibeToneFactory v;
    MarkerManager markerManager;

    LocationChangeListener locationChangeListener;
    LocationManager locationManager;
//    LocationChangeListener locationListener = new LocationChangeListener(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        v = new VibeToneFactory(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean ran_once = sharedPreferences.getBoolean("RAN_ONCE", false);

        if (!ran_once) {
            sharedPreferences.edit().putBoolean("RAN_ONCE", true).apply();
            startActivity(new Intent(MapsActivity.this, MyFireBaseRegistration.class));
        } else {
            Intent intent = new Intent(MapsActivity.this, FirebaseService.class);
            startService(intent);
            Intent intent2 = new Intent(MapsActivity.this, AlarmReset.class);
            startService(intent2);
            FireBaseManager fireBaseManager = new FireBaseManager(sharedPreferences);
            fireBaseManager.loadMyLocs();
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
        //setAlarm();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        if (locationManager != null)
            locationManager.removeUpdates(locationChangeListener);

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
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mapManager = new MapManager(locationManager, this);
        String locationProvider = LocationManager.GPS_PROVIDER;

        mapManager.populateMap("MYMAPS", markerManager);

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

        locationChangeListener = new LocationChangeListener(this);
        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationChangeListener);
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        boolean addSuccess = FaveLocationManager.addLocation(name, loc);
        if (!addSuccess) {
            Toast.makeText(getBaseContext(), "You have to input a unique name! Try again.", Toast.LENGTH_SHORT).show();
        }
        markerManager.dropFavLocMarker(name, loc);

        //Add to Firebase
        LocationFB locFB = new LocationFB();
        locFB.setName(name);
        locFB.setLat(lat);
        locFB.setLong(Long);

        FireBaseManager FBman = new FireBaseManager(sharedPreferences);
        FBman.add(locFB);
    }

    /**
     * Creates these button listeners on the Maps GUI to open other Activities:
     *      + Open HomeScreen [MyLocList]
     *      + Open SOConfig [SO Page]
     *
     */
    void initializeButtons() {

        ImageButton SOlocButton = (ImageButton) findViewById(R.id.myLocButton);
        ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);

        (findViewById(R.id.mapButton)).setBackgroundResource(R.color.colorButtonDepressed);
//        SOlocButton.setBackgroundColor(0xFFFFFF);
//        settingsButton.setBackgroundColor(0xFFFFFF);

        SOlocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MapsActivity.this, SOVisitedActivity.class));
            }
        });

        //SO Page (Right button)

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

    public void runOurList(View view) {
        finish();
        startActivity(new Intent(MapsActivity.this, HomeScreen.class));
    }
    /*
    public void setAlarm() {
        Calendar cur_cal = new GregorianCalendar();
        cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

        Calendar cal = new GregorianCalendar();
//        cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.HOUR_OF_DAY, 3);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
//        cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
//        cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));
        Intent intent = new Intent(MapsActivity.this, AlarmReset.class);
        PendingIntent pintent = PendingIntent.getService(MapsActivity.this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 24*60*60*1000, pintent);
//        String str = cal.toString();


        cal.add(Calendar.DATE, 0);
        Date date = cal.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss  MM-dd-yyyy");
        String str = format1.format(date);

        Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
    }*/
}