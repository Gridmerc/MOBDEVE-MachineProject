package com.mobdeve.s13.s11.group29.mp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ProfileActivity extends AppCompatActivity {

    // Attributes
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ProgressBar pbProfile;
    private String userID;
    private TextView tvWelcome;

    //Database attribs
    DatabaseReference databaseReference;

    // Button attribs
    private FloatingActionButton fabRecord;
    private FloatingActionButton fabViewRecord;
    private FloatingActionButton fabEditProfile;
    private FloatingActionButton fabLogout;
    private TextView tvPrompt;

    private boolean recordStarted = false;
    private String sTimeFrom, sTimeTo, dTimeFrom, dTimeTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.initComponents();
        this.initFirebase();

        // Record sleep function
        this.fabRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(recordStarted){
                    // You can edit the below commented code if you want the logo to change onClick

                    //fabRecord.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sleep_off));
                    //fabRecord.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7d63eb")));

                    tvPrompt.setText("Ready to go to Sleep?");
                    recordStarted = false;

                    Date currTime = Calendar.getInstance().getTime();
                    DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd");
                    timeFormat.setTimeZone(TimeZone.getDefault());
                    dateFormat.setTimeZone(TimeZone.getDefault());
                    sTimeTo = timeFormat.format(currTime);
                    dTimeTo = dateFormat.format(currTime);

                    //add data to database
                    addSleepRecordToUserData(sTimeFrom,dTimeFrom,sTimeTo,dTimeTo);
                    //go to sleep record list
                    Intent i = new Intent(ProfileActivity.this, SleepRecordActivity.class);
                    startActivity(i);
                    finish();

                }
                else{
                    // You can edit the below commented code if you want the logo to change onClick

                    //btnRecord.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sleep_on));
                    //btnRecord.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fccf65")));

                    tvPrompt.setText("Wake Up!");

                    Date currTime = Calendar.getInstance().getTime();
                    DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd");
                    timeFormat.setTimeZone(TimeZone.getDefault());
                    dateFormat.setTimeZone(TimeZone.getDefault());
                    sTimeFrom = timeFormat.format(currTime);
                    dTimeFrom = dateFormat.format(currTime);

                    recordStarted = true;
                }
            }
        });

        // View sleeping records button function
        this.fabViewRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(ProfileActivity.this, SleepRecordActivity.class);
                startActivity(i);
                finish();
            }
        });

        // Log-out button function
        this.fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        // Edit profile button function
        this.fabEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void addSleepRecordToUserData(String sTimeFrom, String dTimeFrom, String sTimeTo, String dTimeTo){
        DatabaseReference key = databaseReference.child(FirebaseAuth.getInstance().getUid()).child(Collections.sleeprecords.name()).push();
        String strKey = key.getKey();
        SleepRecord record = new SleepRecord(dTimeFrom, R.drawable.bed_icon, dTimeTo, sTimeFrom, sTimeTo, strKey);
        key.setValue(record);
    }
    private void initComponents() {
        this.pbProfile = findViewById(R.id.pb_profile);
        this.tvWelcome = findViewById(R.id.tv_welcome);
        this.fabRecord = findViewById(R.id.btn_record);
        this.fabViewRecord = findViewById(R.id.btn_view_record);
        this.fabEditProfile = findViewById(R.id.btn_edit);
        this.fabLogout = findViewById(R.id.btn_log_out);
        this.tvPrompt = findViewById(R.id.tv_prompt);
    }

    private void initFirebase() {
        // Let ProgressBar appear
        this.pbProfile.setVisibility(View.VISIBLE);

        // Get instance of Firebase Authentication
        this.mAuth = FirebaseAuth.getInstance();

        // Get the current user from Firebase
        this.mUser = this.mAuth.getCurrentUser();
        this.userID = this.mUser.getUid();

        // Get the Realtime Database from Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference(Collections.users.name());

        // Acquire information on the current user
        databaseReference.child(this.userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pbProfile.setVisibility(View.GONE);

                String name = snapshot.child("name").getValue().toString();
                tvWelcome.setText("Welcome " + name + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pbProfile.setVisibility(View.GONE);
            }
        });
    }

}