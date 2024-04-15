package com.example.evas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderClient;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.example.evas.Notifications.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;


public class RoleSelection extends AppCompatActivity {

    private Button adminButton;
    private Button driverButton;
    private Button userButton;
    String driverToken;

    FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

//        FirebaseMessaging.getInstance().subscribeToTopic("weather")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = "Subscribed";
//                        if (!task.isSuccessful()) {
//                            msg = "Subscribe failed";
//                        }
//                    }
//                });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM Token", "Fetching FCM token failed", task.getException());
                            return;
                        }

                        // Get the token from the successful result
                        driverToken = task.getResult();
                        Log.d("FCM Token", "FCM token retrieved: " + driverToken);

                        // Now you have the FCM token in the 'token' variable
                        // You can send it to your server for registration, etc.
                    }
                });

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
                finishAffinity();
            }
        }
        {
            SharedPreferences pref = getSharedPreferences("driverPref", MODE_PRIVATE);
            Boolean check = pref.getBoolean("flagDriver", false);

            //Driver Username pref1
            SharedPreferences pref1 = getSharedPreferences("driverUserNamePref", MODE_PRIVATE);
            String driverUserName = pref1.getString("driverUserName", "");
//         Boolean driverUserName = pref.getBoolean("flagDriver", false);
            Intent driverOpenActivity;

            if(check){
                driverOpenActivity = new Intent(RoleSelection.this,  DriverActivity.class);
                startActivity(driverOpenActivity);
                finishAffinity();
            }
        }
        {
            SharedPreferences pref = getSharedPreferences("userPref", MODE_PRIVATE);
            Boolean check = pref.getBoolean("flagUser", false);

            Intent userOpenActivity;

            if(check){
                userOpenActivity = new Intent(RoleSelection.this,  UserActivity.class);
                startActivity(userOpenActivity);
                finishAffinity();
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