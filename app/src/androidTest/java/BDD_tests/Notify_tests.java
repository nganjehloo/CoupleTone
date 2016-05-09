package BDD_tests;

import android.location.LocationListener;
import android.os.AsyncTask;
import android.util.Log;

import com.cse110.team36.coupletones.FaveLocation;
import com.cse110.team36.coupletones.GCM.MyGCMListenerService;
import com.cse110.team36.coupletones.GCM.SOConfig;
import com.cse110.team36.coupletones.GCM.sendNotificationJob;
import com.cse110.team36.coupletones.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.maps.model.LatLng;
import com.cse110.team36.coupletones.Constants;
import android.test.ActivityInstrumentationTestCase2;

import java.io.IOException;

/**
 * Created by Nima on 5/8/2016.
 */
public class Notify_tests extends ActivityInstrumentationTestCase2<SOConfig> implements Constants  {
    LatLng[] testPoints = { new LatLng(0,0),
            new LatLng(ONE_TENTH_MILE/2, ONE_TENTH_MILE/2),
            new LatLng(ONE_TENTH_MILE, ONE_TENTH_MILE),
            new LatLng(ONE_TENTH_MILE+1, ONE_TENTH_MILE+1)};

    public Notify_tests(){super(SOConfig.class);};

    public void test_OutsideRange() {
        new AsyncTask<Void, Void, String>() {

            FaveLocation faveLocation = new FaveLocation("testloc", testPoints[1]);
            SOConfig soConfig = getActivity();
            String token;

            @Override
            protected String doInBackground(Void... params) {
                InstanceID instanceID = InstanceID.getInstance(soConfig.getApplicationContext());

                try {
                    token = instanceID.getToken("755936681526", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return "l"+faveLocation.getName();
            }

            @Override
            protected void onPostExecute(String msg) {
                sendNotificationJob job = new sendNotificationJob();
                String[] param = {token, msg};
                job.execute(param);

                assertEquals(, MyGCMListenerService.getNotificationMessage());

            }
        }.execute(null, null, null);
    }

}
