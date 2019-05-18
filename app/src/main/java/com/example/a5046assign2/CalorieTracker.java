package com.example.a5046assign2;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalorieTracker extends Fragment implements View.OnClickListener{
    View vDisplayCalorieTracker;
    TextView stepsTextView;
    TextView goalTextView;
    TextView calorieConsumptedTextView;
    TextView calorieBurnedTextView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Set Variables and listeners
        vDisplayCalorieTracker = inflater.inflate(R.layout.activity_calorie_tracker, container,
                false);
        stepsTextView = (TextView) vDisplayCalorieTracker.findViewById(R.id.stepsTextView);
        goalTextView =(TextView)vDisplayCalorieTracker.findViewById(R.id.goalTextView);
        calorieConsumptedTextView = (TextView) vDisplayCalorieTracker.findViewById(R.id.calorieConsumedTextView);
        calorieBurnedTextView =(TextView) vDisplayCalorieTracker.findViewById(R.id.calorieBurnedTextView);

        SharedPreferences sharedPreferences;
        sharedPreferences = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        String steps = sharedPreferences.getString("steps","0");
        String goal = sharedPreferences.getString("goal","0");
        String userId = sharedPreferences.getString("userId","0");

        stepsTextView.setText("Total steps taken: "+steps);
        goalTextView.setText("Daily goal: "+goal);
        CalculateCalorieBurnedAsyncTask calculateCalorieBurnedAsyncTask = new CalculateCalorieBurnedAsyncTask();
        calculateCalorieBurnedAsyncTask.execute(userId);

        CalculateCalorieConsumptedAsyncTask calculateCalorieConsumptedAsyncTask = new CalculateCalorieConsumptedAsyncTask();
        calculateCalorieConsumptedAsyncTask.execute(userId,"2019-03-30");




        Log.i("myTag","goal"+goal);
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

    private class CalculateCalorieBurnedAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {
            String burned = RestCustomer.calculatesTheCaloriesBurnedPerStep(params[0]);
            return  burned;
        }
        @Override
        protected void onPostExecute(String response) {
            calorieBurnedTextView.setText("Calorie burned :"+response+" cal");
            SharedPreferences sharedPreferences;
            sharedPreferences = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            int b = (int)Double.parseDouble(response);
            editor.putString("burned",Integer.toString(b));
            editor.apply();
        }
    }


    private class CalculateCalorieConsumptedAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {
            String comsumpted = RestConsumption.calculatesTheCaloriesBurnedPerStep(params[0],params[1]);
            return  comsumpted;
        }
        @Override
        protected void onPostExecute(String response) {
            calorieConsumptedTextView.setText("Calorie consumed: "+response+" cal");
            SharedPreferences sharedPreferences;
            sharedPreferences = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            int c = (int)Double.parseDouble(response);
            editor.putString("consumed",Integer.toString(c));
            editor.apply();


        }
    }

}
