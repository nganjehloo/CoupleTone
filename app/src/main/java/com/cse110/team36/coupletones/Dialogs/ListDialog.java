package com.cse110.team36.coupletones.Dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.cse110.team36.coupletones.R;

import java.util.ArrayList;

/**
 * Created by stazia on 5/26/16.
 */
public class ListDialog extends DialogFragment {
    CharSequence[] sparkles = {"Funkytown short", "Funkytown long"};
    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle("Pick your sparkleTone")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(sparkles,-1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which)
                        {
                            case 0:
                                // Your code when first option selected
                                MediaPlayer mediaPlayer0 = MediaPlayer.create(context, R.raw.funkytown1);
                                mediaPlayer0.start();
                                break;
                            case 1:
                                // Your code when 2nd  option selected
                                MediaPlayer mediaPlayer1 = MediaPlayer.create(context, R.raw.funkytown1);
                                mediaPlayer1.start();
                                break;
                        }
                    }
                })
                // Set the action buttons
                .setPositiveButton(R.string.set_name, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

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
