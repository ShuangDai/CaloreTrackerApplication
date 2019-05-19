package com.example.a5046assign2;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//https://www.tutlane.com/tutorial/android/android-datepicker-with-examples
//https://github.com/PhilJay/MPAndroidChart/wiki/Setting-Data
public class BarchartActivity extends AppCompatActivity {
    TextView startDateTextView;
    TextView endDateTextView;
    Button startDateBtn;
    Button endDateBtn;
    String startDate;
    String endDate;
    Button generatePeriodReportBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);
        startDateTextView =(TextView) findViewById(R.id.startDateTextview);
        endDateTextView =(TextView) findViewById(R.id.endDateTextview);
        startDateBtn=(Button) findViewById(R.id.setStartDate);
        endDateBtn =(Button) findViewById(R.id.setEndDate);
        generatePeriodReportBtn = (Button) findViewById(R.id.generatePeriodReportBtn);

        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker;
                picker = new DatePickerDialog(BarchartActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if (monthOfYear + 1<10) {
                                    startDateTextView.setText(year+"-"+ "0" + (monthOfYear + 1) + "-"+dayOfMonth);

                                }
                                else{
                                    startDateTextView.setText(year+"-" + (monthOfYear + 1)+"-"+dayOfMonth);
                                }
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr2 = Calendar.getInstance();
                int day = cldr2.get(Calendar.DAY_OF_MONTH);
                int month = cldr2.get(Calendar.MONTH);
                int year = cldr2.get(Calendar.YEAR);

                DatePickerDialog picker2;
                picker2 = new DatePickerDialog(BarchartActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if (monthOfYear + 1<10) {
                                    endDateTextView.setText(year+"-"+ "0" + (monthOfYear + 1) + "-"+dayOfMonth);

                                }
                                else{
                                    endDateTextView.setText(year+"-" + (monthOfYear + 1)+"-"+dayOfMonth);
                                }

                            }
                        }, year, month, day);
                picker2.show();
            }
        });


     generatePeriodReportBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startDate= startDateTextView.getText().toString();
            endDate = endDateTextView.getText().toString();
            SharedPreferences sharedPreferences;
            sharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString("userId","0");

            GetPeriodReportAsyncTask getPeriodReportAsyncTask = new GetPeriodReportAsyncTask();
            getPeriodReportAsyncTask.execute(userId,startDate,endDate);
        }
    });
}


    public void showDate(View v) {
        DialogFragment newFragment = new MyDatePickerFragment();
        newFragment.show(getFragmentManager(), "date picker");
    }

    private class GetPeriodReportAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestReport.getPeriodReportByDate(params[0],params[1],params[2]);
        }

        @Override
        protected void onPostExecute(String priodReport) {
            String[] array = priodReport.split(" ");
            List<String> dates = new ArrayList<String>();
            List<Integer> consumed = new ArrayList<Integer>();
            List<Integer> burned = new ArrayList<Integer>();
            Log.i("myTag","period report"+priodReport);
            if (array.length > 1) {
                for (int i = 0; i < (array.length); i += 3) {
                    dates.add(array[i]);
                    consumed.add(Integer.parseInt(array[i + 1]));
                    burned.add(Integer.parseInt(array[i + 2]));
                    Log.i("myTag", array[i]);
                }
                Log.i("myTag", priodReport);

                BarChart chart = findViewById(R.id.barchart);
                List<BarEntry> entries = new ArrayList<>();
                List<BarEntry> entries2 = new ArrayList<>();

                int n = 0;
                for (Integer c : consumed) {
                    entries.add(new BarEntry(n, c));
                    n += 1;

                }

                int k = 0;
                for (Integer b : burned) {
                    entries2.add(new BarEntry(k, b));
                    k += 1;
                }


                IAxisValueFormatter formatter = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return dates.get((int) value);
                    }
                };


                XAxis xAxisFromChart = chart.getXAxis();
                xAxisFromChart.setDrawAxisLine(true);
                xAxisFromChart.setValueFormatter(formatter);
                // minimum axis-step (interval) is 1,if no, the same value will be displayed multiple times
                xAxisFromChart.setGranularity(1f);
                xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);

                BarDataSet set = new BarDataSet(entries, "Calorie Burned");
                BarDataSet set2 = new BarDataSet(entries2, "Calorie Consumpted");

                set.setColor(Color.rgb(17, 4, 225));
                set2.setColor(Color.rgb(0, 176, 80));
                float groupSpace = 0.3f;
                float barSpace = 0.02f;
                float barWidth = 0.3f;

                BarData barData = new BarData(set, set2);
                barData.setBarWidth(barWidth);
                chart.setData(barData);
                chart.groupBars(-0.3f, groupSpace, barSpace);
                chart.invalidate(); // refresh
            }
        }
    }
}
