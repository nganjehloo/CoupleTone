package com.cse110.team36.coupletones.Managers;

import android.content.Context;

import com.cse110.team36.coupletones.FaveLocations.FaveLocation;
import com.cse110.team36.coupletones.FaveLocations.OurFaveLoc;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

/**
 * Created by stazia on 5/5/16.
 */
public class FaveLocationManager {
    public static ArrayList<OurFaveLoc> locList = new ArrayList<OurFaveLoc>();
    static Context context;

    public FaveLocationManager(Context context) {
        this.context = context;
    }

    // for testing purposes only. Not for release

    public static boolean addLocation(String name, LatLng loc) {
        OurFaveLoc newLoc = new OurFaveLoc(name, loc);
        locList.add(newLoc);
        return true;
    }

    public static boolean addLocation(OurFaveLoc ourFaveLoc) {
        OurFaveLoc newLoc = ourFaveLoc;
        locList.add(newLoc);
        return true;
    }

    public static boolean removeLocation(String name) {
        for (int i = 0; i < locList.size(); i++) {
            OurFaveLoc faveLocation = locList.get(i);
            if (faveLocation.getName().equals(name)) {
                locList.remove(i);
                return true;
            }
        }
        return false;
    }

    public static void emptyLocs() {
        locList.clear();
    }
}
