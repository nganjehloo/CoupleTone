package com.cse110.team36.coupletones.GCM;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cse110.team36.coupletones.HomeScreen;
import com.cse110.team36.coupletones.MapsActivity;
import com.cse110.team36.coupletones.R;

/**
 * Created by Nima on 5/7/2016.
 */
public class SOConfig extends AppCompatActivity {
    private static final String TAG = "SOActivity";
    private TextView mInformationTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.so_config);
        Log.d(TAG, "now we chillin");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean sentToken = sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
        mInformationTextView = (TextView) findViewById(R.id.informationTextView);
        if (sentToken) {
            Log.d(TAG, "WOOOOW");
            mInformationTextView.setText(getString(R.string.gcm_send_message));
            mInformationTextView.setText(sharedPreferences.getString("REGID", "nope"));
        } else {
            mInformationTextView.setText(getString(R.string.token_error_message));
        }

        ImageButton mapButton = (ImageButton) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SOConfig.this, MapsActivity.class));
            }});


        ImageButton myLocButton = (ImageButton) findViewById(R.id.myLocButton);
        myLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SOConfig.this, HomeScreen.class));
            }
        });
    }
}
