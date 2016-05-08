package BDD_tests;

import android.app.FragmentManager;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import com.cse110.team36.coupletones.FaveLocation;
import com.cse110.team36.coupletones.HomeScreen;
import com.cse110.team36.coupletones.MyCustomAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by stazia on 5/7/16.
 */
public class ListView_tests extends ActivityInstrumentationTestCase2<HomeScreen> {
    HomeScreen homeScreen;
    private ArrayList<FaveLocation> list = new ArrayList<>();
    LatLng equator = new LatLng(0.000000,0.000000);
    LatLng ucsd = new LatLng(32.000000,117.000000);
    LatLng oc = new LatLng(34.000000,118.000000);
    private Context context;
    private FragmentManager fragmentManager;


    public ListView_tests() {
        super(HomeScreen.class);
    }

    /* (Rename and Delete locations).(Only one favorite place was added) */
    public void test_addLocation() {
        homeScreen = getActivity();
        // add location to locList
        // get custom adapter
        list.add("Equator",Double.valueOf(equator.latitude), Double.valueOf(equator.longitude));

        MyCustomAdapter adapter = new MyCustomAdapter(list, context, fragmentManager);
        assertEquals(adapter.getCount(), 1);
    }

    /* (Rename and Delete locations).(delete location) */
    public void test_deleteLocation() {
        homeScreen = getActivity();
        // add location to locList
        // get custom adapter
        // remove location from locList
        MyCustomAdapter adapter = new MyCustomAdapter(list, context, fragmentManager);
        assertEquals(adapter.getCount(), 0);
    }



}
