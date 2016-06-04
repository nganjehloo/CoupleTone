package com.cse110.team36.coupletones;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Class to access a consistent vibrate function for feedback on long click and notification.
 */

public class SparkleToneFactory implements Constants{
    int[] sounds = {R.raw.ping, R.raw.ding, R.raw.pacman, R.raw.communication_channel,
                    R.raw.funkytown1, R.raw.okarin, R.raw.solemn, R.raw.fart_sound,
                    R.raw.racecar, R.raw.jingle, R.raw.kimmunicator, R.raw.default_depart};
    MediaPlayer p = new MediaPlayer();
    private static boolean sparkEnable = true;
    private boolean created = false;

    public SparkleToneFactory(){}

    public static void setSparkEnable() {
        sparkEnable = !sparkEnable;
        String str = "  sparkleEnable = ";
        str += Boolean.toString(sparkEnable);
        Log.i("MAP",str);
    }

    public boolean getSparkEnable() {
        return sparkEnable;
    }

    public void sparkle(SparkleToneName name, Context myContext) {
        if (getSparkEnable()) {
            created = true;
            p = MediaPlayer.create(myContext, sounds[name.ordinal()]);
            p.start();
        }
    }

    public boolean isCreated() {
        return created;
    }

    public boolean isPlaying() {
        return p.isPlaying();
    }

    public void pause() {
        p.pause();
    }

    public void destroy() {
        p.release();
    }
}