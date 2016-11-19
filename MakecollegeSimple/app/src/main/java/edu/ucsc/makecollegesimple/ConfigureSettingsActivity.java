package edu.ucsc.makecollegesimple;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by pokegrace on 11/15/2016.
 */

// purpose of this activity is to create the initial pie chart.
    // create a T/F variable that turns true if the user has completed this activity once,
    // so they don't have to keep configuring settings every time they log in.

public class ConfigureSettingsActivity extends AppCompatActivity{

    private boolean newUser = true;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_supplies);

    }
}
