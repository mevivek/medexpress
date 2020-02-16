package com.example.medexpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.medexpress.databinding.SplashActivityBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {

    private SplashActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.splash_activity);

        binding.retry.setOnClickListener(view -> {
            binding.retry.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
            startActivityForResult(new Intent(this, LoginHelperActivity.class), LoginHelperActivity.REQUEST_CODE);
        });
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            checkExistingUser();
        } else {
            startActivityForResult(new Intent(this, LoginHelperActivity.class), LoginHelperActivity.REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginHelperActivity.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                checkExistingUser();
            } else {
                binding.progressBar.setVisibility(View.GONE);
                binding.retry.setVisibility(View.VISIBLE);
            }
        } else if (requestCode == RegisterProfileActivity.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Failed to Register.", Toast.LENGTH_SHORT).show();
                binding.progressBar.setVisibility(View.GONE);
                binding.retry.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkExistingUser() {
        FirebaseFirestore.getInstance()
                .collection("patients")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful())
                if (task.getResult().contains("firstName")) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    startActivityForResult(new Intent(SplashActivity.this, RegisterProfileActivity.class), RegisterProfileActivity.REQUEST_CODE);
                }
            else {
                startActivityForResult(new Intent(SplashActivity.this, RegisterProfileActivity.class), RegisterProfileActivity.REQUEST_CODE);
            }
        });
    }
}
