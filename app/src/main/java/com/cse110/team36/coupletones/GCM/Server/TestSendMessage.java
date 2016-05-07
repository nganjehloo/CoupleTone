package com.cse110.team36.coupletones.GCM.Server;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cse110.team36.coupletones.GCM.RegistrationIntentService;
import com.cse110.team36.coupletones.GCM.SOActivity;
import com.cse110.team36.coupletones.GCM.sendNotification;
import com.cse110.team36.coupletones.MyContext;

/**
 * Created by Duc Le on 5/7/2016.
 */
public class TestSendMessage {

    public static void main(String[] args) {

        System.out.println("Sending POST to GCM");

        String apiKey = "AIzaSyCnduESq54RmoStkgClt_W_eF6Ox_WiDwY";
        //Content content = createContent();

       // sendNotification send = new sendNotification();
        //send.arrivalMsg("Burger King", "13", "37", "8:15AM");
        //sendNotification.arrivalMsg("Burger King", "13", "37", "8:15AM");
        //Post2Gcm.post(apiKey, content);

    }

    public static Content createContent() {
        Content c = new Content();

        SharedPreferences sendPreferences = PreferenceManager.getDefaultSharedPreferences(MyContext.getAppContext());
        String SOKey = sendPreferences.getString("REGID", null);

        //c.addRegId("ea_KQUzNVPk:APA91bF6mjY2Knojq2MZKuVk7oTPx9zhlyAWv6JHidXdaglrqrtMlcSM-ujbm3B95qozku6i67JTxKB6Zlp87hY_yC4i3CL-8h7Vi8mow33lOikgE8E9bcg5fsHnCPBX-h9pPiKP6AZY");
        //c.addRegId("edQ7oSRilEs:APA91bHHSE_ekkpKw3PtYwFINdjJNXvJ12CU_Crat9ewYZomyyDPqe0coOpTx6gGQo4ZV4WoSVqty_qEDRZXAHHY1j3IUCk4dKxut4uCnIwTFyyr7jMYZ6PXd6iFKCrGNxVMxXHgKJ0q");
        c.createData("Working!!", "Ayy BBy want sum fuk?");
        c.addRegId(SOKey);

        return c;
    }
}
