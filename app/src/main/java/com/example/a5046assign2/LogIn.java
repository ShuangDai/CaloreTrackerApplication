package com.example.a5046assign2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class LogIn extends AppCompatActivity {
    private EditText userNameEditText;
    private EditText passwordEditText;
    private Button btnLogIn;
    private String userName;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        btnLogIn = (Button) findViewById(R.id.btnLogIn);


        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userNameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                if (!userName.isEmpty() && !password.isEmpty()) {
                    CredentialAsyncTask getUserByNameAsTask = new CredentialAsyncTask();
                    String u = userNameEditText.getText().toString();
                    String p = passwordEditText.getText().toString();
                    getUserByNameAsTask.execute(u);

                }

            }
        });
    }


    private class CredentialAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = RestCredential.findByUserName(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String user) {
            String hashValue="";
            if (user != null) {
                user = user.substring(1, user.length() - 1);
                Gson g = new Gson();
                Credential u = g.fromJson(user, Credential.class);

                String correctPassword = u.getPassword();
                MessageDigest digest = null;
                try {
                    digest = MessageDigest.getInstance("SHA-256");
                    byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                    hashValue=bytesToHex(hash);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                if (hashValue.equals(correctPassword)) {
                    Intent intent = new Intent(LogIn.this,HomeActivity.class);
                    intent.putExtra("userFirstName",u.getUserInfo().getFirstName());
                    startActivity(intent);

                    Toast.makeText(LogIn.this,
                            "Log in successfully",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LogIn.this,
                            "The user name or password is not correct!",
                            Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(LogIn.this,
                        "The user name or password is not correct!",
                        Toast.LENGTH_SHORT).show();

            }

        }

    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }


}
