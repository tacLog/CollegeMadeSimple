package edu.ucsc.makecollegesimple;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by pokegrace on 11/21/2016.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // initializing variables
        final RadioGroup rgRadioSystemGroup = (RadioGroup) findViewById(R.id.rgRadioSystemGroup);
        final Button bSave = (Button) findViewById(R.id.bSave);

        // when the save button is clicked, it'll show a toast of the user's selection
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // gets the selected radio button from the group
                int selectedID = rgRadioSystemGroup.getCheckedRadioButtonId();

                // rbSelectedButton is the button that was selected from the group
                RadioButton rbSelectedButton = (RadioButton) findViewById(selectedID);

                Intent saveIntent = new Intent(SettingsActivity.this, MainMenu.class);
                SettingsActivity.this.startActivity(saveIntent);

                // displays what the user selected
                Toast.makeText(SettingsActivity.this,
                      rbSelectedButton.getText(), Toast.LENGTH_SHORT).show();
                save(selectedID);
            }
        });

        //load();
    }

    // save and load methods below make sure the same radio button is pressed when exiting the app
    // and re-entering the app
    private void save(int selectedID){
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("check", selectedID);
        editor.commit();
    }

    /* ***broken code***
    private void load(){
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        int selectedID = sharedPreferences.getInt("check", 0);

        if(selectedID > 0){
            RadioButton rbtn = (RadioButton) findViewById(selectedID);
            rbtn.setChecked(true);
        }
    }
    */
}

