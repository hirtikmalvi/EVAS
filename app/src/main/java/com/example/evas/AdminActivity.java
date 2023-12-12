package com.example.evas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evas.databinding.ActivityAdminBinding;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    ActivityAdminBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_admin); // Set content view to admin_activity.xml
//        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        // Initialize UI elements
        Button trafficDataButton = findViewById(R.id.trafficDataButton);
        Button requestFromDriverButton = findViewById(R.id.requestFromDriverButton);
        Button sendAlertButton = findViewById(R.id.sendAlertButton);
        Button locationOfDriverButton = findViewById(R.id.locationOfDriverButton);
        TextView logOutTextView = findViewById(R.id.logOutTextView);

        // Add click listeners for the buttons
        trafficDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the Traffic Data button click
            }
        });

        requestFromDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the Request from Driver button click
            }
        });

        sendAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the Send Alert button click
            }
        });

        locationOfDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the Location of Driver button click
            }
        });

        logOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(AdminActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminActivity.this, RoleSelection.class);
                startActivity(intent);
            }
        });
    }
}