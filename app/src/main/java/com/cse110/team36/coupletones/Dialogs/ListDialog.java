package com.cse110.team36.coupletones.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.cse110.team36.coupletones.Constants;
import com.cse110.team36.coupletones.Managers.SOFaveLocManager;
import com.firebase.client.Firebase;

/**
 * Created by stazia on 5/31/16.
 */
public abstract class ListDialog extends DialogFragment implements Constants{
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
        SOLocationName = SOFaveLocManager.locList.get(locListPos).getName();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SOEmail = sharedPreferences.getString("SOEMAIL", null);
        return null;
    }
}
