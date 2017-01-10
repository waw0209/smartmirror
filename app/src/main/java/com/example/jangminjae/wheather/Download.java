package com.example.jangminjae.wheather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by dohee on 16. 12. 5.
 */

public class Download {                     // 서버의 정보 읽어오기

    public String downloadContact(String key){

        String UPLOAD_URL = "http://222.112.247.133:80/list_phone.php";
        String data;
        try{

            data = "&" + URLEncoder.encode("mirror_key", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8");

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
        }
        catch(Exception e){
            return null;
        }
    }

    public String downloadSchedule(String date, String key){

        String UPLOAD_URL = "http://222.112.247.133:80/list_schedule.php";
        String data;
        try{

            data = "&" + URLEncoder.encode("mirror_key", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8");
            data += "&" + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");

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
        }
        catch(Exception e){
            return null;
        }
    }

    public String downloadCalendar(String key){

        String UPLOAD_URL = "http://222.112.247.133:80/list_Point.php";
        String data;
        try{

            data = "&" + URLEncoder.encode("mirror_key", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8");

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
        }
        catch(Exception e){
            return null;
        }
    }
}
