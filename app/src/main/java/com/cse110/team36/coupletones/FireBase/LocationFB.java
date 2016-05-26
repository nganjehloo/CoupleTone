package com.cse110.team36.coupletones.FireBase;

/**
 * Created by Duc Le on 5/21/2016.
 */
public class LocationFB {
    private String name;
    private double Lat;
    private double Long;
    private String inLocation;

    public String getName() {return name;}

    public double getLat() {return Lat;}

    public double getLong() {return Long;}

    public String getHere() {return inLocation;}

    public void setName(String name) {this.name = name;}

    public void setLat(Double lat) {this.Lat = lat;}

    public void setLong(Double Long) {this.Long = Long;}

    public void setHere (String isHere) {this.inLocation = isHere;}

}
