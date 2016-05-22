package com.cse110.team36.coupletones.FireBase;

import android.content.SharedPreferences;

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

    public void createAccount(String email){
        String emailid = email.substring(0, email.length() - 4);
        sharedPreferences.edit().putString("MYEMAIL",emailid );
        Firebase myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/" + emailid);
        FBreg fBreg = new FBreg();
        myFirebaseRef.child("REG").setValue(fBreg);
    }

    public void add(LocationFB data)
    {
        String MYFBID = sharedPreferences.getString("MYFBREGID", "null");
        Firebase myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/" + MYFBID + "/Locations");
        myFirebaseRef.child(data.getName()).setValue(data);
    }

    public void remove(LocationFB data)
    {
        String MYFBID = sharedPreferences.getString("MYFBREGID", "null");
        Firebase myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/" + MYFBID + "/Locations");
        myFirebaseRef.child(data.getName()).removeValue();
    }

    public void rename(LocationFB oldData, LocationFB newData)
    {
        remove(oldData);
        add(newData);
    }

    public void regKey(FBreg regInfo)
    {

    }
}
