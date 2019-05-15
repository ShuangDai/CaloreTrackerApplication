package com.example.a5046assign2;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReportActivity extends Fragment implements View.OnClickListener {

    View vDisplayCalorieTracker;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Set Variables and listeners
        vDisplayCalorieTracker = inflater.inflate(R.layout.activity_report, container,
                false);
        return vDisplayCalorieTracker;
    }
    @Override
    public void onClick(View v) {
        // Get myUnits file
//        SharedPreferences spMyUnits =
//                getActivity().getSharedPreferences("myUnits", Context.MODE_PRIVATE);
//        String units= spMyUnits.getString("message",null);
//        tvDisplayUnits.setText(units);
    }
}
