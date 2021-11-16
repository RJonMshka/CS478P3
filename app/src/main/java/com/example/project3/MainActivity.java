package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button attractionsButton;
    private Button restaurantsButton;
    private String placeType = "";

    private static final String INTENT_ACTION = "edu.uic.cs478.fall2021.project3.broadcastIntent";
    private static final String APP_PERMISSION = "edu.uic.cs478.fall2021.project3";
    private static final int APP_PERMISSION_REQ_CODE = 15;
    private static final String ATTRACTIONS = "edu.uic.cs478.fall2021.project3.attractions";
    private static final String RESTAURANTS = "edu.uic.cs478.fall2021.project3.restaurants";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // restore the data
        if (savedInstanceState != null) {
            placeType = savedInstanceState.getString("placeType");
        }

        attractionsButton = (Button) findViewById(R.id.aButton);
        restaurantsButton = (Button) findViewById(R.id.rButton);

        // Attraction button click listener
        attractionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeType = ATTRACTIONS;
                Intent intent = new Intent(INTENT_ACTION);
                intent.putExtra("placeType", placeType);

                checkPermissionAndBroadcast(intent);
            }
        });

        // Restaurants Button click listener
        restaurantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeType = RESTAURANTS;
                Intent intent = new Intent(INTENT_ACTION);
                intent.putExtra("placeType", placeType);

                checkPermissionAndBroadcast(intent);
            }
        });
    }

    // Checks permission, if granted then broadcast with an intent and same permission
    private void checkPermissionAndBroadcast(Intent intent) {
        if(ActivityCompat.checkSelfPermission(this, APP_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, R.string.toast_msg, Toast.LENGTH_SHORT).show();
            sendOrderedBroadcast(intent, APP_PERMISSION);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{APP_PERMISSION}, APP_PERMISSION_REQ_CODE);
        }
    }

    // Checks permission, if granted then broadcast with an intent and same permission
    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        super.onRequestPermissionsResult(code, permissions, results);
        if (code == APP_PERMISSION_REQ_CODE) {
            if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(INTENT_ACTION);
                intent.putExtra("placeType", placeType);
                Toast.makeText(this, R.string.toast_msg, Toast.LENGTH_SHORT).show();
                sendOrderedBroadcast(intent, APP_PERMISSION);
            } else {
                Toast.makeText(this, R.string.not_granted_msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Save activity data in Bundle
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("placeType", placeType);
    }

}