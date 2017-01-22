package com.gits.sami.billmanagement.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gits.sami.billmanagement.R;
import com.gits.sami.billmanagement.db.DbHelper;
import com.gits.sami.billmanagement.model.Electricity;
import com.gits.sami.billmanagement.others.Utility;

import java.util.ArrayList;
import java.util.List;


public class ElectricityFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText serialNoEditText;
    private EditText meterNoEditText;
    private EditText amountEditText;
    private EditText billingMonthTextView;
    private EditText paymentMonthTextView;
    private EditText fineAmountTextView;
    private Spinner eIsLateSpinner;
    private Button saveButton;
    DbHelper dbHelper;

    public ElectricityFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void Init(View rootView) {
        dbHelper = new DbHelper(rootView.getContext());
        serialNoEditText = (EditText) rootView.findViewById(R.id.serialNoEditText);
        meterNoEditText = (EditText) rootView.findViewById(R.id.meterNoEditText);
        amountEditText = (EditText) rootView.findViewById(R.id.amountEditText);
        billingMonthTextView = (EditText) rootView.findViewById(R.id.billingMonthEditText);
        paymentMonthTextView = (EditText) rootView.findViewById(R.id.paymentDateEditText);
        fineAmountTextView = (EditText) rootView.findViewById(R.id.fineAmountEditText);
        eIsLateSpinner = (Spinner) rootView.findViewById(R.id.isLate);
        saveButton = (Button) rootView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_electicity, container, false);
        Init(rootView);
        populateSpinner();

        return rootView;
    }

    private void populateSpinner() {

        // Spinner Drop down elements
        List<String> items = new ArrayList<String>();
        items.add("False");
        items.add("True");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        eIsLateSpinner.setAdapter(dataAdapter);
        // Spinner click listener
        eIsLateSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (id==1)
            fineAmountTextView.setVisibility(View.VISIBLE);
        else
            fineAmountTextView.setVisibility(View.GONE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void validateElectricity(){
    }
    public Electricity getElectricity() {
        Electricity electricity = new Electricity();
        electricity.serialNo = serialNoEditText.getText().toString();
        electricity.meterNo = meterNoEditText.getText().toString();
        electricity.amount = Double.parseDouble(amountEditText.getText().toString());
        electricity.billingMonth = Utility.getDate(billingMonthTextView.getText().toString());
        electricity.paymentDate = Utility.getDate(paymentMonthTextView.getText().toString());
        electricity.fineAmount = Double.parseDouble(fineAmountTextView.getText().toString());

        return electricity;

    }

    @Override
    public void onClick(View v) {
        Electricity electricity = getElectricity();
        if(dbHelper.insertElectricity(electricity)){
            Toast.makeText(v.getContext(),"Insert Successfully",Toast.LENGTH_SHORT).show();
        }
    }
}
