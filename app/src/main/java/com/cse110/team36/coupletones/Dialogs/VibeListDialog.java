package com.cse110.team36.coupletones.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.cse110.team36.coupletones.Managers.SOFaveLocManager;
import com.cse110.team36.coupletones.R;
import com.cse110.team36.coupletones.VibeToneFactory;
import com.firebase.client.Firebase;

/**
 * Created by stazia on 5/26/16.
 */
public class VibeListDialog extends ListDialog {
    String[] vibes;
    VibeToneFactory vibeToneFactory;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        vibes = new String[NUM_VIBE_TONES];
            for (int i=0;i<NUM_VIBE_TONES;i++)
                vibes[i] = VibeToneName.values()[i].toString();

        vibeToneFactory = new VibeToneFactory(activity);
        // Set the dialog title
        builder.setTitle("Pick your vibeTone")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(vibes,idx, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        savePos = pos;
                        vibeToneFactory.vibeTone(VibeToneName.values()[pos]);
                    }
                })
                // Set the action buttons
                .setPositiveButton(R.string.set_name, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        SOLocationName = SOFaveLocManager.locList.get(locListPos).getName();
                        SOFirebaseSettings = new Firebase("https://coupletones36.firebaseio.com/" + SOEmail + "/Locations/" + SOLocationName);

                        if (getTag().equals("arrivalVibeList")) {
                            Log.d("MAP", "VIBE ARRIVAL");
                            SOFirebaseSettings.child("arrivalVibration").setValue(savePos);
                        } else if (getTag().equals("departVibeList")) {
                            Log.d("MAP", "VIBE DEPART");
                            SOFirebaseSettings.child("departureVibration").setValue(savePos);
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
