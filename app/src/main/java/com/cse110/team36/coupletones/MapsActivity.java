package com.cse110.team36.coupletones;

import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.animation.BounceInterpolator;

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

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    final float ONE_TENTH_MILE = 160.934f;  // ONE TENTH OF A MILE (in meters) ***** ADDED BY MrSwirlyEyes 4/26

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
        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude)));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude)));
        dropPinEffect(marker);


        addMarkerCircle(marker);

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
        // Determines marker position (lat,long) coordinates
//        LatLng favLocMarker = marker.getPosition();

        // Circle object with particular options
//        CircleOptions markerRadius = new CircleOptions()
//                .center(favLocMarker)       // The marker to create the circle around (or at its center) based on LatLng (coordinates)
//                .center(marker.getPosition())
//                .radius(ONE_TENTH_MILE)     // Creates circle of radius ONE_TENTH_MILE (takes in meter value)
//                .fillColor(0x88AABBCC)      // Circle fill colour is 88 (partially transparent) with AABBCC (color code), default is black 0xFF000000
//                .strokeWidth(10.0f)         // Width of the stroke (circle outline), default is 10
//                .strokeColor(0x00000000);   // Circle outline is transparent (the first 2 00's --> transparency)

//        Circle circle = mMap.addCircle(markerRadius);   // In case youwant to act on circle (like set visibility), default is transparent 0x00000000
//        mMap.addCircle(markerRadius);   // Add's circle radius effect to the map around the dropped marker

        // Circle object with particular options, adds it to map around marker determined by position
        mMap.addCircle(/*CircleOptions markerRadius = */new CircleOptions()
//                      .center(favLocMarker)       // The marker to create the circle around (or at its center) based on LatLng (coordinates)
                  .center(marker.getPosition())
                    .radius(ONE_TENTH_MILE)     // Creates circle of radius ONE_TENTH_MILE (takes in meter value)
                    .fillColor(0x88AABBCC)      // Circle fill colour is 88 (partially transparent) with AABBCC (color code), default is black 0xFF000000
                    .strokeWidth(0.0f)         // Width of the stroke (circle outline), default is 10
                    .strokeColor(0x00000000)   // Circle outline is transparent (the first 2 00's --> transparency)
        );
    }


}



