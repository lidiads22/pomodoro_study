package com.example.pomodoro_study;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class todoList extends AppCompatActivity {

    private CalendarView calendarView;
    private LinearLayout taskListContainer;
    private FloatingActionButton addTaskButton;
    private long selectedDateMillis;

    // Firebase instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);// Set the layout

        // Initialize views
        calendarView = findViewById(R.id.calendarView);
        taskListContainer = findViewById(R.id.taskListContainer);
        addTaskButton = findViewById(R.id.addTaskButton);

        // Initialize CalendarView to today's date
        long today = System.currentTimeMillis();
        calendarView.setDate(today, true, true);
        selectedDateMillis = today;

        // When user picks a date, update selectedDateMillis
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view,
                                            int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, dayOfMonth, 0, 0, 0);
                selectedDateMillis = cal.getTimeInMillis();
            }
        });

        // When the "Add Task" button is clicked, navigate to TasksActivity to add a task
        addTaskButton.setOnClickListener(v -> openTaskActivity());

        // NAV BAR setup
        ImageButton homeBtn = findViewById(R.id.nav_home);
        ImageButton taskBtn = findViewById(R.id.nav_tasks);
        ImageButton logoutBtn = findViewById(R.id.nav_logout);

        // Home Button
        homeBtn.setOnClickListener(v -> {
            startActivity(new Intent(todoList.this, HomeDashboard.class));
            finish();
        });

        // Task Button (already in todoList)
        taskBtn.setOnClickListener(v -> {
            startActivity(new Intent(todoList.this, todoList.class));
            finish();
        });

        // Logout Button
        logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(todoList.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(todoList.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clears activity stack
            startActivity(intent);
            finish();
        });
    }

    // Open TasksActivity (pass the selected date to it)
    private void openTaskActivity() {
        Intent intent = new Intent(todoList.this, TasksActivity.class);
        intent.putExtra("selectedDateMillis", selectedDateMillis);  // Pass selected date
        startActivity(intent);
    }

    // Format date and time display (for use in displaying dates)
    private String formatDateTime(long dateMillis, String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d", Locale.getDefault());
        String dateStr = sdf.format(dateMillis);
        if (time != null && !time.isEmpty()) {
            return dateStr + " • " + time;
        } else {
            return dateStr;
        }
    }
}
