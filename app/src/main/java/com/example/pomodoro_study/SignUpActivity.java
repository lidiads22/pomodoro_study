package com.example.pomodoro_study;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    EditText nameInput, emailInput, passwordInput, confirmPasswordInput;
    CheckBox termsCheckbox;
    Button signUpButton;
    TextView signInLink;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        mAuth = FirebaseAuth.getInstance();

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        termsCheckbox = findViewById(R.id.termsCheckbox);
        signUpButton = findViewById(R.id.signUpButton);
        signInLink = findViewById(R.id.signInLink);

        signUpButton.setOnClickListener(view -> {
            String name = nameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString();
            String confirmPassword = confirmPasswordInput.getText().toString();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) ||
                    TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!termsCheckbox.isChecked()) {
                Toast.makeText(this, "Please agree to the terms and privacy policy", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create user
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Account Registered!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, HomeDashboard.class));
                            finish();
                        } else {
                            Exception e = task.getException();
                            Log.e("SignUpError", "Registration failed", e);
                            Toast.makeText(SignUpActivity.this, "Error: " + (e != null ? e.getMessage() : "Unknown error"), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        signInLink.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
