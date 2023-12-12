package com.example.evas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evas.databinding.ActivityAdminBinding;
import com.google.firebase.auth.FirebaseAuth;

public class DriverActivity extends AppCompatActivity {

    ActivityAdminBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_driver);
        mAuth = FirebaseAuth.getInstance();

        TextView logOutTextView = findViewById(R.id.logOutTextView);


        logOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(DriverActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DriverActivity.this, RoleSelection.class);
                startActivity(intent);
            }
        });
    }
}