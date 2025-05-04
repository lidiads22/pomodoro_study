package com.example.pomodoro_study;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class todoList extends AppCompatActivity {

    private CalendarView calendarView;
    private LinearLayout taskListContainer;
    private FloatingActionButton addTaskButton;
    private long selectedDateMillis;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        calendarView = findViewById(R.id.calendarView);
        taskListContainer = findViewById(R.id.taskListContainer);
        addTaskButton = findViewById(R.id.addTaskButton);

        long today = System.currentTimeMillis();
        calendarView.setDate(today, true, true);
        selectedDateMillis = today;

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, dayOfMonth, 0, 0, 0);
            selectedDateMillis = cal.getTimeInMillis();
            loadTasksForSelectedDate();
        });

        addTaskButton.setOnClickListener(v -> {
            Intent intent = new Intent(todoList.this, TasksActivity.class);
            startActivity(intent);
        });


        // NAV BAR
        ImageButton homeBtn = findViewById(R.id.nav_home);
        //ImageButton taskBtn = findViewById(R.id.nav_tasks);
        ImageButton logoutBtn = findViewById(R.id.nav_logout);

        homeBtn.setOnClickListener(v -> {
            startActivity(new Intent(todoList.this, HomeDashboard.class));
            finish();
        });

        logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(todoList.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(todoList.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Load tasks for today
        loadTasksForSelectedDate();
    }

    private void loadTasksForSelectedDate() {
        taskListContainer.removeAllViews();

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not signed in", Toast.LENGTH_SHORT).show();
            // Optional: redirect to login screen
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        String uid = currentUser.getUid();
       // String currentUser = auth.getCurrentUser().getUid();
        String selectedDateStr = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(selectedDateMillis);


        CollectionReference tasksRef = db
                .collection("users")
                .document(uid)
                .collection("tasks");

        tasksRef
                .whereEqualTo("date", selectedDateStr)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        TextView noTasks = new TextView(todoList.this);
                        noTasks.setText("No tasks for this date.");
                        taskListContainer.addView(noTasks);
                    } else {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            String name = doc.getString("taskName");
                            String start = doc.getString("startTime");
                            String end = doc.getString("endTime");

                            // Create task row
                            LinearLayout taskRow = new LinearLayout(todoList.this);
                            taskRow.setOrientation(LinearLayout.HORIZONTAL);
                            taskRow.setPadding(20, 20, 20, 20);
                            taskRow.setBackgroundResource(R.drawable.task_card_background);
                            taskRow.setLayoutParams(new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            taskRow.setElevation(8);

                            CheckBox checkBox = new CheckBox(todoList.this);
                            taskRow.addView(checkBox);

                            LinearLayout textLayout = new LinearLayout(todoList.this);
                            textLayout.setOrientation(LinearLayout.VERTICAL);
                            textLayout.setPadding(20, 0, 0, 0);
                            textLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    1f));

                            TextView taskName = new TextView(todoList.this);
                            taskName.setText(name);
                            taskName.setTextSize(18);
                            taskName.setTextColor(getResources().getColor(android.R.color.black));

                            TextView timeRange = new TextView(todoList.this);
                            timeRange.setText(start + " - " + end);
                            timeRange.setTextSize(14);
                            timeRange.setTextColor(getResources().getColor(android.R.color.darker_gray));

                            textLayout.addView(taskName);
                            textLayout.addView(timeRange);
                            taskRow.addView(textLayout);

                            // Checkbox functionality
                            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                                if (isChecked) {
                                    taskName.setPaintFlags(taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                } else {
                                    taskName.setPaintFlags(taskName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                                }
                            });

                            // Add task row to layout
                            taskListContainer.addView(taskRow);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(todoList.this, "Failed to load tasks: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}