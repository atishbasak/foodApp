package com.example.foode_commerceapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Locale;
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

public class CodActivity extends AppCompatActivity implements LocationListener {

    EditText nameSpace, mobileNumberSpace, flatHouseSpace, areaStreetSpace, pincodeSpace, townCitySpace, emailSpace, enterState;
    Button useLocationBtn, addAddress;
    TextView codPrice;
    int checkOTP;
    boolean Otp_bool = false;
    LocationManager locationManager;

    public static final String PRICE_TO_INTERFACE = "com.example.foode_commerceapp.extra.PRICE_TO_INTERFACE";
    public static final String EMAIL_TO_INTERFACE = "com.example.foode_commerceapp.extra.PRICE_TO_INTERFACE";
    public static final String EMAIL_TO_SUCCESS_PAGE = "com.example.foode_commerceapp.extra.EMAIL_TO_SUCCESS_PAGE";
    public static final String COD_BOOL = "com.example.foode_commerceapp.extra.COD_BOOL";
    private static final int REQUEST_LOCATION_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cod);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        nameSpace = findViewById(R.id.nameSpace);
        mobileNumberSpace = findViewById(R.id.mobileNumberSpace);
        flatHouseSpace = findViewById(R.id.flatHouseSpace);
        areaStreetSpace = findViewById(R.id.areaStreetSpace);
        pincodeSpace = findViewById(R.id.pincodeSpace);
        townCitySpace = findViewById(R.id.townCitySpace);
        emailSpace = findViewById(R.id.emailSpace);
        enterState = findViewById(R.id.enterState);

        useLocationBtn = findViewById(R.id.useLocationBtn);
        addAddress = findViewById(R.id.addAddress);

        codPrice = findViewById(R.id.codPrice);

        Intent intent = getIntent();
        String codPriceValue = intent.getStringExtra(BillingPage.PRICE_TO_COD);
        codPrice.setText(codPriceValue);

        // Check location permission at startup
        if (ContextCompat.checkSelfPermission(CodActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CodActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }


        useLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
    }

    public void sendEmailForCOD(View view) {
        Intent intentTrue = getIntent();
        boolean cod_true = intentTrue.getBooleanExtra(BillingPage.COD_TRUE, false);

        Otp_bool = true;
        boolean valid = true;

        if (this.nameSpace.length() == 0) {
            this.nameSpace.setError("This field is required");
            valid = false;
        }
        if (this.mobileNumberSpace.length() == 0) {
            this.mobileNumberSpace.setError("This field is required");
            valid = false;
        }
        if (this.emailSpace.length() == 0) {
            this.emailSpace.setError("This field is required");
            valid = false;
        }
        if (this.flatHouseSpace.length() == 0) {
            this.flatHouseSpace.setError("This field is required");
            valid = false;
        }
        if (this.areaStreetSpace.length() == 0) {
            this.areaStreetSpace.setError("This field is required");
            valid = false;
        }
        if (this.pincodeSpace.length() == 0) {
            this.pincodeSpace.setError("This field is required");
            valid = false;
        }
        if (this.townCitySpace.length() == 0) {
            this.townCitySpace.setError("This field is required");
            valid = false;
        }
        if (this.enterState.length() == 0) {
            this.enterState.setError("This field is required");
            valid = false;
        }

        if (valid) {
            if (cod_true) {
                Intent intent = new Intent(this, SuccessPage.class);
                String codEmail = emailSpace.getText().toString();
                sendEmailEnd(codEmail);

                startActivity(intent);
            } else {
                String codEmail = emailSpace.getText().toString();
                String codPriceValue = codPrice.getText().toString();
                Intent intent = new Intent(this, PaymentInterface.class);
                intent.putExtra(PRICE_TO_INTERFACE, codPriceValue);
                intent.putExtra(EMAIL_TO_INTERFACE,codEmail);
                startActivity(intent);
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, CodActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(CodActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            String[] addressSlice = address.split(",");
            flatHouseSpace.setText(addressSlice[0]);
            areaStreetSpace.setText(addressSlice[2]);
            townCitySpace.setText(addressSlice[1]);
            slicingState(addressSlice[3]);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    public void slicingState(String statePin){
        pincodeSpace = findViewById(R.id.pincodeSpace);
        enterState = findViewById(R.id.enterState);
        StringBuffer num = new StringBuffer(), alpha = new StringBuffer();

        for(int i=0; i < statePin.length(); i++){
            if(Character.isAlphabetic(statePin.charAt(i)) || Character.isWhitespace(statePin.charAt(i))){
                alpha.append(statePin.charAt(i));
            } else if (Character.isDigit(statePin.charAt(i))) {
                num.append(statePin.charAt(i));
            }
        }
        pincodeSpace.setText(num);
        enterState.setText(alpha);
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






















// 2nd

//package com.example.foode_commerceapp;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import java.util.List;
//import java.util.Locale;
//
//public class CodActivity extends AppCompatActivity implements LocationListener {
//
//    EditText nameSpace,mobileNumberSpace,flatHouseSpace,areaStreetSpace,pincodeSpace,townCitySpace,emailSpace,enterState;
//    Button useLocationBtn,addAddress;
//    TextView codPrice;
//    int checkOTP;
//    boolean Otp_bool = false;
//    LocationManager locationManager;
//
//    public static final String PRICE_TO_INTERFACE = "com.example.foode_commerceapp.extra.PRICE_TO_INTERFACE";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cod);
//
//        nameSpace = findViewById(R.id.nameSpace);
//        mobileNumberSpace = findViewById(R.id.mobileNumberSpace);
//        flatHouseSpace = findViewById(R.id.flatHouseSpace);
//        areaStreetSpace = findViewById(R.id.areaStreetSpace);
//        pincodeSpace = findViewById(R.id.pincodeSpace);
//        townCitySpace = findViewById(R.id.townCitySpace);
//        emailSpace = findViewById(R.id.emailSpace);
//        enterState = findViewById(R.id.enterState);
//
//        useLocationBtn = findViewById(R.id.useLocationBtn);
//        addAddress = findViewById(R.id.addAddress);
//
//        codPrice = findViewById(R.id.codPrice);
//
//        Intent intent = getIntent();
//        String codPriceValue = intent.getStringExtra(BillingPage.PRICE_TO_COD);
//        codPrice.setText(codPriceValue);
//
//
//        if (ContextCompat.checkSelfPermission(CodActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(CodActivity.this,new String[]{
//                    Manifest.permission.ACCESS_FINE_LOCATION
//            },100);
//        }
//
//
//        useLocationBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getLocation();
//            }
//        });
//    }
//
//    public void sendEmailForCOD(View view){
//        Intent intentTrue = getIntent();
//        boolean cod_true = intentTrue.getBooleanExtra(BillingPage.COD_TRUE,false);
//
//        Otp_bool = true;
//        boolean valid = true;
//
//        if(this.nameSpace.length() == 0){
//            this.nameSpace.setError("This field is required");
//            valid = false;
//        }
//        if (this.mobileNumberSpace.length() == 0) {
//            this.nameSpace.setError("This field is required");
//            valid = false;
//        }
//        if (this.emailSpace.length() == 0) {
//            this.emailSpace.setError("This field is required");
//            valid = false;
//        }
//        if (this.flatHouseSpace.length() == 0) {
//            this.flatHouseSpace.setError("This field is required");
//            valid = false;
//        }
//        if (this.areaStreetSpace.length() == 0) {
//            this.areaStreetSpace.setError("This field is required");
//            valid = false;
//        }
//        if (this.pincodeSpace.length() == 0) {
//            this.pincodeSpace.setError("This field is required");
//            valid = false;
//        }
//        if (this.townCitySpace.length() == 0) {
//            this.townCitySpace.setError("This field is required");
//            valid = false;
//        }
//        if(this.enterState.length() == 0){
//            this.enterState.setError("This field is required");
//            valid = false;
//        }
//
//        if(valid){
//            if(cod_true){
//                Intent intent = new Intent(this,SuccessPage.class);
//                startActivity(intent);
//            } else{
//                String codPriceValue = codPrice.getText().toString();
//                Intent intent = new Intent(this,PaymentInterface.class);
//                intent.putExtra(PRICE_TO_INTERFACE,codPriceValue);
//                startActivity(intent);
//            }
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    private void getLocation() {
//
//        try {
//            LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,CodActivity.this);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public void onLocationChanged(@NonNull Location location) {
//        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
//        try {
//            Locale Locale = null;
//            Geocoder geocoder = new Geocoder(CodActivity.this, Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//            String address = addresses.get(0).getAddressLine(0);
//
//            flatHouseSpace.setText(address);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//
//    }
//}




























//package com.example.foode_commerceapp;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class CodActivity extends AppCompatActivity {
//
//    EditText nameSpace,mobileNumberSpace,flatHouseSpace,areaStreetSpace,pincodeSpace,townCitySpace,emailSpace,enterState;
//    Button useLocationBtn,addAddress;
//    TextView codPrice;
////    CountDownTimer timer;
//    int checkOTP;
//    boolean Otp_bool = false;
//
//    public static final String PRICE_TO_INTERFACE = "com.example.foode_commerceapp.extra.PRICE_TO_INTERFACE";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cod);
//
//        nameSpace = findViewById(R.id.nameSpace);
//        mobileNumberSpace = findViewById(R.id.mobileNumberSpace);
//        flatHouseSpace = findViewById(R.id.flatHouseSpace);
//        areaStreetSpace = findViewById(R.id.areaStreetSpace);
//        pincodeSpace = findViewById(R.id.pincodeSpace);
//        townCitySpace = findViewById(R.id.townCitySpace);
//        emailSpace = findViewById(R.id.emailSpace);
//        enterState = findViewById(R.id.enterState);
//
//        useLocationBtn = findViewById(R.id.useLocationBtn);
//        addAddress = findViewById(R.id.addAddress);
//
//        codPrice = findViewById(R.id.codPrice);
//
//        Intent intent = getIntent();
//        String codPriceValue = intent.getStringExtra(BillingPage.PRICE_TO_COD);
//        codPrice.setText(codPriceValue);
//    }
//
//    public void sendEmailForCOD(View view){
//        Intent intentTrue = getIntent();
//        boolean cod_true = intentTrue.getBooleanExtra(BillingPage.COD_TRUE,false);
////        boolean credit_true = intentTrue.getBooleanExtra(BillingPage.CREDIT_TRUE,false);
////        boolean debit_true = intentTrue.getBooleanExtra(BillingPage.DEBIT_TRUE,false);
//
//        Otp_bool = true;
//        boolean valid = true;
//
//        if(this.nameSpace.length() == 0){
//            this.nameSpace.setError("This field is required");
//            valid = false;
//        }
//        if (this.mobileNumberSpace.length() == 0) {
//            this.nameSpace.setError("This field is required");
//            valid = false;
//        }
//        if (this.emailSpace.length() == 0) {
//            this.emailSpace.setError("This field is required");
//            valid = false;
//        }
//        if (this.flatHouseSpace.length() == 0) {
//            this.flatHouseSpace.setError("This field is required");
//            valid = false;
//        }
//        if (this.areaStreetSpace.length() == 0) {
//            this.areaStreetSpace.setError("This field is required");
//            valid = false;
//        }
//        if (this.pincodeSpace.length() == 0) {
//            this.pincodeSpace.setError("This field is required");
//            valid = false;
//        }
//        if (this.townCitySpace.length() == 0) {
//            this.townCitySpace.setError("This field is required");
//            valid = false;
//        }
//        if(this.enterState.length() == 0){
//            this.enterState.setError("This field is required");
//            valid = false;
//        }
//
//        if(valid){
//            if(cod_true){
//                Intent intent = new Intent(this,SuccessPage.class);
//                startActivity(intent);
//            } else{
//                String codPriceValue = codPrice.getText().toString();
//                Intent intent = new Intent(this,PaymentInterface.class);
//                intent.putExtra(PRICE_TO_INTERFACE,codPriceValue);
//                startActivity(intent);
//            }
//        }
//
//
//
////        // timer setup
////        timer = new CountDownTimer(45000,1000) {
////            @Override
////            public void onTick(long millisUntilFinished) {
////                secondTimer.setText("Resend OTP in" + " " + millisUntilFinished/1000  + "s");
////                resendButton.setEnabled(false);
////            }
////
////            @Override
////            public void onFinish() {
////                secondTimer.setText(" ");
////                sendBtnText.setText("Resend");
////                resendButton.setEnabled(true);
////            }
////        }.start();
////
////        resendButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                timer.start();
////            }
////        });
//    }
//}