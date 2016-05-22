package com.cse110.team36.coupletones.FireBase;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cse110.team36.coupletones.Managers.SOFaveLocManager;
import com.cse110.team36.coupletones.SOFaveLoc;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.cse110.team36.coupletones.FireBase.LocationFB;

public class FirebaseService extends Service {
    public FirebaseService() {
    }
    final class MyThread implements Runnable {
        int startId;
        Firebase myFirebaseRefReg;
        Firebase myFirebaseRefLoc;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        public MyThread(int startId) {
            this.startId = startId;
        }
        @Override
        public void run() {
            myFirebaseRefReg = new Firebase("https://coupletones36.firebaseio.com/" + sharedPreferences.getString("MYEMAIL", null) + "/REG");
            myFirebaseRefLoc = new Firebase("https://coupletones36.firebaseio.com/" + sharedPreferences.getString("MYEMAIL", null) + "/Locations");

            myFirebaseRefLoc.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //System.out.println("There are " + snapshot.getChildrenCount() + " locationFBs");
                    // loop through the snapshot's children
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        //grab the children
                        LocationFB locFB = postSnapshot.getValue(LocationFB.class);

                        //somehow put this into SOFaveLocManager, use specialconstructor
                        SOFaveLoc soFaveLoc = new SOFaveLoc(locFB);
                        SOFaveLocManager.addLocation(soFaveLoc);

                        //somehow stick this into manager
                    }
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });

            myFirebaseRefReg.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });

            // I don't know if I need this
            /*
            synchronized(this) {
                try{
                    wait(15000);
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
                stopSelf(startId);
            }
            */
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
