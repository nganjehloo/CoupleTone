package com.cse110.team36.coupletones;

import android.media.Ringtone;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by admin on 5/21/16.
 */
public class SOFaveLoc extends FaveLocation {
    Ringtone sparkleTone;
    VibeToneFactory vibeTone;

    public SOFaveLoc(String name, LatLng loc,Ringtone sparkleTone, VibeToneFactory vibeTone) {
        super(name, loc);
        this.sparkleTone = sparkleTone;
        this.vibeTone = vibeTone;
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
