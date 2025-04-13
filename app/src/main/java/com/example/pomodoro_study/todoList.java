package com.example.pomodoro_study;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class todoList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_todo_list);

        ImageButton homeBtn = findViewById(R.id.nav_home);
        FloatingActionButton addtaskBtn = findViewById(R.id.addTaskButton);
        //ImageButton logoutBtn = findViewById(R.id.nav_logout);

        homeBtn.setOnClickListener(v -> {
            startActivity(new Intent(todoList.this, HomeDashboard.class));
            finish();
        });
        addtaskBtn.setOnClickListener(v -> {
            startActivity(new Intent(todoList.this, TasksActivity.class));
            finish();
        });


    }
}