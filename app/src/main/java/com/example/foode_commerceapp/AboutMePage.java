package com.example.foode_commerceapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AboutMePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me_page);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void toHomePageAbout(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void resume(View view){
        Uri uri = Uri.parse("https://atish-basak.netlify.app/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void downloadCV(View view){
        Uri uri = Uri.parse("https://drive.google.com/file/d/1Dc4wgXk_eCm0JrIqVDFsXCqKa8opLvEW/view");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void toGitHubPage(View view){
        Uri uri = Uri.parse("https://github.com/atishbasak");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void toFacebookPage(View view){
        Uri uri = Uri.parse("https://www.facebook.com/share/QkywyR6Ar15dSpSx/?mibextid=qi2Omg");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void toLinkedInPage(View view){
        Uri uri = Uri.parse("https://www.linkedin.com/in/atish-basak-536a8131a?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void toProjectsPage(View view){
        Uri uri = Uri.parse("https://atish-basak.netlify.app/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void toContactPage(View view){
        Intent intent = new Intent(this, ContactMePage.class);
        startActivity(intent);
    }
}