package com.mobdeve.s13.s11.group29.mp;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.annotations.NotNull;

public class SleepRecordHolder extends RecyclerView.ViewHolder {

    private ImageView imgIconSleepRecord;
    private TextView txtDateFrom;
    private TextView txtDateTo;
    private TextView txtTimeFrom;
    private TextView txtTimeTo;
    private LinearLayout llEntry;
    private FloatingActionButton fabDelete;
    public SleepRecordHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        this.imgIconSleepRecord = itemView.findViewById(R.id.imgIconSleepRecord);
        this.txtDateFrom = itemView.findViewById(R.id.txtDateFrom);
        this.txtDateTo = itemView.findViewById(R.id.txtDateTo);
        this.txtTimeFrom = itemView.findViewById(R.id.txtTimeFrom);
        this.txtTimeTo = itemView.findViewById(R.id.txtTimeTo);
        this.llEntry = itemView.findViewById(R.id.ll_entry);
        this.fabDelete = itemView.findViewById(R.id.btn_delete);

        this.fabDelete.setImageResource(R.drawable.btn_delete);

    }

    public void setImg(int img){
        this.imgIconSleepRecord.setImageResource(img);
    }
    public void setTxtDateFrom(String s){
        this.txtDateFrom.setText(s);
    }
    public void setTxtDateTo(String s){
        this.txtDateTo.setText(s);
    }
    public void setTxtTimeFrom(String s){
        this.txtTimeFrom.setText(s);
    }
    public void setTxtTimeTo(String s){
        this.txtTimeTo.setText(s);
    }

    /*
    public void setTvDate(CustomDate date){
        this.tvDate.setText(date.toStringNoYear());
    }
    public void setTvTweet(String tweet){
        this.tvTweet.setText(tweet);
    }

    */
    public void setFabDeleteOnClickListener(View.OnClickListener onClickListener){
        this.fabDelete.setOnClickListener(onClickListener);
    }
}
