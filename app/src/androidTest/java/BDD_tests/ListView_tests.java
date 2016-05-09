package BDD_tests;

import android.app.FragmentManager;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import com.cse110.team36.coupletones.FaveLocation;
import com.cse110.team36.coupletones.FaveLocationManager;
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

    /* Test FaveLocation */
    public void test_FaveLocation() {
        FaveLocation equ = new FaveLocation("Equator", equator);
        assertEquals(equator.latitude, equ.getLat());
        assertEquals(equator.longitude, equ.getLng());
        assertEquals(equator, equ.getCoords());
        assertEquals("Equator", equ.getName());

        // test renaming
        equ.setName("hi");
        assertEquals("hi", equ.getName());

    }
    /* (Rename and Delete locations).(Only one favorite place was added) */
    public void test_addLocation() {
        homeScreen = getActivity();
        // add location to locList
        FaveLocation equatorLoc = new FaveLocation("Equator", equator);
        list.add(equatorLoc);

        FaveLocationManager favMgr = new FaveLocationManager(context);
        // make sure that it is empty
        assertEquals(0, favMgr.locList.size());

        // make sure that it is added and is updated correctly
        boolean flag = favMgr.addLocation("Equator", equator);
        assertEquals(true, flag);
        assertEquals(1, favMgr.locList.size());

        // get custom adapter
        MyCustomAdapter adapter = new MyCustomAdapter(list, context, fragmentManager);
        assertEquals(adapter.getCount(), 1);
    }

    /* (Rename and Delete locations).(delete location) */
    public void test_deleteLocation() {
        homeScreen = getActivity();

        // list of favorite locations
        FaveLocation equatorLoc = new FaveLocation("Equator", equator);
        FaveLocation ucsdLoc = new FaveLocation("UCSD", ucsd);
        FaveLocation ocLoc = new FaveLocation("OC", oc);
        list.add(equatorLoc);
        list.add(ucsdLoc);
        list.add(ocLoc);

        // add location to locList
        // get custom adapter
        // remove location from locList
        MyCustomAdapter adapter = new MyCustomAdapter(list, context, fragmentManager);
        assertEquals(adapter.getCount(), 3);

        FaveLocationManager favMgr = new FaveLocationManager(context);
        // it remembers what was done in the last test, make sure of this
        assertEquals(1, favMgr.locList.size());

        // add a new location and make sure data is updated correctly
        boolean flag = favMgr.addLocation("ucsd", ucsd);
        assertEquals(true, flag);
        assertEquals(2, favMgr.locList.size());

        // remove a location and make sure data is updated properly
        flag = favMgr.removeLocation("ucsd");
        assertEquals(true, flag);
        assertEquals(1, favMgr.locList.size());

    }

}
