// TasksActivity
package com.example.pomodoro_study;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TasksActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_NAME     = "com.example.pomodoro_study.TASK_NAME";
    public static final String EXTRA_TASK_DATE     = "com.example.pomodoro_study.TASK_DATE";
    public static final String EXTRA_TASK_START    = "com.example.pomodoro_study.TASK_START";
    public static final String EXTRA_TASK_END      = "com.example.pomodoro_study.TASK_END";

    private EditText etTaskName;
    private EditText etTaskDate;
    private EditText etStartTime;
    private EditText etEndTime;
    private ImageButton closeButton;
    private Button btnCreateTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_layout);

        // bind views
        etTaskName    = findViewById(R.id.et_task_name);
        etTaskDate    = findViewById(R.id.et_task_date);
        etStartTime   = findViewById(R.id.et_start_time);
        etEndTime     = findViewById(R.id.et_end_time);
        closeButton   = findViewById(R.id.closeButton);
        btnCreateTask = findViewById(R.id.btn_create_task);

        // close without creating
        closeButton.setOnClickListener(v -> finish());

        // handle create
        btnCreateTask.setOnClickListener(v -> {
            String name  = etTaskName.getText().toString().trim();
            String date  = etTaskDate.getText().toString().trim();
            String start = etStartTime.getText().toString().trim();
            String end   = etEndTime.getText().toString().trim();

            if (name.isEmpty() || date.isEmpty() || start.isEmpty() || end.isEmpty()) {
                Toast.makeText(TasksActivity.this,
                                "Please fill out all fields", Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            // prepare result
            Intent data = new Intent();
            data.putExtra(EXTRA_TASK_NAME, name);
            data.putExtra(EXTRA_TASK_DATE, date);
            data.putExtra(EXTRA_TASK_START, start);
            data.putExtra(EXTRA_TASK_END, end);

            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}


