package com.mobdeve.s13.s11.group29.mp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<SleepRecordHolder>{
    private ArrayList<SleepRecord> dataList;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


    public Adapter(ArrayList<SleepRecord> dataList){

        this.dataList = dataList;

        this.mAuth = FirebaseAuth.getInstance();
        this.mUser = this.mAuth.getCurrentUser();

        databaseReference  = FirebaseDatabase.getInstance().getReference(Collections.users.name()).child(mUser.getUid());
    }
    @NonNull
    @NotNull
    @Override
    public SleepRecordHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.template_record, parent, false);

        SleepRecordHolder sleepRecordHolder = new SleepRecordHolder(view);

        sleepRecordHolder.setFabDeleteOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String key = dataList.get(sleepRecordHolder.getBindingAdapterPosition()).getKey();
                databaseReference.child("sleeprecords").child(key).removeValue();
                Intent i = new Intent(view.getContext(), ProfileActivity.class);
                view.getContext().startActivity(i);
            }
        });
        //return custom viewholder
        return sleepRecordHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SleepRecordHolder holder, int position) {
        holder.setImg(this.dataList.get(position).getImgId());
        holder.setTxtDateFrom(this.dataList.get(position).getFromDate());
        holder.setTxtDateTo(this.dataList.get(position).getToDate());
        holder.setTxtTimeFrom(this.dataList.get(position).getFromTime());
        holder.setTxtTimeTo(this.dataList.get(position).getToTime());

    }

    @Override
    public int getItemCount() {
        return this.dataList.size();
    }
}
