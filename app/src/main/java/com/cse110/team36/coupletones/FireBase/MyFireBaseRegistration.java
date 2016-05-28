package com.cse110.team36.coupletones.FireBase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
                String email = text.getText().toString().trim();
                CharSequence emailCS = email;
                if(isValidEmail(emailCS) == false)
                {
                    Toast.makeText(getBaseContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    fireBaseManager.createAccount(email);
                    startActivity(new Intent(MyFireBaseRegistration.this, SOConfig.class));
                }
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

}
