package com.example.a5046assign2;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class PiechartActivity extends AppCompatActivity {

    Button setDateBtn;
    TextView dateTextView;
    int burned;
    int consumpted;
    int rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);
            setDateBtn =(Button) findViewById(R.id.setDate);
            dateTextView =(TextView)findViewById(R.id.dateTextView);

            setDateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String date = dateTextView.getText().toString();
                    showDate(v);
                    SharedPreferences sharedPreferences;
                    sharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE);
                    String userId = sharedPreferences.getString("userId","0");
                    FindReportAsyncTask findReportAsyncTask = new FindReportAsyncTask();
                    findReportAsyncTask.execute(userId,date);
                }
            });
    }

    private class FindReportAsyncTask extends AsyncTask<String,Void, String>
    {
        @Override
        protected String doInBackground (String...params){
            return RestReport.getReportByDate(params[0],"2019-03-21");
        }
        @Override
        protected void onPostExecute (String response){
            Log.i("myLog",response);
            String[] array = response.split(" ");
            Log.i("myLog",array[0]);
            consumpted =(int)Double.parseDouble(array[0]);
            burned=(int)Double.parseDouble(array[1]);
            rest=-(int)Double.parseDouble(array[2]);
            PieChart pieChart = findViewById(R.id.piechart);
            List<PieEntry> pieEntries = new ArrayList<>();
            pieEntries.add(new PieEntry(burned, "Calorie burned"));
            pieEntries.add(new PieEntry(consumpted, "Calorie consumpted"));
            pieEntries.add(new PieEntry(rest, "Rest Calorie"));
            final int[] MY_COLORS = {Color.rgb(255, 0, 0),Color.rgb(17, 4, 225), Color.rgb(0, 176, 80)};
            ArrayList<Integer> colors = new ArrayList<Integer>();


            PieDataSet pieDataSet = new PieDataSet(pieEntries, "Calorie distribution");
            PieData data = new PieData(pieDataSet);
            for(int c: MY_COLORS) colors.add(c);

            pieDataSet.setColors(colors);

            pieChart.setData(data);
            pieChart.invalidate();
        }
    }
    public void showDate(View v) {
        DialogFragment newFragment = new MyDatePickerFragment();
        newFragment.show(getFragmentManager(), "date picker");
    }


}
