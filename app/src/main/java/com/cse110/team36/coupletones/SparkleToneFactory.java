package com.cse110.team36.coupletones;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Class to access a consistent vibrate function for feedback on long click and notification.
 */

public class SparkleToneFactory implements Constants{
    //int sparkleTones[] = new int[NUM_SPARKLE_TONES];
    int[] sounds = {R.raw.default_arrival, R.raw.default_arrival, R.raw.communication_channel,
                    R.raw.funkytown1, R.raw.okarin, R.raw.served, R.raw.solemn, R.raw.fart_sound,
                    R.raw.racecar};
    Activity activity;
    Context context;
    MediaPlayer p;

    public SparkleToneFactory(Activity activity, Context context) {
        this.context = context;
        this.activity = activity;

    }

    public void sparkle() {}

    public void sparkle(SparkleToneName name) {
        p = MediaPlayer.create(context, sounds[name.ordinal()]);
        p.start();
    }
}