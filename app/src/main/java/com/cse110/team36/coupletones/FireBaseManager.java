package com.cse110.team36.coupletones;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.firebase.client.Firebase;

/**
 * Created by Duc Le on 5/21/2016.
 */
public class FireBaseManager{

    private SharedPreferences sharedPreferences;

    public FireBaseManager(SharedPreferences shared)
    {
        sharedPreferences = shared;
    }

    public void add(LocationFB data)
    {
        String MYFBID = sharedPreferences.getString("MYFBREGID", "null");
        Firebase myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/" + MYFBID);
        myFirebaseRef.child(data.getName()).setValue(data);
    }

    public void remove(LocationFB data)
    {
        String MYFBID = sharedPreferences.getString("MYFBREGID", "null");
        Firebase myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/" + MYFBID);
        myFirebaseRef.child(data.getName()).removeValue();
    }

    public void rename(LocationFB oldData, LocationFB newData)
    {
        remove(oldData);
        add(newData);

    }
}
