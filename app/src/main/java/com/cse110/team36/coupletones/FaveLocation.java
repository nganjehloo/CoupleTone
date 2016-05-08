/*
 * Class name: FaveLocation.java
 * Purpose: This class is used in FaveLocationManager and the rest of the program to represent
 * a SO's favorite location, which consists of a name and a latitude and longitude.
 */
package com.cse110.team36.coupletones;

import com.google.android.gms.maps.model.LatLng;

public class FaveLocation {
    // data member variables
    private double lat;
    private double lon;
    private String name;

    // constructor
    public FaveLocation(String name, LatLng coords) {
        this.lat = coords.latitude;
        this.lon = coords.longitude;
        this.name = name;
    }

    // accessors
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

    // mutators
    protected void setName(String name) {
        this.name = name;
    }
}
