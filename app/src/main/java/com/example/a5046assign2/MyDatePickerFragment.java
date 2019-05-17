package com.example.a5046assign2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MyDatePickerFragment extends DialogFragment {
    TextView dateTextview;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dateTextview = (TextView) getActivity().findViewById(R.id.dateTextView);
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    Toast.makeText(getActivity(), "selected date is " + view.getYear() +
                            "-" + (view.getMonth()+1) +
                            "-" + view.getDayOfMonth(), Toast.LENGTH_SHORT).show();

                    int m =view.getMonth()+1;
                    String edited_month;
                    if(m<10){
                        edited_month="0"+String.valueOf(view.getMonth()+1);
                    }
                    else{
                        edited_month=String.valueOf(view.getMonth()+1);
                    }
                    dateTextview.setText(view.getYear() + "-" + edited_month + "-" + view.getDayOfMonth());
                }
            };
}

