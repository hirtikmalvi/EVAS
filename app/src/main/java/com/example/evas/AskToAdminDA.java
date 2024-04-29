package com.example.evas;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.SnapHelper;

import com.example.evas.Models.Admins;
import com.example.evas.Models.Permissions;
import com.example.evas.databinding.ActivityAddDriverAaBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.Message;
//import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;

public class AskToAdminDA extends AppCompatActivity {

    ActivityAddDriverAaBinding binding;
    private EditText editTextMessageAtoAdmin;
    private Button askToAdminButton;
    FirebaseDatabase database;


    RoleSelection roleSelection;

    String driverToken;
    String adminID;
    String adminToken = "";
    Boolean isAccepted = false;

    String driver = "", admin = "";



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


        driver = user.getEmail();


        //Getting Admin Token from Realtime DB
        database.getReference().child("Admins").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        adminID = snapshot.getKey();
                        Admins adminModel = snapshot.getValue(Admins.class);
                        adminToken = adminModel.getAdminToken();
                        admin = adminModel.getMail();
                        Log.d("adminID", adminID);
                        Log.d("ADMINTOKEN", adminToken);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Button click listener
        askToAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editTextMessageAtoAdmin.getText().toString().isEmpty()){

                    // Get the message entered by the user
                    String messageInput = editTextMessageAtoAdmin.getText().toString();

                    Permissions permission = new Permissions(driverToken, adminToken, driver, admin, messageInput, isAccepted);
                    String id = user.getUid();
                    Log.d("EMAIL", user.getEmail().split("@", 2)[0]);
                    database.getReference().child("Permissions").child(id).setValue(permission);
                    FCMSend.pushNotification(AskToAdminDA.this, adminToken, user.getEmail().split("@", 2)[0] + " is asking for the permission!", messageInput);

                    finish();
                }
                else{
                    Toast.makeText(AskToAdminDA.this, "Please Enter the message!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
