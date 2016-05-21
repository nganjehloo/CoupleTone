package com.cse110.team36.coupletones.FireBase;

/**
 * Created by Duc Le on 5/21/2016.
 */
public class LocationFB {
    String name;
    Double Lat;
    Double Long;
    Boolean inLocation;

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public void setLat(Double lat) {this.Lat = lat;}

    public void setLong(Double Long) {this.Long = Long;}

    public void setHere (Boolean isHere) {this.inLocation = isHere;}

}
