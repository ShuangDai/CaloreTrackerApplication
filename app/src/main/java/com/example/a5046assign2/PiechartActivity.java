package com.example.a5046assign2;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class PiechartActivity extends AppCompatActivity {

    Button setDateBtn;
    TextView dateTextView;
    Button generateReportBtn;
    TextView errorTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);
            setDateBtn =(Button) findViewById(R.id.setDate);
            dateTextView =(TextView)findViewById(R.id.dateSelectTextView);
            generateReportBtn =(Button)findViewById(R.id.generateReport);
            errorTextView =(TextView) findViewById(R.id.errorTextView);

            setDateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("myTag","onclick");
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    // date picker dialog
                    DatePickerDialog picker;
                    picker = new DatePickerDialog(PiechartActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    Log.i("mytag","date" +year+"-"+ "0" + (monthOfYear + 1) + "-"+dayOfMonth);
                                    if (monthOfYear + 1<10) {
                                        dateTextView.setText(year+"-"+ "0" + (monthOfYear + 1) + "-"+dayOfMonth);

                                    }
                                    else{
                                        dateTextView.setText(year+"-" + (monthOfYear + 1)+"-"+dayOfMonth);
                                    }
                                }
                            }, year, month, day);
                    picker.show();
                }
            });


        generateReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = dateTextView.getText().toString();
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
            return RestReport.getReportByDate(params[0],params[1]);
        }
        @Override
        protected void onPostExecute (String response) {
                if(response.equals("0.0 0.0 0.0")){
                    errorTextView.setText("No report for selected date");
                }
                Log.i("myLog", "respose"+response);
                String[] array = response.split(" ");
                Log.i("myLog", "arrary"+array.length);
                if (array.length>1) {
                    float consumpted = Float.parseFloat(array[0]);
                    float burned = Float.parseFloat(array[1]);
                    float rest = -Float.parseFloat(array[2]);

                    PieChart pieChart = findViewById(R.id.piechart);
                    List<PieEntry> pieEntries = new ArrayList<>();
                    float totoal = burned + rest + consumpted;

                    Log.i("myTag:burned", "" + burned);
                    Log.i("myTag:consumed", "" + consumpted);
                    Log.i("myTag:rest", "" + rest);
                    Log.i("myTag:sum", "" + totoal);


                    float burnedPercentage = (burned / totoal) * 100;
                    float consumptedPercenatge = (consumpted / totoal) * 100;
                    float restPercentage = (rest / totoal) * 100;
                    Log.i("myTag", String.valueOf(burnedPercentage));

                    if (restPercentage < 0) {
                        restPercentage = -restPercentage;
                        pieEntries.add(new PieEntry(restPercentage, "Surplus Calorie"));
                    } else {
                        pieEntries.add(new PieEntry(restPercentage, "Rest Calorie"));
                    }
                    pieEntries.add(new PieEntry(burnedPercentage, "Calorie burned"));
                    pieEntries.add(new PieEntry(consumptedPercenatge, "Calorie consumpted"));


                    final int[] MY_COLORS = {Color.rgb(255, 0, 0), Color.rgb(17, 4, 225), Color.rgb(0, 176, 80)};
                    ArrayList<Integer> colors = new ArrayList<Integer>();

                    PieDataSet pieDataSet = new PieDataSet(pieEntries, "Calorie distribution");
                    PieData data = new PieData(pieDataSet);
                    for (int c : MY_COLORS) colors.add(c);

                    data.setValueFormatter(new PercentFormatter());

                    pieDataSet.setColors(colors);
                    pieDataSet.setValueTextSize(12);

                    pieChart.setData(data);
                    pieChart.setUsePercentValues(true);
                    pieChart.invalidate();
                }
        }
    }



}
