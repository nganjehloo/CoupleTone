package com.cse110.team36.coupletones;

import android.content.Context;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

/**
 * Created by stazia on 5/5/16.
 */
public class FaveLocationManager {
    public static ArrayList<FaveLocation> locList = new ArrayList<FaveLocation>();
    static Context context;

    public FaveLocationManager(Context context) {
        this.context = context;
    }

    public static boolean addLocation(String name, LatLng loc) {
        FaveLocation newLoc = new FaveLocation(name, loc);
        int i;
        for (i = 0; i < locList.size(); i++) {
            if (locList.get(i).getName().equals(name)) {
                return false;
            }
        }
        locList.add(newLoc);
        return true;
    }

    public static boolean removeLocation(String name) {
        for (int i = 0; i < locList.size(); i++) {
            FaveLocation faveLocation = locList.get(i);
            if (faveLocation.getName().equals(name)) {
                locList.remove(i);
                return true;
            }
        }
        return false;
    }
}
