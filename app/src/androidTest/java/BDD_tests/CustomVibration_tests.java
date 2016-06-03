package BDD_tests;

import android.test.ActivityInstrumentationTestCase2;

import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.FireBase.LocationFB;
import com.cse110.team36.coupletones.maps.MapsActivity;
import com.firebase.client.Firebase;

/**
 * Created by Duc Le on 6/3/2016.
 */
public class CustomVibration_tests extends ActivityInstrumentationTestCase2<MapsActivity> implements Constants {

    Firebase myTestFirebase;

    public CustomVibration_tests() {
        super(MapsActivity.class);
    }

    //Test for setting vibration
    public void test_setVibration()
    {
        //create a test firebase user
        myTestFirebase = new Firebase("https://coupletones36.firebaseio.com/test/Locations/");

        //create a test location object with default vibration
        LocationFB testLoc = new LocationFB();
        testLoc.setName("testLoc");
        testLoc.setLat(1.0);
        testLoc.setLong(1.0);

        //Put test object to our firebase
        myTestFirebase.setValue(testLoc);

        //Set new vibration to the server
        myTestFirebase.child("arrivalVibration").setValue(5);
        myTestFirebase.child("departureVibration").setValue(5);

        //Get new vibration from the server
        String vibe1 = myTestFirebase.child("arrivalVibration").getKey();
        String vibe2 = myTestFirebase.child("departureVibration").getKey();

        //convert to int
        int newArrivalVibe = Integer.parseInt(vibe1);
        int newDepartureVibe = Integer.parseInt(vibe2);

        //test to see if the set is successful
        assertEquals(newArrivalVibe, 5);
        assertEquals(newDepartureVibe, 6);

    }

}
