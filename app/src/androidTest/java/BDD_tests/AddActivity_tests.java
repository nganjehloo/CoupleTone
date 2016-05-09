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


    private static String regID = "";

    public AddActivity_tests() {
            super(SOActivity.class);
        }


    public void test_AddSO(){
        SOActivity soActivity;
        soActivity = getActivity();



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
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                SOActivity soActivity;
                soActivity = getActivity();
                String message = "ADD SO";

                //Get our RegID
                InstanceID instanceID = InstanceID.getInstance(soActivity.getApplicationContext());
                try {
                    regID = instanceID.getToken("755936681526", "GCM");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return message;
            }

            @Override
            protected void onPostExecute(String msg) {
                //Send a notification to myself
                String params[] = {regID, msg};
                sendNotificationJob job = new sendNotificationJob();
                job.execute(params);

                assertEquals(MyGCMListenerService.getNotificationMessage(), msg);
            }
        }.execute(null, null, null);
    }


}
