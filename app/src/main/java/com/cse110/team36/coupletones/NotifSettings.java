package com.cse110.team36.coupletones;

import android.media.MediaPlayer;
import android.os.Bundle;


import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.cse110.team36.coupletones.Dialogs.SparkleListDialog;
import com.cse110.team36.coupletones.Dialogs.VibeListDialog;
import com.cse110.team36.coupletones.lists.SOListActivity;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class NotifSettings extends AppCompatActivity {
    VibeToneFactory vibeToneFactory;
    Uri ringtone;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vibeToneFactory = new VibeToneFactory(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Bundle bundle = getIntent().getExtras();
        pos = bundle.getInt("key");
    }

    public void selectArrivalSound(View view) {
        //vibeToneFactory.vibeTone(Constants.VibeToneName.FUNKYTOWN);
        SparkleListDialog listDialog = new SparkleListDialog();
        listDialog.setContext(getBaseContext());
        listDialog.show(getFragmentManager(), "list");
    }

    public void selectArrivalVibe(View view) {
        VibeListDialog listDialog = new VibeListDialog();
        listDialog.setContext(getBaseContext());
        listDialog.show(getFragmentManager(), "vibeList");
        vibeToneFactory.vibeTone(Constants.VibeToneName.MOUNTAIN);

    }

    public void selectDepartSound(View view) {
//        vibeToneFactory.vibeTone(Constants.VibeToneName.VALLEY);

        SparkleListDialog listDialog = new SparkleListDialog();
        listDialog.setContext(getBaseContext());
        listDialog.show(getFragmentManager(), "list");
    }

    public void selectDepartVibe(View view) {
        VibeListDialog listDialog = new VibeListDialog();
        listDialog.setContext(getBaseContext());
        listDialog.show(getFragmentManager(), "vibeList");
        vibeToneFactory.vibeTone(Constants.VibeToneName.SLOW2FAST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uri != null) {
//                String ringTonePath = uri.toString();
                RingtoneManager.setActualDefaultRingtoneUri(
                        this,
                        RingtoneManager.TYPE_RINGTONE,
                        uri);
            }
            Ringtone ringtone = RingtoneManager.getRingtone(this, uri);
//            FaveLocationManager.locList.get(0).setRingtone(ringtone);

        }
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

    public void runBack(View view) {
        finish();
        startActivity(new Intent(NotifSettings.this, SOListActivity.class));
    }
}
