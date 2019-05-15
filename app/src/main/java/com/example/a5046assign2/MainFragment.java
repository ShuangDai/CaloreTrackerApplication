package com.example.a5046assign2;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainFragment extends Fragment {
    View vMain;
    private TextView dateTextView;
    private TextView welcomeTextView;
    private TextView displayGoalTextView;
    private Button btnSetGoal;
    private EditText goalEditText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_main, container, false);
        dateTextView=(TextView) vMain.findViewById(R.id.dateTextView);
        welcomeTextView=(TextView) vMain.findViewById(R.id.welcomeMessage);
        btnSetGoal =(Button) vMain.findViewById(R.id.btnSetGoal);
        displayGoalTextView = (TextView) vMain.findViewById(R.id.displayGoalTextView);
        goalEditText =(EditText) vMain.findViewById(R.id.goal);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        dateTextView.setText(dateFormat.format(date));
        HomeActivity homeActivity =(HomeActivity)getActivity();
        String firstName =homeActivity.getMyFristName();
        welcomeTextView.setText("Hello, "+firstName);

        btnSetGoal.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                String goal = goalEditText.getText().toString();
                displayGoalTextView.setText("Your daily goal is "+goal+" cal");
            }
        });


        return vMain;
    }
}