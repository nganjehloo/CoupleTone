package com.cse110.team36.coupletones.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.Managers.SOFaveLocManager;
import com.cse110.team36.coupletones.R;
import com.cse110.team36.coupletones.SparkleToneFactory;
import com.firebase.client.Firebase;

/**
 * Created by stazia on 5/26/16.
 */
public class SparkleListDialog extends ListDialog {
    SparkleToneFactory factory = new SparkleToneFactory();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        sparkles = new String[NUM_SPARKLE_TONES];
        for (int i=0;i<NUM_SPARKLE_TONES;i++)
            sparkles[i] = SparkleToneName.values()[i].toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the dialog title
        builder.setTitle("Pick your sparkleTone")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected

                .setSingleChoiceItems(sparkles,idx, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        if (factory.isPlaying()) {
                            factory.pause();
                        }
                        savePos = pos;
                        factory.sparkle(SparkleToneName.values()[pos], context);
                    }
                })

                // Set the action buttons
                .setPositiveButton(R.string.set_name, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        SOLocationName = SOFaveLocManager.locList.get(locListPos).getName();
                        SOFirebaseSettings = new Firebase("https://coupletones36.firebaseio.com/" + SOEmail + "/Locations/" + SOLocationName);

                        if (getTag().equals("arrivalSparkleList")) {
                            SOFirebaseSettings.child("arrivalSound").setValue(savePos);
                        } else if (getTag().equals("departSparkleList")) {
                            SOFirebaseSettings.child("departureSound").setValue(savePos);
                        }

                        factory.destroy();
                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {}
                });

        return builder.create();
    }
}
