package com.example.library.entities;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.GeoPoint;

public class Ambulance {

    @DocumentId
    private String id;
    private String numberPlate;
    private boolean available;
    private GeoPoint startLatLng;
    private GeoPoint currentLatLng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public GeoPoint getStartLatLng() {
        return startLatLng;
    }

    public void setStartLatLng(GeoPoint startLatLng) {
        this.startLatLng = startLatLng;
    }

    public GeoPoint getCurrentLatLng() {
        return currentLatLng;
    }

    public void setCurrentLatLng(GeoPoint currentLatLng) {
        this.currentLatLng = currentLatLng;
    }
}
