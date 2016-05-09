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
import com.cse110.team36.coupletones.GCM.QuickstartPreferences;
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

    private static String regID = "dPP6UdUV8gI:APA91bHmFkJquQDqB8hzHFEVWLaCAjAZtjlKoF8ER6_yiduhcwEUJjvp-uISlE9AqneYDgYqmFPfiBcJixr2d1jbfXZuyo0CxHY_pAFPOq7YiXBnzSYylHVKjB9F_rZwtniF7AVNKXP5";
//    HomeScreen soActivity;

    public AddActivity_tests() {
            super(SOConfig.class);
        }

    public void test_AddSO(){
        //First TIme adding SO and addSO are now the same
        SOConfig soConfig = getActivity();
        soConfig.addSO();
        assertEquals(true, soConfig.sharedPreferences.getBoolean("HAS_SO", false));
    }

    public void test_AddSOAfterRemoveSO(){

        SOConfig soConfig = getActivity();
        soConfig.addSO();
        soConfig.removeSO();
        assertEquals(false, soConfig.sharedPreferences.getBoolean("HAS_SO", true));
        soConfig.addSO();
        assertEquals(true, soConfig.sharedPreferences.getBoolean("HAS_SO", false));

    }


    /*
     * Cancel during adding SO
     */
    public void test_cancelAdding(){
        //NO LONGER NEEDED TO BE IMPLEMENTED
    }

    /*
     * Test send notification - get a notification by sending it to ourselves
     */
    public void test_getNotification() {

    }

}
