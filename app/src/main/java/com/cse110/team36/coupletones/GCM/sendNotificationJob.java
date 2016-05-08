package com.cse110.team36.coupletones.GCM;

import android.os.AsyncTask;

import com.cse110.team36.coupletones.GCM.Server.Content;
import com.cse110.team36.coupletones.GCM.Server.Post2Gcm;

/**
 * Created by Duc Le on 5/7/2016.
 */

import com.cse110.team36.coupletones.GCM.Server.Content;

public class sendNotificationJob extends AsyncTask<String, Void, String> {

   // String SOKey;
    //String message;


    /*public sendNotificationJob(String key, String msg)
    {
        SOKey = key;
        message = msg;
    }*/

    @Override
    protected String doInBackground(String[] params) {
        Content content = new Content();
        String apiKey = "AIzaSyCnduESq54RmoStkgClt_W_eF6Ox_WiDwY";
        System.out.println("SENDING '" + params[1] + "' TO SO " + params[0]);


        content.addRegId(params[0]);
        content.createData("From " + params[0], params[1]);

        Post2Gcm.post(apiKey, content);
        return "some message";
    }

    @Override
    protected void onPostExecute(String message) {
        //process message
    }
}