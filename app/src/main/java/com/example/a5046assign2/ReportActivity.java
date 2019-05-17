package com.example.a5046assign2;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ReportActivity extends Fragment implements View.OnClickListener {

   View vDisplayReport;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        vDisplayReport = inflater.inflate(R.layout.activity_report, container,
                false);

        Button dailyReportBtn = (Button) vDisplayReport.findViewById(R.id.dailyReportBtn);
        dailyReportBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PiechartActivity.class);
                startActivity(intent);
            }
        });

        Button periodReportBtn = (Button) vDisplayReport.findViewById(R.id.periodReportBtn);
        periodReportBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),BarchartActivity.class);
                startActivity(intent);
            }
        });


        return vDisplayReport;
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
