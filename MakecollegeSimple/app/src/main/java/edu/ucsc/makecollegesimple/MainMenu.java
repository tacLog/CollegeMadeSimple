package edu.ucsc.makecollegesimple;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PieChartFragment.OnFragmentInteractionListener, CategoryEdit.OnFragmentInteractionListener {

    PieChart pieChart;
    private float[] savedCats = new float[5];
    private float totalCost;

    //Variables for storage of subcatatgories and thier values
    //Suplies values
    private static ArrayList<String> supCatagories = new ArrayList();
    private static ArrayList<String> supValues = new ArrayList();


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
                    savedCats[flag] = newSuppliesCost;
                    editor.putFloat("saved_suppliesCost",newSuppliesCost);
                    editor.apply();
                }

            }


        }
        totalCost = savedCats[0]+savedCats[1]+savedCats[2]+savedCats[3]+savedCats[4];
        PieChartFragment pie1 = PieChartFragment.newInstance(savedCats, totalCost);
        PieChartFragment pie2 = PieChartFragment.newInstance(savedCats, totalCost);

        CategoryEdit suplies = CategoryEdit.newInstance("Supplies", supCatagories,supValues);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame1,suplies).commit();
        //fragmentManager.beginTransaction().replace(R.id.content_frame1,pie1).commit();
        //fragmentManager.beginTransaction().replace(R.id.content_frame2,pie2).commit();

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
            savedCats[0] = saved.getFloat("saved_suppliesCost", 10);
        }
        if (flag != 1) {
            savedCats[1] = saved.getFloat("saved_rentCost",10);
        }
        if (flag != 2) {
            savedCats[2] = saved.getFloat("saved_tranCost",10);
        }
        if (flag != 3) {
            savedCats[3] = saved.getFloat("saved_tuitCost",10);
        }
        if (flag != 4) {
            savedCats[4] = saved.getFloat("saved_perCost",10);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
