// use http://developer.android.com/guide/topics/ui/dialogs.html#DialogFragment for this,
// I was on the part about communicating with the activity that called it (the map activity)
package com.cse110.team36.coupletones;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


/**
 * Dialog to appear when naming or renaming locations
 */
public class LocationDialog extends DialogFragment {
    private LatLng coords;
    private Marker marker;

    public interface LocationDialogListener {
        void onDialogPositiveClick(String name, LatLng loc);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    LocationDialogListener listener;

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the LocationDialogListener so we can send events to the host
            listener = (LocationDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement LocationDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View layout = inflater.inflate(R.layout.name_location, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(layout);
        AlertDialog.Builder location = builder.setPositiveButton(R.string.set_name, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String newName = ((EditText) layout.findViewById(R.id.namefield)).getText().toString();
                double loc[] = getArguments().getDoubleArray("location");
                coords = new LatLng(loc[0], loc[1]);
                listener.onDialogPositiveClick(newName, coords);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //listener.onDialogNegativeClick(newName, coords);
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();

    }
}
