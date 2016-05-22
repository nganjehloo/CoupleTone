package com.cse110.team36.coupletones;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        Firebase myFirebaseRef;
        public MyThread(int startId) {
            this.startId = startId;
        }
        @Override
        public void run() {
            myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/debugList");

            myFirebaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //System.out.println("There are " + snapshot.getChildrenCount() + " locationFBs");
                    // loop through the snapshot's children
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        //grab the children
                        LocationFB locFB = postSnapshot.getValue(LocationFB.class);

                        //somehow put this into SOFaveLocManager, use specialconstructor
                        SOFaveLoc soFaveLoc = new SOFaveLoc(locFB);

                        //somehow stick this into manager
                    }
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
