package BDD_tests;

import android.location.LocationListener;
import android.os.AsyncTask;
import android.util.Log;

import com.cse110.team36.coupletones.FaveLocation;
import com.cse110.team36.coupletones.Managers.FaveLocationManager;
import com.cse110.team36.coupletones.GCM.MyGCMListenerService;
import com.cse110.team36.coupletones.GCM.SOConfig;
import com.cse110.team36.coupletones.GCM.sendNotificationJob;
import com.cse110.team36.coupletones.Managers.MapManager;
import com.cse110.team36.coupletones.MapsActivity;
import com.cse110.team36.coupletones.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.maps.model.LatLng;
import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.Managers.MapManager;
import com.google.maps.android.SphericalUtil;

import android.test.ActivityInstrumentationTestCase2;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Nima on 5/8/2016.
 */
public class Notify_tests extends ActivityInstrumentationTestCase2<MapsActivity> implements Constants  {
    LatLng[] testPoints = { new LatLng(40,50), new LatLng(250,250)};

    public Notify_tests(){super(MapsActivity.class);};

   // public void test_OutsideRange(){
      //  FaveLocation faveLocation = new FaveLocation("testloc", testPoints[1]);
   //     MyGCMListenerService.getNotificationMessage();
   // }

    public void test_insideRange() {

        new AsyncTask<Void, Void, String>() {

            MapsActivity map = getActivity();
            FaveLocation faveLocation = new FaveLocation("testloc", testPoints[0]);
            ArrayList<FaveLocation> locList = new ArrayList<FaveLocation>();


            String token;

            @Override
            protected String doInBackground(Void... params) {

                String message = " ";

                locList.add(faveLocation);

                //Get Reg ID
                InstanceID instanceID = InstanceID.getInstance(map.getApplicationContext());
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


                return message;
            }

            @Override
            protected void onPostExecute(String msg) {
                sendNotificationJob job = new sendNotificationJob();
                String[] param = {token, msg};
                job.execute(param);

                System.out.println("!!!!!!!!MESSAAAAAGE IS : " + msg);

                //assert stuff here
                assertEquals(msg , MyGCMListenerService.getNotificationMessage(), "DO NOT SEND NOTIFICATION");


            }
        }.execute(null, null, null);
    }

}
