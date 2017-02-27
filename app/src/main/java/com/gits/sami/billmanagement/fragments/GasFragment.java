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


public class GasFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    DbHelper dbHelper;
    private EditText serialNoEditText;
    private EditText meterNoEditText;
    private EditText amountEditText;
    private EditText billingMonthTextView;
    private EditText paymentMonthTextView;
    private EditText fineAmountTextView;
    private Spinner eIsLateSpinner;
    private Button saveButton;

    public GasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void Init(View rootView) {
        dbHelper = new DbHelper(rootView.getContext());
        serialNoEditText = (EditText) rootView.findViewById(R.id.gSerialNoEditText);
        meterNoEditText = (EditText) rootView.findViewById(R.id.gMeterNoEditText);
        amountEditText = (EditText) rootView.findViewById(R.id.gAmountEditText);
        billingMonthTextView = (EditText) rootView.findViewById(R.id.gBillingMonthEditText);
        paymentMonthTextView = (EditText) rootView.findViewById(R.id.gPaymentDateEditText);
        fineAmountTextView = (EditText) rootView.findViewById(R.id.gFineAmountEditText);
        eIsLateSpinner = (Spinner) rootView.findViewById(R.id.gIsLate);
        saveButton = (Button) rootView.findViewById(R.id.gSaveButton);
        saveButton.setOnClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_gas, container, false);
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

    private boolean validateBill() {

        if (isEmpty(serialNoEditText)) {
            Toast.makeText(getContext(), "Serial no cannot be blank", Toast.LENGTH_LONG).show();
            return false;
        }
        if (isEmpty(meterNoEditText)) {
            Toast.makeText(getContext(), "Meter no cannot be blank", Toast.LENGTH_LONG).show();
            return false;
        }
        if (isEmpty(amountEditText)) {
            Toast.makeText(getContext(), "Amount cannot be blank", Toast.LENGTH_LONG).show();
            return false;
        } else {
            try {
                Double.parseDouble(getMyText(amountEditText));
            } catch (Exception e) {
                Toast.makeText(getContext(), "Check amount properly", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        if (isEmpty(billingMonthTextView)) {
            Toast.makeText(getContext(), "Billing month cannot be blank", Toast.LENGTH_LONG).show();
            return false;
        }
        if (getDate(getMyText(billingMonthTextView), Utility.myDateFormat.MMM_yyyy).equals(Utility.ErrorDate)) {
            Toast.makeText(getContext(), "Billing Date format error", Toast.LENGTH_LONG).show();
            return false;
        }
        if (isEmpty(paymentMonthTextView)) {
            Toast.makeText(getContext(), "Payment date cannot be blank", Toast.LENGTH_LONG).show();
            return false;
        }
        if (getDate(getMyText(paymentMonthTextView), Utility.myDateFormat.dd_MMM_yyyy).equals(Utility.ErrorDate)) {
            Toast.makeText(getContext(), "Payment Date format error", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!isEmpty(fineAmountTextView)) {
            try {
                Double.parseDouble(getMyText(fineAmountTextView));
            } catch (Exception ex) {
                Toast.makeText(getContext(), "Check fine amount properly", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private Bill getBill() {
        if (validateBill()) {
            Bill bill = new Bill();
            bill.billType = Utility.billType.Gas;
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
        Bill bill = getBill();
        if (bill != null) {
            if (dbHelper.insertElectricity(bill)) {
                Toast.makeText(v.getContext(), "Insertion Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "Insertion failed", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
