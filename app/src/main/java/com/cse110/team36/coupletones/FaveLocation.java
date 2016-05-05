package com.cse110.team36.coupletones;

import com.google.android.gms.maps.model.LatLng;

/**
 *
 */
public class FaveLocation {
    private LatLng coords;
    private String name;

    public FaveLocation(String name, LatLng coords) {
        this.coords = coords;
        this.name = name;
    }

    public LatLng getCoords() {
        return coords;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }
}
