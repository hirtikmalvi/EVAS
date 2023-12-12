package com.example.evas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderClient;
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