package com.example.fetch_image_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class activity_onboarding1 extends AppCompatActivity {
    ImageView btnSkip, btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the action bar
        getSupportActionBar().hide();

        // Check if the app has been opened before
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean appOpenedBefore = preferences.getBoolean("appOpenedBefore", false);

        if (appOpenedBefore) {
            // If the app has been opened before, start MainActivity
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        // If it's the first time, set the flag and show onboarding screen
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("appOpenedBefore", true);
        editor.apply();

        setContentView(R.layout.activity_onboarding1);

        btnSkip = findViewById(R.id.btnSkip);
        btnGetStarted = findViewById(R.id.btnGetStarted);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent skip = new Intent(activity_onboarding1.this, activity_onboarding2.class);
                startActivity(skip);
                finish();
            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getStarted = new Intent(activity_onboarding1.this, MainActivity.class);
                startActivity(getStarted);
                finish();
            }
        });
    }
}