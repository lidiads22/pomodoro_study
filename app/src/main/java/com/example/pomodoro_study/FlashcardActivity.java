package com.example.pomodoro_study;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.util.Log;
import java.util.ArrayList;  // This line imports the ArrayList class



public class FlashcardActivity extends AppCompatActivity {

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

        dbHelper = new ProjectDataBaseHelper(this);

        String category = getIntent().getStringExtra("Category"); // Assuming "Category" was put as an extra
        loadFlashcardsFromDatabase(category); // Load flashcards based on the category

        if(!flashcards.isEmpty()) {
            displayCurrentFlashcard();
        }

        flashcardView.setOnClickListener(v -> toggleAnswerVisibility());
    }

    private void loadFlashcardsFromDatabase(String category) {
        flashcards = dbHelper.getFlashcardByCategory(category); // Load all flashcards for a specific category
        Log.e("FlashcardDebug", "Number of flashcards loaded: " + flashcards.size());
    }

    private void displayCurrentFlashcard() {
        if (flashcards != null && !flashcards.isEmpty()) {
            currentFlashcard = flashcards.get(0);
            questionTextView.setText(currentFlashcard.question);
            answerTextView.setText(currentFlashcard.answer);
            answerTextView.setVisibility(View.GONE); // Initially hide the answer
        } else {
            Log.d("FlashcardDebug", "No flashcards found or list is empty.");
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

