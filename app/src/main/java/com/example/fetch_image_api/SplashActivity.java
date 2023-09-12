package com.example.fetch_image_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Check if the app has been opened before
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean appOpenedBefore = preferences.getBoolean("appOpenedBefore", false);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create an Intent to start the next activity
                if (appOpenedBefore) {
                    // If the app has been opened before, start MainActivity
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                    return;
                }
                else {
                    Intent intent = new Intent(SplashActivity.this, activity_onboarding1.class);
                    startActivity(intent);
                    // Close the SplashActivity to prevent going back to it
                    finish();
                }
            }
        }, 2500);
    }
}