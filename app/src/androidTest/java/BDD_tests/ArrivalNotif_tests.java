package BDD_tests;

import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;

import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.FireBase.LocationFB;
import com.cse110.team36.coupletones.maps.MapsActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nima on 6/3/2016.
 */
public class ArrivalNotif_tests extends ActivityInstrumentationTestCase2<MapsActivity> implements Constants{
    LatLng[] testPoints = { new LatLng(0,0),
            new LatLng(ONE_TENTH_MILE/2, ONE_TENTH_MILE/2),
            new LatLng(ONE_TENTH_MILE, ONE_TENTH_MILE),
            new LatLng(ONE_TENTH_MILE+1, ONE_TENTH_MILE+1)};

    MapsActivity mapsActivity;

    public ArrivalNotif_tests(){ super(MapsActivity.class);}


    public void test_sendArrival(){
        mapsActivity = getActivity();
        SharedPreferences sharedPreferences = mapsActivity.sharedPreferences;
        Firebase firebase = new Firebase("https://coupletones36.firebaseio.com/" + "MYTESTUSER" + "/Locations");
        LocationFB locationFB = new LocationFB();
        locationFB.setName("TESTLOC");
        locationFB.setHere("false");
        firebase.child("TESTLOC").setValue(locationFB);
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    LocationFB locFB = postSnapshot.getValue(LocationFB.class);
                    assertEquals("false", locFB.getHere());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



    }

    public void test_receiveArrival(){
        mapsActivity = getActivity();
        SharedPreferences sharedPreferences = mapsActivity.sharedPreferences;
        Firebase secondfirebase = new Firebase("https://coupletones36.firebaseio.com/" + "SOTESTUSER" + "/Locations");
        LocationFB locationFB = new LocationFB();
        locationFB.setName("TESTLOC");
        locationFB.setHere("false");
        secondfirebase.child("TESTLOC").setValue(locationFB);
        secondfirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    LocationFB locFB = postSnapshot.getValue(LocationFB.class);
                    assertEquals("false", locFB.getHere());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
