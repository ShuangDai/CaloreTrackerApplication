package com.example.a5046assign2;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;
//https://github.com/PhilJay/MPAndroidChart/wiki/Setting-Data

public class ReportActivity extends Fragment implements View.OnClickListener {

    View vDisplayReport;
    Button setDateBtn;
    TextView dateTextView;
    int burned;
    int consumpted;
    int rest;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Set Variables and listeners
        vDisplayReport = inflater.inflate(R.layout.activity_report, container,
                false);
        setDateBtn =(Button) vDisplayReport.findViewById(R.id.setDate);
        dateTextView =(TextView)vDisplayReport.findViewById(R.id.dateTextView);
        setDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = dateTextView.getText().toString();
                showDate(v);
                SharedPreferences sharedPreferences;
                sharedPreferences = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("userId","0");

                FindReportAsyncTask findReportAsyncTask = new FindReportAsyncTask();
                findReportAsyncTask.execute(userId,date);




            }
        });

        BarChart chart = vDisplayReport.findViewById(R.id.barchart);
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

        set.setColors(ContextCompat.getColor(getContext(), R.color.blue));
        set2.setColors(ContextCompat.getColor(getContext(), R.color.green));
        float groupSpace = 0.3f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.3f; // x2 dataset



        BarData barData = new BarData(set,set2);
        barData.setBarWidth(barWidth);
        chart.setData(barData);
        chart.groupBars(-0.3f, groupSpace, barSpace);

        chart.invalidate(); // refresh
        return vDisplayReport;
    }

    public void showDate(View v) {
        DialogFragment newFragment = new MyDatePickerFragment();
        newFragment.show(getFragmentManager(), "date picker");
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
            PieChart pieChart = vDisplayReport.findViewById(R.id.piechart);
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
    @Override
    public void onClick(View v) {
        // Get myUnits file
//        SharedPreferences spMyUnits =
//                getActivity().getSharedPreferences("myUnits", Context.MODE_PRIVATE);
//        String units= spMyUnits.getString("message",null);
//        tvDisplayUnits.setText(units);
    }
}
