package com.example.pomodoro_study;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.util.Log;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;


public class FlashcardActivity extends AppCompatActivity {

    // Buttons
    FloatingActionButton close_btn, left_btn, right_btn;


    //Views and text for flashcard
    private TextView questionTextView;
    private TextView answerTextView;
    private CardView flashCardView;
    private ProjectDataBaseHelper dbHelper;
    private boolean isAnswerShown = false;
    private ProjectDataBaseHelper.Flashcard currentFlashcard;
    private ArrayList<ProjectDataBaseHelper.Flashcard> flashcards;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        questionTextView = findViewById(R.id.questionTextView);
        answerTextView = findViewById(R.id.answerTextView);
        flashCardView = findViewById(R.id.flashcardView);
        close_btn = findViewById(R.id.closeButton);
        right_btn = findViewById(R.id.rightSwipeButton);
        left_btn = findViewById(R.id.leftSwipeButton);


        dbHelper = new ProjectDataBaseHelper(this);

        String category = getIntent().getStringExtra("Category"); // Assuming "Category" was put as an extra
        loadFlashcardsFromDatabase(category); // Load flashcards based on the category

        if(!flashcards.isEmpty()) {
            displayCurrentFlashcard();
        }

        flashCardView.setOnClickListener(v -> toggleAnswerVisibility());


        // Actions for floating buttons
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeDashboard.class);
                startActivity(intent);
            }
        });
        left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flashcards != null && !flashcards.isEmpty()) {
                    // Move to previous and wrap around if needed
                    currentIndex = (currentIndex - 1 + flashcards.size()) % flashcards.size();
                    currentFlashcard = flashcards.get(currentIndex);
                    displayCurrentFlashcard();
                }
            }
        });

        // Right button: shows the next flashcard
        right_btn.setOnClickListener(v -> {
            if (flashcards != null && !flashcards.isEmpty()) {
                // Update index and wrap around
                currentIndex = (currentIndex + 1) % flashcards.size();
                currentFlashcard = flashcards.get(currentIndex);
                displayCurrentFlashcard(); // This updates the TextViews
            }
        });

    }

    private void loadFlashcardsFromDatabase(String category) {
        flashcards = dbHelper.getFlashcardByCategory(category); // Load all flashcards for a specific category
        currentIndex = 0;
        Log.e("FlashcardDebug", "Number of flashcards loaded: " + flashcards.size());
    }

    private void displayCurrentFlashcard() {
        if (flashcards != null && !flashcards.isEmpty()) {
         //   currentFlashcard = flashcards.get(0);
            currentFlashcard = flashcards.get(currentIndex);
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

