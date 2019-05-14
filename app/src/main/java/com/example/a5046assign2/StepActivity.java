package com.example.a5046assign2;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StepActivity extends Fragment implements View.OnClickListener {
    LocalDataDatabase db = null;
    EditText editText = null;
    TextView textView_insert = null;
    TextView textView_read = null;
    TextView textView_delete = null;
    TextView textView_update = null;
    TextView textView_total_staps=null;
    private View vDisplaySteps;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Set Variables and listeners
        vDisplaySteps = inflater.inflate(R.layout.activity_step, container,
                false);
        editText=(EditText) vDisplaySteps.findViewById(R.id.editText) ;
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                LocalDataDatabase.class, "LocalDataDatabase")
                .fallbackToDestructiveMigration()
                .build();
        Button addButton = (Button) vDisplaySteps.findViewById(R.id.addButton);
        textView_insert = (TextView) vDisplaySteps.findViewById(R.id.textView);
        Button readButton = (Button) vDisplaySteps.findViewById(R.id.readButton);
        textView_read = (TextView) vDisplaySteps.findViewById(R.id.textView_read);
        Button deleteButton = (Button) vDisplaySteps.findViewById(R.id.deleteButton);
        textView_delete = (TextView) vDisplaySteps.findViewById(R.id.textView_delete);
        Button updateButton = (Button) vDisplaySteps.findViewById(R.id.updateButton);
        textView_update = (TextView) vDisplaySteps.findViewById(R.id.textView_update);
        textView_total_staps = (TextView) vDisplaySteps.findViewById(R.id.textView_total_steps);
        StepActivity.CalculateTotalSteps calculateTotalSteps = new StepActivity.CalculateTotalSteps();
        calculateTotalSteps.execute();

        addButton.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                StepActivity.InsertDatabase addDatabase = new StepActivity.InsertDatabase();
                addDatabase.execute();
                StepActivity.CalculateTotalSteps calculateTotalSteps = new StepActivity.CalculateTotalSteps();
                calculateTotalSteps.execute();
            }
        });
        readButton.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                StepActivity.ReadDatabase readDatabase = new StepActivity.ReadDatabase();
                readDatabase.execute();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                DeleteDatabase deleteDatabase = new DeleteDatabase();
                deleteDatabase.execute();
                StepActivity.CalculateTotalSteps calculateTotalSteps = new StepActivity.CalculateTotalSteps();
                calculateTotalSteps.execute();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                UpdateDatabase updateDatabase = new UpdateDatabase();
                updateDatabase.execute();
                StepActivity.CalculateTotalSteps calculateTotalSteps = new StepActivity.CalculateTotalSteps();
                calculateTotalSteps.execute();
            }
        });
        return vDisplaySteps;
    }
    @Override
    public void onClick(View v) {
        // Get myUnits file
//        SharedPreferences spMyUnits =
//                getActivity().getSharedPreferences("myUnits", Context.MODE_PRIVATE);
//        String units= spMyUnits.getString("message",null);
//        tvDisplayUnits.setText(units);
    }


    private class InsertDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            if (!(editText.getText().toString().isEmpty())){
                try {
                    String[] details = editText.getText().toString().split(" ");
                    if (details.length==1) {
                        if(Integer.parseInt(details[0])>=0){
                            LocalData localData = new LocalData(Integer.parseInt(details[0]));
                            long id = db.localDataDao().insert(localData);
                            return (id + " " + details[0]);
                        }
                        else{
                            return "NagativeValue";
                        }

                    }
                    else
                        return "spaceError";

                }
                catch (NumberFormatException e){
                    return "NumberFormatError";
                }

            }
            else
                return "NullValue";
        }
        @Override
        protected void onPostExecute(String details) {

            if(details.equals("spaceError")){
                textView_insert.setText("Please do not include space");
                textView_insert.setTextColor(Color.rgb(200,0,0));

            }
            else if(details.equals("NumberFormatError")){
                textView_insert.setText("Please input valid number");
                textView_insert.setTextColor(Color.rgb(200,0,0));
            }
            else if(details.equals("NullValue")){
                textView_insert.setText("Steps number is required");
                textView_insert.setTextColor(Color.rgb(200,0,0));

            }
            else if(details.equals("NagativeValue")){
                textView_insert.setText("Steps should be positive");
                textView_insert.setTextColor(Color.rgb(200,0,0));

            }
            else{
                textView_insert.setTextColor(Color.rgb(0,0,0));
                textView_insert.setText("Added Record: " + details);

            }


        }
    }

    private class CalculateTotalSteps extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {
            List<LocalData> localDatas = db.localDataDao().getAll();
            if (!(localDatas.isEmpty() || localDatas == null) ){
                int sum= 0;
                for (LocalData temp : localDatas) {
                    int currentNum = temp.getSteps();
                    sum = sum + currentNum;
                }
                return sum;
            }
            else
                return 0;
        }
        @Override
        protected void onPostExecute(Integer details) {
            textView_total_staps.setTextColor(Color.rgb(0,0,0));
            textView_total_staps.setText("Total steps: " + details);

        }
    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            List<LocalData> localDatas = db.localDataDao().getAll();
            if (!(localDatas.isEmpty() || localDatas == null) ){
                String allInfo = "";
                for (LocalData temp : localDatas) {
                    String userstr = (temp.getDailyStepId() + " " +
                            temp.getSteps());
                    allInfo = allInfo + userstr;
                }
                return allInfo;
            }
            else
                return "";
        }
        @Override
        protected void onPostExecute(String details) {
            textView_read.setTextColor(Color.rgb(0,0,0));
            textView_read.setText("All data: " + details);

        }
    }

    private class DeleteDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            db.localDataDao().deleteAll();
            return null;
        }
        protected void onPostExecute(Void param) {
            textView_delete.setTextColor(Color.rgb(0,0,0));
            textView_delete.setText("All data was deleted");
        }
    }

    private class UpdateDatabase extends AsyncTask<Void, Void, String> {
        @Override protected String doInBackground(Void... params) {
            LocalData localData=null;
            String[] details= editText.getText().toString().split(" ");
            if (details.length==2) {
                int id = Integer.parseInt(details[0]);
                localData = db.localDataDao().findByID(id);
                localData.setSteps(Integer.parseInt(details[1]));
            }
            if (localData!=null) {
                db.localDataDao().updateUsers(localData);
                return (details[0] + " " + details[1]);
            }
            return "";
        }
        @Override
        protected void onPostExecute(String details) {
            textView_update.setText("Updated details: "+ details);
        }
    }

}
