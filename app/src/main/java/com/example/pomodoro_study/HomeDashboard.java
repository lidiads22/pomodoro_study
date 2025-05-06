package com.example.pomodoro_study;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeDashboard extends AppCompatActivity {
    // Hour Buttons
    Button oneHourButton;
    Button twoHourButton;
    Button threeHourButton;
    Button fourHourButton;
    Button learnMoreButton;
    // Flashcard Buttons
    ImageView flashcardButton1;
    ImageView flashcardButton2;
    ImageView flashcardButton3;
    ImageView flashcardButton4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homedashboard);


        if (flashcardButton1 != null) {
            flashcardButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeDashboard.this, FlashcardActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Log.e("HomeDashboard", "Flashcard button is null");
        }


        // Initialize Hour Buttons
        oneHourButton = findViewById(R.id.button_1HR_ID);
        twoHourButton = findViewById(R.id.button_2HR_ID);
        threeHourButton = findViewById(R.id.button_3HR_ID);
        fourHourButton = findViewById(R.id.button_4HR_ID);

        // Initialize Flashcard Buttons
        flashcardButton1 = findViewById(R.id.compFlashcard);
        flashcardButton2 = findViewById(R.id.bioFlashcard);
        flashcardButton3 = findViewById(R.id.busFlashcard);
        flashcardButton4 = findViewById(R.id.anFlashcard);
        learnMoreButton = findViewById(R.id.learnMoreBtn);

        // Set Listeners for Hour Buttons
        setPomodoroButtonListeners();
        // Set Listeners for Flashcard Buttons
        setFlashcardButtonListeners();

        // NAV BAR m
        ImageButton homeBtn = findViewById(R.id.nav_home);
        ImageButton taskBtn = findViewById(R.id.nav_tasks);
        ImageButton logoutBtn = findViewById(R.id.nav_logout);
        //mageButton logoutBtn = findViewById(R.id.nav_logout);

        homeBtn.setOnClickListener(v -> {
            startActivity(new Intent(HomeDashboard.this, HomeDashboard.class));
            finish();
        });

        taskBtn.setOnClickListener(v -> {
            startActivity(new Intent(HomeDashboard.this, todoList.class));
            finish();
        });
        logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(HomeDashboard.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeDashboard.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // optional: clears activity stack
            startActivity(intent);
            finish();
        });

        learnMoreButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeDashboard.this, learnMore.class));
            finish();
        });

    }
    private void setPomodoroButtonListeners() {
        oneHourButton.setOnClickListener(v -> startPomodoroActivity(60));
        twoHourButton.setOnClickListener(v -> startPomodoroActivity(120));
        threeHourButton.setOnClickListener(v -> startPomodoroActivity(180));
        fourHourButton.setOnClickListener(v -> startPomodoroActivity(240));
    }

    private void startPomodoroActivity(int timeDuration) {
        Intent intent = new Intent(HomeDashboard.this, Pomodoro.class);
        intent.putExtra("TIME_DURATION", timeDuration);
        startActivity(intent);
    }

    private void setFlashcardButtonListeners() {
        View.OnClickListener flashcardListener = v -> {
            Intent intent = new Intent(HomeDashboard.this, FlashcardActivity.class);
            startActivity(intent);
        };
        flashcardButton1.setOnClickListener(v -> startFlashcardActivity("Computer Science"));
        flashcardButton3.setOnClickListener(v -> startFlashcardActivity("Biology"));
        flashcardButton2.setOnClickListener(v -> startFlashcardActivity("Business"));
        flashcardButton4.setOnClickListener(v -> startFlashcardActivity("Anatomy"));

        if (flashcardButton1 == null) {
            Log.e("Debug", "flashcardButton1 is null");
        }


    }
    private void startFlashcardActivity(String category) {
        Intent intent = new Intent(HomeDashboard.this, FlashcardActivity.class);
        intent.putExtra("Category", category);
        startActivity(intent);
    }
}
