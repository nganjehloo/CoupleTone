package com.cse110.team36.coupletones.FireBase;

/**
 * Created by Duc Le on 5/21/2016.
 */
public class LocationFB {
    private String name;
    private Double Lat;
    private Double Long;
    private Boolean inLocation;

    public String getName() {return name;}

    public Double getLat() {return Lat;}

    public Double getLong() {return Long;}

    public Boolean getHere() {return inLocation;}

    public void setName(String name) {this.name = name;}

    public void setLat(Double lat) {this.Lat = lat;}

    public void setLong(Double Long) {this.Long = Long;}

    public void setHere (Boolean isHere) {this.inLocation = isHere;}

}
