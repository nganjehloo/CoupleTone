package BDD_tests;


import android.test.ActivityInstrumentationTestCase2;

import com.cse110.team36.coupletones.FireBase.SOConfig;

/**
 * Created by stazia on 5/5/16.
 */

public class AddActivity_tests extends ActivityInstrumentationTestCase2<SOConfig> {

//    HomeScreen soActivity;

    public AddActivity_tests() {
            super(SOConfig.class);
        }

    public void test_AddSO(){
        //First TIme adding SO and addSO are now the same
        SOConfig soConfig = getActivity();
        soConfig.addSO();
        assertEquals(true, soConfig.sharedPreferences.getBoolean("HAS_SO", false));
    }

    public void test_AddSOAfterRemoveSO(){
        test_AddSO();
        test_RemoveSO();
        test_AddSO();
    }

    public void test_RemoveSO(){
        SOConfig soConfig = getActivity();
        soConfig.removeSO();
        assertEquals(false, soConfig.sharedPreferences.getBoolean("HAS_SO", true));
    }

    /*
     * Cancel during adding SO
     */
    public void test_cancelAdding(){
        //NO LONGER NEEDED TO BE IMPLEMENTED
    }

}
