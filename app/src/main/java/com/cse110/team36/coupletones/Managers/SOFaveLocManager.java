package com.cse110.team36.coupletones.Managers;

import android.content.Context;

import com.cse110.team36.coupletones.FaveLocations.FaveLocation;
import com.cse110.team36.coupletones.FaveLocations.SOFaveLoc;
import com.cse110.team36.coupletones.FireBase.LocationFB;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by stazia on 5/5/16.
 */
public class SOFaveLocManager {

    public static Comparator<LocationFB> comparator = new TimeComparator();
    static public class TimeComparator implements Comparator<LocationFB>{
        @Override
        public int compare(LocationFB locationFB1, LocationFB locationFB2){
            if(locationFB1.getEpoch() < locationFB2.getEpoch()){
                return 1;
            }
            if(locationFB1.getEpoch() > locationFB2.getEpoch()){
                return -1;
            }
            return 0;
        }
    }
    public static ArrayList<SOFaveLoc> locList = new ArrayList<SOFaveLoc>();
    public static ArrayList<LocationFB> visList = new ArrayList<LocationFB>() ;
    static Context context;

    public SOFaveLocManager(Context context) {
        this.context = context;
    }

    // for testing purposes only. Not for release

    public static boolean addLocation(SOFaveLoc soFaveLoc) {
        locList.add(soFaveLoc);
        return true;
    }

    public static boolean addLocation(LocationFB soVisitedeLoc) {
        visList.add(soVisitedeLoc);
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

    public  static void emptyVis(){
        visList.clear();
    }
}
