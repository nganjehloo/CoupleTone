package com.cse110.team36.coupletones.FireBase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cse110.team36.coupletones.R;

/**
 * Created by Duc Le on 5/21/2016.
 */
public class MyFireBaseRegistration extends AppCompatActivity {

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
                if(text.getText().toString().equals(""))
                {
                    Toast.makeText(getBaseContext(), "Field can't be blank", Toast.LENGTH_SHORT).show();
                }
                else if(!text.getText().toString().contains("@") || !text.getText().toString().contains("."))
                {
                    Toast.makeText(getBaseContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                }
                else if(text.getText().toString().contains(" "))
                {
                    Toast.makeText(getBaseContext(), "No spaces allowed in email address", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    fireBaseManager.createAccount(text.getText().toString());
                    startActivity(new Intent(MyFireBaseRegistration.this, SOConfig.class));
                }
            }
        });


    }

}
