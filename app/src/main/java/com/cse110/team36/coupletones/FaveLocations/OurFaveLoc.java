package com.cse110.team36.coupletones.FaveLocations;

import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.FaveLocations.FaveLocation;
import com.cse110.team36.coupletones.FireBase.LocationFB;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by admin on 5/21/16.
 */
public class OurFaveLoc extends FaveLocation {
    public OurFaveLoc(String name, LatLng loc) {
        super(name, loc);
    }

    public OurFaveLoc(LocationFB locationFB) {
        super(locationFB.getName(), new LatLng(locationFB.getLat(), locationFB.getLong()));

    }
}
