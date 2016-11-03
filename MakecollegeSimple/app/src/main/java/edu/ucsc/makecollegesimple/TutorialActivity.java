package edu.ucsc.makecollegesimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by pokegrace on 11/2/2016.
 */

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial1);

        // creating variables for respective types and IDs
        final TextView tvTutorial1 = (TextView) findViewById(R.id.tvTutorial1);
    }
}
