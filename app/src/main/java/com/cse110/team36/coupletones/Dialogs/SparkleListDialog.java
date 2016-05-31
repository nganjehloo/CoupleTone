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
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

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
    int idx;

    int locListPos;
    int savePos;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setPositions(int locPosition, int idx) {
        this.locListPos = locPosition;
        this.idx = idx;
        this.savePos = idx;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i("STAZ 1", Integer.toString(idx));
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SOEmail = sharedPreferences.getString("SOEMAIL", null);

        sparkles = new String[NUM_SPARKLE_TONES];
        for (int i=0;i<NUM_SPARKLE_TONES;i++)
            sparkles[i] = SparkleToneName.values()[i].toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Log.i("STAZ before builder", Integer.toString(idx));
        // Set the dialog title
        builder.setTitle("Pick your sparkleTone")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected

                .setSingleChoiceItems(sparkles,idx, new DialogInterface.OnClickListener() {
                    SparkleToneFactory factory = new SparkleToneFactory();

                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        savePos = pos;
                        Log.i("STAZ in onClick", Integer.toString(idx));
                        factory.sparkle(SparkleToneName.values()[pos], context);
                    }
                })

                // Set the action buttons
                .setPositiveButton(R.string.set_name, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (getTag().equals("arrivalSparkleList")) {
                            Log.d("MAP", "SPARKLE ARRIVAL: ");
                            Log.i("STAZ on Set", Integer.toString(idx));
                            SOFaveLocManager.locList.get(locListPos).changeArrivalSparkleTone(SparkleToneName.values()[savePos]);
                            SOLocationName = SOFaveLocManager.locList.get(locListPos).getName();
                            SOFirebaseSettings = new Firebase("https://coupletones36.firebaseio.com/" + SOEmail + "/Locations/" + SOLocationName);
                            SOFirebaseSettings.child("arrivalSound").setValue(savePos);
                            Log.i("STAZ after firebase", Integer.toString(savePos));

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
        Log.i("STAZ exiting", Integer.toString(idx));
        return builder.create();
    }
}
