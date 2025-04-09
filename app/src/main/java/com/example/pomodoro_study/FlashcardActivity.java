package com.example.pomodoro_study;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class FlashcardActivity extends AppCompatActivity {

    // Text views from xml
    private TextView questionTextView;
    private TextView answerTextView;
    private CardView flashcardView;
    private ProjectDataBaseHelper dbHelper;
    private boolean isAnswerShown = false;
    private ProjectDataBaseHelper.Flashcard currentFlashcard;
    private ArrayList<ProjectDataBaseHelper.Flashcard> flashcards; // Declare the flashcards list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        questionTextView = findViewById(R.id.questionTextView);
        answerTextView = findViewById(R.id.answerTextView);
        flashcardView = findViewById(R.id.flashcardView);

        dbHelper = new ProjectDataBaseHelper(this, "Flashcards.db", null, 1);

        loadFlashcardsFromDatabase(); // Load flashcards

        // Set up initial flashcard if available
        if(!flashcards.isEmpty()){
            currentFlashcard = flashcards.get(0); // first flashcard
            displayCurrentFlashcard();
        }

        flashcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAnswerVisibility();
            }
        });
    }

    private void loadFlashcardsFromDatabase() {
        flashcards = dbHelper.getFlashcardByCategory(""); // Load all flashcards, adjust if filtering by category
    }

    private void displayCurrentFlashcard() {
        if (currentFlashcard != null) {
            questionTextView.setText(currentFlashcard.question);
            answerTextView.setText(currentFlashcard.answer);
            answerTextView.setVisibility(View.GONE); // Hide the answer initially
        }
    }

    private void toggleAnswerVisibility() {
        if (isAnswerShown) {
            answerTextView.setVisibility(View.GONE);
            isAnswerShown = false;
        } else {
            answerTextView.setVisibility(View.VISIBLE);
            isAnswerShown = true;
        }
    }
}
