package com.example.evas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.evas.Models.Admins;
import com.example.evas.databinding.ActivityAdminSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AdminSignUpActivity extends AppCompatActivity {

    ActivityAdminSignUpBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(AdminSignUpActivity.this);
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
                                Admins users = new Admins(binding.usernameEditText.getText().toString(),binding.passwordEditText.getText().toString(),binding.keyEditText.getText().toString());
                                String id = task.getResult().getUser().getUid();
                                database.getReference().child("Admins").child(id).setValue(users);
                                Toast.makeText(AdminSignUpActivity.this, "Sign Up successful", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(AdminSignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(AdminSignUpActivity.this, "All the fields are required!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.alreadyHaveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminSignUpActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}