package edu.ucsc.makecollegesimple;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    PieChart pieChart;
    float suppliesCost;
    float rentCost;
    float transportationCost;
    float tuitionCost;
    float personalCost;
    float totalCost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        SharedPreferences saved = getPreferences(MODE_PRIVATE);
        Bundle bundle = getIntent().getExtras(); //get variables that were passed from other activities (if any)
        if (bundle == null) {
            //if no variables were passed
            loadValues(saved, 8);/**/
            Log.i("bundle", String.valueOf(bundle)); //ignore this, used for debugging

        }else{
            //start the editor to input new data as we get it
            SharedPreferences.Editor editor = saved.edit();
            int flag = getIntent().getFlags();

            switch (flag){
                case 0: {
                    loadValues(saved, flag);
                    // if there were variables passed
                    //convert bundle object to string
                    String strsuppliesCost =  String.valueOf(bundle.get("suppliesCost"));
                    Log.i("bundle2",strsuppliesCost); //ignore this, used for debugging
                    //convert string to float
                    float newSuppliesCost = Float.parseFloat(strsuppliesCost);
                    Log.i("bundle3",String.valueOf(newSuppliesCost)); //ignore this, used for debugging
                    //update the Supplies slice on piechart
                    suppliesCost = newSuppliesCost;
                    editor.putFloat("saved_suppliesCost",newSuppliesCost);
                    editor.apply();
                }

            }


        }

        //piechart slices are initialized at 10 for now
        totalCost = suppliesCost + rentCost + transportationCost + tuitionCost + personalCost;

        //Pie chart
        //I used a library to create the pie chart, please refer to https://github.com/PhilJay/MPAndroidChart for documentation.
        pieChart = (PieChart) findViewById(R.id.CostsPieChart);
        //this is the y-axis
        // first parameter of Entry represents the amount in the category and second parameter is index numbered 0-4
        final ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(suppliesCost , 0));  //supplies
        entries.add(new Entry (rentCost, 1));   //rent
        entries.add(new Entry(transportationCost, 2));   //transportation
        entries.add(new Entry(tuitionCost, 3));  //tuition
        entries.add(new Entry(personalCost, 4));   //personal

        PieDataSet dataset = new PieDataSet(entries, "");

        //this is the x-axis
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Supplies");
        labels.add("Rent");
        labels.add("Transportation");
        labels.add("Tuition");
        labels.add("Personal");

        PieData data = new PieData(labels, dataset);

        pieChart.setCenterText("Costs: $"  + totalCost);
        pieChart.setCenterTextSize(15f);

        //appearance
        data.setValueTextSize(11f);
        dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
        pieChart.setDescription(null);
        pieChart.setData(data);
        pieChart.animateY(2500);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Log.i("tag", String.valueOf(e.getXIndex()));
                int costsCategory = e.getXIndex();

                if (costsCategory == 0){
                    // creating intent that opens RegisterActivity
                    Intent newUserIntent = new Intent(MainMenu.this, CostEditActivity.class);
                    newUserIntent.setFlags(0);
                    // telling LoginActivity to perform registerIntent
                    MainMenu.this.startActivity(newUserIntent);

                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void loadValues(SharedPreferences saved, int flag) {
        if (flag != 0){
            suppliesCost = saved.getFloat("saved_suppliesCost", 10);
        }
        if (flag != 1) {
            rentCost = saved.getFloat("saved_rentCost",10);
        }
        if (flag != 2) {
            transportationCost = saved.getFloat("saved_tranCost",10);
        }
        if (flag != 3) {
            tuitionCost = saved.getFloat("saved_tuitCost",10);
        }
        if (flag != 4) {
            personalCost = saved.getFloat("saved_perCost",10);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
            case R.id.nav_home:
                                    break;
            case R.id.nav_tuition:
                                    break;
            case R.id.nav_personal:
                                    break;
            case R.id.nav_supplies: Intent newUserIntent = new Intent(MainMenu.this, CostEditActivity.class);
                                    // telling LoginActivity to perform registerIntent
                                    MainMenu.this.startActivity(newUserIntent);
                                    break;
            case R.id.nav_rent:
                                    break;
            case R.id.nav_transport:
                                    break;
            case R.id.nav_loans:
                                    break;
            case R.id.nav_scholar:
                                    break;
            case R.id.nav_job:
                                    break;
            case R.id.nav_grants:
                                    break;
            case R.id.nav_other:
                                    break;
            case R.id.nav_settings:
                                    break;
            case R.id.nav_help:
                                    break;
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
