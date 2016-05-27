package com.cse110.team36.coupletones.FireBase;

import android.location.Location;

/**
 * Created by Duc Le on 5/21/2016.
 */
public class LocationFB {
    private String name;
    private double Lat;
    private double Long;
    private String inLocation;
    private int sound;
    private int vibration;

    public LocationFB()
    {
        inLocation = "N/A";
        sound = 1;
        vibration = 1;
    }

    public String getName() {return name;}

    public double getLat() {return Lat;}

    public double getLong() {return Long;}

    public String getHere() {return inLocation;}

    public int getSound() {return sound;}

    public int getVibration() {return vibration;}

    public void setName(String name) {this.name = name;}

    public void setLat(Double lat) {this.Lat = lat;}

    public void setLong(Double Long) {this.Long = Long;}

    public void setHere (String isHere) {this.inLocation = isHere;}

    public void setSound (int mySound) {this.sound = mySound;}

    public void setVibration (int myVibration) {this.vibration = myVibration;}

}
