package com.example.a5046assign2;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class test {
    private static final String oauth_consumer_key = "375aed0784df42119808a2188bb02f75";
    private static final String oauth_signature_method ="HMAC-SHA1";
    private static final String oauth_timestamp=Long.valueOf(System.currentTimeMillis() / 1000).toString();
    private static final String oauth_version="1.0";
    final static private String APP_SECRET="b19e74b6ef934da8884256558b43d102";
    final static private String APP_URL = "http://platform.fatsecret.com/rest/server.api";
    final static private String APP_METHOD = "GET";

    private static String getOauth_signature(String method, String uri, String[] params) {
        String[] p = {method, Uri.encode(uri), Uri.encode(paramify(params))};
        String s = join(p,"&");
        SecretKey sk = new SecretKeySpec(APP_SECRET.getBytes(), oauth_signature_method);
        try {
            Mac m = Mac.getInstance(oauth_signature_method);
            m.init(sk);
            Log.i("mylog",Uri.encode(new String(Base64.encode(m.doFinal(s.getBytes()), Base64.DEFAULT)).trim()));
            return Uri.encode(new String(Base64.encode(m.doFinal(s.getBytes()), Base64.DEFAULT)).trim());
        } catch (java.security.NoSuchAlgorithmException e) {
            Log.w("FatSecret_TEST FAIL", e.getMessage());
            return null;
        } catch (java.security.InvalidKeyException e) {
            Log.w("FatSecret_TEST FAIL", e.getMessage());
            return null;
        }
    }

    private static String join(String[] array, String separator) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0)
                b.append(separator);
            b.append(array[i]);
        }
        return b.toString();
    }


    private static String[] generateOauthParams(int i) {
        return new String[]{
                "oauth_consumer_key=" + oauth_consumer_key,
                "oauth_signature_method="+oauth_signature_method ,
                "oauth_timestamp=" +  oauth_timestamp,
                "oauth_nonce=" + nonce(),
                "oauth_version="+oauth_version,
                "format=json",
                "page_number=" + i,
                "max_results=" + 20};
    }

    public static String searchFood(String searchFood, int page) {
        List<String> params = new ArrayList<>(Arrays.asList(generateOauthParams(page)));
        String[] template = new String[1];
        params.add("method=foods.search");
        params.add("search_expression=" + Uri.encode(searchFood));
        params.add("oauth_signature=" + getOauth_signature(APP_METHOD, APP_URL, params.toArray(template)));

        JSONObject foods = null;
        try {
            URL url = new URL(APP_URL + "?" + paramify(params.toArray(template)));
            Log.i("myurl",url.toString());
            URLConnection api = url.openConnection();
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(api.getInputStream()));
            while ((line = reader.readLine()) != null) builder.append(line);
            JSONObject food = new JSONObject(builder.toString());   // { first
            foods = food.getJSONObject("foods");                    // { second
        } catch (Exception exception) {
            Log.e("FatSecret Error", exception.toString());
            exception.printStackTrace();
        }
        return foods.toString();

    }

    public static String getSnippet(String result) {
        return result;
    }

    private static String nonce() {
        Random r = new Random();
        StringBuilder n = new StringBuilder();
        for (int i = 0; i < r.nextInt(8) + 2; i++)
            n.append(r.nextInt(26) + 'a');
        return n.toString();
    }

    private static String paramify(String[] params) {
        String[] p = Arrays.copyOf(params, params.length);
        Arrays.sort(p);
        return join(p, "&");
    }
}
