package com.cse110.team36.coupletones.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.Managers.SOFaveLocManager;
import com.cse110.team36.coupletones.R;
import com.cse110.team36.coupletones.SparkleToneFactory;
import com.cse110.team36.coupletones.VibeToneFactory;
import com.firebase.client.Firebase;

/**
 * Created by stazia on 5/26/16.
 */
public class SparkleListDialog extends DialogFragment implements Constants {
    Context context;
    Activity activity;
    String[] sparkles;
    Firebase SOFirebaseSettings;
    String SOLocationName;
    SharedPreferences sharedPreferences;
    String SOEmail;

    int locListPos;
    int savePos;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setPosition(int position) {
        this.locListPos = position;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SOEmail = sharedPreferences.getString("SOEMAIL", null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        sparkles = new String[NUM_SPARKLE_TONES];
        for (int i=0;i<NUM_SPARKLE_TONES;i++)
            sparkles[i] = SparkleToneName.values()[i].toString();

        // Set the dialog title
        builder.setTitle("Pick your sparkleTone")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(sparkles,-1, new DialogInterface.OnClickListener() {
                    SparkleToneFactory factory = new SparkleToneFactory(activity, context);

                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        savePos = pos;
                        factory.sparkle(SparkleToneName.values()[pos], context);
                    }
                })
                // Set the action buttons
                .setPositiveButton(R.string.set_name, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (getTag().equals("arrivalSparkleList")) {
                            Log.d("MAP", "SPARKLE ARRIVAL: ");
                            SOFaveLocManager.locList.get(locListPos).changeArrivalSparkleTone(SparkleToneName.values()[savePos]);
                            SOLocationName = SOFaveLocManager.locList.get(locListPos).getName();
                            SOFirebaseSettings = new Firebase("https://coupletones36.firebaseio.com/" + SOEmail + "/Locations/" + SOLocationName);
                            SOFirebaseSettings.child("arrivalSound").setValue(savePos);

                        } else if (getTag().equals("departSparkleList")) {
                            Log.d("MAP", "SPARKLE DEPART: ");
                            SOFaveLocManager.locList.get(locListPos).changeDepartSparkleTone(SparkleToneName.values()[savePos]);
                            SOLocationName = SOFaveLocManager.locList.get(locListPos).getName();
                            SOFirebaseSettings = new Firebase("https://coupletones36.firebaseio.com/" + SOEmail + "/Locations/" + SOLocationName);
                            SOFirebaseSettings.child("departureSound").setValue(savePos);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }
}
