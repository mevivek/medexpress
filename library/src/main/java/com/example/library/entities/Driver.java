package com.example.library.entities;

import androidx.annotation.Keep;

import com.google.firebase.firestore.DocumentReference;

@Keep
public class Driver extends User {

    private boolean available;
    private DocumentReference ambulanceReference;

    public Driver() {
        available = false;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public DocumentReference getAmbulanceReference() {
        return ambulanceReference;
    }

    public void setAmbulanceReference(DocumentReference ambulanceReference) {
        this.ambulanceReference = ambulanceReference;
    }
}
