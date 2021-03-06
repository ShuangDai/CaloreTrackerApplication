package com.example.a5046assign2;

import android.util.Log;

import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RestCredential {
    private static final String BASE_URL ="http://10.0.0.86:8080/CalorieTrackerApplication/webresources/";

    public static String findByUserName(String username) {
        final String methodPath = "calorie_tracker.credential/findByUserName/"+username;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }


    public static void createCredential(Credential credential){
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="calorie_tracker.credential/";
        try {
            Gson gson =new Gson();
            String stringCourseJson=gson.toJson(credential);
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/json");
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

}
