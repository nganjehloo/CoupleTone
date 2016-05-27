package com.cse110.team36.coupletones.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.Managers.FaveLocationManager;
import com.cse110.team36.coupletones.Managers.SOFaveLocManager;
import com.cse110.team36.coupletones.R;
import com.cse110.team36.coupletones.VibeToneFactory;

/**
 * Created by stazia on 5/26/16.
 */
public class VibeListDialog extends DialogFragment implements Constants {
    CharSequence[] sparkles = {"Funkytown short", "Funkytown long", "Comm"};
    String[] vibes;
    Context context;
    Activity activity;
    VibeToneFactory vibeToneFactory;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        vibes = new String[NUM_VIBE_TONES];
            for (int i=0;i<NUM_VIBE_TONES;i++)
                vibes[i] = VibeToneName.values()[i].toString();

        vibeToneFactory = new VibeToneFactory(activity);
        // Set the dialog title
        builder.setTitle("Pick your vibeTone")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(vibes,-1, new DialogInterface.OnClickListener() {

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
                        if (getTag().equals("arrivalVibeList")) {
                            SOFaveLocManager.locList.get(locListPos).changeArrivalVibeTone(VibeToneName.values()[savePos]);
                            Log.d("MAP", "ARRIVAL");
                    } else if (getTag().equals("departVibeList")) {
                            SOFaveLocManager.locList.get(locListPos).changeDepartVibeTone(VibeToneName.values()[savePos]);
                            Log.d("MAP", "DEPART");
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
