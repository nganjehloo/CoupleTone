package com.cse110.team36.coupletones;

import android.content.Context;
import android.util.Log;

import com.cse110.team36.coupletones.FaveLocationManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by admin on 5/8/16.
 */
public class SavedLocFileIO {
    public static FileInputStream fis;
    public static FileOutputStream fos;
    public static String savedLocs = "";
    final public static String FILENAME = "favorite_locs";
    static Context context;

    public SavedLocFileIO(Context context) { this.context = context; }


    public static void importSavedFavLocs() {
        try {
            fis = context.openFileInput(FILENAME);
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

    public static void exportSavedFavLocs() {
//        String FILENAME = "favorite_locs";

        savedLocs = "";

        for ( int i = 0 ; i < FaveLocationManager.locList.size() ; i++ ) {
            savedLocs += FaveLocationManager.locList.get(i).getName() + "\n";
            savedLocs += FaveLocationManager.locList.get(i).getLat() + "\n";
            savedLocs += FaveLocationManager.locList.get(i).getLng() + "\n";
        }


        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(savedLocs.getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e("File", "File error!");
        }
    }
}
