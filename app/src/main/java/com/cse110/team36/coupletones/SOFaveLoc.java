package com.cse110.team36.coupletones;

import android.media.Ringtone;

/**
 * Created by admin on 5/21/16.
 */
public class SOFaveLoc extends FaveLocation {
    Ringtone sparkleTone;
    VibeToneFactory vibeTone;

    public SOFaveLoc(Ringtone sparkleTone, VibeToneFactory vibeTone) {
        super();
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
