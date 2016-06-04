package com.cse110.team36.coupletones.FireBase;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import java.lang.Thread;

import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.Managers.SOFaveLocManager;
import com.cse110.team36.coupletones.SparkleToneFactory;
import com.cse110.team36.coupletones.VibeToneFactory;
import com.cse110.team36.coupletones.maps.MapsActivity;
import com.cse110.team36.coupletones.R;
import com.cse110.team36.coupletones.FaveLocations.SOFaveLoc;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class FirebaseService extends Service {
    Service service = this;
    public FirebaseService() {
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Thread myThread = new Thread(new MyThread(0));
        myThread.start();
    }

    final class MyThread implements Runnable {
        int startId;
        Firebase myFirebaseRefReg;
        Firebase soFirebaseRefLoc;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SparkleToneFactory soundFactory;

        public MyThread(int startId) {
            this.startId = startId;
        }
        @Override
        public void run() {
            myFirebaseRefReg = new Firebase("https://coupletones36.firebaseio.com/" + sharedPreferences.getString("MYEMAIL", null) + "/REG");
            soFirebaseRefLoc = new Firebase("https://coupletones36.firebaseio.com/" + sharedPreferences.getString("SOEMAIL", null) + "/Locations");
            //SO LOCATION FIREBASE
            soFirebaseRefLoc.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    String SOName = sharedPreferences.getString("SOEMAIL", "NOSO");
                    // loop through the snapshot's children
                    SOFaveLocManager.emptyLocs();
                    VibeToneFactory v = new VibeToneFactory(service);
                    SparkleToneFactory s = new SparkleToneFactory();

                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        //grab the children
                        LocationFB locFB = postSnapshot.getValue(LocationFB.class);

                        if(locFB.getHere().equals("true")) {
                            int sound = locFB.getArrivalSound();
                            int vibration = locFB.getArrivalVibration();
                            v.vibeTone(Constants.VibeToneName.DEFAULT_ARRIVAL);
                            s.sparkle(Constants.SparkleToneName.ARRIVAL, getApplicationContext());
                            sendNotification(SOName + " has arrived at " + locFB.getName(), vibration);
                            s.sparkle(Constants.SparkleToneName.values()[sound], getApplicationContext());
                            SOFaveLocManager.addLocation(locFB);
                        }
                        else if(locFB.getHere().equals("false"))
                        {
                            int sound = locFB.getDepartureSound();
                            int vibration = locFB.getDepartureVibration();

                            v.vibeTone(Constants.VibeToneName.DEFAULT_DEPART);
                            s.sparkle(Constants.SparkleToneName.DEPART, getApplicationContext());

                            sendNotification(SOName + " has left " + locFB.getName(), vibration);
                            s.sparkle(Constants.SparkleToneName.values()[sound], getApplicationContext());
                        }
                        else
                        {
                            //Do nothing
                        }

                        //somehow put this into SOFaveLocManager, use specialconstructor
                        /*SOFaveLoc soFaveLoc = new SOFaveLoc(locFB);
                        SOFaveLocManager.addLocation(soFaveLoc);*/

                        //somehow stick this into manager
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });


            //MY FIREBASE REG
            myFirebaseRefReg.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FBreg fBreg = dataSnapshot.getValue(FBreg.class);
                    String SOEmail = fBreg.getID();
                    if(fBreg.getRelationshipStatus()) {
                        sendNotification(SOEmail + "Added You",1 );
                    }
                    else
                    {
                        sharedPreferences.edit().remove("SOEMAIL").apply();
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void sendNotification(String message, int vibration) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        if (FaveLocationManager.locList.size() > 0 ) {
//            Ringtone ringtone = FaveLocationManager.locList.get(0).getRingtone();
//        }

//        VibeToneFactory v = new VibeToneFactory();


        Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.funkytown1);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle("CoupleTones")
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setVibrate(VibeToneFactory.getVibeTone(vibration))
                .setDefaults(Notification.DEFAULT_LIGHTS);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
