package com.cse110.team36.coupletones.GCM.Server;

/**
 * Created by Duc Le on 5/6/2016.
 */
import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Content implements Serializable{

    private static final long serialVersionUID = 1L;
    private List<String> registration_ids;
    private Map<String, String> data;



    public void addRegId(String regId) {
        if(registration_ids == null) {
            registration_ids = new LinkedList<String>();
        }
        System.out.println("Content says SO ID IS " + regId);
        System.out.println("Content has " + registration_ids.size() + " SO ID'S");
        registration_ids.add(regId);
    }

    public void createData(String title, String message) {
        if(data == null) {
            data = new HashMap<String, String>();
        }

        System.out.println("Content says message is " + message);
        System.out.println("Content says message title is " + title);
        data.put("title", title);
        data.put("message", message);
    }

    public void printContent(){
        System.out.println("Content has " + registration_ids.size() + " SO ID'S");

        for (String c : registration_ids) {
            System.out.println("Content says SO ID IS " + c);
        }

        for(Map.Entry<String, String> c : data.entrySet()) {
            System.out.println("Content says message is " + c.getValue());
            System.out.println("Content says message title is " + c.getKey());
        }
    }
}