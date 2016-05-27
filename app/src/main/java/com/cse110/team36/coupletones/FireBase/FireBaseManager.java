package com.cse110.team36.coupletones.FireBase;

import android.content.SharedPreferences;

import com.cse110.team36.coupletones.FaveLocations.FaveLocation;
import com.cse110.team36.coupletones.FaveLocations.OurFaveLoc;
import com.cse110.team36.coupletones.FaveLocations.SOFaveLoc;
import com.cse110.team36.coupletones.Managers.FaveLocationManager;
import com.cse110.team36.coupletones.Managers.SOFaveLocManager;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

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
        String emailid = email.replace(".com","");
        sharedPreferences.edit().putString("MYEMAIL",emailid ).apply();
        Firebase myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/" + emailid);
        FBreg fBreg = new FBreg();
        myFirebaseRef.child("REG").setValue(fBreg);
    }

    public void addSO(String email){
        String emailid = email.replace(".com","");
        sharedPreferences.edit().putString("SOEMAIL", emailid).apply();
        Firebase myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/" + sharedPreferences.getString("MYEMAIL", null));
        Firebase soFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/" + emailid);
        FBreg fBreg = new FBreg();
        fBreg.setID(emailid);
        fBreg.setStatus(true);
        myFirebaseRef.child("REG").setValue(fBreg);

        fBreg.setID(sharedPreferences.getString("MYEMAIL", null));
        soFirebaseRef.child("REG").setValue(fBreg);
    }

    public void removeSO(){
        Firebase myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/" + sharedPreferences.getString("MYEMAIL", null));
        Firebase soFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/" + sharedPreferences.getString("SOEMAIL", null));
        FBreg fBreg = new FBreg();
        fBreg.setID("NOID");
        fBreg.setStatus(false);

        myFirebaseRef.child("REG").setValue(fBreg);
        soFirebaseRef.child("REG").setValue(fBreg);

        sharedPreferences.edit().remove("SOEMAIL").apply();

    }

    public void add(LocationFB data)
    {
        Firebase myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/" + sharedPreferences.getString("MYEMAIL", null) + "/Locations");
        myFirebaseRef.child(data.getName()).setValue(data);
    }

    public void remove(LocationFB data)
    {
        Firebase myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/" + sharedPreferences.getString("MYEMAIL", null) + "/Locations");
        myFirebaseRef.child(data.getName()).removeValue();
    }

    public void loadMyLocs(){
        Firebase myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/" + sharedPreferences.getString("MYEMAIL", null) + "/Locations");
        myFirebaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FaveLocationManager.emptyLocs();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    LocationFB locFB = child.getValue(LocationFB.class);
                    OurFaveLoc ourFaveLoc = new OurFaveLoc(locFB);
                    FaveLocationManager.addLocation(ourFaveLoc);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void rename(LocationFB oldData, LocationFB newData)
    {
        remove(oldData);
        add(newData);
    }

}
