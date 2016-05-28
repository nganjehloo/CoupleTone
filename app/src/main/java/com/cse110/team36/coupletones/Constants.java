package com.cse110.team36.coupletones;

import android.media.Ringtone;
import android.media.RingtoneManager;

/**
 * Created by stazia on 5/6/16.
 */
public interface Constants {
    final float ONE_TENTH_MILE = 160.934f;  // ONE TENTH OF A MILE (in meters)
    final int NUM_VIBE_TONES = VibeToneName.values().length;
    final int NUM_SPARKLE_TONES = SparkleToneName.values().length;

    public enum VibeToneName {
          //  0          1               2              3            4          5
            PING, DEFAULT_ARRIVAL, DEFAULT_DEPART, _5THSYMPHONY, PRESENTING, FUNKYTOWN,
          //    6         7         8        9       10
            SLOW2FAST,FAST2SLOW, MOUNTAIN, VALLEY, SWIRLS,
          //   11        12         13
            THE_NIMA, GANGITIS,  SURPRISE;
    }

    public enum SparkleToneName {
        //      0               1           2           3        4      5       6     7
        DEFAULT_ARRIVAL, DEFAULT_DEPART, COMM_LINE, FUNKYTOWN, OKARIN, BEEP, SOLEMN, FART, RACE_CAR
    }



}
