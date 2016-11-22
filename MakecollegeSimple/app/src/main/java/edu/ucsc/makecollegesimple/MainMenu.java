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
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PieChartFragment.OnFragmentInteractionListener, CategoryEdit.OnFragmentInteractionListener {

    PieChart pieChart;
    private float[] savedCostCats = new float[5];
    private float[] savedInCats = new float[5];
    private float totalCost;
    private float totalIn;


    //Variables for storage of subcatatgories and thier values
    private static final String[] costTags = {"Costs:","Supplies","Rent","Transportation","Tution","Personal"};

    //Suplies values
    private static String[] supCatagories = new String[5];
    private static String[] supValues = new String[5];

    //rent values
    private static ArrayList<String> rentCatagories = new ArrayList();
    private static ArrayList<String> rentValues = new ArrayList();

    //personal expenses values
    private static ArrayList<String> perCatagories = new ArrayList();
    private static ArrayList<String> perValues = new ArrayList();

    //Transportation values
    private static ArrayList<String> tranCatagories = new ArrayList();
    private static ArrayList<String> tranValues = new ArrayList();

    //Tuition values
    private static ArrayList<String> tuitCatagories = new ArrayList();
    private static ArrayList<String> tuitValues = new ArrayList();

    //Income Variables

    private static final String[] inTags = {"Income:","Loans","Scholarships","Job","Other","Grants"};

    //Suplies values
    private static ArrayList<String> loansCatagories = new ArrayList();
    private static ArrayList<String> loansValues = new ArrayList();

    //rent values
    private static ArrayList<String> scholarCatagories = new ArrayList();
    private static ArrayList<String> scholarValues = new ArrayList();

    //Job
    private static ArrayList<String> jobCatagories = new ArrayList();
    private static ArrayList<String> jobValues = new ArrayList();

    //other values
    private static ArrayList<String> otherCatagories = new ArrayList();
    private static ArrayList<String> otherValues = new ArrayList();

    //grant values
    private static ArrayList<String> grantCatagories = new ArrayList();
    private static ArrayList<String> grantValues = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        SharedPreferences saved = getPreferences(MODE_PRIVATE);

        savedCostCats = loadValues(saved, 8, savedCostCats);
        savedInCats = loadValues(saved, 8, savedInCats);
        totalIn = savedInCats[0] + savedInCats[1]+savedInCats[2]+savedInCats[3]+savedInCats[4];
        totalCost = savedCostCats[0]+savedCostCats[1]+savedCostCats[2]+savedCostCats[3]+savedCostCats[4];

        PieChartFragment pieCost = PieChartFragment.newInstance(costTags, savedCostCats, totalCost, 0);
        PieChartFragment pieIn = PieChartFragment.newInstance(inTags, savedCostCats, totalCost, 1);

        CategoryEdit suplies = CategoryEdit.newInstance("Supplies", supCatagories,supValues);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_frame1,suplies).commit();
        //manager.beginTransaction().replace(R.id.content_frame1,pieCost).commit();
        //manager.beginTransaction().replace(R.id.content_frame2,pieIn).commit();

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

    }

    public void saveAll(){
        SharedPreferences saved = getPreferences(MODE_PRIVATE);
    }

    private float[] loadValues(SharedPreferences saved, int flag, float[] loaded) {
        if (flag != 0){
            loaded[0] = saved.getFloat("saved_suppliesCost", 10);
        }
        if (flag != 1) {
            loaded[1] = saved.getFloat("saved_rentCost",10);
        }
        if (flag != 2) {
            loaded[2] = saved.getFloat("saved_tranCost",10);
        }
        if (flag != 3) {
            loaded[3] = saved.getFloat("saved_tuitCost",10);
        }
        if (flag != 4) {
            loaded[4] = saved.getFloat("saved_perCost",10);
        }
        return loaded;

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
