package BDD_tests;

import android.test.ActivityInstrumentationTestCase2;

import com.cse110.team36.coupletones.FireBase.SOConfig;
import com.cse110.team36.coupletones.NotifSettings;
import com.cse110.team36.coupletones.SparkleToneFactory;
import com.cse110.team36.coupletones.VibeToneFactory;
import com.cse110.team36.coupletones.maps.MapsActivity;

/**
 * Created by Nima on 6/3/2016.
 */
public class NotifOptions_tests extends ActivityInstrumentationTestCase2<SOConfig> {


    SOConfig soConfig;
    public NotifOptions_tests(){ super(SOConfig.class);}

    public void test_playSound(){
        SparkleToneFactory.setSparkEnable();
        assertEquals(true, (new SparkleToneFactory()).getSparkEnable());
    }

    public void test_playNoSound(){
        SparkleToneFactory.setSparkEnable();
        assertEquals(false, (new SparkleToneFactory()).getSparkEnable());
    }

    public void test_playVIbrate(){
        VibeToneFactory.setVibeEnable();
        soConfig = getActivity();
        assertEquals(true, (new VibeToneFactory(soConfig)).getVibeEnable());
    }

    public void test_playNoVIbrate(){
        soConfig = getActivity();
        VibeToneFactory.setVibeEnable();
        assertEquals(false, (new VibeToneFactory(soConfig)).getVibeEnable());
    }

}
