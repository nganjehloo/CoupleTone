package com.cse110.team36.coupletones.Managers;

import android.app.Activity;
import android.app.FragmentManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.util.Log;

import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.FaveLocation;
import com.cse110.team36.coupletones.LocationDialog;
import com.cse110.team36.coupletones.OurFaveLoc;
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
    LocationDialog locationDialog;
    Activity activity;

    public MapManager(){};

    public MapManager(LocationManager locationManager, Activity activity) {
        this.locationManager = locationManager;
        this.activity = activity;
    }

    public void firstLocationSet(GoogleMap mMap) {
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
            // Create a LatLng object for the current location
            gpsPos = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());

            float zoomLevel = 15; // Sets default zoom level (instead of seeing world, zooms to UCSD) [This goes up to 21]
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gpsPos, zoomLevel));    // MOVES CAMERA THEN ZOOMS TO SET ZOOM LEVEL
        } catch (NullPointerException e) {
            Log.e("NULL_POINTER_EXCEPTION", "GPS LOCATION NOT FOUND");
        }
    }

    public void updateGPSLoc(Location location) {
            gpsPos = new LatLng(location.getLatitude(), location.getLongitude());
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
        locationDialog = new LocationDialog();
        locationDialog.setCoords(point);
        locationDialog.show(fragmentManager, "set location");
    }

    public LatLng getGPSPos() {
        return gpsPos;
    }

    public LocationDialog getLocationDialog() {
        return locationDialog;
    }

    public void populateMap(FileManager fileManager, MarkerManager markerManager) {
        String savedLocArr[] = fileManager.getSavedLocs().split("\n");

        for ( int i = 0 ; i < savedLocArr.length - 1 ; i+=3 )
            FaveLocationManager.locList.add(new OurFaveLoc(new String(savedLocArr[i]),new LatLng(Double.valueOf(savedLocArr[i+1]),Double.valueOf(savedLocArr[i+2]))));
              // The line below is for sound testing purposes only. Final implementation will attribute ringtones to SO's locations, and the below line
              // links it to our own location. The commented out line above is release behavior.
//            FaveLocationManager.locList.add(new OurFaveLoc(new String(savedLocArr[i]),new LatLng(Double.valueOf(savedLocArr[i+1]),Double.valueOf(savedLocArr[i+2])), RingtoneManager.getRingtone(activity,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))));
        for ( int i = 0 ; i < FaveLocationManager.locList.size() ; i++ )
            markerManager.dropFavLocMarker(FaveLocationManager.locList.get(i).getName(), FaveLocationManager.locList.get(i).getCoords());
    }

    public void populateMap(MarkerManager markerManager) {
        // The line below is for sound testing purposes only. Final implementation will attribute ringtones to SO's locations, and the below line
        // links it to our own location. The commented out line above is release behavior.
//            FaveLocationManager.locList.add(new OurFaveLoc(new String(savedLocArr[i]),new LatLng(Double.valueOf(savedLocArr[i+1]),Double.valueOf(savedLocArr[i+2])), RingtoneManager.getRingtone(activity,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))));
        for ( int i = 0 ; i < SOFaveLocManager.locList.size() ; i++ )
            markerManager.dropFavLocMarker(SOFaveLocManager.locList.get(i).getName(), SOFaveLocManager.locList.get(i).getCoords());
    }
}
