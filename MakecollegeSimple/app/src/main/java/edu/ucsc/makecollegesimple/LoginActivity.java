package edu.ucsc.makecollegesimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by pokegrace on 11/2/2016.
 */

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // creating variables for respective types and IDs
        final EditText etUserName = (EditText) findViewById(R.id.etUserName);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        final TextView tvLogin = (TextView) findViewById(R.id.tvLogin);
        
        // when user clicks RegisterLink, takes user to Register Activity
        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // creating intent that opens RegisterActivity
                Intent newUserIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                
                // telling LoginActivity to perform registerIntent
                LoginActivity.this.startActivity(newUserIntent);
            }
        });
        
        // when user clicks bLogin, takes user to MainMenu
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(LoginActivity.this, MainMenu.class);
                
                LoginActivity.this.startActivity(loginIntent);
            }
        });
    }
}
