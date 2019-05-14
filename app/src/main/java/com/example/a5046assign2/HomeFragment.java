package com.example.a5046assign2;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private View vDisplayHome;
    private TextView dateTextView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vDisplayHome = inflater.inflate(R.layout.fragment_main, container,
                false);
        dateTextView=(TextView) vDisplayHome.findViewById(R.id.dateTextView);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        dateTextView.setText(dateFormat.format(date));
        return vDisplayHome;
    }

}
