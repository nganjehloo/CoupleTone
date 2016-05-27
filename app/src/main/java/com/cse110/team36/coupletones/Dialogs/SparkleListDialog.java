package com.cse110.team36.coupletones.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.R;
import com.cse110.team36.coupletones.SparkleToneFactory;
import com.cse110.team36.coupletones.VibeToneFactory;

/**
 * Created by stazia on 5/26/16.
 */
public class SparkleListDialog extends DialogFragment implements Constants {
    Context context;
    Activity activity;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the dialog title
        builder.setTitle("Pick your sparkleTone")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(,-1, new DialogInterface.OnClickListener() {
                    SparkleToneFactory factory = new SparkleToneFactory(activity, context);

                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        factory.sparkle(SparkleToneName.values()[pos]);
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
