package com.cse110.team36.coupletones;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;

import com.cse110.team36.coupletones.lists.HomeScreen;
import com.cse110.team36.coupletones.maps.MapsActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Andrew on 6/3/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class RobolectricButtonTests {
    @Test
    public void testIntent() {
        Activity activity = Robolectric.setupActivity(MapsActivity.class);

        FloatingActionButton toOurLocations = (FloatingActionButton) activity.findViewById(R.id.toOurLocList);

        toOurLocations.performClick();

        Intent expectedIntent = new Intent(activity, HomeScreen.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }
}
