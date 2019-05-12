package com.example.a5046assign2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;

public class SearchGoogleAPI {
    private static final String API_KEY = "AIzaSyCBKOoDbZ-TBLX0IsWAJSJFM7E5fV6fmc0";
    private static final String SEARCH_ID_cx = "004521304641054781421:rl34qs_bjfm";

    public static String search(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter = "";
        if (params != null && values != null) {
            for (int i = 0; i < params.length; i++) {
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key=" +
                    API_KEY + "&cx=" + SEARCH_ID_cx + "&q=" + keyword +" food" + query_parameter);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return textResult;
    }

    public static String getSnippet(String result) {
        String snippet = null;
        String my_new_str = null;

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if (jsonArray != null && jsonArray.length() > 0) {
                snippet = jsonArray.getJSONObject(0).getString("title") +
                        jsonArray.getJSONObject(0).getString("snippet");
                my_new_str = snippet.replace("...", ".");
            }
        } catch (Exception e) {
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return my_new_str;
    }

    public static String getImageURL(String result) {
        String url = null;
        String imageUrl = null;
        Log.i("mytag", result);

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if (jsonArray != null && jsonArray.length() > 0) {
                url = jsonArray.getJSONObject(0).getString("pagemap");
                Log.i("mytag", url);
                JSONObject jsonObject2 = new JSONObject(url);
                JSONArray jsonArray2 = jsonObject2.getJSONArray("cse_image");
                imageUrl = jsonArray2.getJSONObject(0).getString("src");
                Log.i("mytag", imageUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            url = "NO INFO FOUND";
        }
        return imageUrl;
    }
}