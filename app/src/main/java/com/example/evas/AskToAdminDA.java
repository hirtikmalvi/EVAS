package com.example.evas;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.evas.Models.Admins;
import com.example.evas.Models.Permissions;
import com.example.evas.databinding.ActivityAddDriverAaBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.Message;
//import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.RemoteMessage;

public class AskToAdminDA extends AppCompatActivity {

    ActivityAddDriverAaBinding binding;
    private EditText editTextMessageAtoAdmin;
    private Button askToAdminButton;
    FirebaseDatabase database;


    RoleSelection roleSelection;

    String driverToken;


    FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_to_admin_da);

        // Initialize views
        editTextMessageAtoAdmin = findViewById(R.id.editTextMessageAtoAdmin);
        askToAdminButton = findViewById(R.id.askToAdminButton);
        database = FirebaseDatabase.getInstance();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        driverToken = task.getResult();
                    }
                });

//        String driverToken1 = roleSelection.driverToken;

        String adminToken = "adminToken";
        String driver = "Driver", admin = "Admin";
        Boolean isAccepted = false;


        // Button click listener
        askToAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the message entered by the user
                String messageInput = editTextMessageAtoAdmin.getText().toString();

                Permissions permission = new Permissions(driverToken, adminToken, user.getEmail(), "admin@gmail.com", messageInput, isAccepted);
                String id = user.getUid();
                database.getReference().child("Permissions").child(id).setValue(permission);

                String registrationToken = "dFDQ8Qg9RouW6KCSWvcqV8:APA91bF7kw0NxDP0r7bZXCkdzZ5HuM8JBJcVOMXW2AHPLfuZ1Kx8LLMStTgI93KK-bzLyeHp_buT8eei5Ussw3lCrHEUbHtzPfvAhezrQ1xlTGC_jUUDR6c6NhvlHj2OEcJo3t3G7kBj";
//                RemoteMessage.Builder builder = new RemoteMessage.Builder(registrationToken);
//                builder.addData("message", "Admin you have a request from driver");
////                builder.addData("message", "Admin you have a request from driver");
//                RemoteMessage message = builder.build();
//
//                FirebaseMessaging.getInstance().send(message);
////                System.out.println("Successfully sent message: " + response);

//                if (token == null || token.isEmpty()) {
//                    // Handle case where token is not available
//                    return;
//                }

//                Message message = Message.builder()
//                        .putData("title", "Notification Title")
//                        .putData("body", "Notification Message")
//                        .setToken(token)
//                        .build();

//                RemoteMessage remoteMessage = new RemoteMessage.Builder(registrationToken)
//                        .addData("title", "I AM A DRIVER")
//                        .addData("body", "KINDLY ACCEPT MY REQUEST")
//                        .build();
//
//                FirebaseMessaging.getInstance().notify();
//                Log.d("MESSAGE SENT", "SENT SUCCESSFULLY");

                finish();
            }
        });
    }
}
