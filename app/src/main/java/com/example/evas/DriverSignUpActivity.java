package com.example.evas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.evas.Models.Drivers;
import com.example.evas.databinding.ActivityDriverSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class DriverSignUpActivity extends AppCompatActivity {

    ActivityDriverSignUpBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(DriverSignUpActivity.this);
        progressDialog.setTitle("Creating an Account!");
        progressDialog.setMessage("Wait for a while...");

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.usernameEditText.getText().toString().isEmpty() && !binding.passwordEditText.getText().toString().isEmpty() && !binding.keyEditText.getText().toString().isEmpty()){
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(binding.usernameEditText.getText().toString(),binding.passwordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()){
                                Drivers users = new Drivers(binding.usernameEditText.getText().toString(),binding.passwordEditText.getText().toString(),binding.keyEditText.getText().toString());
                                String id = task.getResult().getUser().getUid();
                                database.getReference().child("Drivers").child(id).setValue(users);
                                Toast.makeText(DriverSignUpActivity.this, "Sign Up successful", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(DriverSignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(DriverSignUpActivity.this, "All the fields are required!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.alreadyHaveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverSignUpActivity.this, DriverLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}