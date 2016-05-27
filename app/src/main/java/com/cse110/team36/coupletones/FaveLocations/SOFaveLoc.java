package com.cse110.team36.coupletones.FaveLocations;

import android.app.Activity;
import android.media.Ringtone;

import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.FaveLocations.FaveLocation;
import com.cse110.team36.coupletones.FireBase.LocationFB;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by admin on 5/21/16.
 */
public class SOFaveLoc extends FaveLocation {
    Constants.VibeToneName vibeToneArrivalName;
    Constants.VibeToneName vibeToneDepartName;

    Constants.SparkleToneName sparkleToneArrivalName;
    Constants.SparkleToneName sparkleToneDepartName;

    public SOFaveLoc(String name, LatLng loc,Ringtone sparkleTone, Constants.VibeToneName vibeToneName) {
        super(name, loc);
        //this.sparkleTone = RingtoneManager.getRingtone(activity,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
//        this.sparkleTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        this.vibeToneArrivalName = Constants.VibeToneName.DEFAULT_ARRIVAL;
        this.vibeToneDepartName = Constants.VibeToneName.DEFAULT_DEPART;
        this.sparkleToneArrivalName = Constants.SparkleToneName.DEFAULT_ARRIVAL;
        this.sparkleToneDepartName = Constants.SparkleToneName.DEFAULT_DEPART;
    }

    // constructor for converting a LocationFB to a SOFaveLoc
    public SOFaveLoc(LocationFB locationFB) {
        super(locationFB.getName(), new LatLng(locationFB.getLat(), locationFB.getLong()));
        this.vibeToneArrivalName = Constants.VibeToneName.DEFAULT_ARRIVAL;
        this.vibeToneDepartName = Constants.VibeToneName.DEFAULT_DEPART;
        this.sparkleToneArrivalName = Constants.SparkleToneName.DEFAULT_ARRIVAL;
        this.sparkleToneDepartName = Constants.SparkleToneName.DEFAULT_DEPART;
    }

    public void changeArrivalVibeTone(Constants.VibeToneName vibeToneArrivalName) {
        this.vibeToneArrivalName = vibeToneArrivalName;
    }

    public void changeDepartVibeTone(Constants.VibeToneName vibeToneDepartName) {
        this.vibeToneDepartName = vibeToneDepartName;
    }

    public void changeArrivalSparkleTone(Constants.SparkleToneName sparkleToneArrivalName) {
        this.sparkleToneArrivalName = sparkleToneArrivalName;
    }

    public void changeDepartSparkleTone(Constants.SparkleToneName sparkleToneDepartName) {
        this.sparkleToneDepartName = sparkleToneDepartName;
    }
}
