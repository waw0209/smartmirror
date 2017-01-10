package com.example.jangminjae.wheather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by dohee on 16. 11. 28.
 */


public class CheckInfo {

     String UPLOAD_URL = "";

    int serverResponseCode = 0;

    public String checkInfo(String key,int i){

        String data;

        try {
            data = "&" + URLEncoder.encode("mirror_key", "UTF-8") + "=" + URLEncoder.encode((String)key, "UTF-8");

            if(i==1)UPLOAD_URL  = "http://222.112.247.133:80/list_media.php";
            else if(i==2) UPLOAD_URL = "http://222.112.247.133:80/list_phone.php";


            URL url = new URL(UPLOAD_URL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                sb.append(line);
                break;
            }
            return sb.toString();

        } catch (Exception e) {
            return null;
        }

    }

}
