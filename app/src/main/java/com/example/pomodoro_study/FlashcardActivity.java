package com.example.pomodoro_study;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FlashcardActivity extends AppCompatActivity {

    // Textviews from xml
    private TextView questionTextView;
    private TextView answerTextView;
    private CardView flashcardView;
    private TextView flashcardContent;
    private ProjectDataBaseHelper dbHelper;
    private boolean isAnswerShown = false;
    private ProjectDataBaseHelper.Flashcard currentFlashcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        questionTextView = findViewById(R.id.questionTextView);
        answerTextView = findViewById(R.id.answerTextView);
        flashcardView = findViewById(R.id.flashcardView);
    }
}