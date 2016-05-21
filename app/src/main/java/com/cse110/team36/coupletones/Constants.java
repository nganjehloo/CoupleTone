package com.cse110.team36.coupletones;

/**
 * Created by stazia on 5/6/16.
 */
public interface Constants {
    final float ONE_TENTH_MILE = 160.934f;  // ONE TENTH OF A MILE (in meters)
    final int NUM_VIBE_TONES = VibeToneName.values().length;

    public enum VibeToneName {
          //      0           1           2          3          4         5
            DEFAULT, _5THSYMPHONY, PRESENTING, FUNKYTOWN, SLOW2FAST, FAST2SLOW, MOUNTAIN,
          //      6           7           8          9         10
                VALLEY,     SWIRLS,    THE_NIMA,  GANGITIS,  SURPRISE }

}
