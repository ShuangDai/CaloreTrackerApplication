package com.example.a5046assign2;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.AsyncTask;

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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private Button btnSignUp;
    private TextView DOBTextView;

    private TextView firstNameErrorTextView;
    private TextView surNameErrorTextView;
    private TextView emailErrorTextView;
    private TextView userNameErrorTextView;
    private TextView passwordErrorTextView;
    private TextView heightErrorTextView;
    private TextView weightErrorTextView;
    private TextView addressErrorTextView;
    private TextView postcodeErrorTextView;



    private String firstName;
    private String surName;
    private String email;
    private int height;
    private int weight;
    private String dateOfBirth;
    private String address;
    private String postcode;

    private int levelOfActivity;
    private String gender;
    private String userName;
    private String password;
    Boolean userNameDuplicate;
    Boolean emailDuplicate;


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
        DOBTextView = (TextView) findViewById(R.id.dateTextView);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        firstNameErrorTextView =(TextView) findViewById(R.id.firstNameErrorTextView);
        surNameErrorTextView =(TextView) findViewById(R.id.surNameErrorTextView);
        emailErrorTextView =(TextView) findViewById(R.id.emailErrorTextView);
        userNameErrorTextView=(TextView) findViewById(R.id.userNameErrorTextView);
        passwordErrorTextView =(TextView) findViewById(R.id.passwordErrorTextView);
        heightErrorTextView =(TextView) findViewById(R.id.heightErrorTextView);
        weightErrorTextView=(TextView) findViewById(R.id.weightErrorTextView);
        addressErrorTextView =(TextView) findViewById(R.id.addressErrorTextView);
        postcodeErrorTextView=(TextView) findViewById(R.id.postcodeErrorTextView);



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            Boolean check =dataValidation();
            Log.i("myTag",firstName);
            double x=(height/2.54)*0.413/12;
            int stepsPerMile=(int)Math.round(5280/x);

            if(check){
                height = Integer.parseInt(heightEditText.getText().toString());
                weight = Integer.parseInt(weightEditText.getText().toString());
                levelOfActivity = Integer.parseInt(String.valueOf(levelOfActivitySpinner.getSelectedItem()));
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) findViewById(selectedId);
                gender = radioSexButton.getText().toString();
                Toast.makeText(SignUp.this,
                        levelOfActivity + dateOfBirth + gender + userName + password,
                        Toast.LENGTH_SHORT).show();
                String id = generateId(dateOfBirth);

            }

//                PostAsyncTask postAsyncTask = new PostAsyncTask();
//                postAsyncTask.execute(id, firstName, surName, email, dateOfBirth, String.valueOf(height), String.valueOf(weight), gender, address, postcode, String.valueOf(levelOfActivity), userName, password,String.valueOf(stepsPerMile));

            }
        });
    }

    public void init(){
        firstNameErrorTextView.setText("");
        surNameErrorTextView.setText("");
        emailErrorTextView.setText("");
        userNameErrorTextView.setText("");
        passwordErrorTextView.setText("");
        heightErrorTextView.setText("");
        weightErrorTextView.setText("");
        addressErrorTextView.setText("");
        postcodeErrorTextView.setText("");
        DOBTextView.setText("");
    }

    public Boolean dataValidation(){

        firstName = firstNameEditText.getText().toString();
        surName = surnameEditText.getText().toString();
        email = emailEditText.getText().toString();
        address = addressEditText.getText().toString();
        postcode = postcodeEditText.getText().toString();
        dateOfBirth = DOBTextView.getText().toString();
        userName = userNameEditText.getText().toString();
        password = passwordEditText.getText().toString();

        FindUserNamesAsyncTask findUserNamesAsyncTask = new FindUserNamesAsyncTask();
        findUserNamesAsyncTask.execute(userName);

        FindEmailAsyncTask findEmailAsyncTask = new FindEmailAsyncTask();
        findEmailAsyncTask.execute(email);

        if(firstName.equals("")){
            firstNameErrorTextView.setText("First name is required");
            firstNameErrorTextView.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        if(firstName.matches(".*\\d.*")){
            firstNameErrorTextView.setText("First name can not contain numbers");
            firstNameErrorTextView.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        if(surName.equals("")){
            surNameErrorTextView.setText("Surname is required");
            surNameErrorTextView.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        if(surName.matches(".*\\d.*")){
            surNameErrorTextView.setText("Surname can not contain numbers");
            surNameErrorTextView.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        if(email.equals("")){
            emailErrorTextView.setText("Email is required");
            emailErrorTextView.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        if(!validateEmail(email)){
            emailErrorTextView.setText("Please input valid email address");
            emailErrorTextView.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        if(userName.equals("")){
            userNameErrorTextView.setText("User name is required");
            userNameErrorTextView.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if(password.equals("")){
            passwordErrorTextView.setText("Password is required");
            passwordErrorTextView.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        if (heightEditText.getText().toString().equals("")){
            heightErrorTextView.setText("Height is required");
            heightErrorTextView.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        if(weightEditText.getText().toString().equals("")){
            weightErrorTextView.setText("Weight is required");
            weightErrorTextView.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if (!isInteger(heightEditText.getText().toString())){
            heightErrorTextView.setText("Please enter integer value for height");
            heightErrorTextView.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        if (!isInteger(weightEditText.getText().toString())){
            weightErrorTextView.setText("Please enter interger value for weight");
            weightErrorTextView.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }
        if(address.equals("")){
            addressErrorTextView.setText("Address is required");
            addressErrorTextView.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if(postcode.equals("")){
            postcodeErrorTextView.setText("Postcode is required");
            postcodeErrorTextView.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        if(dateOfBirth.equals("")){
            DOBTextView.setText("Please set your date of birth");
            DOBTextView.setTextColor(Color.parseColor("#FF0000"));
            return false;
        }

        return false;

    }

//    https://stackoverflow.com/questions/8204680/java-regex-email
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

//    https://stackoverflow.com/questions/237159/whats-the-best-way-to-check-if-a-string-represents-an-integer-in-java
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public String generateId(String dob) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String s = dateFormat.format(date) + dob;
        String strippedInput = s.replaceAll("\\W", "");
        return strippedInput;
    }

    public void showDatePicker(View v) {
        DialogFragment newFragment = new MyDatePickerFragment();
        newFragment.show(getFragmentManager(), "date picker");

    }

    private class PostAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            UserInfo userInfo = new UserInfo(params[0], params[1], params[2], params[3], params[4], Integer.valueOf(params[5]), Integer.valueOf(params[6]), params[7], params[8], params[9], Integer.valueOf(params[10]),Integer.valueOf(params[13]));
            RestCustomer.createUser(userInfo);
            String originalPassword=params[12];
            MessageDigest digest = null;
            String hashValue="";
            try {
                digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(originalPassword.getBytes(StandardCharsets.UTF_8));
                hashValue=bytesToHex(hash);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String newDate = formatter.format(date);
            Credential credential = new Credential(params[0], params[11], hashValue,newDate,userInfo);
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
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private class FindUserNamesAsyncTask extends AsyncTask<String , Void, String>
    {
        @Override
        protected String doInBackground (String...params){
            return RestCredential.findByUserName(params[0]);
        }
        @Override
        protected void onPostExecute (String response){
            Log.i("myTag",response);
            if(response.equals("")){
                userNameDuplicate=false;

            }
            else {
                userNameDuplicate=true;
                userNameErrorTextView.setText("User name is dupicated");
                userNameErrorTextView.setTextColor(Color.parseColor("#FF0000"));
            }

        }
    }

    private class FindEmailAsyncTask extends AsyncTask<String , Void, String>
    {
        @Override
        protected String doInBackground (String...params){
            return RestCustomer.findByUserEmail(params[0]);
        }
        @Override
        protected void onPostExecute (String response){
            Log.i("myTag",response);
            if(response.equals("")){
                emailDuplicate=false;

            }
            else {
                emailDuplicate=true;
                emailErrorTextView.setText("Email is dupicated");
                emailErrorTextView.setTextColor(Color.parseColor("#FF0000"));
            }

        }
    }



}
