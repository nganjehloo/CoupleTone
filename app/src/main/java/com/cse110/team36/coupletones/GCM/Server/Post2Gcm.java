package com.cse110.team36.coupletones.GCM.Server;

/**
 * Created by Duc Le on 5/6/2016.
 */
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Post2Gcm {
    public static void post(String apiKey, Content content) {
        try {

            URL url = new URL("https://android.googleapis.com/gcm/send");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "key="+apiKey);

            conn.setDoOutput(true);

            // Use Jackson object mapper to convert Content object into JSON
            ObjectMapper mapper = new ObjectMapper();

            mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

            //Get connection output stream
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

            //Copy Content "JSON" into wr
            mapper.writeValue(wr, content);

            //Send the request
            wr.flush();

            //Close
            wr.close();

            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'POST' request to URL:" + url);
            System.out.println("Response Code: " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            System.out.println(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


