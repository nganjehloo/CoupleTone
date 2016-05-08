package BDD_tests;

import android.test.ActivityInstrumentationTestCase2;

import com.cse110.team36.coupletones.HomeScreen;

/**
 * Created by stazia on 5/7/16.
 */
public class ListView_tests extends ActivityInstrumentationTestCase2<HomeScreen> {
    HomeScreen homeScreen;

    public ListView_tests() {
        super(HomeScreen.class);
    }

    /* (Rename and Delete locations).(Only one favorite place was added) */
    public void test_addLocation() {
        homeScreen = getActivity();
        // add location to locList
        // get custom adapter
        assertEquals(adapter.getCount(), 1);
    }

    /* (Rename and Delete locations).(delete location) */
    public void test_deleteLocation() {
        homeScreen = getActivity();
        // add location to locList
        // get custom adapter
        // remove location from locList
        assertEquals(adapter.getCount(), 0);
    }



}
