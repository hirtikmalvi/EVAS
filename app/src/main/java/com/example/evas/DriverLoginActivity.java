package com.example.evas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.evas.Notifications.Token;
import com.example.evas.databinding.ActivityDriverLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class DriverLoginActivity extends AppCompatActivity {

    ActivityDriverLoginBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        progressDialog = new ProgressDialog(DriverLoginActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Validating Credentials...");

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.editUsername.getText().toString().isEmpty() && !binding.passwordEditText.getText().toString().isEmpty() && !binding.keyEditText.getText().toString().isEmpty()){
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(binding.editUsername.getText().toString(),binding.passwordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()){
                                SharedPreferences pref = getSharedPreferences("driverPref", MODE_PRIVATE);
                                SharedPreferences.Editor editor= pref.edit();
                                editor.putBoolean("flagDriver", true);
                                editor.apply();


                                //Driver UserName Shared Preferences

                                SharedPreferences pref1 = getSharedPreferences("driverUserNamePref", MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = pref1.edit();
                                editor1.putString("driverUserName", binding.editUsername.getText().toString());
                                editor1.apply();

                                Intent intent = new Intent(DriverLoginActivity.this, DriverActivity.class);
                                Toast.makeText(DriverLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(DriverLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(DriverLoginActivity.this, "All the fields are required!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

//binding.doNotHaveAccountButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DriverLoginActivity.this, AddDriverAdminA.class);
//                startActivity(intent);
//            }
//        });