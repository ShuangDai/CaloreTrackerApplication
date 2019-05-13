package com.example.a5046assign2;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalorieTracker extends Fragment implements View.OnClickListener{
    View vDisplayCalorieTracker;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Set Variables and listeners
        vDisplayCalorieTracker = inflater.inflate(R.layout.activity_calorie_tracker, container,
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
