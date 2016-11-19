package edu.ucsc.makecollegesimple;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Pie chart
        //I used a library to create the pie chart, please refer to https://github.com/PhilJay/MPAndroidChart for documentation.
        pieChart = (PieChart) findViewById(R.id.idPieChart);
        //this is the y-axis where each Entry represents the amount in the category and each category is numbered 0-4
        final ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));  //(number before f is the amount for that category)
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(12f, 3));
        entries.add(new Entry(8f, 4));
        PieDataSet dataset = new PieDataSet(entries, "calls");

        //this is the x-axis
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Supplies");
        labels.add("Rent");
        labels.add("Transportation");
        labels.add("Tuition");
        labels.add("Personal");

        PieData data = new PieData(labels, dataset);
        data.setValueTextSize(11f);

        dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
        pieChart.setData(data);
        pieChart.setCenterText("Costs");
        pieChart.setCenterTextSize(15f);
        pieChart.animateY(2500);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Log.i("tag", String.valueOf(e.getXIndex()));
                int costsCategory = e.getXIndex();

                if (costsCategory == 0){

                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            case R.id.nav_supplies:
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
