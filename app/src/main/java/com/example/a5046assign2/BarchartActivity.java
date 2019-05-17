package com.example.a5046assign2;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class BarchartActivity extends AppCompatActivity {

    TextView startDateTextView;
    TextView endDateTextView;
    Button startDateBtn;
    Button endDateBtn;
    String startDate;
    String endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);
        startDateTextView =(TextView) findViewById(R.id.startDateTextview);
        endDateTextView =(TextView) findViewById(R.id.endDateTextview);
        startDateBtn=(Button) findViewById(R.id.setStartDate);
        endDateBtn =(Button) findViewById(R.id.setEndDate);


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
                                startDateTextView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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
                                endDateTextView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                picker2.show();
            }
        });




        BarChart chart = findViewById(R.id.barchart);
        List<BarEntry> entries = new ArrayList<>();
        float[] xAxis = {0f,1f,2f,3f,4f,5f,6f};
        float[] yAxis = {245, 340, 450, 561, 700,880,970};

        List<BarEntry> entries2 = new ArrayList<>();
        float[] xAxis2 = {0f,1f,2f,3f,4f,5f,6f};
        float[] yAxis2 = {200, 400, 500, 600, 700,800,900};

        for (int i=0; i<xAxis.length; i++){
            entries.add(new BarEntry(xAxis[i], yAxis[i]));
            entries2.add(new BarEntry(xAxis2[i], yAxis2[i]));
        }

        final String[] years = new String[] { "Sun","Mon", "Tues", "Wes", "Thurs","Fir","Sat" };
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return years[(int)value];
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

//        set.setColors(new int[] { R.color.green }, Context);
//        set2.setColors(ContextCompat.getColor(context, R.color.green));
        float groupSpace = 0.3f;
        float barSpace = 0.02f;
        float barWidth = 0.3f;

        BarData barData = new BarData(set,set2);
        barData.setBarWidth(barWidth);
        chart.setData(barData);
        chart.groupBars(-0.3f, groupSpace, barSpace);
        chart.invalidate(); // refresh

    }
    public void showDate(View v) {
        DialogFragment newFragment = new MyDatePickerFragment();
        newFragment.show(getFragmentManager(), "date picker");
    }
}
