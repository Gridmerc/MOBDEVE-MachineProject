package com.mobdeve.s13.s11.group29.mp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SleepRecordActivity extends AppCompatActivity {
    /* Activity where the sleep records are displayed through rv_list */

    private RecyclerView rvList;
    private RecyclerView.LayoutManager rvManager;
    private Adapter adapter;
    private ArrayList<SleepRecord> dataList;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference databaseReference;

    //buttons
    private FloatingActionButton fabAddRecord;
    private FloatingActionButton fabEditProfile;
    private FloatingActionButton fabLogout;


    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_record);

        this.mAuth = FirebaseAuth.getInstance();
        this.mUser = this.mAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference(Collections.users.name());
        databaseReference = databaseReference.child(FirebaseAuth.getInstance().getUid()).child(Collections.sleeprecords.name());

        initRecyclerView();
    }

    private void initRecyclerView(){
        //reference recyclerview
        this.rvList = findViewById(R.id.rv_list);

        //initialize layout manager
        this.rvManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.rvList.setLayoutManager(this.rvManager);

        //initialize adapter
        this.dataList = new ArrayList<SleepRecord>();
        this.adapter = new Adapter(this.dataList);
        this.rvList.setAdapter(this.adapter);
        initButtons();

        SnapHelper sh = new PagerSnapHelper();
        sh.attachToRecyclerView(this.rvList);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    SleepRecord sleepRecord = dataSnapshot.getValue(SleepRecord.class);

                    //int img = dataSnapshot.getValue().get("imgId");

                    dataList.add(sleepRecord);
                    System.out.println("success");
                    System.out.println(dataSnapshot.getValue().getClass());
                    System.out.println(dataSnapshot.getValue());
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("fail");
            }
        });

    }

    private void initButtons(){
        //initialize onClick methods for bottom actionbar fabs (edit, view, logout)
        this.fabAddRecord = findViewById(R.id.btn_add_record_list);
        this.fabEditProfile = findViewById(R.id.btn_edit_list);
        this.fabLogout = findViewById(R.id.btn_log_out_list);

        this.fabAddRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SleepRecordActivity.this, ProfileActivity.class);
                startActivity(i);
                finish();
            }
        });

        this.fabEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SleepRecordActivity.this, EditProfileActivity.class);
                startActivity(i);
                finish();
            }
        });

        this.fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i = new Intent(SleepRecordActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}