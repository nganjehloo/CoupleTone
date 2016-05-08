package BDD_tests;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.TextView;

import com.cse110.team36.coupletones.GCM.SOActivity;
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
     * Testing by adding ourselves
     */
    @UiThreadTest
    public void test_addSO() {
        soActivity = getActivity();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(soActivity.getApplicationContext());

        //Get our RegID
        InstanceID instanceID = InstanceID.getInstance(soActivity.getApplicationContext());
        try {
            String token = instanceID.getToken("755936681526", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            //Add our regID to sharedpreferences
            sharedPreferences.edit().putString("MYIDTEST", token).apply();
            sharedPreferences.edit().putBoolean("HAS_SO", true).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //get the mBaeCode text field
        TextView mBaeCode = (TextView) soActivity.findViewById(R.id.editText);

        //set it to our RegID
        mBaeCode.setText(sharedPreferences.getString("MYIDTEST", null));

        //run the add SO
        Button soButton = (Button) soActivity.findViewById(R.id.button);
        soButton.performClick();


    }
}
