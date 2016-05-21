package com.cse110.team36.coupletones;

import android.app.Notification;
import android.media.Ringtone;
import android.media.RingtoneManager;

import com.google.android.gms.maps.model.LatLng;

public class FaveLocation {
    private double lat;
    private double lon;
    private String name;
    private Ringtone ringtone;

    // for testing purposes only. Do not use for release
    public FaveLocation(String name, LatLng coords, Ringtone ringtone) {
        this.lat = coords.latitude;
        this.lon = coords.longitude;
        this.name = name;
        this.ringtone = ringtone;
    }

    public FaveLocation(String name, LatLng coords) {
        this.lat = coords.latitude;
        this.lon = coords.longitude;
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lon;
    }

    public LatLng getCoords() {
        return new LatLng(lat, lon);
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public void setRingtone (Ringtone ringtone) {
        this.ringtone = ringtone;
    }

    public Ringtone getRingtone () {
        return ringtone;
    }
}
