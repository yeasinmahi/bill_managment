package com.gits.sami.billmanagement.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gits.sami.billmanagement.R;
import com.gits.sami.billmanagement.db.DbHelper;
import com.gits.sami.billmanagement.model.Bill;
import com.gits.sami.billmanagement.others.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ReportsFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener{

    private TableLayout tableLayout;
    private DbHelper dbHelper;
    private Spinner billSpinner;
    private EditText reportMonthEditText;
    private Button searchButton;

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
        Init(view);
        InitSpinner();
        dbHelper = new DbHelper(getContext());
        tableLayout = (TableLayout) view.findViewById(R.id.reportTable);

        ArrayList<Bill> content = dbHelper.getAllElectricity(Utility.billType.All,Utility.ErrorDate);
        createReportTable(content);
        return view;
    }

    private void InitSpinner() {
        // Spinner Drop down elements
        List<String> items = new ArrayList<String>();
        items.add(Utility.billType.All.toString());
        items.add(Utility.billType.Electricity.toString());
        items.add(Utility.billType.Wasa.toString());
        items.add(Utility.billType.Gas.toString());
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        billSpinner.setAdapter(dataAdapter);
        // Spinner click listener
        billSpinner.setOnItemSelectedListener(this);
    }

    private void Init(View view) {
        billSpinner = (Spinner) view.findViewById(R.id.billSpinner);
        reportMonthEditText= (EditText) view.findViewById(R.id.reportMonthEditText);
        searchButton = (Button) view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

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
        tableLayout.removeAllViews();
        if (!content.isEmpty()) {
            populateReportTable();
            for (Bill bill : content) {
                TableRow tr = getARow(bill);
                tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
            }
        }
        else{
            tableLayout.removeAllViews();
            Toast.makeText(getContext(),"No Data Found",Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        ArrayList<Bill> content;
        Utility.billType billType = Utility.billType.valueOf(billSpinner.getItemAtPosition(billSpinner.getSelectedItemPosition()).toString());
        Date reportMonth=Utility.ErrorDate;
        if(!reportMonthEditText.getText().toString().trim().equals("")){
            if (Utility.getDate(reportMonthEditText.getText().toString().trim(), Utility.myDateFormat.MMM_yyyy).equals(Utility.ErrorDate)) {
                Toast.makeText(getContext(), "Selected month format error", Toast.LENGTH_LONG).show();
                return;
            }else {
                reportMonth = Utility.getDate(reportMonthEditText.getText().toString().trim(), Utility.myDateFormat.MMM_yyyy);
            }
        }
        content = dbHelper.getAllElectricity(billType,reportMonth);
        createReportTable(content);
    }
}
