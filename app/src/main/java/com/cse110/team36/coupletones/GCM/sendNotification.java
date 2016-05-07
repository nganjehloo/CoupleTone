package com.cse110.team36.coupletones.GCM;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.preference.PreferenceManager;
import android.util.Log;

import com.cse110.team36.coupletones.GCM.Server.Content;
import com.cse110.team36.coupletones.GCM.Server.Post2Gcm;
import com.cse110.team36.coupletones.GCM.Server.TestSendMessage;
import com.cse110.team36.coupletones.MapsActivity;
import com.cse110.team36.coupletones.MyContext;

/**
 * Created by Duc Le on 5/7/2016.
 */
public class sendNotification extends Activity {


    /*
     * Send a notification message to your SO when arrive at a favorite location
     */
    public static void arrivalMsg(String SOKey, String message)
    {
        createContent(SOKey, message);
        Log.i("TAG", "KEY !!!!!!" + SOKey);
    }

    public void addSO(String SOKey, String message)
    {
        createContent(SOKey, message);
    }

    public static void createContent(String SOKey, String message) {

        Content content = new Content();
        String apiKey = "AIzaSyCnduESq54RmoStkgClt_W_eF6Ox_WiDwY";

        System.out.println(SOKey);
        //content.addRegId("ea_KQUzNVPk:APA91bF6mjY2Knojq2MZKuVk7oTPx9zhlyAWv6JHidXdaglrqrtMlcSM-ujbm3B95qozku6i67JTxKB6Zlp87hY_yC4i3CL-8h7Vi8mow33lOikgE8E9bcg5fsHnCPBX-h9pPiKP6AZY");
        //c.addRegId("edQ7oSRilEs:APA91bHHSE_ekkpKw3PtYwFINdjJNXvJ12CU_Crat9ewYZomyyDPqe0coOpTx6gGQo4ZV4WoSVqty_qEDRZXAHHY1j3IUCk4dKxut4uCnIwTFyyr7jMYZ6PXd6iFKCrGNxVMxXHgKJ0q");

        content.addRegId(SOKey);
        content.createData("Working!!", message);

        Post2Gcm.post(apiKey, content);
    }
}


