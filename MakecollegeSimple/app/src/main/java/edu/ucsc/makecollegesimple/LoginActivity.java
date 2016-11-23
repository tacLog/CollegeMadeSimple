package edu.ucsc.makecollegesimple;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

// code by Kundan Roy from http://www.androidwarriors.com/2016/02/google-plus-integration-in-android-sign.html

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    // initialize variables
    TextView tvUserName;
    TextView tvTitle;
    Button bNewUser;
    GoogleApiClient mGoogleApiClient;
    TextView tvMenuLink;

    private static final int RC_SIGN_IN = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvUserName = (TextView) findViewById(R.id.tvUserName);
        bNewUser = (Button) findViewById(R.id.bNewUser);
        tvMenuLink = (TextView) findViewById(R.id.tvMenuLink);

        //Register both button and add click listener
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.btn_logout).setOnClickListener(this);

        // adding tvTitle to screen
        findViewById(R.id.tvTitle);

        // new user clicks this to go to new user activity
        bNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newUserIntent = new Intent(LoginActivity.this, NewUserActivity.class);
                LoginActivity.this.startActivity(newUserIntent);
            }
        });
        
        tvMenuLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuLinkIntent = new Intent(LoginActivity.this, MainMenu.class);
                LoginActivity.this.startActivity(menuLinkIntent);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.sign_in_button:

                signIn();

                break;
            case R.id.btn_logout:

                signOut();

                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        tvUserName.setText("");
                    }
                });
        Toast.makeText(LoginActivity.this,
                "You are signed out.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {

            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            tvUserName.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));

            Intent menuIntent = new Intent(LoginActivity.this, MainMenu.class);
            LoginActivity.this.startActivity(menuIntent);

        } else {
            // Signed out, show unauthenticated UI.
            // updateUI(false);
        }
    }
}