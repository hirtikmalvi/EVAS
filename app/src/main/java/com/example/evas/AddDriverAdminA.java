package com.example.evas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.evas.Models.Drivers;
import com.example.evas.databinding.ActivityAddDriverAaBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AddDriverAdminA extends AppCompatActivity {

    ActivityAddDriverAaBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddDriverAaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(AddDriverAdminA.this);
        progressDialog.setTitle("Creating an Account!");
        progressDialog.setMessage("Wait for a while...");


        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.usernameEditText.getText().toString().isEmpty() && !binding.passwordEditText.getText().toString().isEmpty() && !binding.keyEditText.getText().toString().isEmpty() && !binding.nameEditText.getText().toString().isEmpty()
                && !binding.phoneEditText.getText().toString().isEmpty() && !binding.addressEditText.getText().toString().isEmpty() && !binding.aadharEditText.getText().toString().isEmpty() &&
                isAadharValid(binding.aadharEditText.getText().toString()) && isPhoneNumberValid(binding.phoneEditText.getText().toString()) ){
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(binding.usernameEditText.getText().toString(),binding.passwordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()){
                                Drivers users = new Drivers(binding.nameEditText.getText().toString(),binding.phoneEditText.getText().toString(), binding.addressEditText.getText().toString(), binding.aadharEditText.getText().toString(),
                                        binding.usernameEditText.getText().toString(),binding.passwordEditText.getText().toString(),binding.keyEditText.getText().toString());
                                String id = task.getResult().getUser().getUid();
                                database.getReference().child("Drivers").child(id).setValue(users);
                                Toast.makeText(AddDriverAdminA.this, "Sign Up successful", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                Toast.makeText(AddDriverAdminA.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(AddDriverAdminA.this, "All the fields are required!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isAadharValid(String aadhar) {
        // Aadhar should have exactly 12 digits
        return aadhar.length() == 12 && TextUtils.isDigitsOnly(aadhar);
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // Phone number should have exactly 10 digits
        return phoneNumber.length() == 10 && TextUtils.isDigitsOnly(phoneNumber);
    }
}

//binding.alreadyHaveAccountButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AddDriverAdminA.this, DriverLoginActivity.class);
//                startActivity(intent);
//            }
//        });