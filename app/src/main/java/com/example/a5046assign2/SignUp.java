package com.example.a5046assign2;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private Spinner levelOfActivitySpinner;
    private Button btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSignUp =(Button) findViewById(R.id.btnSignUp);
        levelOfActivitySpinner=(Spinner) findViewById(R.id.activity_level_spinner);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUp.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : "+ String.valueOf(levelOfActivitySpinner.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void showDatePicker(View v) {
        DialogFragment newFragment = new MyDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");
    }

}
