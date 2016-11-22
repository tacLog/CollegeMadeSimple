package edu.ucsc.makecollegesimple;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by pokegrace on 11/21/2016.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // initializing variables
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvSystem = (TextView) findViewById(R.id.tvSystem);
        RadioButton rbQuarter = (RadioButton) findViewById(R.id.rbQuarter);
        RadioButton rbSemester = (RadioButton) findViewById(R.id.rbSemester);

    }
}
