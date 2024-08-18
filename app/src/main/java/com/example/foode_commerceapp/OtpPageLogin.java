package com.example.foode_commerceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class OtpPageLogin extends AppCompatActivity {

    TextView secondTimer, sendBtnText,textView78;
    Button resendButton;
    CountDownTimer timer;
    int checkOTP;
    EditText digit1, digit2, digit3, digit4, digit5, digit6;

    boolean Otp_bool = false;

    public static final String LOGIN_VALID = "com.example.foode_commerceapp.extra.LOGIN_VALID";
    public static final String PREF_LOGGED_IN = "com.example.foode_commerceapp.extra.PREF_LOGGED_IN";
    public static final String PREFS_NAME = "LoginPrefs";
    public static final String PREF_OTP_VERIFIED = "otp_verified";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        digit1 = findViewById(R.id.digit1);
        digit2 = findViewById(R.id.digit2);
        digit3 = findViewById(R.id.digit3);
        digit4 = findViewById(R.id.digit4);
        digit5 = findViewById(R.id.digit5);
        digit6 = findViewById(R.id.digit6);

        textView78 = findViewById(R.id.textView78);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        String emailStr = intent.getStringExtra(LoginPage.EMAIL_VALUE);

        textView78.setText("to your email " + emailStr);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);


        if (preferences.getBoolean(PREF_OTP_VERIFIED, false)) {
            goToLoginPage();
            return;
        }

        secondTimer = findViewById(R.id.secondTimer);
        resendButton = findViewById(R.id.resendButton);
        sendBtnText = findViewById(R.id.sendBtnText);

        moveToNext();
    }



    public void sendEmail(View view) {
        Otp_bool = true;
        Random random = new Random();
        int OTP_NUMBER = random.nextInt(500000-400000) + 452364;

        checkOTP = OTP_NUMBER;
        Intent intent = getIntent();
        String emailStr = intent.getStringExtra(LoginPage.EMAIL_VALUE);

        String senderEmail = "sendingtest24@gmail.com";
        String receiverEmail = emailStr;
        String passwordSenderEmail = "lejohedxrdcxjmzb";

        String stringHost = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", stringHost);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, passwordSenderEmail);
            }
        });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            mimeMessage.setSubject("Log-in Verification");
            mimeMessage.setText("Hi,\n\n"+OTP_NUMBER + " is OTP(one time password) for verification. \n \nThanks for logging into the food delivery application. \n \nRegards, \nAtish Basak");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(OtpPageLogin.this, "Email Sent", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (MessagingException e) {
//                        Log.e("OtpPageLogin", "Failed to send email", e);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(OtpPageLogin.this, "Failed to send email", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            Toast.makeText(this, "Failed to create email message", Toast.LENGTH_SHORT).show();
        }

        // timer setup
        timer = new CountDownTimer(45000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                secondTimer.setText("Resend OTP in " + millisUntilFinished / 1000 + "s");
                resendButton.setEnabled(false);
            }

            @Override
            public void onFinish() {
                secondTimer.setText(" ");
                sendBtnText.setText("Resend");
                resendButton.setEnabled(true);
            }
        }.start();

        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
            }
        });
    }

    // for OTP verification
    public void checkingOTP(View view) {
        // check send button is clicked or not
        if (Otp_bool) {
            // find_view the digit box
            digit1 = findViewById(R.id.digit1);
            digit2 = findViewById(R.id.digit2);
            digit3 = findViewById(R.id.digit3);
            digit4 = findViewById(R.id.digit4);
            digit5 = findViewById(R.id.digit5);
            digit6 = findViewById(R.id.digit6);

            // get string from edittext
            String num1 = digit1.getText().toString();
            String num2 = digit2.getText().toString();
            String num3 = digit3.getText().toString();
            String num4 = digit4.getText().toString();
            String num5 = digit5.getText().toString();
            String num6 = digit6.getText().toString();

            // check the otp box is empty or not
            if (num1.isEmpty() || num2.isEmpty() || num3.isEmpty() || num4.isEmpty() || num5.isEmpty() || num6.isEmpty()) {
                Toast.makeText(this, "Please put a valid OTP", Toast.LENGTH_SHORT).show();
            } else {
                // string convert to integer
                int num1INT = Integer.parseInt(num1);
                int num2INT = Integer.parseInt(num2);
                int num3INT = Integer.parseInt(num3);
                int num4INT = Integer.parseInt(num4);
                int num5INT = Integer.parseInt(num5);
                int num6INT = Integer.parseInt(num6);

                // OTP calculation
                int convertedINT = ((num1INT * 100000) + (num2INT * 10000) + (num3INT * 1000) + (num4INT * 100) + (num5INT * 10) + (num6INT));

                // condition for going to next activity
                if (convertedINT == checkOTP) {
                    SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(PREF_OTP_VERIFIED, true);
                    editor.putBoolean(PREF_LOGGED_IN, true);
                    editor.apply();

                    goToLoginPage();
                } else {
                    Toast.makeText(this, "Please enter a valid OTP", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Please click the send button and put the valid OTP to verify", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToLoginPage() {
        Intent intent = new Intent(this, LoginPage.class);
        intent.putExtra(LOGIN_VALID, true);
        startActivity(intent);
        finish();
    }

    private void moveToNext(){
        digit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    digit2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        digit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    digit3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        digit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    digit4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        digit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    digit5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        digit5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    digit6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        digit6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    digit6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}


















//package com.example.foode_commerceapp;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.ActivityInfo;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.Properties;
//import java.util.Random;
//
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.AddressException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//public class OtpPageLogin extends AppCompatActivity {
//
//    TextView secondTimer, sendBtnText;
//    Button resendButton;
//    CountDownTimer timer;
//    int checkOTP;
//    EditText digit1, digit2, digit3, digit4, digit5, digit6;
//
//    boolean Otp_bool = false;
//    boolean login_valid = false;
//
//    public static final String LOGIN_VALID = "com.example.foode_commerceapp.extra.LOGIN_VALID";
//    public static final String PREFS_NAME = "com.example.foode_commerceapp.PREFERENCES";
//    public static final String PREF_OTP_VERIFIED = "otp_verified";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_otp);
//
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//        if (preferences.getBoolean(PREF_OTP_VERIFIED, false)) {
//            // OTP has been verified before, redirect to login page
//            redirectToLoginPage();
//            return;
//        }
//
//        secondTimer = findViewById(R.id.secondTimer);
//        resendButton = findViewById(R.id.resendButton);
//        sendBtnText = findViewById(R.id.sendBtnText);
//    }
//
//    public void sendEmail(View view) {
//        Otp_bool = true;
//        Random random = new Random();
//        int OTP_NUMBER = random.nextInt(500000 - 400000) + 452364;
//
//        checkOTP = OTP_NUMBER;
//
//        try {
//            String senderEmail = "sendingtest24@gmail.com";
//            String receiverEmail = "devanshraj275@gmail.com";
//            String passwordSenderEmail = "lejohedxrdcxjmzb";
//
//            String stringHost = "smtp.gmail.com";
//
//            Properties properties = System.getProperties();
//
//            properties.put("mail.smtp.host", stringHost);
//            properties.put("mail.smtp.port", "465");
//            properties.put("mail.smtp.ssl.enable", "true");
//            properties.put("mail.smtp.auth", "true");
//
//            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
//                @Override
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(senderEmail, passwordSenderEmail);
//                }
//            });
//
//            MimeMessage mimeMessage = new MimeMessage(session);
//            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
//            mimeMessage.setSubject("Log-in Verification ");
//            mimeMessage.setText("Hi,\n\n" + OTP_NUMBER + " is OTP (one time password) for verification. \n\nThanks for logging into the food delivery application. \n\nRegards, \nAtish Basak");
//
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Transport.send(mimeMessage);
//                    } catch (MessagingException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            thread.start();
//
//        } catch (AddressException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//        // timer setup
//        timer = new CountDownTimer(45000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                secondTimer.setText("Resend OTP in " + millisUntilFinished / 1000 + "s");
//                resendButton.setEnabled(false);
//            }
//
//            @Override
//            public void onFinish() {
//                secondTimer.setText(" ");
//                sendBtnText.setText("Resend");
//                resendButton.setEnabled(true);
//            }
//        }.start();
//
//        resendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                timer.start();
//            }
//        });
//    }
//
//    // for OTP verification
//    public void checkingOTP(View view) {
//        // check send button is clicked or not
//        if (Otp_bool) {
//            // find_view the digit box
//            digit1 = findViewById(R.id.digit1);
//            digit2 = findViewById(R.id.digit2);
//            digit3 = findViewById(R.id.digit3);
//            digit4 = findViewById(R.id.digit4);
//            digit5 = findViewById(R.id.digit5);
//            digit6 = findViewById(R.id.digit6);
//
//            // get string from edittext
//            String num1 = digit1.getText().toString();
//            String num2 = digit2.getText().toString();
//            String num3 = digit3.getText().toString();
//            String num4 = digit4.getText().toString();
//            String num5 = digit5.getText().toString();
//            String num6 = digit6.getText().toString();
//
//            // check the otp box is empty or not
//            if (num1.isEmpty() || num2.isEmpty() || num3.isEmpty() || num4.isEmpty() || num5.isEmpty() || num6.isEmpty()) {
//                Toast.makeText(this, "Please put a valid OTP", Toast.LENGTH_SHORT).show();
//            } else {
//                // string convert to integer
//                int num1INT = Integer.parseInt(num1);
//                int num2INT = Integer.parseInt(num2);
//                int num3INT = Integer.parseInt(num3);
//                int num4INT = Integer.parseInt(num4);
//                int num5INT = Integer.parseInt(num5);
//                int num6INT = Integer.parseInt(num6);
//
//                // OTP calculation
//                int convertedINT = ((num1INT * 100000) + (num2INT * 10000) + (num3INT * 1000) + (num4INT * 100) + (num5INT * 10) + (num6INT));
//
//                // condition for going to next activity
//                if (convertedINT == checkOTP) {
//                    login_valid = true;
//                    SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putBoolean(PREF_OTP_VERIFIED, true);
//                    editor.apply();
//
//                    redirectToLoginPage();
//                } else {
//                    Toast.makeText(this, "Please enter a valid OTP", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } else {
//            Toast.makeText(this, "Please click the send button and put the valid OTP to verify", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void redirectToLoginPage() {
//        Intent intent = new Intent(this, LoginPage.class);
//        intent.putExtra(LOGIN_VALID, login_valid);
//        startActivity(intent);
//        finish();
//    }
//}
