package com.cse110.team36.coupletones;

import android.app.FragmentManager;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.animation.BounceInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Time;
import java.util.Timer;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener, OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(ucsd));   // MOVES CAMERA TO FOCUS UCSD IN THE MIDDLE

        float zoomLevel = 15; // Sets default zoom level (instead of seeing world, zooms to UCSD) [This goes up to 21]
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ucsd, zoomLevel));    // MOVES CAMERA THEN ZOOMS TO SET ZOOM LEVEL

//        mMap.getUiSettings().setZoomControlsEnabled(true);  // ??? Sets zoom controls to work!?!
        //TO HERE
    }

    /***** DROPPING OF MAP MARKER ON MAP LONG-CLICK
     *          ADDED BY: MrSwirlyEyes
     *                      4/25
     *
     *****/
    public void onMapLongClick(LatLng point) {



//        mMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude)));
        dropPinEffect(mMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude))));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /* Open dialog box for saving location
         *          Added by WigginWannabe 26 Apr 2016
         */
        LocationDialog locationDialog = new LocationDialog(point);
        locationDialog.show(getFragmentManager(), "set location");
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


}



