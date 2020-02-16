package com.example.library.firebase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseCollections {

    private static final String PATIENTS = "patients";
    private static final String HOSPITALS = "hospitals";
    private static final String VEHICLES = "vehicles";
    private static final String DRIVERS = "drivers";
    private static final String REQUESTS = "requests";

    public static CollectionReference patients = FirebaseFirestore.getInstance().collection(PATIENTS);
    public static CollectionReference partners = FirebaseFirestore.getInstance().collection(HOSPITALS);
    public static CollectionReference vehicles = FirebaseFirestore.getInstance().collection(VEHICLES);
    public static CollectionReference drivers = FirebaseFirestore.getInstance().collection(DRIVERS);
    public static CollectionReference requests = FirebaseFirestore.getInstance().collection(REQUESTS);
}
