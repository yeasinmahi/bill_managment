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

import static com.gits.sami.billmanagement.others.Utility.getDate;
import static com.gits.sami.billmanagement.others.Utility.getMyText;
import static com.gits.sami.billmanagement.others.Utility.isEmpty;


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
        serialNoEditText = (EditText) rootView.findViewById(R.id.eSerialNoEditText);
        meterNoEditText = (EditText) rootView.findViewById(R.id.eMeterNoEditText);
        amountEditText = (EditText) rootView.findViewById(R.id.eAmountEditText);
        billingMonthTextView = (EditText) rootView.findViewById(R.id.eBillingMonthEditText);
        paymentMonthTextView = (EditText) rootView.findViewById(R.id.ePaymentDateEditText);
        fineAmountTextView = (EditText) rootView.findViewById(R.id.eFineAmountEditText);
        eIsLateSpinner = (Spinner) rootView.findViewById(R.id.eIsLate);
        saveButton = (Button) rootView.findViewById(R.id.eSaveButton);
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

        if (isEmpty(serialNoEditText)) {
            serialNoEditText.setError(Utility.getErrorMsg());
            return false;
        }
        if (isEmpty(meterNoEditText)) {
            meterNoEditText.setError(Utility.getErrorMsg());
            return false;
        }
        if (isEmpty(amountEditText)) {
            amountEditText.setError(Utility.getErrorMsg());
            return false;
        } else {
            try {
                Double.parseDouble(getMyText(amountEditText));
            } catch (Exception e) {
                amountEditText.setError("Check amount properly");
                return false;
            }
        }
        if (isEmpty(billingMonthTextView)) {
            billingMonthTextView.setError(Utility.getErrorMsg());
            return false;
        }
        if (getDate(getMyText(billingMonthTextView), Utility.myDateFormat.MMM_yyyy).equals(Utility.ErrorDate)) {
            billingMonthTextView.setError("Billing date format error");
            return false;
        }
        if (isEmpty(paymentMonthTextView)) {
            paymentMonthTextView.setError(Utility.getErrorMsg());
            return false;
        }
        if (getDate(getMyText(paymentMonthTextView), Utility.myDateFormat.dd_MMM_yyyy).equals(Utility.ErrorDate)) {
            paymentMonthTextView.setError("Payment date format error");
            return false;
        }
        if (!isEmpty(fineAmountTextView)) {
            try {
                Double.parseDouble(getMyText(fineAmountTextView));
            } catch (Exception ex) {
                fineAmountTextView.setError("Check fine amount properly");
                return false;
            }
        }
        return true;
    }

    private Bill getElectricity() {
        if (validateElectricity()) {
            Bill bill = new Bill();
            bill.billType = Utility.billType.Electricity;
            bill.serialNo = getMyText(serialNoEditText);
            bill.meterNo = getMyText(meterNoEditText);
            bill.amount = Double.parseDouble(getMyText(amountEditText));
            bill.billingMonth = getDate(getMyText(billingMonthTextView), Utility.myDateFormat.MMM_yyyy);
            bill.paymentDate = getDate(getMyText(paymentMonthTextView), Utility.myDateFormat.dd_MMM_yyyy);
            bill.fineAmount = !isEmpty(fineAmountTextView) ? Double.parseDouble(getMyText(fineAmountTextView)) : Double.parseDouble("0.0");
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
