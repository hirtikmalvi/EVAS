package com.example.evas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evas.Models.Drivers;
import com.example.evas.databinding.ActivityDriverManagementAaBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class DriverManagement extends AppCompatActivity {

    RecyclerView recyclerView;
    DriverAdapterAA driverAdapter;
    EditText searchDriverEditText;
    Button searchDriverButton;
    Button addDriverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_management_aa);

        recyclerView = (RecyclerView)findViewById(R.id.driverManagerRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        //Drivers Data

        FirebaseRecyclerOptions<Drivers> options = new FirebaseRecyclerOptions.Builder<Drivers>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Drivers"), Drivers.class)
                .build();

        driverAdapter = new DriverAdapterAA(options);
        recyclerView.setAdapter(driverAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        driverAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        driverAdapter.stopListening();
    }
}
