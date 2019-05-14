package com.example.a5046assign2;

import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//References:https://www.viralandroid.com/2016/01/simple-android-user-contact-form-xml-ui-design.html

public class SignUp extends AppCompatActivity {
    private Spinner levelOfActivitySpinner;
    private EditText firstNameEditText;
    private EditText surnameEditText;
    private EditText emailEditText;
    private EditText heightEditText;
    private EditText weightEditText;
    private EditText addressEditText;
    private EditText postcodeEditText;
    private EditText StepsPerMileEditText;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private Button btnSignUp;
    private TextView DOBTextView;

    private String firstName;
    private String surName;
    private String email;
    private int height;
    private int weight;
    private String dateOfBirth;
    private String address;
    private String postcode;
    private int stepsPerMile;
    private int levelOfActivity;
    private String gender;
    private String userName;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        levelOfActivitySpinner = (Spinner) findViewById(R.id.activity_level_spinner);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        heightEditText = (EditText) findViewById(R.id.heightEditText);
        weightEditText = (EditText) findViewById(R.id.weightEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        postcodeEditText = (EditText) findViewById(R.id.PostCodeEditText);
        StepsPerMileEditText = (EditText) findViewById(R.id.stepsPerMileEditText);
        DOBTextView = (TextView) findViewById(R.id.dateTextView);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = firstNameEditText.getText().toString();
                surName = surnameEditText.getText().toString();
                email = emailEditText.getText().toString();
                height = Integer.parseInt(heightEditText.getText().toString());
                weight = Integer.parseInt(weightEditText.getText().toString());
                address = addressEditText.getText().toString();
                postcode = postcodeEditText.getText().toString();
                stepsPerMile = Integer.parseInt(StepsPerMileEditText.getText().toString());
                levelOfActivity = Integer.parseInt(String.valueOf(levelOfActivitySpinner.getSelectedItem()));
                dateOfBirth = DOBTextView.getText().toString();
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) findViewById(selectedId);
                gender = radioSexButton.getText().toString();
                userName = userNameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                Toast.makeText(SignUp.this,
                        levelOfActivity + dateOfBirth + gender + userName + password,
                        Toast.LENGTH_SHORT).show();
                String id = generateId(dateOfBirth);
                PostAsyncTask postAsyncTask = new PostAsyncTask();
                postAsyncTask.execute(id, firstName, surName, email, dateOfBirth, String.valueOf(height), String.valueOf(weight), gender, address, postcode, String.valueOf(levelOfActivity), userName, password);

            }
        });
    }

    public String generateId(String dob) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String s = dateFormat.format(date) + dob;
        String strippedInput = s.replaceAll("\\W", "");
        return strippedInput;
    }

    public void showDatePicker(View v) {
        DialogFragment newFragment = new MyDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");
    }

    private class PostAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            UserInfo userInfo = new UserInfo(params[0], params[1], params[2], params[3], params[4], Integer.valueOf(params[5]), Integer.valueOf(params[6]), params[7], params[8], params[9], Integer.valueOf(params[10]));
            RestCustomer.createUser(userInfo);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String s = dateFormat.format(date);
            Log.i("mytag",s);
            Credential credential = new Credential(params[0], params[11], params[12],s,userInfo);
            RestCredential.createCredential(credential);
            Log.i("mytag",credential.toString());
            return "Create account successfully";
        }

        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(SignUp.this,
                    response,
                    Toast.LENGTH_SHORT).show();

        }

    }




}
