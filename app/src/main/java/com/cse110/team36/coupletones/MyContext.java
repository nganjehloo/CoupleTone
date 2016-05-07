package com.cse110.team36.coupletones;

import android.app.Application;
import android.content.Context;

/**
 * Created by Duc Le on 5/7/2016.
 */
public class MyContext extends Application {
    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }
}
