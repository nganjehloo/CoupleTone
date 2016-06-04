package BDD_tests;

import android.test.ActivityInstrumentationTestCase2;

import com.cse110.team36.coupletones.FireBase.FBreg;
import com.cse110.team36.coupletones.FireBase.LocationFB;
import com.cse110.team36.coupletones.lists.SOListActivity;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class ViewSOList_tests extends ActivityInstrumentationTestCase2<SOListActivity> {
    public ViewSOList_tests(){super(SOListActivity.class);};

    Firebase myFirebaseRef;
    Firebase soFirebaseRef;

    //receive SO's newly added location
    public void test_SOAddsLoc(){

        myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/" + "SOTESTLISTUSER1" + "/locations");
        soFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/" + "SOTESTLISTUSER2" + "/locations");

        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LocationFB locFB = dataSnapshot.getValue(LocationFB.class);
                assertEquals(locFB.getName(), "yes");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        LocationFB locFB = new LocationFB();
        locFB.setName("yes");
        locFB.setLat(1.0);
        locFB.setLong(1.0);
        myFirebaseRef.setValue(locFB);


    }
    //get updated SO's list should they delete

    //be able to see list of SO's list upon opening the apps
}
