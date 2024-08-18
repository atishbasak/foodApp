package com.example.foode_commerceapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ContactMePage extends AppCompatActivity {

    EditText editTextText,editTextTextEmailAddress,editTextText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_me_page);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    public void sendEmailContact(View view) {

        String senderEmail = "sendingtest24@gmail.com";
        String receiverEmail = "devanshraj275@gmail.com";
        String passwordSenderEmail = "lejohedxrdcxjmzb";

        String stringHost = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", stringHost);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        editTextText = findViewById(R.id.editTextText);
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextText2 = findViewById(R.id.editTextText2);

        String emailEmail = editTextTextEmailAddress.getText().toString();
        String emailName = editTextText.getText().toString();
        String emailDecsription = editTextText2.getText().toString();

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, passwordSenderEmail);
            }
        });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            mimeMessage.setSubject("Contact Email");
            mimeMessage.setText("From : "+ emailEmail + "\nName : " + emailName + "\n\n" + emailDecsription);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ContactMePage.this, "Email Sent", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (MessagingException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ContactMePage.this, "Failed to send email", Toast.LENGTH_SHORT).show();
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

        editTextText.setText(" ");
        editTextTextEmailAddress.setText(" ");
        editTextText2.setText(" ");
    }

    public void toFacebookPageCont(View view){
        Uri uri = Uri.parse("https://www.facebook.com/share/QkywyR6Ar15dSpSx/?mibextid=qi2Omg");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void toLinkedInPageCont(View view){
        Uri uri = Uri.parse("https://www.linkedin.com/in/atish-basak-536a8131a?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    public void toHomePage(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}