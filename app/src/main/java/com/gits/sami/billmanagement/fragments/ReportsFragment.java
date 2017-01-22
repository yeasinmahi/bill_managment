package com.gits.sami.billmanagement.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gits.sami.billmanagement.R;
import com.gits.sami.billmanagement.db.DbHelper;
import com.gits.sami.billmanagement.model.Electricity;
import com.gits.sami.billmanagement.others.Utility;

import java.util.ArrayList;


public class ReportsFragment extends Fragment {

    private TableLayout tableLayout;
    private DbHelper dbHelper;

    public ReportsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        
        dbHelper = new DbHelper(getContext());
        tableLayout = (TableLayout) view.findViewById(R.id.reportTable);

        ArrayList<Electricity> content = dbHelper.getAllElectricity();

        createReportTable(content);
        return view;
    }

    private void populateReportTable() {
        TableRow tr = new TableRow(getContext());
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT));
        //tr.setPadding(10,10,10,10);

        tr.addView(getTextView("Serial No",true));
        tr.addView(getTextView("Meter No",true));
        tr.addView(getTextView("Amount",true));
        tr.addView(getTextView("Billing Month",true));
        tr.addView(getTextView("Payment Date",true));
        tr.addView(getTextView("Fine Amount",true));

        tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

    }
    private void createReportTable(ArrayList<Electricity> content) {
        populateReportTable();
        for (Electricity electricity : content) {
            TableRow tr = getARow(electricity);
            tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        }
    }
    private TableRow getARow(Electricity electricity) {
        TableRow tr = new TableRow(getContext());
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT));
        //tr.setPadding(10,10,10,10);


        tr.addView(getTextView(electricity.serialNo,false));
        tr.addView(getTextView(electricity.meterNo,false));
        tr.addView(getTextView(String.valueOf(electricity.amount),false));
        tr.addView(getTextView((Utility.getHalfDateAsString(electricity.billingMonth)),false));
        tr.addView(getTextView((Utility.getFullDateAsString(electricity.paymentDate)),false));
        tr.addView(getTextView((String.valueOf(electricity.fineAmount)),false));
        return tr;
    }

    private TextView getTextView(String value,boolean head) {
        TextView tvName = new TextView(getContext());
        tvName.setPadding(10, 10, 10, 10);
        tvName.setText(value);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (head){
                tvName.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_head_shape));
                tvName.setTextColor(Color.WHITE);
            }
            else {
                tvName.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_shape));
            }
        }
        return tvName;
    }
}
