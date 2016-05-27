package com.cse110.team36.coupletones;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;

/**
 * Class to access a consistent vibrate function for feedback on long click and notification.
 */

public class SparkleToneFactory implements Constants{
    //int sparkleTones[] = new int[NUM_SPARKLE_TONES];
    int[] sounds = {R.raw.default_arrival, R.raw.default_arrival, R.raw.communication_channel,
                    R.raw.funkytown1, R.raw.okarin, R.raw.served, R.raw.solemn};
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

    private long[] getSparkleTone(int i) {
        switch(i) {
            case(0):
                // 0: DEFAULT_ARRIVAL
                return new long[] {500, 500};
            case(1):
                // 1: DEFAULT_DEPART
                return new long[] {200, 200, 200, 200};
            case(2):
                // 2: _5THSYMPHONY
                return new long[] {150, 75, 150, 75, 150, 75, 150, 1000};
            case(3):
                // 3: PRESENTING
                return new long[] {100, 400, 100, 100, 100, 100, 100, 400, 100, 500, 250, 250, 100, 250};
            case(4):
                // 4: FUNKYTOWN
                return new long[] {100, 400, 100, 300, 100, 250, 150, 100, 150, 100, 150, 100, 300, 400, 100, 300, 100, 250};
            case(5):
                // 5: SLOW2FAST
                return new long[] {20, 20, 40, 40, 60, 60, 80, 80, 100, 100, 200, 200, 300, 300, 400, 400};
            case(6):
                // 6: FAST2SLOW
                return new long[] {400, 400, 300, 300, 200, 200, 100, 100, 80, 80, 60, 60, 40, 40, 20, 20};
            case(7):
                // 7: MOUNTAIN
                return new long[] {25, 25, 50, 50, 100, 100, 150, 150, 200, 200, 200, 200, 150, 150, 100, 100, 50, 50, 25, 25};
            case(8):
                // 8: VALLEY
                return new long[] {200, 200, 150, 150, 100, 100, 50, 50, 25, 25, 25, 25, 50, 50, 100, 100, 150, 150, 200, 200};
            case(9):
                // 9: TODO
                return new long[] {100, 100};
            case(10):
                // 10: TODO
                return new long[] {100, 100};
            case(11):
                // 11: TODO
                return new long[] {100, 100};
            default:
                // Just a bzzzt!!
                return new long[] {100, 100};
        }
    }
}