package com.cse110.team36.coupletones.Managers;

import android.content.Context;

import com.cse110.team36.coupletones.FaveLocations.FaveLocation;
import com.cse110.team36.coupletones.FaveLocations.SOFaveLoc;

import java.util.ArrayList;

/**
 * Created by stazia on 5/5/16.
 */
public class SOFaveLocManager {
    public static ArrayList<SOFaveLoc> locList = new ArrayList<SOFaveLoc>();
    static Context context;

    public SOFaveLocManager(Context context) {
        this.context = context;
    }

    // for testing purposes only. Not for release

    public static boolean addLocation(SOFaveLoc soFaveLoc) {
        locList.add(soFaveLoc);
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

    public static void emptyLocs() {
        locList.clear();
    }
}
