package com.cse110.team36.coupletones;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by admin on 4/27/16.
 */
public class GpsUtility {

    GpsUtility() {
    }

    static final double EARTH_RADIUS = 6371009;

    /**
     * Returns the angle between two LatLngs, in radians. This is the same as the distance
     * on the unit sphere.
     */
    double computeAngleBetween(LatLng from, LatLng to) {
        return distanceRadians(Math.toRadians(from.latitude), Math.toRadians(from.longitude),
                Math.toRadians(to.latitude), Math.toRadians(to.longitude));
    }

    /**
     * Returns distance on the unit sphere; the arguments are in radians.
     */
    private double distanceRadians(double lat1, double lng1, double lat2, double lng2) {
        return arcHav(havDistance(lat1, lat2, lng1 - lng2));
    }

    public double computeDistanceBetween(LatLng from, LatLng to) {
        return computeAngleBetween(from, to) * EARTH_RADIUS;
    }

    /**
     * Wraps the given value into the inclusive-exclusive interval between min and max.
     *
     * @param n   The value to wrap.
     * @param min The minimum.
     * @param max The maximum.
     */
    double wrap(double n, double min, double max) {
        return (n >= min && n < max) ? n : (mod(n - min, max - min) + min);
    }

    /**
     * Returns the non-negative remainder of x / m.
     *
     * @param x The operand.
     * @param m The modulus.
     */
    double mod(double x, double m) {
        return ((x % m) + m) % m;
    }

    /**
     * Computes inverse haversine. Has good numerical stability around 0.
     * arcHav(x) == acos(1 - 2 * x) == 2 * asin(sqrt(x)).
     * The argument must be in [0, 1], and the result is positive.
     */
    double arcHav(double x) {
        return 2 * Math.asin(Math.sqrt(x));
    }

    /**
     * Returns hav() of distance from (lat1, lng1) to (lat2, lng2) on the unit sphere.
     */
    double havDistance(double lat1, double lat2, double dLng) {
        return hav(lat1 - lat2) + hav(dLng) * Math.cos(lat1) * Math.cos(lat2);
    }
    /**
     * Returns haversine(angle-in-radians).
     * hav(x) == (1 - cos(x)) / 2 == sin(x / 2)^2.
     */
    double hav(double x) {
        double sinHalf = Math.sin(x * 0.5);
        return sinHalf * sinHalf;
    }
}
