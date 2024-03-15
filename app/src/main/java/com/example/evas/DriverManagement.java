package com.example.evas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.evas.databinding.ActivityDriverManagementAaBinding;

public class DriverManagement extends AppCompatActivity {

    EditText searchDriverEditText;
    Button searchDriverButton;
    Button addDriverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_management_aa);

        // Initialize UI elements
        searchDriverEditText = findViewById(R.id.searchDriverEditText);
        searchDriverButton = findViewById(R.id.searchDriverButton);
        addDriverButton = findViewById(R.id.addDriverButton);

        // Intent for AddDriverAdminA activity
        Intent addDriverIntent = new Intent(this, AddDriverAdminA.class);

        // Set OnClickListener for Add Driver button
        addDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addDriverIntent);
            }
        });
    }
}
