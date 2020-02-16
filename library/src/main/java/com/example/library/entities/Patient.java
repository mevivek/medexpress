package com.example.library.entities;

import androidx.annotation.Keep;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

@Keep
public class Patient extends User {

    @ServerTimestamp
    private Timestamp timestamp;
    private String bloodGroup;

    public Patient() {
        super();
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
