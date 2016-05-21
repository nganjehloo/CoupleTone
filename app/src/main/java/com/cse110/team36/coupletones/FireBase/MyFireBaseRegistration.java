package com.cse110.team36.coupletones.FireBase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cse110.team36.coupletones.R;

/**
 * Created by Duc Le on 5/21/2016.
 */
public class MyFireBaseRegistration extends AppCompatActivity {

    String MY_ID;
    SharedPreferences sharedPreferences;
    FireBaseManager FBman;
    Button regBtn;
    EditText text;

    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        FBman = new FireBaseManager(sharedPreferences);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerinput);

        regBtn = (Button) findViewById(R.id.button);
        text = (EditText) findViewById(R.id.editText);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

}
