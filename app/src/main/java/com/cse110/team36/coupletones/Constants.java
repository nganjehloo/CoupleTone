package com.cse110.team36.coupletones;

import android.media.Ringtone;
import android.media.RingtoneManager;

/**
 * Created by stazia on 5/6/16.
 */
public interface Constants {
    float ONE_TENTH_MILE = 160.934f;  // ONE TENTH OF A MILE (in meters)
    int NUM_VIBE_TONES = VibeToneName.values().length;
    int NUM_SPARKLE_TONES = SparkleToneName.values().length;

    enum VibeToneName {
          //  0          1               2              3            4          5
            PING, DEFAULT_ARRIVAL, DEFAULT_DEPART, _5THSYMPHONY, PRESENTING, FUNKYTOWN,
          //    6         7         8        9       10
            SLOW2FAST,FAST2SLOW, MOUNTAIN, VALLEY, SWIRLS,
          //   11        12         13
            THE_NIMA, GANGITIS,  SURPRISE;
    }

    enum SparkleToneName {
        //0          1                  2           3        4         5       6     7
        PING, ARRIVAL, DEPART, COMM_LINE, FUNKYTOWN, OKARIN, SOLEMN, FART,
        //  8       9           10          11
         RACE_CAR, JINGLE, KIMMUNICATOR, GUITAR
    }



}
