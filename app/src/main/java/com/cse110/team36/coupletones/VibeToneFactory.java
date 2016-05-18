package com.cse110.team36.coupletones;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

import com.cse110.team36.coupletones.Managers.FaveLocationManager;

/**
 * Class to access a consistent vibrate function for feedback on long click and notification.
 */

public class VibeToneFactory implements Constants{
    long vibeTones[][] = new long[NUM_VIBE_TONES][];
    Activity activity;
    Vibrator v;

    public VibeToneFactory(Activity activity) {
        this.activity = activity;
        v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        for (int i=0;i<NUM_VIBE_TONES;i++)
            vibeTones[i] = getVibeTone(i);

    }

    public void vibrate() {v.vibrate(100);}

    public void vibeTone(VibeToneName name) {v.vibrate(vibeTones[name.ordinal()],-1);}

    private long[] getVibeTone(int i) {
        switch(i) {
            case(0):
                // 0: _5THSYMPHONY
                return new long[] {150, 75, 150, 75, 150, 75, 150, 1000};
            case(1):
                // 1: PRESENTING
                return new long[] {100, 400, 100, 100, 100, 100, 100, 400, 100, 500, 250, 250, 100, 250};
            case(2):
                // 2: FUNKYTOWN
                return new long[] {100, 400, 100, 300, 100, 250, 150, 100, 150, 100, 150, 100, 300, 400, 100, 300, 100, 250};
            case(3):
                // 3: SLOW2FAST
                return new long[] {20, 20, 40, 40, 60, 60, 80, 80, 100, 100, 200, 200, 300, 300, 400, 400};
            case(4):
                // 4: FAST2SLOW
                return new long[] {400, 400, 300, 300, 200, 200, 100, 100, 80, 80, 60, 60, 40, 40, 20, 20};
            case(5):
                // 5: MOUNTAIN
                return new long[] {25, 25, 50, 50, 100, 100, 150, 150, 200, 200, 200, 200, 150, 150, 100, 100, 50, 50, 25, 25};
            case(6):
                // 6: VALLEY
                return new long[] {200, 200, 150, 150, 100, 100, 50, 50, 25, 25, 25, 25, 50, 50, 100, 100, 150, 150, 200, 200};
            case(7):
                // 7: TODO
                return new long[] {100, 100};
            case(8):
                // 8: TODO
                return new long[] {100, 100};
            case(9):
                // 9: TODO
                return new long[] {100, 100};
            default:
                // Just a bzzzt!!
                return new long[] {100, 100};
        }
    }
}