package com.gits.sami.billmanagement.fragments;

import android.graphics.Color;
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
import com.gits.sami.billmanagement.model.Bill;
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

        ArrayList<Bill> content = dbHelper.getAllElectricity();

        createReportTable(content);
        return view;
    }

    private void populateReportTable() {
        TableRow tr = new TableRow(getContext());
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT));
        //tr.setPadding(10,10,10,10);

        tr.addView(getTextView("Bill Type",true));
        tr.addView(getTextView("Serial No",true));
        tr.addView(getTextView("Meter No",true));
        tr.addView(getTextView("Amount",true));
        tr.addView(getTextView("Billing Month",true));
        tr.addView(getTextView("Payment Date",true));
        tr.addView(getTextView("Fine Amount",true));

        tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

    }
    private void createReportTable(ArrayList<Bill> content) {
        populateReportTable();
        for (Bill bill : content) {
            TableRow tr = getARow(bill);
            tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        }
    }
    private TableRow getARow(Bill bill) {
        TableRow tr = new TableRow(getContext());
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT));
        //tr.setPadding(10,10,10,10);
        tr.addView(getTextView(bill.billType.toString(),false));
        tr.addView(getTextView(bill.serialNo,false));
        tr.addView(getTextView(bill.meterNo,false));
        tr.addView(getTextView(String.valueOf(bill.amount),false));
        tr.addView(getTextView(Utility.getDateAsString(bill.billingMonth,Utility.myDateFormat.MMM_yyyy),false));
        tr.addView(getTextView(Utility.getDateAsString(bill.paymentDate,Utility.myDateFormat.dd_MMM_yyyy),false));
        tr.addView(getTextView((String.valueOf(bill.fineAmount)),false));
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
