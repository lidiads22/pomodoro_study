package com.example.pomodoro_study;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeDashboard  extends AppCompatActivity {
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

    // 4 textview for hours

    // 4 category textview

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.homedashboard);
    // mAuth=FirebaseAuth.getInstance();
    // initialized objects

    // Hour Buttons
    oneHourButton = findViewById(R.id.button_1HR_ID);
    twoHourButton = findViewById(R.id.button_2hr_id);
    threeHourButton = findViewById(R.id.button_3hr_id);
    fourHourButton = findViewById(R.id.button_4hr_id);
    // Flashcards Imageview
    flashcardButton1 = findViewById(R.id.blueFlashcardID);
    flashcardButton2 = findViewById(R.id.redFlashcardID);
    flashcardButton3 = findViewById(R.id.purpleFlashcardID);
    flashcardButton4 = findViewById(R.id.greenFlashcardID);


//    textView = findViewById(R.id.registerNow);
//    textView.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            // for the register textview
//            Intent intent = new Intent(getApplicationContext(),register.class);
//            startActivity(intent);
//            finish();
//        }
    };
}
