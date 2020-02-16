package com.example.library.firebase;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseQueryLive extends LiveData<QuerySnapshot> {

    private Query query;
    private EventListener<QuerySnapshot> listener = (queryDocumentSnapshots, e) -> setValue(queryDocumentSnapshots);
    public FirebaseQueryLive(Query query) {
        this.query = query;
    }
    @Override
    protected void onActive() {
        query.addSnapshotListener(listener);
    }
}