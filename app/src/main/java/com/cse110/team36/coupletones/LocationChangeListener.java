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
    Activity mapsActivity;
    FaveLocation currFavLoc = null;
    FaveLocation lastFavLoc = null;
    int currLoc = -1;
    int lastLoc = -1;
    boolean currOutside = false;
    boolean prevOutside = false;
    double dist = 0;

    public LocationChangeListener(Activity mapsActivity) {
        this.mapsActivity = mapsActivity;
    }

    public void onLocationChanged(Location location) {
        LocationManager locationManager = (LocationManager) mapsActivity.getSystemService(Context.LOCATION_SERVICE);
        MapManager mapManager = new MapManager(locationManager);

        mapManager.updateGPSLoc(location);
        if (FaveLocationManager.locList.size() > 0) {
            String string = null;
            for ( int i = 0 ; i < FaveLocationManager.locList.size() ; i++ ) {
                dist = SphericalUtil.computeDistanceBetween(FaveLocationManager.locList.get(i).getCoords(), mapManager.getGPSPos());
                if (dist>ONE_TENTH_MILE) {
                    currOutside = true;
                } else {
                    currOutside = false;
                    currLoc = i;
                    break;
                }
//                if ( dist <= ONE_TENTH_MILE ) {
//                    // I AM WITHIN ONE TENTH OF A MILE OF MY FAVORITE LOCATION!
////                    currFavLoc = new FaveLocation(FaveLocationManager.locList.get(i).getName(), FaveLocationManager.locList.get(i).getCoords() );
//                    currFavLoc = FaveLocationManager.locList.get(i);
//                    if ( lastFavLoc == null ) {
//                        // FIRST RUN, SET MY LAST LOCATION
//                        lastFavLoc = currFavLoc;
//                        string = "first time checked in!" + "\nLocName=" + FaveLocationManager.locList.get(i).getName() + "\nArraySize=" + FaveLocationManager.locList.size() + "\nx=" + FaveLocationManager.locList.get(i).getCoords().latitude + "\ny=" + FaveLocationManager.locList.get(i).getCoords().longitude;
//                        string = "first time checked in";
//                        lastLoc = lastFavLoc;
//                    } else if ( currFavLoc.getName().equals(lastFavLoc.getName()) ) {
//                        // I AM STILL IN THE LOCATION I WAS JUST IN, NO NOTIFY REQUIRED
//                        string = "Still @ Curr Fav Loc!" + "\nLocName=" + FaveLocationManager.locList.get(i).getName() + "\nArraySize=" + FaveLocationManager.locList.size() + "\nx=" + FaveLocationManager.locList.get(i).getCoords().latitude + "\ny=" + FaveLocationManager.locList.get(i).getCoords().longitude;
//                        string = "Still @ Curr Fav Loc!";
//                    } else {
//                        // I AM A NEW LOCATION, CHECK ME IN!!  NOTIFY MY SO!!
//                        notifySOLoc();
//                        string = "checked in!" + "\nLocName=" + FaveLocationManager.locList.get(i).getName() + "\nArraySize=" + FaveLocationManager.locList.size() + "\nx=" + FaveLocationManager.locList.get(i).getCoords().latitude + "\ny=" + FaveLocationManager.locList.get(i).getCoords().longitude;
//                        string = "checked in new loc found!";
//                        lastLoc = lastFavLoc;
//                    }
//                    lastFavLoc = currFavLoc;
//
//                    string = "" + i + ": " + string;
//                    Log.d("MAP",string);
//                    break;
//                } else {
//
////                    currFavLoc = new FaveLocation("outside",mapManager.getGPSPos());
//                    // I am in no favorite location - NO NOTIFY NEEDED
//                    if (lastLoc != null/* && lastFavLoc == lastLoc */) {
////                        lastLoc = currFavLoc;
//                        string = "JUST CHECKED OUT OF A FAV LOC";
//                        lastLoc = null;
//                        string = "" + i + ": " + string;
//                        Log.d("MAP",string);
//                        break;
////                        break;
//                    } else {
//                        string = "NOT checked in!" + "\nLocName=" + FaveLocationManager.locList.get(i).getName() + "\nArraySize=" + FaveLocationManager.locList.size() + "\nx=" + FaveLocationManager.locList.get(i).getCoords().latitude + "\ny=" + FaveLocationManager.locList.get(i).getCoords().longitude;
//                        string = "NOT checked in! for awhile";
//                        string = "" + i + ": " + string;
//                        Log.d("MAP",string);
//                    }
//                    //Toast.makeText(getBaseContext(), string, Toast.LENGTH_SHORT).show(); //?
//
//                }
            }
//            Log.d("MAP",string);
            if (currOutside && prevOutside) {
                Log.d("MAP","Still outside of ALL Locs");
            } else if (currOutside && !prevOutside) {
                if (currLoc == -1) {
                    Log.d("MAP","Still outside of ALL Locs");
                } else {
                    Log.d("MAP","DEPART NOTIF! Just left Loc: " + FaveLocationManager.locList.get(currLoc).getName());
                }
            } else if (!currOutside && prevOutside) {
                Log.d("MAP","ARRIVAL NOTIF! Inside NEW Loc: " + FaveLocationManager.locList.get(currLoc).getName());
            } else {
                if (currLoc == lastLoc) {
                    Log.d("MAP","Still inside CurrLoc: " + FaveLocationManager.locList.get(currLoc).getName());
                } else {
                    Log.d("MAP","ARRIVAL + DEPART NOTIF! Inside NEW Loc: " + FaveLocationManager.locList.get(currLoc).getName());
                    //Departing lastLoc
                }
            }
            prevOutside = currOutside;
            lastLoc = currLoc;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    void notifySOLoc() {
        String str = currFavLoc.getName();
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
