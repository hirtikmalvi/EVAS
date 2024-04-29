package com.example.evas;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.evas.AdminLoginActivity;
import com.example.evas.DriverLoginActivity;
import com.example.evas.UserLoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
//import com.karumi.dexter.PermissionDeniedResponse;
//import com.karumi.dexter.PermissionGrantedResponse;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

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

        // Request POST_NOTIFICATIONS permission
        Dexter.withContext(getBaseContext())
                .withPermission(Manifest.permission.POST_NOTIFICATIONS)
                .withListener(new PermissionListener() {

                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        // Permission granted, you can proceed with your logic
                        Log.d("Permission", "POST_NOTIFICATIONS permission granted");
                        Toast.makeText(RoleSelection.this, "NOTIFICATIONS permission granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(RoleSelection.this, "Permission POST_NOTIFICATIONS denied", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM Token", "Fetching FCM token failed", task.getException());
                            return;
                        }
                        driverToken = task.getResult();
                        Log.d("FCM Token", "FCM token retrieved: " + driverToken);
                    }
                });

        adminButton = findViewById(R.id.adminButton);
        driverButton = findViewById(R.id.driverButton);
        userButton = findViewById(R.id.userButton);

        Intent admin = new Intent(this, AdminLoginActivity.class);
        Intent driver = new Intent(this, DriverLoginActivity.class);
        Intent user = new Intent(this, UserLoginActivity.class);

        SharedPreferences adminPref = getSharedPreferences("adminPref", MODE_PRIVATE);
        SharedPreferences driverPref = getSharedPreferences("driverPref", MODE_PRIVATE);
        SharedPreferences driverUserNamePref = getSharedPreferences("driverUserNamePref", MODE_PRIVATE);
        SharedPreferences userPref = getSharedPreferences("userPref", MODE_PRIVATE);

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

        // Check if admin is logged in
        Boolean checkAdmin = adminPref.getBoolean("flagAdmin", false);
        if (checkAdmin) {
            Intent adminOpenActivity = new Intent(RoleSelection.this,  AdminActivity.class);
            startActivity(adminOpenActivity);
            finishAffinity();
        }

        // Check if driver is logged in
        Boolean checkDriver = driverPref.getBoolean("flagDriver", false);
        String driverUserName = driverUserNamePref.getString("driverUserName", "");
        if (checkDriver) {
            Intent driverOpenActivity = new Intent(RoleSelection.this,  DriverActivity.class);
            startActivity(driverOpenActivity);
            finishAffinity();
        }

        // Check if user is logged in
        Boolean checkUser = userPref.getBoolean("flagUser", false);
        if (checkUser) {
            Intent userOpenActivity = new Intent(RoleSelection.this,  UserActivity.class);
            startActivity(userOpenActivity);
            finishAffinity();
        }
    }
}
