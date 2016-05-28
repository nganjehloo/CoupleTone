package com.cse110.team36.coupletones.FireBase;

import com.firebase.client.Firebase;

/**
 * Created by Duc Le on 5/20/2016.
 */
public class FirebaseContext extends android.app.Application{
    public void onCreate()
    {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
