// TasksActivity.java
package com.example.pomodoro_study;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TasksActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_layout);


        ImageView closeButton = findViewById(R.id.closeButton);

        closeButton.setOnClickListener(v -> {
            startActivity(new Intent(TasksActivity.this, HomeDashboard.class));
            finish();
        });


    }
}

