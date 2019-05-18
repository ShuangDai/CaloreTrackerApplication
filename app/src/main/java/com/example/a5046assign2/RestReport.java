package com.example.a5046assign2;

import android.util.Log;

import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RestReport {
    private static final String BASE_URL ="http://10.0.0.86:8080/CalorieTrackerApplication/webresources/";

    public static String getReportByDate(String userId, String reportDate) {
        final String methodPath = "calorie_tracker.report/getReport/"+userId+"/"+reportDate;
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

    public static String getPeriodReportByDate(String userId, String startDate, String endDate) {
        final String methodPath = "calorie_tracker.report/getPeriodReport/"+userId+"/"+startDate+"/"+endDate;
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


    public static void createCalorieReport(Report report){
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="calorie_tracker.report/";
        try {
            Gson gson =new Gson();
            String stringCourseJson=gson.toJson(report);
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
