package com.example.a5046assign2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

public class DatePickerWidget extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker_widget);
        DatePicker dp = findViewById(R.id.datePicker);
        dp.setOnDateChangedListener(dateChangedListener);
    }
    private DatePicker.OnDateChangedListener dateChangedListener =
            new DatePicker.OnDateChangedListener(){
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    Toast.makeText(DatePickerWidget.this, "picked date is " + datePicker.getDayOfMonth() +
                            "-" + (datePicker.getMonth()+1) +
                            "-" + datePicker.getYear(), Toast.LENGTH_SHORT).show();
                }
            };
}
