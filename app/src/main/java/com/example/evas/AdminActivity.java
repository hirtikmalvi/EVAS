package com.example.evas;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evas.Models.Permissions;
import com.example.evas.databinding.ActivityAdminBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    ActivityAdminBinding binding;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    String adminToken;

    int requestColor = Color.RED;  // Color for pending request
    int noRequestColor = Color.GREEN; // Color for no request
    String previousMessage = ""; // Variable to store the previous message

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_admin); // Set content view to admin_activity.xml
//        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Initialize UI elements
        Button trafficDataButton = findViewById(R.id.trafficDataButton);
        Button requestFromDriverButton = findViewById(R.id.requestFromDriverButton);
        Button sendAlertButton = findViewById(R.id.sendAlertButton);
        Button locationOfDriverButton = findViewById(R.id.locationOfDriverButton);
        TextView logOutTextView = findViewById(R.id.logOutTextView);
        Button driverManagementButton = findViewById(R.id.driverManagementAA);


        // Set initial color (optional)
//        requestFromDriverButton.setBackgroundColor(noRequestColor);
//        requestFromDriverButton.setText("No Requeste");

        //GETTING DEVICE TOKEN AND STORING TO REALTIME DATABASE
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        adminToken = task.getResult();
                        Log.d("ADMIN TOKEN", adminToken);
                    }
                });
        String id = user.getUid();
        database.getReference().child("Admins").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Admin entry exists, proceed with update
                    updateAdminEntry(id, adminToken);
                } else {
                    // Handle scenario where Admin doesn't exist (optional)
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            private void updateAdminEntry(String id, String adminToken) {
                Map<String, Object> adminUpdates = new HashMap<>();
                adminUpdates.put("adminToken", adminToken);
                database.getReference().child("Admins").child(id).updateChildren(adminUpdates);
                Toast.makeText(AdminActivity.this, "Token Stored Successfully!", Toast.LENGTH_SHORT).show();
            }
        });


        // Add click listeners for the buttons

        Intent driverManagement = new Intent(this, DriverManagement.class);
        driverManagementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(driverManagement);
            }
        });

        trafficDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the Traffic Data button click
            }
        });



        //CHECKING WHETHER THERE IS REQUEST FROM DRIVER OR NOT!
        // Get a reference to the "permissions" path in the Realtime Database
        // Add a ValueEventListener to listen for changes in the "permissions" path
        database.getReference("Permissions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method will be called whenever data in the "permissions" path changes
                Log.d("DATA MODIFIED", "DATA ADDED IN RT DB");

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Retrieve the data from the dataSnapshot
                    String permissionKey = snapshot.getKey();
//                    String isAccepted = snapshot.child("isAccepted").getValue(String.class);
                    Permissions permissions = snapshot.getValue(Permissions.class);

                    // Process the retrieved data
                    Log.d("RETRIEVED DATA", "Permission key: " + permissionKey + ", isAccepted: " + permissions.getIsAccepted());
                    Log.d("DATA", permissionKey + " " + permissions.getMessage() + " " + permissions.getIsAccepted());
                    Log.d("RECEIVER TOKEN", permissions.getReceiverToken());
                    Log.d("SENDER TOKEN", permissions.getSenderToken());
                    // Update the UI or perform actions based on the retrieved data

                    String currentMessage = permissions.getMessage();

                    // Check if the current message is different from the previous one
                    if (!currentMessage.equals(previousMessage)) {
                        // Set initial color (optional)
                        requestFromDriverButton.setBackgroundColor(requestColor);
                        requestFromDriverButton.setText("Driver Request");

                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                        // Set dialog title and message
                        builder.setTitle("New Permission Request");
                        builder.setMessage("Message from driver: " + currentMessage);

                        // Add the buttons.
                        builder.setPositiveButton(R.string.Accept, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                database.getReference("Permissions").child(permissionKey).child("isAccepted").setValue(true);
                                FCMSend.pushNotification(AdminActivity.this, permissions.getSenderToken(), "Accepted Your Request.", "Congratulations, Your request has been accpeted by Admin!");
                            }
                        });
                        builder.setNegativeButton(R.string.Reject, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                database.getReference("Permissions").child(permissionKey).child("isAccepted").setValue(false);
                                FCMSend.pushNotification(AdminActivity.this, permissions.getSenderToken(), "Rejected Your Request.", "Sorry, Your request has been Rejected by Admin!");
                            }
                        });
                        requestFromDriverButton.setBackgroundColor(noRequestColor);
                        requestFromDriverButton.setText("No Driver Request");
                        // Create and show the AlertDialog.
                        builder.show();
                        // Update previousMessage to currentMessage
                        previousMessage = currentMessage;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors that occur while retrieving data
                Log.e("FAILED", "Failed to read permission data.", databaseError.toException());
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
                SharedPreferences pref = getSharedPreferences("adminPref", MODE_PRIVATE);
                SharedPreferences.Editor editor= pref.edit();
                editor.putBoolean("flagAdmin", false);
                editor.apply();
                Toast.makeText(AdminActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                finishAffinity();
                Intent intent = new Intent(AdminActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}