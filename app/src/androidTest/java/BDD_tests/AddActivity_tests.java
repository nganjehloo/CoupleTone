package BDD_tests;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cse110.team36.coupletones.GCM.MyGCMListenerService;
import com.cse110.team36.coupletones.GCM.SOActivity;
import com.cse110.team36.coupletones.GCM.SOConfig;
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

public class AddActivity_tests extends ActivityInstrumentationTestCase2<SOConfig> {

    private static String regID = "";
//    HomeScreen soActivity;

    public AddActivity_tests() {
            super(SOConfig.class);
        }

    public void test_AddSO(){
        SOActivity soActivity;


    }


    public void test_AddSOAfterRemoveSO(){


    }


    /*
     * Cancel during adding SO
     */
    public void test_cancelAdding(){
        //NO LONGER BE IMPLEMENTED
    }

    /*
     * Test send notification - get a notification by sending it to ourselves
     */
    public void test_getNotification() {

    }

}
