package com.cse110.team36.coupletones;

import com.google.android.gms.maps.model.LatLng;

//import java.sql.Time;
import java.util.Calendar;

/**
 * Created by Andrew on 5/25/2016.
 */

public class VisitedLoc extends FaveLocation {
    private Calendar cal;
    private boolean arriving;

    public VisitedLoc(String name, LatLng coords, boolean b) {
        this.name = name;
        this.lat = coords.latitude;
        this.lon = coords.longitude;
        this.cal = Calendar.getInstance();
        arriving = b;
    }

    public Calendar getCal() {
        return this.cal;
    }


}
