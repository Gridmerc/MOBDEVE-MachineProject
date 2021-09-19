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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.initFirebase();
        this.initComponents();
    }

    private void initComponents() {
        this.btnRegister = findViewById(R.id.btn_register);
        this.etFullName = findViewById(R.id.et_register_name);
        this.etBirthday = findViewById(R.id.et_register_date);
        this.etEmail = findViewById(R.id.et_register_email);
        this.etPassword = findViewById(R.id.et_register_password);
        this.pbRegister = findViewById(R.id.pb_register);
        this.tvLoginLink = findViewById(R.id.tv_link_login);

        this.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = etFullName.getText().toString().trim();
                String birthday = etBirthday.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if(!checkValidity(fullName, birthday, email, password)) {
                    User user = new User(fullName, birthday, email, password);
                    storeUser(user);
                }
            }
        });

        this.tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void initFirebase() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    private boolean checkValidity(String name, String birthday, String email, String password) {
        boolean contentError = false;
        if(name.isEmpty()) {
            this.etFullName.setError("Required Field");
            contentError = true;
        }
        if(birthday.isEmpty()) {
            this.etBirthday.setError("Required Field");
            contentError = true;
        }
        if(email.isEmpty()) {
            this.etEmail.setError("Required Field");
            contentError = true;
        }
        if(password.isEmpty()) {
            this.etPassword.setError("Required Field");
            contentError = true;
        }
        if(password.length() < 6) {
            this.etPassword.setError("Password must be at least six characters");
            contentError = true;
        }
        return contentError;
    }

    private void storeUser(User user) {
        this.pbRegister.setVisibility(View.VISIBLE);

        // Register the user to Firebase
        this.mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    addUserToDatabase(user);
                    successfulRegistration();
                }
                else {
                    failedRegistration(task);
                }
            }
        });
    }

    private void addUserToDatabase(User user) {
        // Please check documentation on the logic of the database
        DatabaseReference database = FirebaseDatabase.getInstance().getReference(Collections.users.name());
        database.child(FirebaseAuth.getInstance().getUid()).setValue(user);
        //database.child(FirebaseAuth.getInstance().getUid()).child(Collections.sleeprecords.name()).setValue(sleepRecordObject)
    }

    private void successfulRegistration() {
        this.pbRegister.setVisibility(View.GONE);

        Toast.makeText(this, "User Registration Success!", Toast.LENGTH_SHORT).show();

        // After successful registration, move back to MainActivity (Login)
        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void failedRegistration(@NonNull Task<AuthResult> task) {
        this.pbRegister.setVisibility(View.GONE);
        Toast.makeText(this, "User Registration Failed!: " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
    }

    // Attributes
    private Button btnRegister;
    private EditText etFullName;
    private EditText etBirthday;
    private EditText etEmail;
    private EditText etPassword;
    private FirebaseAuth mAuth;
    private ProgressBar pbRegister;
    private TextView tvLoginLink;
}