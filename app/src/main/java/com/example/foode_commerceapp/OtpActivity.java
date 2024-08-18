package com.example.foode_commerceapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
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


public class OtpActivity extends AppCompatActivity {

    TextView secondTimer,sendBtnText,textView78;
    Button resendButton;
    CountDownTimer timer;
    int checkOTP;
    EditText digit1;
    EditText digit2;
    EditText digit3;
    EditText digit4;
    EditText digit5;
    EditText digit6;

    public static final String EMAIL_TO_SUCCESS = "com.example.foode_commerceapp.extra.EMAIL_TO_SUCCESS";
    public static final String OTP_BOOL = "com.example.foode_commerceapp.extra.OTP_BOOL";

    boolean Otp_bool = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        secondTimer = findViewById(R.id.secondTimer);
        resendButton =  findViewById(R.id.resendButton);
        sendBtnText = findViewById(R.id.sendBtnText);

        textView78 = findViewById(R.id.textView78);

        Intent intent = getIntent();
        String emailSTR = intent.getStringExtra(PaymentInterface.EMAIL_TO_OTP_PAGE);

        textView78.setText("to your email " + emailSTR);
    }

    public void sendEmail(View view){
        Otp_bool = true;
        Random random = new Random();
        int OTP_NUMBER = random.nextInt(500000-400000) + 452364;

        Intent intentEmail = getIntent();
        String emailStr = intentEmail.getStringExtra(PaymentInterface.EMAIL_TO_OTP_PAGE);

        checkOTP = OTP_NUMBER;

        try {
            String senderEmail = "sendingtest24@gmail.com";
//            String receiverEmails = "atishbasak38@gmail.com";
            String receiverEmails = emailStr;
            String passwordSenderEmail = "lejohedxrdcxjmzb";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, passwordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmails));

            mimeMessage.setSubject("OTP Verification");
            mimeMessage.setText("Hi,\n\n"+OTP_NUMBER + " is OTP(one time password) for verification. \n \nThanks for trying the food delivery application. \n \nRegards, \nAtish Basak");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        // timer setup
        timer = new CountDownTimer(45000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                secondTimer.setText("Resend OTP in" + " " + millisUntilFinished/1000  + "s");
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


    //  for OTP verification
    public  void checkingOTP(View view){
        // check send button is clicked or not
        if(Otp_bool){
            //  find_view the digit box
            digit1 = findViewById(R.id.digit1);
            digit2 = findViewById(R.id.digit2);
            digit3 = findViewById(R.id.digit3);
            digit4 = findViewById(R.id.digit4);
            digit5 = findViewById(R.id.digit5);
            digit6 = findViewById(R.id.digit6);

            //  get string from edittext
            String num1 = digit1.getText().toString();
            String num2 = digit2.getText().toString();
            String num3 = digit3.getText().toString();
            String num4 = digit4.getText().toString();
            String num5 = digit5.getText().toString();
            String num6 = digit6.getText().toString();


            // check the otp box is empty or not
            if(num1.isEmpty() || num2.isEmpty() || num3.isEmpty() || num4.isEmpty() || num5.isEmpty() || num6.isEmpty()){
                Toast.makeText(this, "Please Put a valid OTP", Toast.LENGTH_SHORT).show();
            }else{
                //  string convert to integer
                int num1INT = Integer.parseInt(num1);
                int num2INT = Integer.parseInt(num2);
                int num3INT = Integer.parseInt(num3);
                int num4INT = Integer.parseInt(num4);
                int num5INT = Integer.parseInt(num5);
                int num6INT = Integer.parseInt(num6);

                // OTP calculation
                int convertedINT = ((num1INT*100000) + (num2INT*10000) + (num3INT*1000) + (num4INT*100) + (num5INT*10) + (num6INT));

                // condition for going to next activity
                if(convertedINT == checkOTP){
                    Intent intentEmail = getIntent();
                    String emailStr = intentEmail.getStringExtra(PaymentInterface.EMAIL_TO_OTP_PAGE);
                    Intent intent = new Intent(this, SuccessPage.class);
//                    intent.putExtra(OTP_BOOL,true);
//                    intent.putExtra(EMAIL_TO_SUCCESS,emailStr);
                    sendEmailEnd(emailStr);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(this, "Please click the send button and put the valid OTP to varify", Toast.LENGTH_SHORT).show();
        }
    }



    public void sendEmailEnd(String email) {
        Intent intent = getIntent();

        String senderEmail = "sendingtest24@gmail.com";
        String receiverEmail = email;
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
            mimeMessage.setText("Hi,\n\n"+ "Thanks for trying the application. Don't worry no order will deliver to you. It's just a demo application. \n \nRegards, \nAtish Basak");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                    } catch (MessagingException e) {
//                        Log.e("OtpPageLogin", "Failed to send email", e);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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
    }

}