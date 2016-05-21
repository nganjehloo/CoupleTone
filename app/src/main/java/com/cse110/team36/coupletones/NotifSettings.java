package com.cse110.team36.coupletones;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class NotifSettings extends AppCompatActivity {
    VibeToneFactory vibeToneFactory;

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
        vibeToneFactory.vibeTone(Constants.VibeToneName.FUNKYTOWN);
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

}
