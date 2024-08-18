package com.example.foode_commerceapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textview;
//    private ImageView imageView2;
    private ImageView imageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        textview = findViewById(R.id.textView);
        imageView4 = findViewById(R.id.imageView4);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, LoginPage.class));
            finish();
        }, 3000);
    }
    public void openActivityItem(View view ){
        Intent intent = new Intent(this,SpecificItemPage.class);
        startActivity(intent);
    }

    public void openLoginActivity(View view){
        Intent intent = new Intent(this,LoginPage.class);
        startActivity(intent);
    }

}