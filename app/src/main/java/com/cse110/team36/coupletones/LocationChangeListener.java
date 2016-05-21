package com.cse110.team36.coupletones;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.cse110.team36.coupletones.GCM.sendNotificationJob;
import com.cse110.team36.coupletones.Managers.FaveLocationManager;
import com.cse110.team36.coupletones.Managers.MapManager;
import com.google.maps.android.SphericalUtil;

/**
 * Created by admin on 5/8/16.
 */
public class LocationChangeListener implements LocationListener, Constants {
    private Activity mapsActivity;
    private int currLoc = -1;
    private int lastLoc = -1;
    private boolean currOutside = false;
    private boolean prevOutside = false;

    public LocationChangeListener(Activity mapsActivity) {
        this.mapsActivity = mapsActivity;
    }

    public void onLocationChanged(Location location) {
        LocationManager locationManager = (LocationManager) mapsActivity.getSystemService(Context.LOCATION_SERVICE);
        MapManager mapManager = new MapManager(locationManager, mapsActivity);

        mapManager.updateGPSLoc(location);
        if (FaveLocationManager.locList.size() > 0) {
            for ( int i = 0 ; i < FaveLocationManager.locList.size() ; i++ ) {
                double dist = gpsToLocDistance(mapManager,i);
                if (dist>ONE_TENTH_MILE) {
                    currOutside = true;
                } else {
                    currOutside = false;
                    currLoc = i;
                    break;
                }
            }
            boundaryCheck();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    private double gpsToLocDistance(MapManager mapManager, int i) {
        return SphericalUtil.computeDistanceBetween(FaveLocationManager.locList.get(i).getCoords(), mapManager.getGPSPos());
    }
    private void boundaryCheck() {
        if (currOutside && prevOutside) {
            Log.d("MAP","Still outside of ALL Locs");
        } else if (currOutside && !prevOutside) {
            if (currLoc == -1) {
                Log.d("MAP","Still outside of ALL Locs");
            } else {
                Log.d("MAP","DEPART NOTIF! Just left Loc: " + FaveLocationManager.locList.get(currLoc).getName());
                // TODO: VIBETONE_DEPART
            }
        } else if (!currOutside && prevOutside) {
            Log.d("MAP","ARRIVAL NOTIF! Inside NEW Loc: " + FaveLocationManager.locList.get(currLoc).getName());
            notifySOLoc(currLoc);
            // TODO: VIBETONE_ARRIVAL
        } else {
            if (currLoc == lastLoc) {
                Log.d("MAP","Still inside CurrLoc: " + FaveLocationManager.locList.get(currLoc).getName());
            } else {
                Log.d("MAP","ARRIVAL + DEPART NOTIF! Inside NEW Loc: " + FaveLocationManager.locList.get(currLoc).getName());
                //Departing lastLoc
                // TODO: VIBETONE_DEPART
            }
        }
        prevOutside = currOutside;
        lastLoc = currLoc;
    }

    private void notifySOLoc(int currLoc) {
        String str = FaveLocationManager.locList.get(currLoc).getName();
        SharedPreferences keyPreferences = PreferenceManager.getDefaultSharedPreferences(mapsActivity.getApplicationContext());
        String SOKey = keyPreferences.getString("SOREGID", null);
        String message = "l" + str;
        String[] params = {SOKey, message};
        sendNotificationJob job = new sendNotificationJob();

        job.execute(params);

        VibeToneFactory vibrate = new VibeToneFactory(mapsActivity);
        vibrate.vibrate();
    }
}
