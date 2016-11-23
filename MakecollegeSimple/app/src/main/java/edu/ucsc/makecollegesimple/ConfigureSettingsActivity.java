package edu.ucsc.makecollegesimple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by pokegrace on 11/15/2016.
 */

// purpose of this activity is to create the initial pie chart.
    // create a T/F variable that turns true if the user has completed this activity once,
    // so they don't have to keep configuring settings every time they log in.

public class ConfigureSettingsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_settings);

        final Button bSave = (Button) findViewById(R.id.bSave);

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent menuIntent = new Intent(ConfigureSettingsActivity.this, MainMenu.class);
                ConfigureSettingsActivity.this.startActivity(menuIntent);

            }
        });

    }
}
