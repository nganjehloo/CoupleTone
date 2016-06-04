package BDD_tests;


import android.test.ActivityInstrumentationTestCase2;

import com.cse110.team36.coupletones.FireBase.FireBaseManager;
import com.cse110.team36.coupletones.FireBase.SOConfig;
import com.firebase.client.Firebase;

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
        FireBaseManager fireBaseManager = new FireBaseManager(soConfig.sharedPreferences);
        fireBaseManager.addSO("testso.com");
        assertEquals("testso", soConfig.sharedPreferences.getString("SOEMAIL", null));
    }

    public void test_AddSOAfterRemoveSO(){
        test_RemoveSO();
        test_AddSO();
    }

    public void test_RemoveSO(){
        SOConfig soConfig = getActivity();
        FireBaseManager fireBaseManager = new FireBaseManager(soConfig.sharedPreferences);
        fireBaseManager.removeSO();
        assertEquals(null, soConfig.sharedPreferences.getString("SOEMAIL", null));
    }

    /*
     * Cancel during adding SO
     */
    public void test_cancelAdding(){
        //NO LONGER NEEDED TO BE IMPLEMENTED
    }

}
