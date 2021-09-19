package com.mobdeve.s13.s11.group29.mp;

import java.util.ArrayList;

public class User {
    // Constructor
    public User(String name, String birthday, String email, String password) {
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        sleepRecord = new ArrayList<SleepRecord>();
    }

    // Methods
    public String getName() {
        return this.name;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    // Attributes
    private String name;
    private String birthday;
    private String email;
    private String password;
    private ArrayList<SleepRecord> sleepRecord;
}
