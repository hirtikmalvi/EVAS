package com.example.evas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evas.databinding.ActivityAdminBinding;
import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {

    ActivityAdminBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_user);
        mAuth = FirebaseAuth.getInstance();

        TextView logOutTextView = findViewById(R.id.logOutTextView);
        Button locationButton = findViewById(R.id.locationButton);


        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, UserMapsActivity.class);
                startActivity(intent);
            }
        });

        logOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SharedPreferences pref = getSharedPreferences("userPref", MODE_PRIVATE);
                SharedPreferences.Editor editor= pref.edit();
                editor.putBoolean("flagUser", false);
                editor.apply();
                Toast.makeText(UserActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                finishAffinity();
                Intent intent = new Intent(UserActivity.this, UserLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}