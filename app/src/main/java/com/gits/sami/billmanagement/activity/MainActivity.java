package com.gits.sami.billmanagement.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.gits.sami.billmanagement.R;
import com.gits.sami.billmanagement.db.DbHelper;
import com.gits.sami.billmanagement.db.PopulatedOpenHelper;
import com.gits.sami.billmanagement.fragments.DatePickerFragment;
import com.gits.sami.billmanagement.fragments.ElectricityFragment;
import com.gits.sami.billmanagement.fragments.GasFragment;
import com.gits.sami.billmanagement.fragments.ReportsFragment;
import com.gits.sami.billmanagement.fragments.WasaFragment;
import com.gits.sami.billmanagement.others.Utility.dateEnum;
import com.gits.sami.billmanagement.others.ViewPagerAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, NavigationView.OnNavigationItemSelectedListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Init();
        InitNavigation();
        PopulatedOpenHelper.getInstance(getApplicationContext());
    }

    private void InitNavigation() {


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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ElectricityFragment(), "Electricity");
        adapter.addFragment(new WasaFragment(), "Wasa");
        adapter.addFragment(new GasFragment(), "Gas");
        adapter.addFragment(new ReportsFragment(), "Reports");
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_electricity) {
            moveToFragment(new ElectricityFragment());
        } else if (id == R.id.nav_wasa) {
            moveToFragment(new WasaFragment());
        } else if (id == R.id.nav_gas) {

        } else if (id == R.id.nav_reports) {

        } else*/ if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void moveToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.viewpager, fragment, "createPost").addToBackStack(null).commit();

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
