package com.example.pomodoro_study;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeDashboard extends AppCompatActivity {
    // Hour Buttons
    Button oneHourButton;
    Button twoHourButton;
    Button threeHourButton;
    Button fourHourButton;
    // Flashcard Buttons
    ImageView flashcardButton1;
    ImageView flashcardButton2;
    ImageView flashcardButton3;
    ImageView flashcardButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homedashboard);

        // Initialize Hour Buttons
        oneHourButton = findViewById(R.id.button_1HR_ID);
        twoHourButton = findViewById(R.id.button_2hr_id);
        threeHourButton = findViewById(R.id.button_3hr_id);
        fourHourButton = findViewById(R.id.button_4hr_id);

        // Initialize Flashcard Buttons
        flashcardButton1 = findViewById(R.id.blueFlashcardID);
        flashcardButton2 = findViewById(R.id.redFlashcardID);
        flashcardButton3 = findViewById(R.id.purpleFlashcardID);

        // Set Listeners for Hour Buttons
        setPomodoroButtonListeners();

        // Set Listeners for Flashcard Buttons
        setFlashcardButtonListeners();
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
        flashcardButton1.setOnClickListener(flashcardListener);
        flashcardButton2.setOnClickListener(flashcardListener);
        flashcardButton3.setOnClickListener(flashcardListener);
        // If you have a fourth flashcard button, initialize it and set its listener as well
    }
}
