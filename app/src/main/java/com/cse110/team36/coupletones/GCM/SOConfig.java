package com.cse110.team36.coupletones.GCM;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cse110.team36.coupletones.FireBase.FireBaseManager;
import com.cse110.team36.coupletones.GCM.Server.Content;
import com.cse110.team36.coupletones.HomeScreen;
import com.cse110.team36.coupletones.MapsActivity;
import com.cse110.team36.coupletones.R;

import org.w3c.dom.Text;

/**
 * Created by Nima on 5/7/2016.
 */
public class SOConfig extends AppCompatActivity {
    private static final String TAG = "SOActivity";
    private TextView mInformationTextView;
    public SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.so_config);

        refreshCoupleCode();
        initalizeButtons();
        refreshIDView();
    }


    public void refreshIDView(){
        refreshCoupleCode();

        boolean sentToken = sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
        mInformationTextView = (TextView) findViewById(R.id.informationTextView);
        TextView mBaeCode = (TextView) findViewById(R.id.editText);

        mInformationTextView.setText(sharedPreferences.getString("MYEMAIL", "ERROR"));
    }

    public void addSO(){

        TextView mBaeCode = (TextView) findViewById(R.id.editText);
        String SOKey = mBaeCode.getText().toString();

        FireBaseManager fb = new FireBaseManager(sharedPreferences);
        fb.addSO(SOKey);

        /*
        TextView mBaeCode = (TextView) findViewById(R.id.editText);
        Button addremovebtn = (Button) findViewById(R.id.button);

        String SOKey = mBaeCode.getText().toString();

        sharedPreferences.edit().putString("SOREGID", (mBaeCode.getText()).toString()).apply();
        sharedPreferences.edit().putBoolean("HAS_SO", true).apply();


        Toast.makeText(getBaseContext(), (mBaeCode.getText()).toString(), Toast.LENGTH_SHORT).show();
        //sendNotification

        String[] param = {SOKey, "a" + sharedPreferences.getString("MYREGID", null)};
        sendNotificationJob job = new sendNotificationJob();
        job.execute(param);*/

    }

    public void removeSO(){
        TextView mBaeCode = (TextView) findViewById(R.id.editText);
        Button addremovebtn = (Button) findViewById(R.id.button);
        String SOKey = mBaeCode.getText().toString();

        sharedPreferences.edit().putBoolean("HAS_SO", false).apply();
        sharedPreferences.edit().remove("SOREGID").apply();

        Toast.makeText(getBaseContext(), "Removed SO", Toast.LENGTH_SHORT).show();
        //sendNotification

        String[] param = {SOKey, "e" + sharedPreferences.getString("MYREGID", null)};
        sendNotificationJob job = new sendNotificationJob();
        job.execute(param);

    }

    public void refreshCoupleCode(){
        TextView mBaeCode = (TextView) findViewById(R.id.editText);
        mBaeCode.setText(sharedPreferences.getString("SOREGID", "NO SO ID"));
    }

    public void initalizeButtons() {
        setContentView(R.layout.so_config);
        final ImageButton mylocations = (ImageButton) findViewById(R.id.myLocButton);
        final ImageButton map = (ImageButton) findViewById(R.id.mapButton);

        mylocations.setBackgroundColor(0xFFFFFF);
        (findViewById(R.id.settingsButton)).setBackgroundResource(R.color.colorButtonDepressed);
        map.setBackgroundColor(0xFFFFFF);

        Button soButton = (Button) findViewById(R.id.button);
        soButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(sharedPreferences.getBoolean("HAS_SO", false))) {
                    addSO();
                }
            }
        });

        Button soButton1 = (Button) findViewById(R.id.button2);
        soButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((sharedPreferences.getBoolean("HAS_SO", false))) {
                    removeSO();
                }
            }
        });

        ImageButton mapButton = (ImageButton) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SOConfig.this, MapsActivity.class));
            }
        });


        ImageButton myLocButton = (ImageButton) findViewById(R.id.myLocButton);
        myLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SOConfig.this, HomeScreen.class));
            }
        });
    }
}


