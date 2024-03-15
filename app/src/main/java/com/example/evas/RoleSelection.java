package com.example.evas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderClient;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


public class RoleSelection extends AppCompatActivity {

    private Button adminButton;
    private Button driverButton;
    private Button userButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        adminButton = findViewById(R.id.adminButton);
        driverButton = findViewById(R.id.driverButton);
        userButton = findViewById(R.id.userButton);

        Intent admin = new Intent(this, AdminLoginActivity.class);
        Intent driver = new Intent(this, DriverLoginActivity.class);
        Intent user = new Intent(this, UserLoginActivity.class);

        {
            SharedPreferences pref = getSharedPreferences("adminPref", MODE_PRIVATE);
            Boolean check = pref.getBoolean("flagAdmin", false);

            Intent adminOpenActivity;

            if(check){
                adminOpenActivity = new Intent(RoleSelection.this,  AdminActivity.class);
                startActivity(adminOpenActivity);
            }
        }
        {
            SharedPreferences pref = getSharedPreferences("driverPref", MODE_PRIVATE);
            Boolean check = pref.getBoolean("flagDriver", false);

            Intent driverOpenActivity;

            if(check){
                driverOpenActivity = new Intent(RoleSelection.this,  DriverActivity.class);
                startActivity(driverOpenActivity);
            }
        }
        {
            SharedPreferences pref = getSharedPreferences("userPref", MODE_PRIVATE);
            Boolean check = pref.getBoolean("flagUser", false);

            Intent userOpenActivity;

            if(check){
                userOpenActivity = new Intent(RoleSelection.this,  UserActivity.class);
                startActivity(userOpenActivity);
            }
        }

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(admin);
            }
        });
        driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(driver);
            }
        });
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(user);
            }
        });

    }
}