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
    private long epoch;
    private int arrivalSound;
    private int arrivalVibration;
    private int departureSound;
    private int departureVibration;

    public LocationFB()
    {
        epoch = System.currentTimeMillis()/1000;
        inLocation = "N/A";
        arrivalSound = 0;
        arrivalVibration = 0;
        departureSound = 1;
        departureVibration = 1;
    }

    public String getName() {return name;}

    public double getLat() {return Lat;}

    public double getLong() {return Long;}

    public String getHere() {return inLocation;}

    public long getEpoch() {return epoch;}

    public String getTime() { return new java.text.SimpleDateFormat("h:mm a").format(new java.util.Date (epoch*1000));}

    public int getArrivalSound() {return arrivalSound;}

    public int getArrivalVibration() {return arrivalVibration;}

    public int getDepartureSound() {return departureSound;}

    public int getDepartureVibration() {return departureVibration;}

    public void setName(String name) {this.name = name;}

    public void setLat(Double lat) {this.Lat = lat;}

    public void setLong(Double Long) {this.Long = Long;}

    public void setHere (String isHere) {this.inLocation = isHere;}

    public void setArrivalSound (int mySound) {this.arrivalSound = mySound;}

    public void setArrivalVibration (int myVibration) {this.arrivalVibration = myVibration;}

    public void setDepartureSound (int mySound) {this.departureSound = mySound;}

    public void setDepartureVibration (int myVibration) {this.departureVibration = myVibration;}


}
