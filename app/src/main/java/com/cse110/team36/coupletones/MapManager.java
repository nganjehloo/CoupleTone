package com.cse110.team36.coupletones;

import android.app.FragmentManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by stazia on 5/4/16.
 */
public class MapManager implements Constants{

    double dist = 0;
    LocationDialog locationDialog;

    MapManager() {}

    public void updateGPSLatLong(LocationManager locationManager, GoogleMap mMap) {
        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);
        Location myLocation;
        try {
            myLocation = locationManager.getLastKnownLocation(provider);
        } catch (SecurityException e) {
            Log.e("PERMISSION_EXCEPTION", "PERMISSION_NOT_GRANTED");
            return;
        }
        try {
            // Get latitude of the current location
//                gpsLongitude = myLocation.getLatitude();
            MapsActivity.gpsLatitude = myLocation.getLatitude();

            // Get longitude of the current location
//                gpsLongitude = myLocation.getLongitude();
            MapsActivity.gpsLongitude = myLocation.getLongitude();
//            Toast.makeText(getBaseContext(), "Found You!", Toast.LENGTH_SHORT).show();
            // Create a LatLng object for the current location
            LatLng gpsPos = new LatLng(MapsActivity.gpsLatitude, MapsActivity.gpsLongitude);
            String string = "" + String.valueOf(myLocation.getLatitude()) + ", " + String.valueOf(myLocation.getLongitude()) + "\n";
//                Toast.makeText(getBaseContext(), string, Toast.LENGTH_LONG).show();

//            mMap.moveCamera(CameraUpdateFactory.newLatLng(gpsPos));
            float zoomLevel = 15; // Sets default zoom level (instead of seeing world, zooms to UCSD) [This goes up to 21]
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gpsPos, zoomLevel));    // MOVES CAMERA THEN ZOOMS TO SET ZOOM LEVEL
        } catch (NullPointerException e) {
            Log.e("NULL_POINTER_EXCEPTION", "GPS LOCATION NOT FOUND");
        }
    }

    public boolean isValidDrop(LatLng point, ArrayList<FaveLocation> locList) {
        for (int i = 0; i < locList.size(); i++) {  //Iterate through every element in the saved locList
            //Initialize our GpsUtility object (not important to know what is going on with it)
            GpsUtility distance = new GpsUtility();
            //Computes distance (based on GPS coords, earth sphericalness, etc.) from current point/marker trying to drop
            // to ALL OTHER points/marker in the list
            dist = distance.computeDistanceBetween(new LatLng(locList.get(i).getCoords().latitude, locList.get(i).getCoords().longitude), new LatLng(point.latitude, point.longitude));

            // If distance between 2 markers (radius's) are within 2*ONE_TENTH_MILEs
            //We do not want to drop a marker, and thus we will abort
            if (dist <= 2 * ONE_TENTH_MILE) {
                //Dont drop that marker
                return false;
            }
        }
        return true;
    }

    public double getDist() {
        return dist;
    }

    public void showLocationDialog(LatLng point, FragmentManager fragmentManager) {
        locationDialog = new LocationDialog();
        locationDialog.setCoords(point);
        locationDialog.show(fragmentManager, "set location");
    }

    /* Necessary only for testing */
    public LocationDialog getLocationDialog() {
        return locationDialog;
    }
}
