package BDD_tests;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.cse110.team36.coupletones.FireBase.FireBaseManager;
import com.cse110.team36.coupletones.FireBase.LocationFB;
import com.cse110.team36.coupletones.Managers.SOFaveLocManager;
import com.cse110.team36.coupletones.lists.HomeScreen;
import com.cse110.team36.coupletones.lists.SOVisitedActivity;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Andrew on 6/3/2016.
 */
public class VisitedList_tests extends ActivityInstrumentationTestCase2<SOVisitedActivity> {

    private Context context;
    private FragmentManager fragmentManager;
    private SharedPreferences sp;

    SOVisitedActivity soVisitedActivity;


    public VisitedList_tests() {
        super(SOVisitedActivity.class);
    }

    public void test_visListOrderedByTime() {
        soVisitedActivity = getActivity();

        sp = soVisitedActivity.sharedPreferences;
        //SOFaveLocManager testFaveLocManager = new SOFaveLocManager(context);
        ArrayList<LocationFB> visList = new ArrayList<>();
        FireBaseManager fbManager = new FireBaseManager(sp);


        for (int i = 0; i < 10; i++) {
            LocationFB testLFB = new LocationFB();
            visList.add(testLFB);
            fbManager.add(testLFB);
            new CountDownTimer(1000, 1000) {
                int i = 0;

                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d("Tick", i++ + "s");
                }

                @Override
                public void onFinish() {
                    Log.d("Done", "Finished.");
                }
            }.start();
        }

        Collections.sort(visList, SOFaveLocManager.comparator);
        for(int i = 0; i < SOFaveLocManager.visList.size(); i++) {
            assertEquals(equals(SOFaveLocManager.visList.get(i), visList.get(i)), true);
        }
    }

    public boolean equals(LocationFB loc1, LocationFB loc2) {

        if(loc1.getName() != loc2.getName())
            return false;
        if(loc1.getEpoch() != loc2.getEpoch())
            return false;

        return true;
    }
}
