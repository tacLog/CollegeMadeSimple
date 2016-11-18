package edu.ucsc.makecollegesimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by pokegrace on 11/15/2016.
 */

public class ConfigureSettingsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_settings);

        // creating variables for the respective types and IDs
        final TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
    }
}
