package com.mobdeve.s13.s11.group29.mp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    // Attributes
    private Button btnUpdate;
    private EditText etDate;
    private EditText etName;
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabEdit;
    private FloatingActionButton fabLogout;
    private ProgressBar pbEdit;
    private String date;
    private String name;
    private String userID;

    // Firebase Attributes
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        this.initComponents();
        this.initFirebase();
        this.initButtons();
    }

    private void initButtons() {
        this.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String extractName = etName.getText().toString().trim();
                String extractDate = etDate.getText().toString().trim();

                if(name.equals(extractName) && date.equals(extractDate)) {
                    Toast.makeText(EditProfileActivity.this, "The name and birthday are the same!", Toast.LENGTH_SHORT).show();
                }
                else if(name.equals(extractName)) {
                    databaseReference.child(userID).child("birthday").setValue(extractDate);
                    Toast.makeText(EditProfileActivity.this, "Birthday updated!", Toast.LENGTH_SHORT).show();
                    initFirebase();
                }
                else if(date.equals(extractDate)) {
                    databaseReference.child(userID).child("name").setValue(extractName);
                    Toast.makeText(EditProfileActivity.this, "Name updated!", Toast.LENGTH_SHORT).show();
                    initFirebase();
                }
                else {
                    databaseReference.child(userID).child("name").setValue(extractName);
                    databaseReference.child(userID).child("birthday").setValue(extractDate);
                    Toast.makeText(EditProfileActivity.this, "Name and birthday updated!", Toast.LENGTH_SHORT).show();
                    initFirebase();
                }
            }
        });

        this.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(i);
                finish();
            }
        });

        this.fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void initComponents() {
        this.btnUpdate = findViewById(R.id.btn_edit_update);
        this.etDate = findViewById(R.id.et_edit_birthday);
        this.etName = findViewById(R.id.et_edit_name);
        this.fabAdd = findViewById(R.id.fab_sleep_edit);
        this.fabEdit = findViewById(R.id.fab_edit_edit);
        this.fabLogout = findViewById(R.id.fab_logout_edit);
        this.pbEdit = findViewById(R.id.pb_edit);
    }

    private void initFirebase() {
        this.pbEdit.setVisibility(View.VISIBLE);

        this.mAuth = FirebaseAuth.getInstance();
        this.mUser = this.mAuth.getCurrentUser();
        this.userID = this.mUser.getUid();

        this.databaseReference = FirebaseDatabase.getInstance().getReference(Collections.users.name());

        this.databaseReference.child(this.userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pbEdit.setVisibility(View.GONE);

                name = snapshot.child("name").getValue().toString();
                date = snapshot.child("birthday").getValue().toString();

                etName.setText(name);
                etDate.setText(date);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pbEdit.setVisibility(View.GONE);
            }
        });

    }
}