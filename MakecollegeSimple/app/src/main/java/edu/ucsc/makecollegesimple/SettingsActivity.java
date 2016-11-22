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
<<<<<<< Updated upstream
        CheckBox cbQuarter = (CheckBox) findViewById(R.id.cbQuarter);
        CheckBox cbSemester = (CheckBox) findViewById(R.id.cbSemester);
=======
        RadioButton rbQuarter = (RadioButton) findViewById(R.id.rbQuarter);
        RadioButton rbSemester = (RadioButton) findViewById(R.id.rbSemester);

>>>>>>> Stashed changes
    }
}
