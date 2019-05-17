package com.example.a5046assign2;

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
}
