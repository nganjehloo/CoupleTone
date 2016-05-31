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
    int[] sounds = {R.raw.ping, R.raw.ding, R.raw.default_depart, R.raw.communication_channel,
                    R.raw.funkytown1, R.raw.okarin, R.raw.solemn, R.raw.fart_sound,
                    R.raw.racecar, R.raw.jingle, R.raw.kimmunicator};
    MediaPlayer p;
    private static boolean sparkEnable = true;

    public SparkleToneFactory(){}

    public static void setSparkEnable() {
        sparkEnable = !sparkEnable;
    }

    private static boolean getSparkEnable() {
        return sparkEnable;
    }

    public void sparkle(SparkleToneName name, Context myContext) {
        if (getSparkEnable()) {
            p = MediaPlayer.create(myContext, sounds[name.ordinal()]);
            p.start();
        }
    }

    public boolean isPlaying() {
        return p.isPlaying();
    }
}