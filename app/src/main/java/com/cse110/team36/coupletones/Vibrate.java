package com.cse110.team36.coupletones;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

/**
 * Class to access a consistent vibrate function for feedback on long click and notification.
 */
public class Vibrate  {
    Activity mapsActivity;
    Vibrator v;
    public Vibrate(Activity mapsActivity) {
        this.mapsActivity = mapsActivity;
        v = (Vibrator) mapsActivity.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void vibrate() {
        v.vibrate(100);
    }
}
