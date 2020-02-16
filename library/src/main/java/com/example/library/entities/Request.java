package com.example.library.entities;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

public class Request {

    private DocumentReference patientReference;
    private String patientId;
    private String emergencyType;
    private GeoPoint latLng;
    private String location;
    @ServerTimestamp
    private Timestamp timestamp;
    private int status;
    private DocumentReference assignedAmbulance;
    private DocumentReference assignedHospital;

    public DocumentReference getPatientReference() {
        return patientReference;
    }

    public void setPatientReference(DocumentReference patientReference) {
        this.patientReference = patientReference;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getEmergencyType() {
        return emergencyType;
    }

    public void setEmergencyType(String emergencyType) {
        this.emergencyType = emergencyType;
    }

    public GeoPoint getLatLng() {
        return latLng;
    }

    public void setLatLng(GeoPoint latLng) {
        this.latLng = latLng;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DocumentReference getAssignedAmbulance() {
        return assignedAmbulance;
    }

    public void setAssignedAmbulance(DocumentReference assignedAmbulance) {
        this.assignedAmbulance = assignedAmbulance;
    }

    public DocumentReference getAssignedHospital() {
        return assignedHospital;
    }

    public void setAssignedHospital(DocumentReference assignedHospital) {
        this.assignedHospital = assignedHospital;
    }
}
