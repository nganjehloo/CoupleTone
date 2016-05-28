package com.cse110.team36.coupletones.Managers;

import android.content.Context;

import com.cse110.team36.coupletones.VisitedLoc;

import java.util.ArrayList;

/**
 * Created by Andrew on 5/25/2016.
 */
public class VisitedLocManager {
    public static ArrayList<VisitedLoc> visitedLocs = new ArrayList<>();
    static Context context;

    public VisitedLocManager(Context c) {
        context = c;
    }

    public static boolean addLocation(VisitedLoc v) {
        return visitedLocs.add(v);
    }

    public static void clear() {
        visitedLocs.clear();
    }
}
