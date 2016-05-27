package com.cse110.team36.coupletones.FireBase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cse110.team36.coupletones.maps.MapsActivity;
import com.cse110.team36.coupletones.R;
import com.cse110.team36.coupletones.lists.SOVisitedActivity;

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

        //set button and edit text fields
        if(!sharedPreferences.getBoolean("isAdded", false))
        {
            enableAddFields();
        }
        else
        {
            disableAddFields();
        }
    }


    public void refreshIDView(){
        mInformationTextView = (TextView) findViewById(R.id.informationTextView);
        TextView mBaeCode = (TextView) findViewById(R.id.editText);
        mBaeCode.setText(sharedPreferences.getString("SOEMAIL", "bae") + ".com");
        mInformationTextView.setText(sharedPreferences.getString("MYEMAIL", "ERROR") + ".com");
    }

    public void addSO(){

        TextView mBaeCode = (TextView) findViewById(R.id.editText);
        String SOKey = mBaeCode.getText().toString().trim();

        FireBaseManager fb = new FireBaseManager(sharedPreferences);
        if(isValidEmail(SOKey) == false)
        {
            Toast.makeText(getBaseContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        }
        else
        {
            disableAddFields();
            sharedPreferences.edit().putBoolean("isAdded", true).apply();
            fb.addSO(SOKey);
            Toast.makeText(getBaseContext(), "ADDED SO", Toast.LENGTH_SHORT).show();
        }

    }

    public void removeSO(){
        FireBaseManager fb = new FireBaseManager(sharedPreferences);
        fb.removeSO();
        enableAddFields();
        sharedPreferences.edit().putBoolean("isAdded", false).apply();
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


        ImageButton SOLocButton = (ImageButton) findViewById(R.id.myLocButton);
        SOLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SOConfig.this, SOVisitedActivity.class));
            }
        });
    }

    private final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private void enableAddFields()
    {
        Button soButton = (Button) findViewById(R.id.button);
        Button soRemoveButton = (Button) findViewById(R.id.button2);
        TextView mBaeCode = (TextView) findViewById(R.id.editText);

        soButton.setEnabled(true);
        mBaeCode.setEnabled(true);
        soRemoveButton.setEnabled(false);
    }

    private void disableAddFields()
    {
        Button soButton = (Button) findViewById(R.id.button);
        Button soRemoveButton = (Button) findViewById(R.id.button2);
        TextView mBaeCode = (TextView) findViewById(R.id.editText);

        soButton.setEnabled(false);
        mBaeCode.setEnabled(false);
        soRemoveButton.setEnabled(true);
    }
}


