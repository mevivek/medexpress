package com.example.medexpress;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.library.entities.Patient;
import com.example.library.enums.Gender;
import com.example.library.firebase.FirebaseCollections;
import com.example.medexpress.databinding.RegisterProfileActivityBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterProfileActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;

    private RegisterProfileActivityBinding binding;
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.register_profile_activity);
        setSupportActionBar(binding.toolbar);
        binding.gender.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.gender_male:
                    binding.getUser().setGender(Gender.MALE);
                    break;
                case R.id.gender_female:
                    binding.getUser().setGender(Gender.FEMALE);
                    break;
                case R.id.gender_others:
                    binding.getUser().setGender(Gender.OTHERS);
                    break;
            }
        });
        patient = new Patient();
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            finish();
        patient.setPrimaryPhoneNumber(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        binding.setUser(patient);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.phone_number:
                Toast.makeText(this, "Number can not be changed.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.save_button:
                patient.setFirstName(binding.firstName.getText() != null ? binding.firstName.getText().toString() : "");
                patient.setLastName(binding.lastName.getText() != null ? binding.lastName.getText().toString() : "");
                patient.setBloodGroup(binding.bloodGroup.getText().toString());
                if (patient.getFirstName().length() < 2) {
                    binding.firstNameLayout.setError("Enter a valid name");
                    return;
                }
                if (patient.getLastName().length() == 1) {
                    binding.lastNameLayout.setError("Enter a valid name");
                    return;
                }
                patient.setPrimaryPhoneNumber(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                Toast.makeText(this, "Updating profile...", Toast.LENGTH_SHORT).show();
                view.setEnabled(false);
                FirebaseCollections.patients.document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(patient).addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Registration complete", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Registration failed.", Toast.LENGTH_SHORT).show();
                    view.setEnabled(true);
                });
                break;
            case R.id.cancel_button:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}
