package com.cse110.team36.coupletones;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

import com.cse110.team36.coupletones.Managers.FaveLocationManager;

/**
 * Class to access a consistent vibrate function for feedback on long click and notification.
 */

public class VibeToneFactory implements Constants{
    long vibeTones[][];
    Activity activity;
    Vibrator v;
//    VibeToneName L = VibeToneName.SWIRLS;

    public VibeToneFactory(Activity activity) {
        this.activity = activity;
        v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        for (int i=0;i<=NUM_VIBE_TONES;i++) {

        }
    }

    public void vibrate() {v.vibrate(100);}

    public void vibeTone(VibeToneName name) {
        v.vibrate(vibeTones[name.ordinal()],-1);
    }
}