package com.example.a5046assign2;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class StepActivity extends Fragment implements View.OnClickListener {
    LocalDataDatabase db = null;
    EditText editText = null;
    TextView textView_insert = null;
    TextView textView_read = null;
    TextView textView_delete = null;
    TextView textView_update = null;
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
        addButton.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                StepActivity.InsertDatabase addDatabase = new StepActivity.InsertDatabase();
                addDatabase.execute();
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
                String[] details = editText.getText().toString().split(" ");
                if (details.length==1) {
                    LocalData localData = new LocalData(Integer.parseInt(details[0]));
                    long id = db.localDataDao().insert(localData);
                    return (id + " " + details[0]);
                }
                else
                    return "";
            }
            else
                return "";
        }
        @Override
        protected void onPostExecute(String details) {
            textView_insert.setText("Added Record: " + details);
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
            textView_delete.setText("All data was deleted");
        }
    }

}
