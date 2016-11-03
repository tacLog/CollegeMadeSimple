package edu.ucsc.makecollegesimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by pokegrace on 11/2/2016.
 */

public class WelcomeActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // creating variables for the respective types and IDs
        final TextView tvWelcomeMessage = (TextView) findViewById(R.id.tvWelcomeMessage);
        final TextView tvCredits = (TextView) findViewById(R.id.tvCredits);
        final TextView tvMissionStatement = (TextView) findViewById(R.id.tvMissionStatement);
        final TextView tvSkipTutorial = (TextView) findViewById(R.id.tvSkipTutorial);
        final Button bStartingSettings = (Button) findViewById(R.id.bStartingSettings);

        // when user clicks bTutorial, it takes them to StartingSettingsActivity
        bStartingSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tutorialIntent = new Intent(WelcomeActivity.this, StartingSettingsActivity.class);

                WelcomeActivity.this.startActivity(tutorialIntent);
            }
        });

    }
}
