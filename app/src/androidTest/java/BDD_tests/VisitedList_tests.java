package BDD_tests;

import android.app.FragmentManager;
import android.content.Context;
import android.os.CountDownTimer;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.cse110.team36.coupletones.FireBase.LocationFB;
import com.cse110.team36.coupletones.Managers.SOFaveLocManager;
import com.cse110.team36.coupletones.lists.HomeScreen;
import com.cse110.team36.coupletones.lists.SOVisitedActivity;

import java.util.Collections;

/**
 * Created by Andrew on 6/3/2016.
 */
public class VisitedList_tests extends ActivityInstrumentationTestCase2<SOVisitedActivity> {

    private Context context;
    private FragmentManager fragmentManager;

    SOVisitedActivity soVisitedActivity;


    public VisitedList_tests() {
        super(SOVisitedActivity.class);
    }

    public void test_visListOrderedByTime() {
        soVisitedActivity = getActivity();

        SOFaveLocManager testFaveLocManager = new SOFaveLocManager(context);

        for(int i = 0; i < 10; i++){
            testFaveLocManager.visList.add(new LocationFB());
            new CountDownTimer(1000,1000) {
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

        Collections.sort(testFaveLocManager.visList, );
    }
}
