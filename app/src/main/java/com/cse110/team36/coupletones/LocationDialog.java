package com.cse110.team36.coupletones;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.cse110.team36.coupletones.R;
import com.google.android.gms.maps.model.LatLng;

/**
 * Dialog to appear when naming or renaming locations
 */
public class LocationDialog extends DialogFragment {
    LatLng spot;

    public LocationDialog(LatLng spot) {
        super();
        this.spot = spot;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.name_location, null));
        builder.setPositiveButton(R.string.set_name, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //FaveLocation newLoc = new FaveLocation();
            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
