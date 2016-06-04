package com.cse110.team36.coupletones;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.firebase.client.Firebase;

import java.util.Date;

public class AlarmReset extends Service {
    public AlarmReset() {
    }

    @Override
    public void onCreate(){
        Thread thread = new Thread( new Server());
        thread.start();
    }


    public class Server implements Runnable{

        Server(){ }

        public void run(){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Date currentDate = new Date();
            int currhours = currentDate.getHours();
            int currmin = currentDate.getMinutes();
            int currsecs = currentDate.getSeconds();

            long epoch = System.currentTimeMillis()/1000;
            long waitamthr = Math.abs(currhours - 3);
            long waitamtmin = Math.abs(currmin - 00);
            long waitamtsec = Math.abs(currsecs - 00);
            long totalwait = waitamthr * 3600000 + waitamtmin * 60000;

            while(true){
                synchronized (this) {
                    try {
                        System.out.println("WAIT: " + waitamthr + ":" + waitamtmin);
                        System.out.println("TOTALWAIT: " + totalwait);
                        wait(totalwait);
                        totalwait = 86400000;
                        Firebase firebase = new Firebase("https://coupletones36.firebaseio.com/" + sharedPreferences.getString("MYEMAIL", null) + "/Visited");
                        firebase.removeValue();
                    } catch (Exception e) {

                    }
                }
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
