package BDD_tests;

import android.test.ActivityInstrumentationTestCase2;

import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.FireBase.LocationFB;
import com.cse110.team36.coupletones.maps.MapsActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Duc Le on 6/3/2016.
 */
public class CustomVibration_tests extends ActivityInstrumentationTestCase2<MapsActivity> implements Constants {

    Firebase myTestFirebase;

    public CustomVibration_tests() {
        super(MapsActivity.class);
    }

    public void test_setVibration()
    {
        //create a test firebase user
        myTestFirebase = new Firebase("https://coupletones36.firebaseio.com/test/");

        //Set a listener
        myTestFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    LocationFB locFB = postSnapshot.getValue(LocationFB.class);

                    //Get new vibration from the server
                    int newArrivalVibe = locFB.getArrivalVibration();
                    int newDepartureVibe = locFB.getDepartureVibration();

                    //test to see if the set is successful
                    assertEquals(newArrivalVibe, 5);
                    assertEquals(newDepartureVibe, 6);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        //Call set vibration
        setVibration();
    }
    //Test for setting vibration
    private void setVibration()
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
        myTestFirebase.child("departureVibration").setValue(6);

    }

}
