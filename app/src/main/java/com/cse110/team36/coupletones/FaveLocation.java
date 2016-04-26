package com.cse110.team36.coupletones;

import com.google.android.gms.maps.model.LatLng;

/**
 *
 */
public class FaveLocation {
    private LatLng coords;
    private String name;

    FaveLocation(LatLng coords, String name) {
        this.coords = coords;
        this.name = name;
    }

    LatLng getCoords() {
        return coords;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}
