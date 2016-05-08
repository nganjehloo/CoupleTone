package BDD_tests;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.TextView;

import com.cse110.team36.coupletones.GCM.MyGCMListenerService;
import com.cse110.team36.coupletones.GCM.SOActivity;
import com.cse110.team36.coupletones.GCM.Server.Post2Gcm;
import com.cse110.team36.coupletones.GCM.sendNotificationJob;
import com.cse110.team36.coupletones.HomeScreen;
import com.cse110.team36.coupletones.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by stazia on 5/5/16.
 */
public class AddActivity_tests extends ActivityInstrumentationTestCase2<SOActivity> {
//    Activity soActivity ;
    SOActivity soActivity;
//    HomeScreen soActivity;
        public AddActivity_tests() {
            super(SOActivity.class);
        }

    /*
     * Testing send to ourselves
     */
    @UiThreadTest
    public void test_sendNotification() throws IOException {
        soActivity = getActivity();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(soActivity.getApplicationContext());
        String message = "ADD SO";

        //Get our RegID
        InstanceID instanceID = InstanceID.getInstance(soActivity.getApplicationContext());
        String token = instanceID.getToken("755936681526", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
        //Add our regID to sharedpreferences
        sharedPreferences.edit().putString("MYIDTEST", token).apply();
        sharedPreferences.edit().putBoolean("HAS_SO", true).apply();

        //Send a notification to myself
        String params[] = {sharedPreferences.getString("MYIDTEST", null), message};
        sendNotificationJob job = new sendNotificationJob();
        job.execute(params);

        //check to see if we get the message
        assertEquals(message, sharedPreferences.getString("MYIDTEST", null));

    }
}
