package com.cse110.team36.coupletones;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

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
                if ( dist <= ONE_TENTH_MILE ) {
                    // I AM WITHIN ONE TENTH OF A MILE OF MY FAVORITE LOCATION!
                    currFavLoc = new FaveLocation(FaveLocationManager.locList.get(i).getName(), FaveLocationManager.locList.get(i).getCoords() );
                    string = "checked in!" + "\nLocName=" + FaveLocationManager.locList.get(i).getName() + "\nArraySize=" + FaveLocationManager.locList.size() + "\nx=" + FaveLocationManager.locList.get(i).getCoords().latitude + "\ny=" + FaveLocationManager.locList.get(i).getCoords().longitude;
                    if ( lastFavLoc == null ) {
                        // FIRST RUN, SET MY LAST LOCATION
                        lastFavLoc = currFavLoc;
                    } else if ( currFavLoc.getName().equals(lastFavLoc.getName()) ) {
                        // I AM STILL IN THE LOCATION I WAS JUST IN, NO NOTIFY REQUIRED
                        string = "Still @ Curr Fav Loc!" + "\nLocName=" + FaveLocationManager.locList.get(i).getName() + "\nArraySize=" + FaveLocationManager.locList.size() + "\nx=" + FaveLocationManager.locList.get(i).getCoords().latitude + "\ny=" + FaveLocationManager.locList.get(i).getCoords().longitude;
                    } else {
                        // I AM A NEW LOCATION, CHECK ME IN!!  NOTIFY MY SO!!
                        notifySOLoc();
                        string = "checked in!" + "\nLocName=" + FaveLocationManager.locList.get(i).getName() + "\nArraySize=" + FaveLocationManager.locList.size() + "\nx=" + FaveLocationManager.locList.get(i).getCoords().latitude + "\ny=" + FaveLocationManager.locList.get(i).getCoords().longitude;

                    }
                    lastFavLoc = currFavLoc;
                    break;
                } else {
                    // I am in no favorite location - NO NOTIFY NEEDED
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

    void notifySOLoc() {
        String str = currFavLoc.getName();
        SharedPreferences keyPreferences = PreferenceManager.getDefaultSharedPreferences(mapsActivity.getApplicationContext());
        String SOKey = keyPreferences.getString("SOREGID", null);
        String message = "l" + str;
        String[] params = {SOKey, message};
        sendNotificationJob job = new sendNotificationJob();

        job.execute(params);

        Vibrate vibrate = new Vibrate(mapsActivity);
        vibrate.vibrate();
    }
}
