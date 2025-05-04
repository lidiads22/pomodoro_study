package com.example.pomodoro_study;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Locale;
import java.util.Timer;


public class Pomodoro extends AppCompatActivity {
    Button breakButton, focusButton, resetButton;
    FloatingActionButton startButton, pauseButton,backButton;
    Timer timer;
    TextView textView;

    TextView timerTextView;
    long durationInSeconds;
    CountDownTimer countDownTimer;
    Boolean value = false;
    int num = 25 * 60;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        timerTextView = findViewById(R.id.timerID); // Ensure this ID matches your TextView in activity_pomodoro.xml

        startButton = findViewById(R.id.startButtonID);
        pauseButton = findViewById(R.id.pauseButtonID);
        //resetButton = findViewById(R.id.reset_buttonID);breakButton = findViewById(R.id.break_buttonID);
        focusButton = findViewById(R.id.focus_buttonID);
        backButton = findViewById(R.id.backButtonID);

        // Retrieve the duration passed from HomeDashboard
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TIME_DURATION")) {
            int durationMinutes = intent.getIntExtra("TIME_DURATION", 25); // Default to 25 minutes if no data found
            durationInSeconds = durationMinutes * 60; // Convert minutes to seconds
            startTimer(durationInSeconds * 1000); // Convert seconds to milliseconds for the timer
        }

        // Actions for floating buttons
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeDashboard.class);
                startActivity(intent);
            }
        });
    }

    // Start Break
    private int pomodoroCount = 0;
    private void startBreak(int breakMinutes) {
        new CountDownTimer(breakMinutes * 60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(formatTime(millisUntilFinished));
            }

            public void onFinish() {
                pomodoroCount++;
                if (pomodoroCount % 2 == 1) {
                    // Start 5 min break after each work session
                    startBreak(5);
                } else if (pomodoroCount == 8) {
                    // After 4 work sessions and 4 breaks (8 total), give a long break
                    startBreak(15);
                    pomodoroCount = 0; // Reset cycle
                } else {
                    // Start next 25 min work session
                    startTimer(25 * 60 * 1000);
                }
                timerTextView.setText("00:00");
            }
        }.start();
    }

    // If it was a 25-min work session: ➔ start a 5-min break next.
    //
    //If it was a 5-min break: ➔ start a new 25-min work session.
    //
    //After 4 work sessions: ➔ take a longer 15-min break.
    private void startTimer(long durationMs) {
        countDownTimer = new CountDownTimer(durationMs, 1000) {
            public void onTick(long millisUntilFinished) {
                // Update UI with remaining time
                timerTextView.setText(formatTime(millisUntilFinished));
            }

            public void onFinish() {
                timerTextView.setText("00:00");

                // ➡️ After work session finishes
                pomodoroCount++;

                if (pomodoroCount % 4 == 0) {
                    // After every 4 work sessions → long break
                    startBreak(15);
                } else {
                    // Otherwise → short break
                    startBreak(5);
                }
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
