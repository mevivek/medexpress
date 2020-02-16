package com.example.library.entities;

import androidx.annotation.Keep;
import androidx.lifecycle.LiveData;

import com.example.library.firebase.FirebaseCollections;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import java.util.Map;

@Keep
public class CurrentPatient extends LiveData<Patient> {
    private DocumentReference patientDocumentReference;

    public CurrentPatient() {
        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() == null) setValue(null);
            else {
                String uid = firebaseAuth.getCurrentUser().getUid();
                patientDocumentReference = FirebaseCollections.patients.document(uid);
                patientDocumentReference.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null)
                        setValue(task.getResult().toObject(Patient.class));
                    else setValue(null);
                });
            }
        });
    }

    public Task<Void> updateUser(Map<String, Object> update) {
        return patientDocumentReference.update(update);
    }
}
