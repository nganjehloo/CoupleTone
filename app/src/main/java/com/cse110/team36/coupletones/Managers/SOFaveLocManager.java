package com.cse110.team36.coupletones.Managers;

import android.content.Context;
import android.media.Ringtone;

import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.FaveLocation;
import com.cse110.team36.coupletones.OurFaveLoc;
import com.cse110.team36.coupletones.SOFaveLoc;
import com.cse110.team36.coupletones.VibeToneFactory;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

/**
 * Created by stazia on 5/5/16.
 */
public class SOFaveLocManager {
    public static ArrayList<FaveLocation> locList = new ArrayList<FaveLocation>();
    static Context context;

    public SOFaveLocManager(Context context) {
        this.context = context;
    }

    // for testing purposes only. Not for release

    public static boolean addLocation(String name, LatLng loc, Ringtone sparkleTone, Constants.VibeToneName vibeTone) {
//        FaveLocation newLoc = new SOFaveLoc(name, loc, sparkleTone, vibeTone);
//        locList.add(newLoc);
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
