package com.example.quizmeapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.quizmeapp.R;
import com.example.quizmeapp.databinding.ActivityLogInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
            ActivityLogInBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

                auth = FirebaseAuth.getInstance();

                dialog = new ProgressDialog(this);
                dialog.setMessage("Logging in...");

                if(auth.getCurrentUser() != null) {
                    startActivity(new Intent(LogInActivity.this, MainActivity.class));
                    finish();
                }

                binding.submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email, pass;
                        email = binding.emailBox.getText().toString();
                        pass = binding.passwordBox.getText().toString();


                        dialog.show();

                        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                dialog.dismiss();
                                if(task.isSuccessful()) {
                                    startActivity(new Intent(LogInActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LogInActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

        binding.createNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
            }
        });

    }
        }

