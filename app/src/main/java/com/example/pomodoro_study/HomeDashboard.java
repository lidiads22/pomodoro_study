package com.example.pomodoro_study;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        twoHourButton = findViewById(R.id.button_2hr_id);
        threeHourButton = findViewById(R.id.button_3hr_id);
        fourHourButton = findViewById(R.id.button_4hr_id);

        // Initialize Flashcard Buttons
        flashcardButton1 = findViewById(R.id.blueFlashcardID);
        flashcardButton2 = findViewById(R.id.redFlashcardID);
        flashcardButton3 = findViewById(R.id.purpleFlashcardID);
        flashcardButton4 = findViewById(R.id.greenFlashcardID);

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
        flashcardButton1.setOnClickListener(v -> startFlashcardActivity("Computer Science"));
        flashcardButton2.setOnClickListener(v -> startFlashcardActivity("Biology"));
        flashcardButton3.setOnClickListener(v -> startFlashcardActivity("Business"));
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
