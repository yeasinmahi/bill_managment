package com.gits.sami.billmanagement.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;

import com.gits.sami.billmanagement.others.Utility;

import java.util.Calendar;

import static com.gits.sami.billmanagement.others.Utility.*;
import static com.gits.sami.billmanagement.others.Utility.isFullDateEnum.*;


public class DatePickerFragment extends DialogFragment{

    private isFullDateEnum isFullDate= TRUE;
    public DatePickerFragment(){

    }
    public DatePickerFragment(isFullDateEnum isFullDate){
        this.isFullDate=isFullDate;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)getActivity(),year,month,day);
        if (isFullDate.equals(FALSE)){
            ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
        }
        return datePickerDialog;
    }


}
