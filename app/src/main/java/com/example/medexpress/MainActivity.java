package com.example.medexpress;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.library.entities.Ambulance;
import com.example.library.entities.CurrentPatient;
import com.example.library.entities.Hospital;
import com.example.library.entities.Patient;
import com.example.library.entities.Request;
import com.example.library.firebase.FirebaseCollections;
import com.example.medexpress.databinding.MainActivityBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

public class MainActivity extends AppCompatActivity {

    private LatLng latLng;
    private String location;

    protected MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        new CurrentPatient().observe(this, patient -> {
            ((MaterialToolbar) findViewById(R.id.toolbar)).setTitle("Welcome " + patient.getFullName().toUpperCase());
        });
        setLocation();
        FirebaseCollections.requests
                .whereEqualTo("patientReference", FirebaseCollections.patients.document(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                .whereLessThan("status", 4)
                .addSnapshotListener((queryDocumentSnapshots, exception) -> {
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty() && queryDocumentSnapshots.getDocuments().size() > 0) {
                        showRequestStatusLayout(queryDocumentSnapshots.getDocuments().get(0).toObject(Request.class));
                    } else showNewRequestLayout();
                });
    }

    private String getEmergency() {
        switch (((ChipGroup) findViewById(R.id.emergencies)).getCheckedChipId()) {
            case R.id.migraine:
                return "MIGRAINE";
            case R.id.heart_attack:
                return "HEART ATTACK";
            case R.id.burn:
                return "BURN";
            case R.id.snake_bite:
                return "SNAKE BITE";
            default:
                return "UNKNOWN";
        }
    }

    private void showNewRequestLayout() {
        binding.newRequest.getRoot().setVisibility(View.VISIBLE);
        binding.requestStatus.getRoot().setVisibility(View.GONE);
        binding.newRequest.request.setOnClickListener(view -> {
            Request request = new Request();
            request.setPatientReference(FirebaseFirestore.getInstance().collection("patients").document(FirebaseAuth.getInstance().getCurrentUser().getUid()));
            request.setPatientId(FirebaseAuth.getInstance().getCurrentUser().getUid());
            request.setEmergencyType(getEmergency());
            request.setLatLng(new GeoPoint(latLng.latitude, latLng.longitude));
            request.setLocation(location);
            FirebaseFirestore.getInstance().collection("requests").add(request)
                    .addOnCompleteListener(task -> {
                        Toast.makeText(this, "Success.", Toast.LENGTH_SHORT).show();
                    });
        });
        binding.newRequest.emergencies.setOnCheckedChangeListener((group, checkedId) -> {
            binding.newRequest.request.setEnabled(true);
        });
    }

    private void showRequestStatusLayout(Request request) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                binding.requestStatus.slider.setProgress(binding.requestStatus.slider.getProgress());
                handler.postDelayed(this, 500);
            }
        };
        handler.post(runnable);
        binding.requestStatus.getRoot().setVisibility(View.VISIBLE);
        binding.newRequest.getRoot().setVisibility(View.GONE);
        binding.requestStatus.setRequest(request);
        binding.requestStatus.setHospital(null);
        binding.requestStatus.setAmbulance(null);
        request.getPatientReference().get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        binding.requestStatus.setPatient(task.getResult().toObject(Patient.class));
                    } else
                        Toast.makeText(this, "Failed to get patient.", Toast.LENGTH_SHORT).show();
                });
        if (request.getAssignedHospital() != null)
            request.getAssignedHospital().get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            binding.requestStatus.setHospital(task.getResult().toObject(Hospital.class));
                        } else
                            Toast.makeText(this, "Failed to get hospital.", Toast.LENGTH_SHORT).show();
                    });
        if (request.getAssignedAmbulance() != null)
            request.getAssignedAmbulance().addSnapshotListener((documentSnapshot, e) -> {
                if (documentSnapshot != null) {
                    Ambulance ambulance = documentSnapshot.toObject(Ambulance.class);
                    if (ambulance == null)
                        return;
                    binding.requestStatus.setAmbulance(ambulance);
                    float startDistance = CurrentLocation.getDistanceBetweenLocations(ambulance.getStartLatLng(), ambulance.getCurrentLatLng());

                } else
                    Toast.makeText(this, "Failed to get ambulance.", Toast.LENGTH_SHORT).show();
            });
    }

    private void setLocation() {
        CurrentLocation.getCurrentLocation(this).addOnSuccessListener(booleanLatLngPair -> {
            if (booleanLatLngPair == null || booleanLatLngPair.first == null || !booleanLatLngPair.first)
                return;
            latLng = booleanLatLngPair.second;
            Address address = CurrentLocation.getAddressFromGeocode(booleanLatLngPair.second, this);
            TextView locationTV = findViewById(R.id.location);
            location = "";
            if (address.getSubLocality() != null)
                location = location + address.getSubLocality() + ", ";
            if (address.getLocality() != null)
                location = location + address.getLocality() + ", ";
            if (address.getAdminArea() != null)
                location = location + address.getAdminArea();
            locationTV.setText("Location: " + location);
        }).addOnFailureListener(exception -> {
            TextView locationTV = findViewById(R.id.location);
            String location = "Location: Unavailable";
            locationTV.setText(location);
            locationTV.setTextColor(Color.parseColor("#ea5050"));
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CurrentLocation.LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                setLocation();
            }
        }
    }
}
