package com.cse110.team36.coupletones;

import com.google.android.gms.maps.model.LatLng;

/**
 *
 */
public class FaveLocation {
    private double lat;
    private double lon;
    private String name;

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

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }
}
