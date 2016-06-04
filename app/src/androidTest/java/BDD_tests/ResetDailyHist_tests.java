package BDD_tests;

import com.firebase.client.Firebase;

/**
 * Created by Nima on 6/3/2016.
 */
public class ResetDailyHist_tests  {
    public ResetDailyHist_tests(){}

    public void reset3AM(){
        long epoch = System.currentTimeMillis()/1000;
        String time = new java.text.SimpleDateFormat("h:mm:ss a").format(new java.util.Date (epoch*1000));

        assert(time.equals("3:00:00 AM"));
    }

    public void noreset3AM(){
        long epoch = System.currentTimeMillis()/1000;
        String time = new java.text.SimpleDateFormat("h:mm:ss a").format(new java.util.Date (epoch*1000));

        assert(!time.equals("3:00:00 AM"));
    }
}
