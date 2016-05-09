package BDD_tests;

import com.cse110.team36.coupletones.FaveLocation;
import com.google.android.gms.maps.model.LatLng;
import com.cse110.team36.coupletones.Constants;
/**
 * Created by Nima on 5/8/2016.
 */
public class Notify_tests implements Constants{
    LatLng[] testPoints = { new LatLng(0,0),
            new LatLng(ONE_TENTH_MILE/2, ONE_TENTH_MILE/2),
            new LatLng(ONE_TENTH_MILE, ONE_TENTH_MILE),
            new LatLng(ONE_TENTH_MILE+1, ONE_TENTH_MILE+1)};

    public void test_OutsideRange(){
        FaveLocation faveLocation = new FaveLocation("testloc", testPoints[1]);

    }

}
