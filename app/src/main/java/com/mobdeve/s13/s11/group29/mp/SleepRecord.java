package com.mobdeve.s13.s11.group29.mp;

public class SleepRecord {

    private String fromDate, toDate, fromTime, toTime, key;
    private int imgId;

    public SleepRecord(String fromDate, int imgId, String toDate, String fromTime, String toTime, String key){
        this.imgId = imgId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.key = key;
    }

    public SleepRecord(){

    }
    public int getImgId(){ return this.imgId;}
    public String getFromDate(){ return this.fromDate;}
    public String getToDate(){ return this.toDate;}
    public String getFromTime(){ return this.fromTime;}
    public String getToTime(){ return this.toTime;}
    public String getKey() {
        return this.key;
    }
}