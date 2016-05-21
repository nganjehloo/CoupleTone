package com.cse110.team36.coupletones;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class NotifSettings extends AppCompatActivity {
//    VibeToneFactory vibeToneFactory = new VibeToneFactory(this);
    VibeToneFactory vibeToneFactory;
    Uri ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        vibeToneFactory = new VibeToneFactory(this);
    }

    public void selectArrivalSound(View view) {
        //vibeToneFactory.vibeTone(Constants.VibeToneName.FUNKYTOWN);
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select sound for notifications:");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,RingtoneManager.TYPE_NOTIFICATION);

        // for existing ringtone
        Uri uri =     RingtoneManager.getActualDefaultRingtoneUri(
                getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, uri);

        this.startActivityForResult(intent,999);
    }
    public void selectArrivalVibe(View view) {
        vibeToneFactory.vibeTone(Constants.VibeToneName.MOUNTAIN);
    }
    public void selectDepartSound(View view) {
        vibeToneFactory.vibeTone(Constants.VibeToneName.VALLEY);
    }
    public void selectDepartVibe(View view) {
        vibeToneFactory.vibeTone(Constants.VibeToneName.SLOW2FAST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uri != null) {
                String ringTonePath = uri.toString();
                RingtoneManager.setActualDefaultRingtoneUri(
                        this,
                        RingtoneManager.TYPE_RINGTONE,
                        uri);
            }
            Ringtone ringtone = RingtoneManager.getRingtone(this, uri);
            ringtone.play();
        }
    }
}
