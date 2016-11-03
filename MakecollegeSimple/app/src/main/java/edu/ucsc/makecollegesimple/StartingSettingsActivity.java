package edu.ucsc.makecollegesimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by pokegrace on 11/2/2016.
 */

public class StartingSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_settings);

        // creating variables for the respective types and IDs
        final TextView tvGettingStarted = (TextView) findViewById(R.id.tvGettingStarted);
        final EditText etSchoolName = (EditText) findViewById(R.id.etSchoolName);
        final CheckBox cbQuarter = (CheckBox) findViewById(R.id.cbQuarter);
        final CheckBox cbSemester = (CheckBox) findViewById(R.id.cbSemester);
        final CheckBox cbYes = (CheckBox) findViewById(R.id.cbYes);
        final CheckBox cbNo = (CheckBox) findViewById(R.id.cbNo);
        final TextView tvLiving = (TextView) findViewById(R.id.tvLiving);
        final TextView tvSkipTutorial = (TextView) findViewById(R.id.tvSkipTutorial);
        final Button bTutorial = (Button) findViewById(R.id.bTutorial);

        // when user clicks bTutorial, goes to TutorialActivity
        bTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tutorialIntent = new Intent(StartingSettingsActivity.this, TutorialActivity.class);

                StartingSettingsActivity.this.startActivity(tutorialIntent);
            }
        });

        // when user clicks tvSkipTutorial, goes to MainMenu
        tvSkipTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent skipTutorialIntent = new Intent(StartingSettingsActivity.this, MainMenu.class);

                StartingSettingsActivity.this.startActivity(skipTutorialIntent);
            }
        });
    }
}
