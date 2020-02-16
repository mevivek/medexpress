package com.example.library.firebase;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;

public class FirebaseDocument extends LiveData<DocumentSnapshot> {

    private DocumentReference documentReference;
    private ListenerRegistration listenerRegistration;
    private EventListener<DocumentSnapshot> eventListener = (documentSnapshot, exception) -> {
        if (documentSnapshot != null && documentSnapshot.exists())
            setValue(documentSnapshot);
    };

    public FirebaseDocument(DocumentReference documentReference) {
        this.documentReference = documentReference;
    }

    @Override
    protected void onActive() {
        listenerRegistration = documentReference.addSnapshotListener(eventListener);
    }

    @Override
    protected void onInactive() {
        listenerRegistration.remove();
    }


}
