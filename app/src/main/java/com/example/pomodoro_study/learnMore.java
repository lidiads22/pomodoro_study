package com.example.pomodoro_study;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.pomodoro_study.databinding.ActivityLearnMoreBinding;
import com.google.firebase.auth.FirebaseAuth;

public class learnMore extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityLearnMoreBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(HomeDashboard.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeDashboard.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // optional: clears activity stack
            startActivity(intent);
            finish();
        });
    }
}