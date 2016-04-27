package com.cse110.team36.coupletones;

import android.Manifest;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Timer;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener,
                                                                OnMapReadyCallback,
                                                                LocationDialog.LocationDialogListener {

    private GoogleMap mMap;
    final float ONE_TENTH_MILE = 160.934f;  // ONE TENTH OF A MILE (in meters) ***** ADDED BY MrSwirlyEyes 4/26

    //Temporary until we get DB
    ArrayList<LatLng> locList = new ArrayList<LatLng>();

    double latitude;
    double longitude;
    LatLng gpsPos;


    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the LocationDialogFragment.LocationDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ImageButton mapButton = (ImageButton) findViewById(R.id.mapButton);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this, MapsActivity.class));
            }});


        Button myLocButton = (Button) findViewById(R.id.myLocButton);

        myLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this, MapsActivity.class));
            }});


        ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this, MapsActivity.class));
            }});
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

        mMap.setOnMapLongClickListener(this);   // LISTENER FOR THE LONG-CLICK SO MARKER DROPS ON HELD LOCATION


        /***** Add a marker at UCSD and move the camera
         *          ADDED BY: MrSwirlyEyes
         *                      4/25
         *
         *****/
        //FROM HERE
        LatLng ucsd = new LatLng(32.8801, -117.2340);       // GPS COORDS OF UCSD
        mMap.addMarker(new MarkerOptions().position(ucsd).title("Marker in UCSD")); // MARKER OPTIONS with TITLE and POSITION @ GPS COORDS OF UCSD

        mMap.getUiSettings().setZoomControlsEnabled(false); //Disable zoom toolbar
        mMap.getUiSettings().setMapToolbarEnabled(false);   //Disable (useless) map toolbar (literally is garbage)

        float zoomLevel = 15; // Sets default zoom level (instead of seeing world, zooms to UCSD) [This goes up to 21]
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ucsd, zoomLevel));    // MOVES CAMERA THEN ZOOMS TO SET ZOOM LEVEL




        //GPS
        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
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
        }
//        final Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("updated path"));
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                // Create a criteria object to retrieve provider
                Criteria criteria = new Criteria();

                // Get the name of the best provider
                String provider = locationManager.getBestProvider(criteria, true);
                Location myLocation = null;
                try {
                    if (mMap != null)
                        myLocation = locationManager.getLastKnownLocation(provider);
                } catch (SecurityException e) {
                    Log.e("PERMISSION_EXCEPTION", "PERMISSION_NOT_GRANTED");
                }
                // Get latitude of the current location
                latitude = myLocation.getLatitude();

                // Get longitude of the current location
                longitude = myLocation.getLongitude();

                // Create a LatLng object for the current location
                gpsPos = new LatLng(latitude, longitude);


//                marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(gpsPos));
            }

//            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

//            @Override
            public void onProviderEnabled(String provider) {

            }

//            @Override
            public void onProviderDisabled(String provider) {

            }
        };

//        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);

    }

    /***** DROPPING OF MAP MARKER ON MAP LONG-CLICK
     *          ADDED BY: MrSwirlyEyes
     *                      4/25
     *
     *****/
    public void onMapLongClick(LatLng point) {
//        Criteria criteria = new Criteria();
//        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        String provider = locationManager.getBestProvider(criteria, true);
//
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    100);
//            Log.d("test1", "ins");
//            return;
//        } else if(mMap != null) {
//            Log.d("test2", "outs");
//            mMap.setMyLocationEnabled(true);
//        }
//        Location myLocation = locationManager.getLastKnownLocation(provider);

//        LatLng tmp = new LatLng((float) gpsPos.latitude, (float) gpsPos.longitude);
//        float abs_x = Math.abs((float) point.latitude - (float) gpsPos.latitude);
//        abs_x = abs_x * abs_x;
//        float abs_y = Math.abs((float) point.longitude - (float) gpsPos.longitude);
//        abs_y = abs_y * abs_y;

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

        String markerCoords = "t = " + timeStamp + "\nx =" + String.valueOf(point.latitude) + "\ny=" + point.longitude;

        Toast.makeText(MapsActivity.this, markerCoords, Toast.LENGTH_LONG).show();
//        if ( Math.sqrt(abs_x + abs_y) > ONE_TENTH_MILE ) {
        // Get instance of Vibrator from current Context
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            // Vibrate for 400 milliseconds
            v.vibrate(100);


            // Marker object, drops where long click has occured
            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude)));
            // Special dropping effect
            dropPinEffect(marker);
            // Add circle around marker to display the 1/10th of a mile radius
            addMarkerCircle(marker);

        /* Open dialog box for saving location
         *          Added by WigginWannabe 26 Apr 2016
         */
            LocationDialog locationDialog = new LocationDialog();
            locationDialog.setArguments(new Bundle());
            locationDialog.show(getFragmentManager(), "set location");

            // Stazia: I am not finished implementing it yet, but I expected that locations team will have
            // the new name available to them around here, for creating a new location
//        }
    }



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
    void addMarkerCircle(Marker marker) {
        // Circle object with particular options, adds it to map around marker determined by position
        mMap.addCircle(/*CircleOptions markerRadius = */new CircleOptions()
                .center(marker.getPosition())
                .radius(ONE_TENTH_MILE)     // Creates circle of radius ONE_TENTH_MILE (takes in meter value)
                .fillColor(0x88AABBCC)      // Circle fill colour is 88 (partially transparent) with AABBCC (color code), default is black 0xFF000000
                .strokeWidth(0.0f)         // Width of the stroke (circle outline), default is 10
                .strokeColor(0x00000000)   // Circle outline is transparent (the first 2 00's --> transparency)
        );
    }


}



