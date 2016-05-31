package com.cse110.team36.coupletones;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cse110.team36.coupletones.Dialogs.SparkleListDialog;
import com.cse110.team36.coupletones.Dialogs.VibeListDialog;
import com.cse110.team36.coupletones.Managers.SOFaveLocManager;
import com.cse110.team36.coupletones.lists.SOListActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class NotifSettings extends AppCompatActivity {
    VibeToneFactory vibeToneFactory;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    int pos;
    int idx;
    Firebase SOFirebaseSettings;
    String SOLocationName;
    SharedPreferences sharedPreferences;
    String SOEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vibeToneFactory = new VibeToneFactory(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Bundle bundle = getIntent().getExtras();
        pos = bundle.getInt("key");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SOEmail = sharedPreferences.getString("SOEMAIL", null);
        SOLocationName = SOFaveLocManager.locList.get(pos).getName();
        SOFirebaseSettings = new Firebase("https://coupletones36.firebaseio.com/" + SOEmail + "/Locations/" + SOLocationName);
    }

    public void selectArrivalSound(View view) {
        //vibeToneFactory.vibeTone(Constants.VibeToneName.FUNKYTOWN);
        final SparkleListDialog listDialog = new SparkleListDialog();
        listDialog.setContext(getBaseContext());

        SOFirebaseSettings.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                long IDX = (long) snapshot.child("arrivalSound").getValue();
                idx = Long.valueOf(IDX).intValue();
                listDialog.setPositions(pos, idx);
                listDialog.show(getFragmentManager(), "arrivalSparkleList");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}

        });
    }

    public void selectArrivalVibe(View view) {
        VibeListDialog listDialog = new VibeListDialog();
        listDialog.setContext(getBaseContext());
        listDialog.setActivity(this);
        listDialog.setPosition(pos);
        listDialog.show(getFragmentManager(), "arrivalVibeList");
    }

    public void selectDepartSound(View view) {
        //vibeToneFactory.vibeTone(Constants.VibeToneName.FUNKYTOWN);
        final SparkleListDialog listDialog = new SparkleListDialog();
        listDialog.setContext(getBaseContext());

        SOFirebaseSettings.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                long IDX = (long) snapshot.child("departureSound").getValue();
                idx = Long.valueOf(IDX).intValue();
                listDialog.setPositions(pos, idx);
                listDialog.show(getFragmentManager(), "departSparkleList");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
    }

    public void selectDepartVibe(View view) {
        VibeListDialog listDialog = new VibeListDialog();
        listDialog.setContext(getBaseContext());
        listDialog.show(getFragmentManager(), "departVibeList");
        listDialog.setActivity(this);
        vibeToneFactory.vibeTone(Constants.VibeToneName.SLOW2FAST);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "NotifSettings Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.cse110.team36.coupletones/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "NotifSettings Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.cse110.team36.coupletones/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                startActivity(new Intent(NotifSettings.this, SOListActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
