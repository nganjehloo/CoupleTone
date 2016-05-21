package com.cse110.team36.coupletones.FireBase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cse110.team36.coupletones.R;

/**
 * Created by Duc Le on 5/21/2016.
 */
public class MyFireBaseRegistration extends AppCompatActivity {

    String MY_ID;
    SharedPreferences sharedPreferences;
    FireBaseManager FBman;
    Button regBtn;
    TextView text;

    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        FBman = new FireBaseManager(sharedPreferences);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerinput);

        regBtn = (Button) findViewById(R.id.button);
        text = (TextView) findViewById(R.id.editText);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FireBaseManager fireBaseManager = new FireBaseManager(sharedPreferences);
                fireBaseManager.createAccount( text.getText().toString() );
            }
        });


    }

}
