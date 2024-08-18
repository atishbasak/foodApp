package com.example.foode_commerceapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentInterface extends AppCompatActivity {
    Button proceedToPay;
    EditText numberValue, nameValue, expiryValue, ccvValue;
    TextView cardNumber;
    TextView paymentValue;

    public static final String EMAIL_TO_OTP_PAGE = "com.example.foode_commerceapp.extra.EMAIL_TO_OTP_PAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_interface);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cardNumber = findViewById(R.id.cardNumber);
        proceedToPay = findViewById(R.id.proceedToPay);
        paymentValue = findViewById(R.id.paymentValue);

        numberValue = findViewById(R.id.numberValue);
        nameValue = findViewById(R.id.nameValue);
        expiryValue = findViewById(R.id.expiryValue);
        ccvValue = findViewById(R.id.ccvValue);

        Intent intent = getIntent();
        String payment_price = intent.getStringExtra(CodActivity.PRICE_TO_INTERFACE);
        String email_save_interface = intent.getStringExtra(CodActivity.EMAIL_TO_INTERFACE);
    }

    public void goTOOtpPage(View view) {
        Intent intentCardName = getIntent();
        String email_save_interface = intentCardName.getStringExtra(CodActivity.EMAIL_TO_INTERFACE);

        Intent intent = new Intent(this,OtpActivity.class);
        boolean paymentInterface = true;
        boolean valid = true;

        if (numberValue.length() == 0) {
            numberValue.setError("This field is required");
            valid = false;
        }
        if (nameValue.length() == 0) {
            nameValue.setError("This field is required");
            valid = false;
        }
        if (expiryValue.length() == 0) {
            expiryValue.setError("This field is required");
            valid = false;
        }
        if (ccvValue.length() == 0) {
            ccvValue.setError("This field is required");
            valid = false;
        }

        if (valid) {
            intent.putExtra(EMAIL_TO_OTP_PAGE,email_save_interface);
            startActivity(intent);
            finish();
        }
    }

    public void goToBilling(View view) {
        Intent intent = new Intent(this, BillingPage.class);
        startActivity(intent);
    }
}



