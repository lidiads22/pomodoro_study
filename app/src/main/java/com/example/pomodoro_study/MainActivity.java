package com.example.pomodoro_study;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    // Firebase authentication instance
    private FirebaseAuth mAuth;

    // UI elements for login
    private EditText emailEditText, passwordEditText;
    private Button loginButton, signupButton;
    private TextView forgotPasswordTextView;
    BottomNavigationView bottomNavigationItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase authentication
        mAuth = FirebaseAuth.getInstance();


        // Check if the user is already logged in
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // User is already logged in, proceed to home/dashboard screen
            navigateToHomeDashboard();
        } else {
            // User is not logged in, show login UI
            showLoginUI();
        }

    }

    // Method to show login UI elements
    private void showLoginUI() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupbutton);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordID);

        // Set up listeners for login actions
        loginButton.setOnClickListener(v -> loginUser());
        signupButton.setOnClickListener(v -> navigateToSignUp());
        forgotPasswordTextView.setOnClickListener(v -> navigateToForgotPassword());
    }

    // Method to handle user login
    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Check if fields are empty
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Authenticate user with email and password
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful, proceed to home/dashboard
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                        navigateToHomeDashboard();
                    } else {
                        // Authentication failed
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Method to navigate to the sign-up screen
    private void navigateToSignUp() {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    // Method to navigate to the forgot password screen
    private void navigateToForgotPassword() {
        Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    // Method to navigate to the home/dashboard screen
    private void navigateToHomeDashboard() {
        Intent intent = new Intent(MainActivity.this, HomeDashboard.class);
        startActivity(intent);
        finish();  // Close the login screen so the user can't navigate back
    }//nav_logout


}
