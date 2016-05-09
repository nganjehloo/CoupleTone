package com.cse110.team36.coupletones;

import android.content.Context;
import android.util.Log;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by admin on 5/8/16.
 */
public class SavedLocFileIO {
    public FileInputStream fis;
    public FileOutputStream fos;
    public String savedLocs = "";
    final public String FILENAME = "favorite_locs";
    static Context context;

//    public SavedLocFileIO(Context context) { this.context = context; }


    public void importSavedFavLocs() {
        savedLocs = "";
        try {
            fis = MyContext.getAppContext().openFileInput(FILENAME);
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

        for ( int i = 0 ; i < FaveLocationManager.locList.size() ; i++ ) {
            savedLocs += FaveLocationManager.locList.get(i).getName() + "\n";
            savedLocs += FaveLocationManager.locList.get(i).getLat() + "\n";
            savedLocs += FaveLocationManager.locList.get(i).getLng() + "\n";
        }


        try {
            fos = MyContext.getAppContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(savedLocs.getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e("File", "File error!");
        }
    }
}
