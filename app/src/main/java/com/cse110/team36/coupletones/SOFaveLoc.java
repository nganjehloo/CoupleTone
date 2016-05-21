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
    Constants.VibeToneName vibeToneName;
    Activity activity;

    public SOFaveLoc(String name, LatLng loc,Ringtone sparkleTone, Constants.VibeToneName vibeToneName, Activity activity) {
        super(name, loc);
        this.activity = activity;
        this.sparkleTone = RingtoneManager.getRingtone(activity,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        this.vibeToneName = Constants.VibeToneName.DEFAULT;
    }


    public void addArrivalVibeTone() {

    }

    public void addDepartVibeTone() {

    }

    public void addArrivalSparkleTone() {

    }

    public void addDepartSparkleTones() {

    }

    public void setRingtone (Ringtone sparkleTone) {
        this.sparkleTone = sparkleTone;
    }

    public Ringtone getRingtone () {
        return sparkleTone;
    }

}
