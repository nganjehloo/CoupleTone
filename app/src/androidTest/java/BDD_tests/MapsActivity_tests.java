package BDD_tests;

import android.test.ActivityInstrumentationTestCase2;
import com.cse110.team36.coupletones.Constants;

import com.cse110.team36.coupletones.Managers.FaveLocationManager;
import com.cse110.team36.coupletones.maps.MapsActivity;
import com.cse110.team36.coupletones.R;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by stazia on 5/4/16.
 *
 * Tester for BDD scenarios reliant on the MapsActivity. Scenarios can be mapped
 * to our submitted scenarios by (Story Name).(Scenario Name)
 */


public class MapsActivity_tests extends ActivityInstrumentationTestCase2<MapsActivity>  implements Constants {
    LatLng[] testPoints = { new LatLng(0,0),
            new LatLng(ONE_TENTH_MILE/2, ONE_TENTH_MILE/2),
            new LatLng(ONE_TENTH_MILE, ONE_TENTH_MILE),
            new LatLng(ONE_TENTH_MILE+1, ONE_TENTH_MILE+1)};
    MapsActivity mapsActivity;

    public MapsActivity_tests() {
        super(MapsActivity.class);
    }

    /* (Click map to add favorite location).(first time adding a favorite place) */
    public void test_firstLocationSave() {
        mapsActivity = getActivity();
        FaveLocationManager.emptyLocs();
        mapsActivity.onMapLongClick(testPoints[0]);
        assertNotNull(mapsActivity.mapManager.getLocationDialog());
        FaveLocationManager.addLocation("loc0", testPoints[0]);

        assertEquals(FaveLocationManager.locList.get(0).getCoords(), testPoints[0]);
        assertEquals(1, FaveLocationManager.locList.size());
    }

    /* (Click map to add favorite location).(Only have one marker within 1/10th mile radius) */
    public void test_tooCloseNoAdd() {
        mapsActivity = getActivity();
        FaveLocationManager.emptyLocs();
        mapsActivity.onMapLongClick(testPoints[0]);
        mapsActivity.onMapLongClick(testPoints[1]);

        FaveLocationManager.addLocation(Integer.toString(R.string.loc0), testPoints[0]);
        FaveLocationManager.addLocation(Integer.toString(R.string.loc1), testPoints[1]);

        assertEquals(FaveLocationManager.locList.get(0).getCoords(), testPoints[0]);
        assertEquals(FaveLocationManager.locList.get(1).getCoords(), testPoints[1]);
        assertNotNull(FaveLocationManager.locList.get(1));
        assertEquals(FaveLocationManager.locList.size(), 2);
    }

    /* (Click map to add favorite location).(Adding a favorite location) */
    public void test_commonSaveLoc() {
        mapsActivity = getActivity();
        FaveLocationManager.emptyLocs();
        mapsActivity.onMapLongClick(testPoints[0]);
        mapsActivity.onMapLongClick(testPoints[2]);

        FaveLocationManager.addLocation("loc0", testPoints[0]);
        FaveLocationManager.addLocation("loc1", testPoints[2]);

        assertEquals(testPoints[0], FaveLocationManager.locList.get(0).getCoords());
        assertEquals(testPoints[2], FaveLocationManager.locList.get(1).getCoords());
        assertNotNull(FaveLocationManager.locList.get(1));
        assertEquals(2, FaveLocationManager.locList.size());
    }

    /* (Click map to add favorite location).(Re add deleted location) */
    public void test_reAddDeleted() {
        mapsActivity = getActivity();
        FaveLocationManager.emptyLocs();
        mapsActivity.onMapLongClick(testPoints[0]);
        mapsActivity.onMapLongClick(testPoints[3]);

        FaveLocationManager.addLocation("loc0", testPoints[0]);
        FaveLocationManager.addLocation("loc3", testPoints[3]);

        assertEquals(FaveLocationManager.locList.get(0).getCoords(), testPoints[0]);
        assertEquals(FaveLocationManager.locList.get(1).getCoords(), testPoints[3]);
        assertEquals(FaveLocationManager.locList.size(), 2);

        FaveLocationManager.removeLocation("loc0");

        assertEquals(FaveLocationManager.locList.size(), 1);

        FaveLocationManager.removeLocation("loc3");

        assertEquals(FaveLocationManager.locList.size(), 0);
    }

}
