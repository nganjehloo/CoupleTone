package com.cse110.team36.coupletones.Managers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.cse110.team36.coupletones.Managers.FaveLocationManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by admin on 5/8/16.
 */
public class FileManager {
    private Activity mapsActivity;
//    private FileInputStream fis;
//    private FileOutputStream fos;
    private String savedLocs = "";
    final private String FILENAME = "favorite_locs";
    static Context context;

    public FileManager(Activity mapsActivity /*Context context*/) {
        this.mapsActivity = mapsActivity;
        this.context = context;
    }


    public void importSavedFavLocs() {
        savedLocs = "";
        try {
            FileInputStream fis = mapsActivity.openFileInput(FILENAME);
            int test = 0;
            while (test != -1) {
                test = fis.read();
                if (test != -1)
                    savedLocs += (char) test;
            } fis.close();
        } catch (Exception e) {
            Log.e("File", "File error!");
        }
    }

    public void exportSavedFavLocs() {
        savedLocs = "";

        for (int i = 0; i < FaveLocationManager.locList.size() ; i++ ) {
            savedLocs += FaveLocationManager.locList.get(i).getName() + "\n";
            savedLocs += FaveLocationManager.locList.get(i).getLat() + "\n";
            savedLocs += FaveLocationManager.locList.get(i).getLng() + "\n";
        }


        try {
            FileOutputStream fos = mapsActivity.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(savedLocs.getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e("File", "File error!");
        }
    }

    public String getSavedLocs() {
        return savedLocs;
    }
}
