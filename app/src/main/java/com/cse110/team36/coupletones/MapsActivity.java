package com.cse110.team36.coupletones;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.SystemClock;
import android.os.Vibrator;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import android.location.LocationListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener,
                                                                OnMapReadyCallback,
                                                                LocationDialog.LocationDialogListener,
                                                                Constants {
    String savedLocs = "";

    FaveLocation currFavLoc = null;
    FaveLocation lastFavLoc = null;

    MapManager mapManager;
    FaveLocationManager faveLocationManager = new FaveLocationManager(getBaseContext());

    boolean debug = true;
    private GoogleMap mMap;
    double dist = 0;

    boolean dropMarker = true;


    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the LocationDialogFragment.LocationDialogListener interface
    /* Create new location with the coordinates and name */
    @Override
    public void onDialogPositiveClick(String name, LatLng loc) {
        /* NOTE (Aimed toward locations team)
         * This implementation is temporary - I was testing that the information gets here
         * Use this method to save the new location
         */
        //TODO: DELETE ME!
        if (name.equals("")) {
            savedLocs = "";
            faveLocationManager.emptyLocs();
            return;
        }

        faveLocationManager.addLocation(name, loc);
//        savedLocs += newLoc.getName() + "\n" + newLoc.getCoords().latitude + "\n" + newLoc.getCoords().longitude;
        dropFavLocMarker(name, loc);

//        Toast.makeText(getBaseContext(),
//                "" + newLoc.getName() + "\n" + String.valueOf(newLoc.getCoords().latitude) + ", " + String.valueOf(newLoc.getCoords().longitude),
//                Toast.LENGTH_SHORT).show();
        //TODO: DELETE ME!
        if (name.equals("")) {
            savedLocs = "";
            faveLocationManager.emptyLocs();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        importSavedFavLocs();



        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ImageButton myLocButton = (ImageButton) findViewById(R.id.myLocButton);
        myLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this, HomeScreen.class));
            }
        });

//        ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);
//        settingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //startActivity(new Intent(MapsActivity.this, MapsActivity.class));
//                Toast.makeText(getBaseContext(), "Sorry, this page not implemented yet", Toast.LENGTH_SHORT).show();
//            }});
    }

    /* (non-Javadoc)
* @see android.app.Activity#onStop()
*/
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop", "On Stop .....");

        exportSavedFavLocs();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        loadAndDropSavedLocs();


        mMap.setOnMapLongClickListener(this);   // LISTENER FOR THE LONG-CLICK SO MARKER DROPS ON HELD LOCATION

        /***** Add a marker at UCSD and move the camera
         *          ADDED BY: MrSwirlyEyes
         *                      4/25
         *
         *****/
        //FROM HERE
//        LatLng ucsd = new LatLng(32.8801, -117.2340);       // GPS COORDS OF UCSD
//        mMap.addMarker(new MarkerOptions().position(ucsd).title("Marker in UCSD")); // MARKER OPTIONS with TITLE and POSITION @ GPS COORDS OF UCSD

        mMap.getUiSettings().setZoomControlsEnabled(false); //Disable zoom toolbar
        mMap.getUiSettings().setMapToolbarEnabled(false);   //Disable (useless) map toolbar (literally is garbage)
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        //GPS
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mapManager = new MapManager(locationManager);
        String locationProvider = LocationManager.GPS_PROVIDER;
//        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
            Log.d("test1", "ins");
            return;
        } else if(mMap != null) {
            Log.d("test2", "outs");
            mMap.setMyLocationEnabled(true);

            // Create a criteria object to retrieve provider
            mapManager.firstLocationSet(mMap);
        }
//        final Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("updated path"));
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
            mapManager.updateGPSLoc(location);

                ArrayList<FaveLocation> locList = faveLocationManager.getLocList();
                if (faveLocationManager.locList.size() > 0) {
                    String string = null;
//                    currLoc = new LatLng(gpsLatitude, gpsLongitude);
                    for ( int i = 0 ; i < locList.size() ; i++ ) {
                        dist = SphericalUtil.computeDistanceBetween(locList.get(i).getCoords(), mapManager.getGPSPos());
                        if ( dist <= ONE_TENTH_MILE ) {
                            currFavLoc = new FaveLocation(locList.get(i).getName(), locList.get(i).getCoords() );
                            string = "checked in!" + "\nLocName=" + locList.get(i).getName() + "\nArraySize=" + locList.size() + "\nx=" + locList.get(i).getCoords().latitude + "\ny=" + locList.get(i).getCoords().longitude;
                            if ( lastFavLoc == null ) {
                                lastFavLoc = currFavLoc;
                            } else if ( currFavLoc.getName().equals(lastFavLoc.getName()) ) {
                                string = "Still @ Curr Fav Loc!" + "\nLocName=" + locList.get(i).getName() + "\nArraySize=" + locList.size() + "\nx=" + locList.get(i).getCoords().latitude + "\ny=" + locList.get(i).getCoords().longitude;
//                                Toast.makeText(getBaseContext(), string, Toast.LENGTH_SHORT).show();
                                //TODO: NO NEED FOR NOTIFY, I AM WHERE I JUST WAS
//                                break;
                            } else {
                                //TODO: I AM SOMEWHERE NEW! NOTIFY
                                string = "checked in!" + "\nLocName=" + locList.get(i).getName() + "\nArraySize=" + locList.size() + "\nx=" + locList.get(i).getCoords().latitude + "\ny=" + locList.get(i).getCoords().longitude;
                            }
//                            else if ( !currFavLoc.getName().equals(lastFavLoc.getName())) {
//                                //TODO: I AM NOT IN THE SAME PLACE, BUT I AM IN A DIFF LOC!
//                                String string = "Still @ Curr Fav Loc!" + "\nLocName=" + locList.get(i).getName() + "\nArraySize=" + locList.size() + "\nx=" + locList.get(i).getCoords().latitude + "\ny=" + locList.get(i).getCoords().longitude;
//                                Toast.makeText(getBaseContext(), string, Toast.LENGTH_SHORT).show();
//                                break;
//                            }


//                            currLoc = new LatLng(gpsLatitude,gpsLongitude);


                            //Toast.makeText(getBaseContext(), string, Toast.LENGTH_SHORT).show();
                            i = locList.size();
                            // TODO: notify()

                            lastFavLoc = currFavLoc;
                            break;
                        } else {
                            //TODO:  I AM IN NO FAV LOCATION, NO NOTIFY NEEDED
                            string = "NOT checked in!" + "\nLocName=" + locList.get(i).getName() + "\nArraySize=" + locList.size() + "\nx=" + locList.get(i).getCoords().latitude + "\ny=" + locList.get(i).getCoords().longitude;
                            //Toast.makeText(getBaseContext(), string, Toast.LENGTH_SHORT).show(); //?
                        }
                    }
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);

//        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
//                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                // TODO: Get info about the selected place.
//                Log.i("PLACE ERROR", "Place: " + place.getName());
//            }
//
//            @Override
//            public void onError(Status status) {
//                // TODO: Handle the error.
//                Log.i("STATUS ERROR", "An error occurred: " + status);
//            }
//        });
    }

    /***** DROPPING OF MAP MARKER ON MAP LONG-CLICK
     *          ADDED BY: MrSwirlyEyes
     *                      4/25
     *
     *****/
    public void onMapLongClick(LatLng point) {
        vibrate();

        dropMarker = true;  //Always assume we can drop a marker (and show we cannot, if we cannot)
        String markerCoords = "";   //Initializing markerCoords string (for debug output)

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

        /*
        if (debug) {
            markerCoords = "ArraySize=\n" + (locList.size()) + "\ndropMarker=" + dropMarker;
            Toast.makeText(getBaseContext(), markerCoords, Toast.LENGTH_LONG).show();
        }
        */


        // If the list already has ANY point (if we already have saved favorite locations
            //TODO: REPLACE WITH DATABASE WHEN TEAM GETS IT
        if (faveLocationManager.locList.size() > 0) {
            dropMarker = mapManager.checkValidDrop(faveLocationManager.locList, point);
        }

        //Here, we know there was either:
            // No markers/saved-locs to begin with
            // OR, all the other markers are outside of 2*ONE_TENTH_MILE rule
        // Thus we wish to drop a marker, and we can!

            if (dropMarker) { //Check our boolean to be absolutely sure we can still drop marker

                // Stazia's code
                mapManager.showLocationDialog(point, getFragmentManager());

                if (debug) markerCoords = "D=" + dist + "m\nt=" + timeStamp + "\nYES! DROP THE MARKER" + "\nArraySize=" + faveLocationManager.locList.size();
            } else {
                if (debug) markerCoords = "D=" + dist + "m\nt=" + timeStamp + "\nDONT DROP THE MARKER" + "\nArraySize=" + faveLocationManager.locList.size();
            }
            markerCoords += "\n" + savedLocs;
            if (debug) Toast.makeText(getBaseContext(), markerCoords, Toast.LENGTH_LONG).show();
    }










    /***** BOUNCE EFFECT FOR MARKER DROP
     *          ADDED BY: MrSwirlyEyes
     *                      4/25
     *
     *****/
    private void dropPinEffect(final Marker marker) {
        // Handler allows us to repeat a code block after a specified delay
        final android.os.Handler handler = new android.os.Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 750;

        // Use the bounce interpolator
        final android.view.animation.Interpolator interpolator =
                new BounceInterpolator();

        // Animate marker with a bounce updating its position every 15ms
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                // Calculate t for bounce based on elapsed time
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed
                                / duration), 0);
                // Set the anchor
                marker.setAnchor(0.5f, 1.0f + 2 * t);

                if (t > 0.0) {
                    // Post this event again 15ms from now.
                    handler.postDelayed(this, 15);
                } else { // done elapsing, show window
                    marker.showInfoWindow();
                }
            }
        });
    }


    /***** CIRCLE RADIUS AROUND DROPPED MARKER
     *          ADDED BY: MrSwirlyEyes
     *                      4/26
     *
     *****/
    /*
        Takes in Marker object (from after use long-click drops),
            then creates a circle around it to display the 1/10th mile radius
     */
    Circle addMarkerCircle(Marker marker) {
        // Circle object with particular options, adds it to map around marker determined by position
        return mMap.addCircle(new CircleOptions()
                .center(marker.getPosition())
                .radius(ONE_TENTH_MILE)     // Creates circle of radius ONE_TENTH_MILE (takes in meter value)
                .fillColor(0x88AABBCC)      // Circle fill colour is 88 (partially transparent) with AABBCC (color code), default is black 0xFF000000
                .strokeWidth(0.0f)         // Width of the stroke (circle outline), default is 10
                .strokeColor(0x00000000));   // Circle outline is transparent (the first 2 00's --> transparency));
    }

    /***** Simple Vibrate, used to detect on-long click
     *          ADDED BY: MrSwirlyEyes
     *                      4/29
     *
     *****/
    void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 100 milliseconds
        v.vibrate(100);
    }

    void dropFavLocMarker(String name, LatLng loc) {
        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(loc.latitude, loc.longitude)));
        // Special dropping effect
        dropPinEffect(marker);
        // Add circle around marker to display the 1/10th of a mile radius
//                addMarkerCircle(marker);

//            Circle circle = addMarkerCircle(marker);
        addMarkerCircle(marker);
            /* Open dialog box for saving location
             *          Added by WigginWannabe 26 Apr 2016
             */
//            Location.distanceBetween( MapsActivity.gpsLatitude, MapsActivity.gpsLongitude,
//                                        circle.getCenter().latitude, circle.getCenter().longitude, vector);

        marker.setTitle(name);
    }

    void importSavedFavLocs() {
        try {
            FileInputStream fis = openFileInput("favorite_locs");
            int test = 0;
            while (test != -1) {
                test = fis.read();
                if (test != -1)
                    savedLocs += (char) test;
            } fis.close();
        } catch (Exception e) {
            Log.e("File", "File error!");
        }
    }

    void exportSavedFavLocs() {
        String FILENAME = "favorite_locs";

        savedLocs = "";

        for ( int i = 0 ; i < faveLocationManager.locList.size() ; i++ ) {
            savedLocs += faveLocationManager.locList.get(i).getName() + "\n";
            savedLocs += faveLocationManager.locList.get(i).getLat() + "\n";
            savedLocs += faveLocationManager.locList.get(i).getLng() + "\n";
        }


        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(savedLocs.getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e("File", "File error!");
        }
    }

    void loadAndDropSavedLocs() {
        String savedLocArr[] = savedLocs.split("\n");

        for ( int i = 0 ; i < savedLocArr.length - 1 ; i+=3 )
            faveLocationManager.locList.add(new FaveLocation(new String(savedLocArr[i]),new LatLng(Double.valueOf(savedLocArr[i+1]),Double.valueOf(savedLocArr[i+2]))));

        for ( int i = 0 ; i < faveLocationManager.locList.size() ; i++ )
            dropFavLocMarker(faveLocationManager.locList.get(i).getName(),faveLocationManager.locList.get(i).getCoords());
    }
}