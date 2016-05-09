package BDD_tests;

import android.os.AsyncTask;

import com.cse110.team36.coupletones.FaveLocation;
import com.cse110.team36.coupletones.Managers.FaveLocationManager;
import com.cse110.team36.coupletones.GCM.sendNotificationJob;
import com.cse110.team36.coupletones.MapsActivity;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.maps.model.LatLng;
import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.MapManager;
import com.google.maps.android.SphericalUtil;

import android.test.ActivityInstrumentationTestCase2;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Nima on 5/8/2016.
 */
public class Notify_tests extends ActivityInstrumentationTestCase2<MapsActivity> implements Constants  {
    LatLng[] testPoints = { new LatLng(0,0),
            new LatLng(ONE_TENTH_MILE/2, ONE_TENTH_MILE/2),
            new LatLng(ONE_TENTH_MILE, ONE_TENTH_MILE),
            new LatLng(ONE_TENTH_MILE+1, ONE_TENTH_MILE+1)};

    public Notify_tests(){super(MapsActivity.class);};

    public void test_OutsideRange() {
        new AsyncTask<Void, Void, String>() {
            FaveLocation faveLocation = new FaveLocation("testloc", testPoints[0]);
            ArrayList<FaveLocation> locList = new ArrayList<FaveLocation>();


            String token;

            @Override
            protected String doInBackground(Void... params) {
                MapsActivity mapsActivity = getActivity();

                String message = "";
                locList.add(faveLocation);

                //Get Reg ID

                InstanceID instanceID = InstanceID.getInstance(mapsActivity.getApplicationContext());
                try {
                    token = instanceID.getToken("755936681526", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //get boolean value if our current location is within the fav location
                MapManager map = new MapManager();
                boolean isValid = map.checkValidDrop(locList, testPoints[1]);

                if(isValid)
                {
                    message = "lSEND NOTIFICATION";
                }
                else
                {
                    message = "lDO NOT SEND NOTIFICATION";
                }
                return "l"+faveLocation.getName();
            }

            @Override
            protected void onPostExecute(String msg) {
                sendNotificationJob job = new sendNotificationJob();
                String[] param = {token, msg};
                job.execute(param);


                assertEquals("DO NOT SEND NOTIFICATION", MyGCMListenerService.getNotificationMessage());

            }
        }.execute(null, null, null);
    }

}