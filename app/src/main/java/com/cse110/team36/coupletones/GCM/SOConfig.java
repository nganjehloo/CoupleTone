package com.cse110.team36.coupletones.GCM;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cse110.team36.coupletones.GCM.Server.Content;
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

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean sentToken = sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
        mInformationTextView = (TextView) findViewById(R.id.informationTextView);
        if (sentToken) {
            Log.d(TAG, "WOOOOW");
            mInformationTextView.setText(getString(R.string.gcm_send_message));
            mInformationTextView.setText(sharedPreferences.getString("REGID", "nope"));
        } else {
            mInformationTextView.setText(getString(R.string.token_error_message));
        }

        final ImageButton mylocations = (ImageButton) findViewById(R.id.myLocButton);
        final ImageButton map = (ImageButton) findViewById(R.id.mapButton);
        mylocations.setBackgroundColor(0xFFFFFF);
        (findViewById(R.id.settingsButton)).setBackgroundResource(R.color.colorButtonDepressed);
        map.setBackgroundColor(0xFFFFFF);

        Button soButton = (Button) findViewById(R.id.button);
        soButton.setOnClickListener(new View.OnClickListener() {
            TextView mBaeCode = (TextView) findViewById(R.id.editText);

            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putString("SOREGID", (mBaeCode.getText()).toString());
                Toast.makeText(getBaseContext(), (mBaeCode.getText()).toString() , Toast.LENGTH_SHORT).show();
                //sendNotification
            }});


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
