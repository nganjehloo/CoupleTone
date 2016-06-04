package BDD_tests;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.FireBase.FirebaseContext;
import com.cse110.team36.coupletones.FireBase.LocationFB;
import com.cse110.team36.coupletones.SparkleToneFactory;
import com.cse110.team36.coupletones.maps.MapsActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Duc Le on 6/3/2016.
 */
public class CustomSound_tests extends ActivityInstrumentationTestCase2<MapsActivity> implements Constants {

    Firebase myTestFirebase;

    public CustomSound_tests() {
        super(com.cse110.team36.coupletones.maps.MapsActivity.class);
    }

    //Test for setting sound
    public void test_setSound()
    {
        //create a test firebase user
        myTestFirebase = new Firebase("https://coupletones36.firebaseio.com/testSound/Locations");

        //Set a listener
        myTestFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    LocationFB locFB = postSnapshot.getValue(LocationFB.class);

                    //Get new sound from the server
                    int newArrivalSound = locFB.getArrivalSound();
                    int newDepartureSound = locFB.getDepartureSound();

                    //test to see if the set is successful
                    assertEquals(newArrivalSound, 5);
                    assertEquals(newDepartureSound, 6);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        //Call set sound
        setSound();
    }

    public void test_playNewSound()
    {
        //
        final MapsActivity map = getActivity();

        //create a test firebase user
        myTestFirebase = new Firebase("https://coupletones36.firebaseio.com/testSound2/Locations");

        //Set a listener
        myTestFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    LocationFB locFB = postSnapshot.getValue(LocationFB.class);

                    //Get new sound from the server
                    int newArrivalVibe = locFB.getArrivalSound();
                    int newDepartureVibe = locFB.getDepartureSound();

                    SparkleToneFactory s = new SparkleToneFactory();
                    s.sparkle(Constants.SparkleToneName.values()[newArrivalVibe], map.getApplicationContext());
                    assertEquals(s.isPlaying(), true);

                    s.sparkle(Constants.SparkleToneName.values()[newDepartureVibe], map.getApplicationContext());
                    assertEquals(s.isPlaying(), true);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        //Call set sound
        setSound4PlayNew();
    }

    private void setSound4PlayNew()
    {
        //create a test firebase user
        myTestFirebase = new Firebase("https://coupletones36.firebaseio.com/testSound2/Locations/");

        //create a test location object with default vibration
        LocationFB testLoc = new LocationFB();
        testLoc.setName("testLoc");
        testLoc.setLat(1.0);
        testLoc.setLong(1.0);

        //Put test object to our firebase
        myTestFirebase.child(testLoc.getName()).setValue(testLoc);

        //Set new vibration to the server
        myTestFirebase.child(testLoc.getName()).child("arrivalSound").setValue(5);
        myTestFirebase.child(testLoc.getName()).child("departureSound").setValue(6);

    }

    private void setSound()
    {
        //create a test firebase user
        myTestFirebase = new Firebase("https://coupletones36.firebaseio.com/testSound/Locations/");

        //create a test location object with default vibration
        LocationFB testLoc = new LocationFB();
        testLoc.setName("testLoc");
        testLoc.setLat(1.0);
        testLoc.setLong(1.0);

        //Put test object to our firebase
        myTestFirebase.child(testLoc.getName()).setValue(testLoc);

        //Set new vibration to the server
        myTestFirebase.child(testLoc.getName()).child("arrivalSound").setValue(5);
        myTestFirebase.child(testLoc.getName()).child("departureSound").setValue(6);

    }

}
