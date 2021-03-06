package com.cse110.team36.coupletones;

import android.app.FragmentManager;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;

/**
 * Created by stazia on 5/6/16.
 */
public class MapManager implements Constants {
    LocationManager locationManager;
    LatLng gpsPos;

    public MapManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public void firstLocationSet(GoogleMap mMap) {
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);
        Location myLocation;
        try {
//                if (mMap != null) {
            myLocation = locationManager.getLastKnownLocation(provider);
//                }
        } catch (SecurityException e) {
            Log.e("PERMISSION_EXCEPTION", "PERMISSION_NOT_GRANTED");
            return;
        }
        try {
//            Toast.makeText(getBaseContext(), "Found You!", Toast.LENGTH_SHORT).show();
            // Create a LatLng object for the current location
            gpsPos = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
//                String string = "" + String.valueOf(myLocation.getLatitude()) + ", " + String.valueOf(myLocation.getLongitude()) + "\n";
//                Toast.makeText(getBaseContext(), string, Toast.LENGTH_LONG).show();

//            mMap.moveCamera(CameraUpdateFactory.newLatLng(gpsPos));
            float zoomLevel = 15; // Sets default zoom level (instead of seeing world, zooms to UCSD) [This goes up to 21]
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gpsPos, zoomLevel));    // MOVES CAMERA THEN ZOOMS TO SET ZOOM LEVEL
        } catch (NullPointerException e) {
            Log.e("NULL_POINTER_EXCEPTION", "GPS LOCATION NOT FOUND");
        }
    }

    public void updateGPSLoc(Location location) {
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);
        Location myLocation = null;
        try {
//                    if (mMap != null) {}
            myLocation = locationManager.getLastKnownLocation(provider);
        } catch (SecurityException e) {
            Log.e("PERMISSION_EXCEPTION", "PERMISSION_NOT_GRANTED");
            return;
        }
        try {
            // Create a LatLng object for the current location
            gpsPos = new LatLng(location.getLatitude(), location.getLongitude());
//                String string = "" + String.valueOf(location.getLatitude()) + ", " + String.valueOf(location.getLongitude()) + "\n";

//                Toast.makeText(getBaseContext(), string, Toast.LENGTH_LONG).show();


        } catch (NullPointerException e) {
            Log.e("NULL_POINTER_EXCEPTION", "GPS LOCATION NOT FOUND");
        }

        //Initialize our GpsUtility object (not important to know what is going on with it)

        //Computes distance (based on GPS coords, earth sphericalness, etc.) from current point/marker trying to drop
        // to ALL OTHER points/marker in the list
    }

    public boolean checkValidDrop(ArrayList<FaveLocation> locList, LatLng point) {
        double dist;
        for (int i = 0; i < locList.size(); i++) {  //Iterate through every element in the saved locList
            //Initialize our GpsUtility object (not important to know what is going on with it)

            //Computes distance (based on GPS coords, earth sphericalness, etc.) from current point/marker trying to drop
            // to ALL OTHER points/marker in the list
            dist = SphericalUtil.computeDistanceBetween(new LatLng(locList.get(i).getCoords().latitude, locList.get(i).getCoords().longitude), new LatLng(point.latitude, point.longitude));

            // If distance between 2 markers (radius's) are within 2*ONE_TENTH_MILEs
            //We do not want to drop a marker, and thus we will abort
            if (dist <= 2 * ONE_TENTH_MILE) {
                //Don't drop that marker
                return false;
            }
        }
        return true;
    }

    public void showLocationDialog(LatLng point, FragmentManager fragmentManager) {
        LocationDialog locationDialog = new LocationDialog();
        locationDialog.setCoords(point);
        locationDialog.show(fragmentManager, "set location");
    }

    public LatLng getGPSPos() {
        return gpsPos;
    }
}
