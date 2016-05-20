package com.cse110.team36.coupletones.GCM;

/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.cse110.team36.coupletones.MapsActivity;
import com.cse110.team36.coupletones.R;
import com.cse110.team36.coupletones.VibeToneFactory;
import com.google.android.gms.gcm.GcmListenerService;

public class MyGCMListenerService extends GcmListenerService {

    private static final String TAG = "MyGCMListenerService";
    private static String testMessage = "";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        testMessage = message; // use for testing
        Log.d(TAG, "DOWNLOADING");

        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

      /*  if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {

        }*/

        Log.d(TAG, "DOWNLOADING");
        char type = message.charAt(0);
        String appended = message.substring(1);

        Log.d(TAG, "APPEND IS " + appended);
        Log.d(TAG, "MESSAGE TYPE IS " + appended);
        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */

        messageType(type, appended);
        // [END_EXCLUDE]
        Log.d(TAG, "DOWNLOAD COMPLETE");
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {
        Log.d(TAG, "CREATING NOTIFICATION");
        Intent intent = new Intent(this, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.funkytown);

//        VibeToneFactory v = new VibeToneFactory();



        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle("CoupleTones")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void addSO(String append){
        Log.d(TAG, "ADDING SO");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.edit().putBoolean("HAS_SO", true).apply();
        sharedPreferences.edit().putString("SOREGID", append).apply();

        System.out.println("AFTER ADDING SO, THEIR ID IS " + sharedPreferences.getString("SOREGID", null));
        sendNotification("ADDED SO");
    }

    private void removeSO(){
        Log.d(TAG, "REMOVING SO");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.edit().putBoolean("HAS_SO", false).apply();
        sharedPreferences.edit().remove("SOREGID").apply();
        System.out.println("AFTER REMOVING SO, THEIR ID IS " + sharedPreferences.getString("SOREGID", null));

        sendNotification("YOUR SO REMOVED YOU");

    }


    private void messageType(char type, String append) {
        if (type == 'a') {
            addSO(append);
        } else if (type == 'e') {
            removeSO();
        } else if (type == 'l') {
            sendNotification("SO visited " + append);
        }else {
            Log.d(TAG, "INVALID MESSAGE RECEIVED");
        }
    }

    public static String getNotificationMessage()
    {
        return testMessage;
    }

}