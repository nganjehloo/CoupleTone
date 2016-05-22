package com.cse110.team36.coupletones;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by admin on 5/21/16.
 */
public class SOFaveLoc extends FaveLocation {
    Ringtone sparkleTone;
    Activity activity;

    Constants.VibeToneName vibeToneArrivalName;
    Constants.VibeToneName vibeToneDepartName;

    public SOFaveLoc(String name, LatLng loc,Ringtone sparkleTone, Constants.VibeToneName vibeToneName) {
        super(name, loc);
        this.activity = activity;
        //this.sparkleTone = RingtoneManager.getRingtone(activity,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
//        this.sparkleTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        this.vibeToneArrivalName = Constants.VibeToneName.DEFAULT_ARRIVAL;
        this.vibeToneDepartName = Constants.VibeToneName.DEFAULT_DEPART;
    }


    public void changeArrivalVibeTone(Constants.VibeToneName vibeToneArrivalName) {
        this.vibeToneArrivalName = vibeToneArrivalName;
    }

    public void changeDepartVibeTone(Constants.VibeToneName vibeToneDepartName) {
        this.vibeToneDepartName = vibeToneDepartName;
    }

    public void changeArrivalSparkleTone() {

    }

    public void changeDepartSparkleTones() {

    }

    public void setRingtone (Ringtone sparkleTone) {
        this.sparkleTone = sparkleTone;
    }

    public Ringtone getRingtone () {
        return sparkleTone;
    }

}
