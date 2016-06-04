package BDD_tests;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.cse110.team36.coupletones.FaveLocations.SOFaveLoc;
import com.cse110.team36.coupletones.FireBase.FBreg;
import com.cse110.team36.coupletones.FireBase.FireBaseManager;
import com.cse110.team36.coupletones.FireBase.LocationFB;
import com.cse110.team36.coupletones.Managers.SOFaveLocManager;
import com.cse110.team36.coupletones.lists.HomeScreen;
import com.cse110.team36.coupletones.lists.SOVisitedActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Andrew on 6/3/2016.
 */
public class VisitedList_tests extends ActivityInstrumentationTestCase2<SOVisitedActivity> {

    private Context context;
    private FragmentManager fragmentManager;
    private SharedPreferences sp;
    private String testAcc = "testVisited@gmail";
    private String testSOAcc = "soTestVis@gmail";
    private final String fbURL = "https://coupletones36.firebaseio.com/";

    SOVisitedActivity soVisitedActivity;


    public VisitedList_tests() {
        super(SOVisitedActivity.class);
    }

    @Test
    public void test_visListOrderedByTime() {
        soVisitedActivity = getActivity();

        Firebase myFB = new Firebase(fbURL + testAcc);
        Firebase soFB = new Firebase(fbURL + testSOAcc);

        /* create a Firebase test account */
        FBreg myReg = new FBreg();
        myReg.setID(testSOAcc);
        myReg.setStatus(true);
        myFB.child("REG").setValue(myReg);

        /* create a Firebase SO test account */
        FBreg soReg = new FBreg();
        soReg.setID(testAcc);
        soReg.setStatus(true);
        soFB.child("REG").setValue(soReg);

        //sp = soVisitedActivity.sharedPreferences;
        ArrayList<LocationFB> visList = new ArrayList<>();
        //FireBaseManager fbManager = new FireBaseManager(sp);

        Firebase soVisFB = new Firebase (fbURL + testSOAcc + "/Visited");

        for (int i = 0; i < 10; i++) {
            LocationFB testLFB = new LocationFB();
            testLFB.setName("loc" + i);
            visList.add(testLFB);
            soVisFB.child(testLFB.getName()).setValue(testLFB);

            try {
                Thread.sleep(1000);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        //loadSOVisited();

        Collections.sort(visList, SOFaveLocManager.comparator);

        //assertEquals((SOFaveLocManager.visList.size() > 0), true);

            boolean test = equals(SOFaveLocManager.visList, visList);
            assertEquals(test, true);
    }

    public boolean equals(ArrayList<LocationFB> l1, ArrayList<LocationFB> l2) {
        boolean equal = true;
        for(int i = 0; i < l1.size(); i++) {
            Log.d("Visited", l1.get(i).getName() + " " + l2.get(i).getName());
            if(!(l1.get(i).getName().equals(l2.get(i).getName())))
                equal = false;
        }

        return equal;
    }

    public void loadSOVisited() {
        Log.d("FIREBASEMANAGER", "NOT NULL");
        Firebase myFirebaseRef = new Firebase("https://coupletones36.firebaseio.com/" + testSOAcc + "/Visited");
        myFirebaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("FIREBASEMANAGER", "on data changed");
                SOFaveLocManager.emptyVis();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    LocationFB locFB = child.getValue(LocationFB.class);
                    Log.d("FIREBASEMANAGER", locFB.getName());
                    SOFaveLocManager.addLocation(locFB);
                }
                Collections.sort(SOFaveLocManager.visList, SOFaveLocManager.comparator);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
            });
    }

}
