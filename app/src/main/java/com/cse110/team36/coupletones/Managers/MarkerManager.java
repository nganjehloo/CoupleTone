package com.cse110.team36.coupletones.Managers;

import android.app.Activity;
import android.os.SystemClock;
import android.view.animation.BounceInterpolator;

import com.cse110.team36.coupletones.Constants;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by admin on 5/8/16.
 */
public class MarkerManager implements Constants {
    Activity mapsActivity;
    GoogleMap mMap;
    public MarkerManager(Activity mapsActivity, GoogleMap mMap) {
        this.mapsActivity = mapsActivity;
        this.mMap = mMap;
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
    Circle addMarkerCircle(Marker marker) {
        // Circle object with particular options, adds it to map around marker determined by position
        return mMap.addCircle(new CircleOptions()
                .center(marker.getPosition())
                .radius(ONE_TENTH_MILE)     // Creates circle of radius ONE_TENTH_MILE (takes in meter value)
                .fillColor(0x88AABBCC)      // Circle fill colour is 88 (partially transparent) with AABBCC (color code), default is black 0xFF000000
                .strokeWidth(0.0f)         // Width of the stroke (circle outline), default is 10
                .strokeColor(0x00000000));   // Circle outline is transparent (the first 2 00's --> transparency));
    }

    public void dropFavLocMarker(String name, LatLng loc) {
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
}
