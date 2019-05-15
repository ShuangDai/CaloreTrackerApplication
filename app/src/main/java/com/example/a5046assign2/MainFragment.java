package com.example.a5046assign2;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainFragment extends Fragment {
    View vMain;
    private TextView dateTextView;
    private TextView welcomeTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_main, container, false);
        dateTextView=(TextView) vMain.findViewById(R.id.dateTextView);
        welcomeTextView=(TextView) vMain.findViewById(R.id.welcomeMessage);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        dateTextView.setText(dateFormat.format(date));
        HomeActivity homeActivity =(HomeActivity)getActivity();
        String firstName =homeActivity.getMyFristName();

        welcomeTextView.setText("Hello, "+firstName);


        return vMain;
    }
}