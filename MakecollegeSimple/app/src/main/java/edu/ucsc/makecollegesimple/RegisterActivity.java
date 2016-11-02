package edu.ucsc.makecollegesimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by pokegrace on 11/2/2016.
 */

public class RegisterActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // creating variables for the respective types and IDs
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etRUserName = (EditText) findViewById(R.id.etRUserName);
        final EditText etRPassword = (EditText) findViewById(R.id.etRPassword);
        final EditText etRPasswordConfirm = (EditText) findViewById(R.id.etRPasswordConfirm);
        final Button bRegister = (Button) findViewById(R.id.bRegister);
        final TextView tvRegister = (TextView) findViewById(R.id.tvRegister);

        // when user clicks bRegister, goes to MainMenu activity
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(RegisterActivity.this, MainMenu.class);

                RegisterActivity.this.startActivity(registerIntent);
            }
        });
    }
}
