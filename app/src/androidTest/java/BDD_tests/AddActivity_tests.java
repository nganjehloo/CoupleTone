package BDD_tests;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.cse110.team36.coupletones.GCM.SOActivity;
import com.cse110.team36.coupletones.HomeScreen;

/**
 * Created by stazia on 5/5/16.
 */
public class AddActivity_tests extends ActivityInstrumentationTestCase2<SOActivity> {
//    Activity soActivity ;
    SOActivity soActivity;
//    HomeScreen soActivity;
        public AddActivity_tests() {
            super(SOActivity.class);
        }

    public void test_addSO() {
        soActivity = getActivity();



    }
}
