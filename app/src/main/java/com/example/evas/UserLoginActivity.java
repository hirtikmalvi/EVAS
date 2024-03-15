package com.example.evas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

import com.example.evas.Models.Users;
import com.example.evas.databinding.ActivityUserLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserLoginActivity extends AppCompatActivity {

    ActivityUserLoginBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(UserLoginActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Validating Credentials...");

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.usernameEditText.getText().toString().isEmpty() && !binding.passwordEditText.getText().toString().isEmpty()){
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(binding.usernameEditText.getText().toString(),binding.passwordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()){
                                SharedPreferences pref = getSharedPreferences("userPref", MODE_PRIVATE);
                                SharedPreferences.Editor editor= pref.edit();
                                editor.putBoolean("flagUser", true);
                                editor.apply();
                                Intent intent = new Intent(UserLoginActivity.this, UserActivity.class);
                                Toast.makeText(UserLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                startActivity(intent); //Starting the UserActivities
                            }
                            else {
                                Toast.makeText(UserLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(UserLoginActivity.this, "All the fields are required!", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        if (mAuth.getCurrentUser()!=null){
//            Intent intent = new Intent(UserLoginActivity.this, UserLoginActivity.class);
//            startActivity(intent);
//        }
        binding.doNotHaveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, UserSignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}