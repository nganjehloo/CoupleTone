package com.cse110.team36.coupletones.FireBase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cse110.team36.coupletones.GCM.QuickstartPreferences;
import com.cse110.team36.coupletones.HomeScreen;
import com.cse110.team36.coupletones.MapsActivity;
import com.cse110.team36.coupletones.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Nima on 5/7/2016.
 */
public class SOConfig extends AppCompatActivity {
    private static final String TAG = "SOActivity";
    private TextView mInformationTextView;
    public SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = new Intent(SOConfig.this, FirebaseService.class);
        startService(intent);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.so_config);

        initalizeButtons();
        refreshIDView();
    }


    public void refreshIDView(){
        mInformationTextView = (TextView) findViewById(R.id.informationTextView);
        TextView mBaeCode = (TextView) findViewById(R.id.editText);
        mBaeCode.setText(sharedPreferences.getString("SOEMAIL", "bae") + ".com");
        mInformationTextView.setText(sharedPreferences.getString("MYEMAIL", "ERROR") + ".com");
    }

    public void addSO(){

        Button soButton = (Button) findViewById(R.id.button);
        Button soRemoveButton = (Button) findViewById(R.id.button2);
        soRemoveButton.setEnabled(false);

        TextView mBaeCode = (TextView) findViewById(R.id.editText);
        String SOKey = mBaeCode.getText().toString();

        FireBaseManager fb = new FireBaseManager(sharedPreferences);
        if(SOKey.equals(""))
        {
            Toast.makeText(getBaseContext(), "Field can't be blank", Toast.LENGTH_SHORT).show();
        }
        else if(!SOKey.contains("@") || !SOKey.contains("."))
        {
            Toast.makeText(getBaseContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        }
        else
        {
            soButton.setEnabled(false);
            mBaeCode.setEnabled(false);
            soRemoveButton.setEnabled(true);
            fb.addSO(SOKey);
            Toast.makeText(getBaseContext(), "ADDED SO", Toast.LENGTH_SHORT).show();
        }

    }

    public void removeSO(){
        Button soButton = (Button) findViewById(R.id.button);
        Button soRemoveButton = (Button) findViewById(R.id.button2);
        TextView mBaeCode = (TextView) findViewById(R.id.editText);
        FireBaseManager fb = new FireBaseManager(sharedPreferences);
        fb.removeSO();
        soButton.setEnabled(true);
        mBaeCode.setEnabled(true);
        soRemoveButton.setEnabled(false);
        Toast.makeText(getBaseContext(), "Removed SO", Toast.LENGTH_SHORT).show();

    }


    public void initalizeButtons() {
        setContentView(R.layout.so_config);
        final ImageButton mylocations = (ImageButton) findViewById(R.id.myLocButton);
        final ImageButton map = (ImageButton) findViewById(R.id.mapButton);
        final TextView mBaeCode = (TextView) findViewById(R.id.editText);

        mylocations.setBackgroundColor(0xFFFFFF);
        (findViewById(R.id.settingsButton)).setBackgroundResource(R.color.colorButtonDepressed);
        map.setBackgroundColor(0xFFFFFF);

        Button soButton = (Button) findViewById(R.id.button);
        soButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSO();
            }
        });

        Button soRemoveButton = (Button) findViewById(R.id.button2);
        soRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeSO();
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


