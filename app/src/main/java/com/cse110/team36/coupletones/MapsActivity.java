package com.cse110.team36.coupletones;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;

import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.location.LocationListener;

import com.cse110.team36.coupletones.GCM.RegistrationIntentService;
import com.cse110.team36.coupletones.GCM.SOActivity;
import com.cse110.team36.coupletones.GCM.SOConfig;
import com.cse110.team36.coupletones.GCM.sendNotificationJob;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;


public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener,
                                                                OnMapReadyCallback,
                                                                LocationDialog.LocationDialogListener,
                                                                Constants {
    String savedLocs = "";

    FaveLocation currFavLoc = null;
    FaveLocation lastFavLoc = null;

    static boolean firstOpen = true;

    public MapManager mapManager;
//    public FaveLocationManager FaveLocationManager = new FaveLocationManager(getBaseContext());

    boolean debug = true;
    private GoogleMap mMap;
    double dist = 0;

    boolean dropMarker = true;

    private static String SOKey;
    private static String message;

    SharedPreferences sharedPreferences;


    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the LocationDialogFragment.LocationDialogListener interface
    /* Create new location with the coordinates and name */
    @Override
    public void onDialogPositiveClick(String name, LatLng loc) {
        /* NOTE (Aimed toward locations team)
         * This implementation is temporary - I was testing that the information gets here
         * Use this method to save the new location
         */
        //TODO: DELETE ME!
        if (name.equals("")) {
            savedLocs = "";
            FaveLocationManager.emptyLocs();
            return;
        }

        boolean addSuccess = FaveLocationManager.addLocation(name, loc);
        if (!addSuccess) {
            Toast.makeText(getBaseContext(), "You have to input a unique name! Try again.", Toast.LENGTH_SHORT).show();
        }
        dropFavLocMarker(name, loc);

//        Toast.makeText(getBaseContext(),
//                "" + newLoc.getName() + "\n" + String.valueOf(newLoc.getCoords().latitude) + ", " + String.valueOf(newLoc.getCoords().longitude),
//                Toast.LENGTH_SHORT).show();
        //TODO: DELETE ME!
        if (name.equals("")) {
            savedLocs = "";
            FaveLocationManager.emptyLocs();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (firstOpen) {
            importSavedFavLocs();
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
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            initializeButtons();

        }
    }

    /* (non-Javadoc)
* @see android.app.Activity#onStop()
*/
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop", "On Stop .....");

        exportSavedFavLocs();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
//    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        loadAndDropSavedLocs();


        mMap.setOnMapLongClickListener(this);   // LISTENER FOR THE LONG-CLICK SO MARKER DROPS ON HELD LOCATION

        /***** Add a marker at UCSD and move the camera
         *          ADDED BY: MrSwirlyEyes
         *                      4/25
         *
         *****/
        initializeGMapUISettings();


        //GPS
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mapManager = new MapManager(locationManager);
        String locationProvider = LocationManager.GPS_PROVIDER;

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
            Log.d("test1", "ins");
            return;
        } else if(mMap != null) {
            Log.d("test2", "outs");
            mMap.setMyLocationEnabled(true);

            // Create a criteria object to retrieve provider
            mapManager.firstLocationSet(mMap);
        }
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
            mapManager.updateGPSLoc(location);
                if (FaveLocationManager.locList.size() > 0) {
                    String string = null;
                    for ( int i = 0 ; i < FaveLocationManager.locList.size() ; i++ ) {
                        dist = SphericalUtil.computeDistanceBetween(FaveLocationManager.locList.get(i).getCoords(), mapManager.getGPSPos());
                        if ( dist <= ONE_TENTH_MILE ) {
                            currFavLoc = new FaveLocation(FaveLocationManager.locList.get(i).getName(), FaveLocationManager.locList.get(i).getCoords() );
                            string = "checked in!" + "\nLocName=" + FaveLocationManager.locList.get(i).getName() + "\nArraySize=" + FaveLocationManager.locList.size() + "\nx=" + FaveLocationManager.locList.get(i).getCoords().latitude + "\ny=" + FaveLocationManager.locList.get(i).getCoords().longitude;
                            if ( lastFavLoc == null ) {
                                lastFavLoc = currFavLoc;
                            } else if ( currFavLoc.getName().equals(lastFavLoc.getName()) ) {
                                string = "Still @ Curr Fav Loc!" + "\nLocName=" + FaveLocationManager.locList.get(i).getName() + "\nArraySize=" + FaveLocationManager.locList.size() + "\nx=" + FaveLocationManager.locList.get(i).getCoords().latitude + "\ny=" + FaveLocationManager.locList.get(i).getCoords().longitude;
                                //TODO: NO NEED FOR NOTIFY, I AM WHERE I JUST WAS
                            } else {

                                //TODO: I AM SOMEWHERE NEW! NOTIFY
                                String str = currFavLoc.getName();
                                SharedPreferences keyPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SOKey = keyPreferences.getString("SOREGID", null);
                                message = "l " + str;
                                String[] params = { SOKey, message};
                                sendNotificationJob job = new sendNotificationJob();
                                job.execute(params);
                                string = "checked in!" + "\nLocName=" + FaveLocationManager.locList.get(i).getName() + "\nArraySize=" + FaveLocationManager.locList.size() + "\nx=" + FaveLocationManager.locList.get(i).getCoords().latitude + "\ny=" + FaveLocationManager.locList.get(i).getCoords().longitude;
                            }


                            lastFavLoc = currFavLoc;
                            break;
                        } else {
                            //TODO:  I AM IN NO FAV LOCATION, NO NOTIFY NEEDED
                            string = "NOT checked in!" + "\nLocName=" + FaveLocationManager.locList.get(i).getName() + "\nArraySize=" + FaveLocationManager.locList.size() + "\nx=" + FaveLocationManager.locList.get(i).getCoords().latitude + "\ny=" + FaveLocationManager.locList.get(i).getCoords().longitude;
                            //Toast.makeText(getBaseContext(), string, Toast.LENGTH_SHORT).show(); //?
                        }
                    }
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);

    }

    /***** DROPPING OF MAP MARKER ON MAP LONG-CLICK
     *          ADDED BY: MrSwirlyEyes
     *                      4/25
     *
     *****/
    public void onMapLongClick(LatLng point) {
        vibrate();

        dropMarker = true;  //Always assume we can drop a marker (and show we cannot, if we cannot)
        String markerCoords = "";   //Initializing markerCoords string (for debug output)

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());


        // If the list already has ANY point (if we already have saved favorite locations
            //TODO: REPLACE WITH DATABASE WHEN TEAM GETS IT
        if (FaveLocationManager.locList.size() > 0) {
            dropMarker = mapManager.checkValidDrop(FaveLocationManager.locList, point);
        }

        //Here, we know there was either:
            // No markers/saved-locs to begin with
            // OR, all the other markers are outside of 2*ONE_TENTH_MILE rule
        // Thus we wish to drop a marker, and we can!

            if (dropMarker) { //Check our boolean to be absolutely sure we can still drop marker

                // Stazia's code
                mapManager.showLocationDialog(point, getFragmentManager());

                if (debug) markerCoords = "D=" + dist + "m\nt=" + timeStamp + "\nYES! DROP THE MARKER" + "\nArraySize=" + FaveLocationManager.locList.size();
            } else {
                if (debug) markerCoords = "D=" + dist + "m\nt=" + timeStamp + "\nDONT DROP THE MARKER" + "\nArraySize=" + FaveLocationManager.locList.size();
            }
            markerCoords += "\n" + savedLocs;
            if (debug) Toast.makeText(getBaseContext(), markerCoords, Toast.LENGTH_LONG).show();
    }


/***************************************************************************************************
 *
 *                                      FUNCTIONS
 *
 *
 **************************************************************************************************/







    /***** BOUNCE EFFECT FOR MARKER DROP
     *          ADDED BY: MrSwirlyEyes
     *                      4/25
     *
     *****/
    private void dropPinEffect(final Marker marker) {
        // Handler allows us to repeat a code block after a specified delay
        final android.os.Handler handler = new android.os.Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 750;

        // Use the bounce interpolator
        final android.view.animation.Interpolator interpolator =
                new BounceInterpolator();

        // Animate marker with a bounce updating its position every 15ms
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                // Calculate t for bounce based on elapsed time
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed
                                / duration), 0);
                // Set the anchor
                marker.setAnchor(0.5f, 1.0f + 2 * t);

                if (t > 0.0) {
                    // Post this event again 15ms from now.
                    handler.postDelayed(this, 15);
                } else { // done elapsing, show window
                    marker.showInfoWindow();
                }
            }
        });
    }


    /***** CIRCLE RADIUS AROUND DROPPED MARKER
     *          ADDED BY: MrSwirlyEyes
     *                      4/26
     *
     *****/
    /*
        Takes in Marker object (from after use long-click drops),
            then creates a circle around it to display the 1/10th mile radius
     */
    Circle addMarkerCircle(Marker marker) {
        // Circle object with particular options, adds it to map around marker determined by position
        return mMap.addCircle(new CircleOptions()
                .center(marker.getPosition())
                .radius(ONE_TENTH_MILE)     // Creates circle of radius ONE_TENTH_MILE (takes in meter value)
                .fillColor(0x88AABBCC)      // Circle fill colour is 88 (partially transparent) with AABBCC (color code), default is black 0xFF000000
                .strokeWidth(0.0f)         // Width of the stroke (circle outline), default is 10
                .strokeColor(0x00000000));   // Circle outline is transparent (the first 2 00's --> transparency));
    }

    /***** Simple Vibrate, used to detect on-long click
     *          ADDED BY: MrSwirlyEyes
     *                      4/29
     *
     *****/
    void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 100 milliseconds
        v.vibrate(100);
    }

    void dropFavLocMarker(String name, LatLng loc) {
        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(loc.latitude, loc.longitude)));
        // Special dropping effect
        dropPinEffect(marker);
        // Add circle around marker to display the 1/10th of a mile radius

        addMarkerCircle(marker);
            /* Open dialog box for saving location
             *          Added by WigginWannabe 26 Apr 2016
             */

        marker.setTitle(name);
    }

    void importSavedFavLocs() {
        try {
            FileInputStream fis = openFileInput("favorite_locs");
            int test = 0;
            while (test != -1) {
                test = fis.read();
                if (test != -1)
                    savedLocs += (char) test;
            } fis.close();
        } catch (Exception e) {
            Log.e("File", "File error!");
        }
    }

    void exportSavedFavLocs() {
        String FILENAME = "favorite_locs";

        savedLocs = "";

        for ( int i = 0 ; i < FaveLocationManager.locList.size() ; i++ ) {
            savedLocs += FaveLocationManager.locList.get(i).getName() + "\n";
            savedLocs += FaveLocationManager.locList.get(i).getLat() + "\n";
            savedLocs += FaveLocationManager.locList.get(i).getLng() + "\n";
        }


        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(savedLocs.getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e("File", "File error!");
        }
    }

    void loadAndDropSavedLocs() {
        String savedLocArr[] = savedLocs.split("\n");

        for ( int i = 0 ; i < savedLocArr.length - 1 ; i+=3 )
            FaveLocationManager.locList.add(new FaveLocation(new String(savedLocArr[i]),new LatLng(Double.valueOf(savedLocArr[i+1]),Double.valueOf(savedLocArr[i+2]))));

        for ( int i = 0 ; i < FaveLocationManager.locList.size() ; i++ )
            dropFavLocMarker(FaveLocationManager.locList.get(i).getName(),FaveLocationManager.locList.get(i).getCoords());
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


    public static String getSOKey(){
        return SOKey;
    }

    public static String getMessage(){
        return message;
    }
}


