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

    CardView googleSignIn,othersLogin;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        googleSignIn = findViewById(R.id.googleSignIn);
        othersLogin = findViewById(R.id.othersLogin);

        Intent intentBool = getIntent();
        if (getSharedPreferences(PREFS_NAME, 0).getBoolean(PREF_LOGGED_IN, false)) {
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

    public void openHomeActivity(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }
}
