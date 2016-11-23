package edu.ucsc.makecollegesimple;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PieChartFragment.OnFragmentInteractionListener{

    //output string tags:
    private static final String catIn = "categories";
    private static final String valuesIn = "values";
    private static final String masterCatTag = "masterCategories";
    private static final String masterValTag = "masterValues";
    private static final String catTotTag = "categoryTotals";


    PieChart pieChart;
    private static float totalCost;
    private static float totalIn;





    //Variables for storage of subcatatgories and thier values
    private static final String[] costTags = {"Costs:","Supplies","Rent","Transportation","Tution","Personal"};

    //Income Variables

    private static final String[] inTags = {"Income:","Loans","Scholarships","Job","Other","Grants"};

    //subcategory values
    private static String[][]   masterCategories = new String[10][5];
    private static String[][]   masterValues = new String[10][5];
    private static float[]        categoryTotals = new float[10];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        SharedPreferences saver = getPreferences(Context.MODE_PRIVATE);
        String[] inCategories = null;
        String[] inValues   = null;

        final TextView inText = (TextView) findViewById(R.id.inText);
        final TextView costsText = (TextView) findViewById(R.id.costsText);
        final TextView sum = (TextView) findViewById(R.id.sum);

        //load setup data
        int flag = getIntent().getFlags();
        if(getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            inCategories = bundle.getStringArray(catIn);
            inValues = bundle.getStringArray(valuesIn);
        }
        else {
            loadAll(saver);
        }

        if(inCategories!=null && inValues != null){
            int newTotal = 0;
            for(int i =0; i <5; i++){
                masterCategories[flag][i]   =inCategories[i];
                masterValues[flag][i]       =inValues[i];
                if(inValues[i]!=null){
                    newTotal +=                 Float.valueOf(inValues[i]);
                }
            }
            categoryTotals[flag]= newTotal;
        }


        totalCost = categoryTotals[0] + categoryTotals[1] + categoryTotals [2] + categoryTotals[3] + categoryTotals[4];
        totalIn = categoryTotals[5] + categoryTotals[6] + categoryTotals [7] + categoryTotals[8] + categoryTotals[9];
        inText.setText(String.format("%.2f",totalIn) + " Costs");
        costsText.setText(String.format("%.2f",totalCost) + " Costs");
        float total = totalIn - totalCost;
        sum.setText(String.format("%.2f",total) + " Net");





        float[] savedCostCats = Arrays.copyOfRange(categoryTotals, 0, 5);
        float[] savedInCats =   Arrays.copyOfRange(categoryTotals, 5, 10);

        PieChartFragment pieCost = PieChartFragment.newInstance(costTags, savedCostCats, totalCost, 0);
        PieChartFragment pieIn = PieChartFragment.newInstance(inTags, savedInCats, totalIn, 1);


        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_frame1,pieCost).commit();
        manager.beginTransaction().replace(R.id.content_frame2,pieIn).commit();

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        saveAll(saver);

    }

    private void saveAll(SharedPreferences saver) {
        SharedPreferences.Editor editor = saver.edit();

        for(int i = 0; i < 50; i++){
            editor.putString(masterCatTag+i,masterCategories[i%10][i%5]);
            editor.putString(masterValTag+i,masterValues[i%10][i%5]);
        }
        for (int i = 0; i < 10; i++){
            editor.putFloat(catTotTag+i,categoryTotals[i]);
        }
        editor.commit();
    }

    private void loadAll(SharedPreferences saver) {
        for(int i = 0; i < 50; i++){
            masterCategories[i%10][i%5]= saver.getString(masterCatTag+i, null);
            masterValues[i%10][i%5]= saver.getString(masterValTag+i, null);
        }
        for (int i = 0; i < 10; i++){
            categoryTotals[i] = saver.getFloat(catTotTag+i, 10);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // takes user back to login activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {

            Intent loginIntent = new Intent(MainMenu.this, LoginActivity.class);
            MainMenu.this.startActivity(loginIntent);

            return true;
        }

        if(id == R.id.action_settings){

            Intent settingsIntent = new Intent(MainMenu.this, SettingsActivity.class);
            MainMenu.this.startActivity(settingsIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id) {
            case R.id.nav_supplies:
                startCategoryEditActivity(0);
                break;
            case R.id.nav_rent:
                startCategoryEditActivity(1);
                break;
            case R.id.nav_transport:
                startCategoryEditActivity(2);
                break;
            case R.id.nav_tuition:
                startCategoryEditActivity(3);
                break;
            case R.id.nav_personal:
                startCategoryEditActivity(4);
                break;
            case R.id.nav_loans:
                startCategoryEditActivity(5);
                break;
            case R.id.nav_scholar:
                startCategoryEditActivity(6);
                break;
            case R.id.nav_job:
                startCategoryEditActivity(7);
                break;
            case R.id.nav_other:
                startCategoryEditActivity(8);
                break;
            case R.id.nav_grants:
                startCategoryEditActivity(9);
                break;
            case R.id.nav_settings:
                Intent settingsIntent = new Intent(MainMenu.this, SettingsActivity.class);
                MainMenu.this.startActivity(settingsIntent);
                break;
            case R.id.nav_help:
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startCategoryEditActivity(int i) {
        Intent newUserIntent = new Intent(MainMenu.this, CategoryEditActivity.class);
        // telling LoginActivity to perform registerIntent
        setSendDataCatEdit(newUserIntent, i);
        MainMenu.this.startActivity(newUserIntent);
        finish();
    }

    private void setSendDataCatEdit(Intent newUserIntent, int i) {
        newUserIntent.setFlags(i);
        newUserIntent.putExtra(catIn, masterCategories[i]);
        newUserIntent.putExtra(valuesIn, masterValues[i] );
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
