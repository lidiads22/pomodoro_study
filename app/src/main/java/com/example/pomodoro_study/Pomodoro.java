package com.example.pomodoro_study;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Timer;
import java.util.TimerTask;

public class Pomodoro extends AppCompatActivity {
    Button  breakButton, focusButton, resetButton;
    FloatingActionButton startButton,pauseButton;
    Timer timer;
    TextView textView;
    Boolean value;
    int num =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pomodoro);

        startButton = findViewById(R.id.startButtonID);
        pauseButton = findViewById(R.id.pauseButtonID);
        resetButton = findViewById(R.id.reset_buttonID);
        breakButton = findViewById(R.id.break_buttonID);
        focusButton = findViewById(R.id.focus_buttonID);
        textView = findViewById(R.id.timerID);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!value){
                  //  startButton();
                    value=true;
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("0");
                timer.cancel();
                num = 0;
                value = false;
             }
        });

    }
}

//    public void startButton(){
//        timer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView.setText(String.valueOf(num));
//                        num = num + 1;
//                    }
//                });
//            }
//        };
//        timer.schedule(task,0,1000);
//    }
//
//}