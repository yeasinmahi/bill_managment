package com.gits.sami.billmanagement.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.FILL_PARENT));
        tr.setPadding(10,10,30,10);

        tr.addView(getTextView("Serial No"));
        tr.addView(getTextView("Meter No"));
        tr.addView(getTextView("Amount"));
        tr.addView(getTextView("Billing Month"));
        tr.addView(getTextView("Payment Date"));
        tr.addView(getTextView("Fine Amount"));

        tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT));

    }
    private void createReportTable(ArrayList<Electricity> content) {
        populateReportTable();
        for (Electricity electricity : content) {
            TableRow tr = getARow(electricity);
            tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT));
        }
    }
    private TableRow getARow(Electricity electricity) {

        TableRow tr = new TableRow(getContext());
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.FILL_PARENT));
        tr.setPadding(10,10,10,10);

        tr.addView(getTextView(electricity.serialNo));
        tr.addView(getTextView(electricity.meterNo));
        tr.addView(getTextView((String.valueOf(electricity.amount))));
        tr.addView(getTextView((Utility.getHalfDateAsString(electricity.billingMonth))));
        tr.addView(getTextView((Utility.getFullDateAsString(electricity.paymentDate))));
        tr.addView(getTextView((String.valueOf(electricity.fineAmount))));

        return tr;
    }

    private TextView getTextView(String value) {
        TextView tvName = new TextView(getContext());
        tvName.setPadding(10, 10, 10, 10);
        tvName.setText(value);
        return tvName;
    }
}
