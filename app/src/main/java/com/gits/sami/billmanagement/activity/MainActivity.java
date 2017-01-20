package com.gits.sami.billmanagement.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.gits.sami.billmanagement.R;
import com.gits.sami.billmanagement.db.DbHelper;
import com.gits.sami.billmanagement.db.PopulatedOpenHelper;
import com.gits.sami.billmanagement.fragments.DatePickerFragment;
import com.gits.sami.billmanagement.fragments.ElectricityFragment;
import com.gits.sami.billmanagement.fragments.GasFragment;
import com.gits.sami.billmanagement.fragments.WasaFragment;
import com.gits.sami.billmanagement.others.Utility.dateEnum;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Init();
        PopulatedOpenHelper.getInstance(getApplicationContext());
    }

    private void Init() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ElectricityFragment(), "Electricity");
        adapter.addFragment(new WasaFragment(), "Wasa");
        adapter.addFragment(new GasFragment(), "Gas");
        viewPager.setAdapter(adapter);
    }
    dateEnum bill;
    public void datePicker(View view){
        switch (view.getId()){
            case R.id.billingDate: bill = dateEnum.ElectricityBillingDate;
                break;
            case R.id.paymentDate: bill = dateEnum.ElectricityPaymentDate;
                break;

        }
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(),"date");
    }

    private void setDate(final Calendar calender){
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        switch (bill){
            case ElectricityBillingDate: ((EditText) findViewById(R.id.billingMonthEditText)).setText(dateFormat.format(calender.getTime()));
                break;
            case ElectricityPaymentDate: ((EditText) findViewById(R.id.paymentDateEditText)).setText(dateFormat.format(calender.getTime()));
                break;

        }

    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        setDate(calendar);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
