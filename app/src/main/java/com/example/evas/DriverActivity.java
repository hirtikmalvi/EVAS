package com.example.evas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evas.databinding.ActivityAdminBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.*;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.messaging.FirebaseMessaging;

public class DriverActivity extends AppCompatActivity {

    ActivityAdminBinding binding;
    FirebaseAuth mAuth;
    String driverToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_driver);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        TextView logOutTextView = findViewById(R.id.logOutTextView);

        Button askToAdminButton = findViewById(R.id.askToAdminButton);

        SharedPreferences pref1 = getSharedPreferences("driverUserNamePref", MODE_PRIVATE);
        String driverUserName = pref1.getString("driverUserName", "");
        TextView driverTextView = findViewById(R.id.driverTextView);


        askToAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverActivity.this, AskToAdminDA.class);
                startActivity(intent);
            }
        });

        driverTextView.setText("Driver: " +user.getEmail());


        logOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SharedPreferences pref = getSharedPreferences("driverPref", MODE_PRIVATE);
                SharedPreferences.Editor editor= pref.edit();
                editor.putBoolean("flagDriver", false);
                editor.apply();

                //Driver Username
                SharedPreferences pref1 = getSharedPreferences("driverUserNamePref", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = pref1.edit();
                editor1.putString("driverUserName", "");
                editor1.apply();

                Toast.makeText(DriverActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                finishAffinity();
                Intent intent = new Intent(DriverActivity.this, DriverLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}