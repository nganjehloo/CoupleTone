package com.cse110.team36.coupletones;

import android.content.Context;
import android.widget.Toast;

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

    public static void addLocation(String name, LatLng loc) {
        FaveLocation newLoc = new FaveLocation(name, loc);
        locList.add(newLoc);
//        Toast.makeText(context,
//                "" + newLoc.getName() + "\n" + String.valueOf(newLoc.getLat()) + ", " + String.valueOf(newLoc.getLng()),
//                Toast.LENGTH_SHORT).show();
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

    public static void emptyLocs() {
        locList.clear();
    }

    public static ArrayList<FaveLocation> getLocList() {
        return locList;
    }
}
