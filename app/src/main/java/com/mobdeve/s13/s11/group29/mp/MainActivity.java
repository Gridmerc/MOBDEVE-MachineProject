package com.mobdeve.s13.s11.group29.mp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        this.initFirebase();

        // Initialize components
        this.initComponents();
    }

    private void initFirebase() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    private void initComponents() {
        this.btnLogin = findViewById(R.id.btn_login);
        this.etEmail = findViewById(R.id.et_login_email);
        this.etPassword = findViewById(R.id.et_login_password);
        this.pbLogin = findViewById(R.id.pb_login);
        this.tvRegisterLink = findViewById(R.id.tv_link_register);

        // Login button function
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Extract value from inputs
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Check if email and password are valid
                if(!checkValidity(email, password)) {
                    userSignIn(email, password);
                }
            }
        });

        // "Create an account link" to RegisterActivity
        this.tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private boolean checkValidity(String email, String password) {
        boolean contentError = false;
        if(email.isEmpty()) {
            this.etEmail.setError("Required field");
            contentError = true;
        }
        if(password.isEmpty()) {
            this.etPassword.setError("Required field");
            contentError = true;
        }
        return contentError;
    }

    private void userSignIn(String email, String password) {
        this.pbLogin.setVisibility(View.VISIBLE);

        this.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    successfulRegistration();
                }
                else {
                    failedRegistration(task);
                }
            }
        });
    }

    private void successfulRegistration() {
        this.pbLogin.setVisibility(View.GONE);

        Toast.makeText(this, "User Login Success!", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(i);
        finish();
    }

    private void failedRegistration(@NonNull Task<AuthResult> task) {
        this.pbLogin.setVisibility(View.GONE);
        Toast.makeText(this, "User Login Failed!: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
    }

    // Attributes
    private Button btnLogin;
    private EditText etEmail;
    private EditText etPassword;
    private FirebaseAuth mAuth;
    private ProgressBar pbLogin;
    private TextView tvRegisterLink;
}