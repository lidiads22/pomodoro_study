package com.example.pomodoro_study;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
        setContentView(R.layout.activity_learn_more);
        ImageView backbutton = findViewById(R.id.backButtonID2);



        backbutton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(learnMore.this, HomeDashboard.class);
            startActivity(intent);
            finish();
        });
    }
}