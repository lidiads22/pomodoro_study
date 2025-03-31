package com.example.pomodoro_study;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Locale;
import java.util.Timer;


public class Pomodoro extends AppCompatActivity {
    Button breakButton, focusButton, resetButton;
    FloatingActionButton startButton, pauseButton;
    Timer timer;
    TextView textView;

    TextView timerTextView;
    long durationInSeconds;
    CountDownTimer countDownTimer;
    Boolean value = false;  // Initialized as false
    int num = 25 * 60; // Initialize for 25 minutes

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_pomodoro);
//
//        startButton = findViewById(R.id.startButtonID);
//        pauseButton = findViewById(R.id.pauseButtonID);
//       // resetButton = findViewById(R.id.reset_buttonID);
//        breakButton = findViewById(R.id.break_buttonID);
//        focusButton = findViewById(R.id.focus_buttonID);
//        textView = findViewById(R.id.timerID);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        timerTextView = findViewById(R.id.timerID); // Ensure this ID matches your TextView in activity_pomodoro.xml

        // Retrieve the duration passed from HomeDashboard
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TIME_DURATION")) {
            int durationMinutes = intent.getIntExtra("TIME_DURATION", 25); // Default to 25 minutes if no data found
            durationInSeconds = durationMinutes * 60; // Convert minutes to seconds
            startTimer(durationInSeconds * 1000); // Convert seconds to milliseconds for the timer
        }
    }

        private void startTimer(long durationMs) {
            countDownTimer = new CountDownTimer(durationMs, 1000) {
                public void onTick(long millisUntilFinished) {
                    timerTextView.setText(formatTime(millisUntilFinished));
                }

                public void onFinish() {
                    timerTextView.setText("00:00");
                }
            }.start();
        }

    private String formatTime(long millis) {
        long hours = (millis / 1000) / 3600;  // Convert milliseconds to hours
        long minutes = ((millis / 1000) % 3600) / 60;  // Remaining minutes
        long seconds = (millis / 1000) % 60;  // Remaining seconds
        if (hours > 0) {
            return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
    }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
        }
    }
