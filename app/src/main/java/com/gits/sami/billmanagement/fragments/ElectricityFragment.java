package com.gits.sami.billmanagement.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.gits.sami.billmanagement.model.Bill;
import com.gits.sami.billmanagement.others.Utility;

import java.util.ArrayList;
import java.util.List;


public class ElectricityFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    DbHelper dbHelper;
    private EditText serialNoEditText;
    private EditText meterNoEditText;
    private EditText amountEditText;
    private EditText billingMonthTextView;
    private EditText paymentMonthTextView;
    private EditText fineAmountTextView;
    private Spinner eIsLateSpinner;
    private Button saveButton;

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
        View rootView = inflater.inflate(R.layout.fragment_electicity, container, false);
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
        if (id == 1)
            fineAmountTextView.setVisibility(View.VISIBLE);
        else
            fineAmountTextView.setVisibility(View.GONE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean validateElectricity() {

        if (serialNoEditText.getText().toString().trim().equals("")) {
            Toast.makeText(getContext(), "Serial no cannot be blank", Toast.LENGTH_LONG).show();
            return false;
        }
        if (meterNoEditText.getText().toString().trim().equals("")) {
            Toast.makeText(getContext(), "Meter no cannot be blank", Toast.LENGTH_LONG).show();
            return false;
        }
        if (amountEditText.getText().toString().trim().equals("")) {
            Toast.makeText(getContext(), "Amount cannot be blank", Toast.LENGTH_LONG).show();
            return false;
        } else {
            try {
                Double.parseDouble(amountEditText.getText().toString().trim());
            } catch (Exception e) {
                Toast.makeText(getContext(), "Check amount properly", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        if (billingMonthTextView.getText().toString().trim().equals("")) {
            Toast.makeText(getContext(), "Billing month cannot be blank", Toast.LENGTH_LONG).show();
            return false;
        }
        if (Utility.getDate(billingMonthTextView.getText().toString(), Utility.myDateFormat.MMM_yyyy).equals(Utility.ErrorDate)) {
            Toast.makeText(getContext(), "Billing Date format error", Toast.LENGTH_LONG).show();
            return false;
        }
        if (paymentMonthTextView.getText().toString().trim().equals("")) {
            Toast.makeText(getContext(), "Payment date cannot be blank", Toast.LENGTH_LONG).show();
            return false;
        }
        if (Utility.getDate(paymentMonthTextView.getText().toString(), Utility.myDateFormat.dd_MMM_yyyy).equals(Utility.ErrorDate)) {
            Toast.makeText(getContext(), "Payment Date format error", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!fineAmountTextView.getText().toString().trim().equals("")) {
            try {
                Double.parseDouble(fineAmountTextView.getText().toString().trim());
            } catch (Exception ex) {
                Toast.makeText(getContext(), "Check fine amount properly", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private Bill getElectricity() {
        if (validateElectricity()) {
            Bill bill = new Bill();
            bill.billType = Utility.billType.Electricity;
            bill.serialNo = serialNoEditText.getText().toString().trim();
            bill.meterNo = meterNoEditText.getText().toString().trim();
            bill.amount = Double.parseDouble(amountEditText.getText().toString().trim());
            bill.billingMonth = Utility.getDate(billingMonthTextView.getText().toString(), Utility.myDateFormat.MMM_yyyy);
            bill.paymentDate = Utility.getDate(paymentMonthTextView.getText().toString(), Utility.myDateFormat.dd_MMM_yyyy);
            bill.fineAmount = !fineAmountTextView.getText().toString().trim().equals("") ? Double.parseDouble(fineAmountTextView.getText().toString().trim()) : Double.parseDouble("0.0");
            return bill;
        } else {
            return null;
        }
    }

    @Override
    public void onClick(View v) {
        Bill bill = getElectricity();
        if (bill != null) {
            if (dbHelper.insertElectricity(bill)) {
                Toast.makeText(v.getContext(), "Insertion Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "Insertion failed", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
