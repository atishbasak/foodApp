//package com.example.foode_commerceapp;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.ActivityInfo;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class LoginPage extends AppCompatActivity {
//    EditText enterName;
//    EditText enterEmail;
//    Button continueToHome;
//
//    public static final String NAME_VALUE = "com.example.foode_commerceapp.extra.NAME_VALUE";
//    public static final String EMAIL_VALUE = "com.example.foode_commerceapp.extra.EMAIL_VALUE";
//    public static final String PREFS_NAME = "LoginPrefs";
//    public static final String PREF_LOGGED_IN = "logged_in";
//    public static final String PREF_OTP_VERIFIED = "otp_verified";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//        boolean loggedIn = settings.getBoolean(PREF_LOGGED_IN, false);
//        boolean otpVerified = settings.getBoolean(PREF_OTP_VERIFIED, false);
//
//        if (loggedIn && otpVerified) {
//            // User is already logged in and OTP is verified, go to HomeActivity
//            Intent intent = new Intent(this, HomeActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            // User is not logged in or OTP is not verified so show the login page
//            setContentView(R.layout.activity_login_page);
//            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//            enterName = findViewById(R.id.enterName);
//            enterEmail = findViewById(R.id.enterEmail);
//            continueToHome = findViewById(R.id.continueToHome);
//
//            // onClick listener for the button
//            continueToHome.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    loginToHome(v);
//                }
//            });
//        }
//    }
//
//    public void loginToHome(View view) {
//        if (enterName.length() == 0) {
//            enterName.setError("This field is required");
//        } else if (enterEmail.length() == 0) {
//            enterEmail.setError("This field is required");
//        } else {
//            String nameSTR = enterName.getText().toString();
////            String emailSTR = enterEmail.getText().toString();
//
//            Intent intent = new Intent(this, OtpPageLogin.class);
//            intent.putExtra(NAME_VALUE, nameSTR);
////            intent.putExtra(EMAIL_VALUE, emailSTR);
//            startActivity(intent);
//            finish();
//        }
//    }
//
//    public void openHomeActivity(View view) {
//        Intent intent = new Intent(this, HomeActivity.class);
//        startActivity(intent);
//    }
//}



package com.example.foode_commerceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class LoginPage extends AppCompatActivity {
    public static final String EMAIL_VALUE = "com.example.foode_commerceapp.extra.EMAIL_VALUE";
    public static final String NAME_VALUE = "com.example.foode_commerceapp.extra.NAME_VALUE";
    public static final String PREFS_NAME = "LoginPrefs";
    public static final String PREF_LOGGED_IN = "logged_in";
    Button continueToHome;
    EditText enterEmail;
    EditText enterName;




    ////////////////////////////////////////////////
//    GoogleSignInOptions gso;
//    GoogleSignInClient gsc;
    CardView googleSignIn,othersLogin;
    ////////////////////////////////////////////////


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        googleSignIn = findViewById(R.id.googleSignIn);
        othersLogin = findViewById(R.id.othersLogin);

        Intent intentBool = getIntent();
//        boolean login = intentBool.getBooleanExtra(OtpPageLogin.PREF_LOGGED_IN,false);
//        boolean login_valid = intentBool.getBooleanExtra(OtpPageLogin.LOGIN_VALID,false);
        if (getSharedPreferences(PREFS_NAME, 0).getBoolean(PREF_LOGGED_IN, false)) {
//        if (login && login_valid) {
//            startActivity(new Intent(this, HomeActivity.class));
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.activity_login_page);
        this.enterName = (EditText) findViewById(R.id.enterName);
        this.enterEmail = (EditText) findViewById(R.id.enterEmail);
        this.continueToHome = (Button) findViewById(R.id.continueToHome);
        this.continueToHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LoginPage.this.loginToHome(v);
            }
        });

//        googleSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(LoginPage.this, "Not being initialised till now", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        othersLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(LoginPage.this, "Not being initialised till now", Toast.LENGTH_SHORT).show();
//            }
//        });




        ////////////////////////////////////
//        googleSignIn = findViewById(R.id.googleSignIn);
//
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        gsc = GoogleSignIn.getClient(this,gso);
//
//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//        if(acct!=null){
//            navigateToSecondActivity();
//        }
//
//
//        googleSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signIn();
//            }
//        });


        ////////////////////////////////////
    }

    public void loginToHome(View view) {
        if (this.enterName.length() == 0) {
            this.enterName.setError("This field is required");
        } else if (this.enterEmail.length() == 0) {
            this.enterEmail.setError("This field is required");
        } else {
            String nameSTR = this.enterName.getText().toString();
            String emailSTR = this.enterEmail.getText().toString();
            Intent intent = new Intent(this, OtpPageLogin.class);
            intent.putExtra(NAME_VALUE, nameSTR);
            intent.putExtra(EMAIL_VALUE, emailSTR);
            SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
            editor.putBoolean(PREF_LOGGED_IN, true);
            editor.apply();
            startActivity(intent);
            finish();
        }
    }


    ////////////////////////////////////////////////////
//    void signIn(){
//        Intent signInIntent = gsc.getSignInIntent();
//        startActivityForResult(signInIntent,1000);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1000) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                task.getResult(ApiException.class);
//                navigateToSecondActivity();
//            } catch (ApiException e) {
//                int statusCode = e.getStatusCode();
//                Toast.makeText(getApplicationContext(), "Google sign-in failed: " + statusCode, Toast.LENGTH_LONG).show();
//                // You can also log the error for further debugging
//                e.printStackTrace();
//            }
//        }
//    }


    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1000){
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//
//            try {
//                task.getResult(ApiException.class);
//                navigateToSecondActivity();
//            } catch (ApiException e) {
//                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    }
//    void navigateToSecondActivity(){
//        finish();
//        Intent intent = new Intent(this,OtpPageLogin.class);
//        startActivity(intent);
//    }
    ////////////////////////////////////////////////////



    public void openHomeActivity(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }
}
