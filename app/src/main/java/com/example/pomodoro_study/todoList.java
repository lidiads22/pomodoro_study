// TasksActivity.java
package com.example.pomodoro_study;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.TextKeyListener;
import android.text.method.TextKeyListener.Capitalize;
import android.text.method.LinkMovementMethod;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class todoList extends AppCompatActivity {

    private CalendarView calendarView;
    private LinearLayout taskListContainer;
    private FloatingActionButton addTaskButton;
    private long selectedDateMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);  //xml

        calendarView = findViewById(R.id.calendarView);
        taskListContainer = findViewById(R.id.taskListContainer);
        addTaskButton = findViewById(R.id.addTaskButton);

        // 1) Initialize CalendarView to today
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
                // TODO: filter existing tasks by date if you store them with metadata
            }
        });

        // 2) Handle "Add Task" button
        addTaskButton.setOnClickListener(v -> showAddTaskDialog());
    }

    private void showAddTaskDialog() {
        // Create an EditText for the task title
        final EditText titleInput = new EditText(this);
        titleInput.setHint("Task title");
        titleInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        // Create an EditText for the time
        final EditText timeInput = new EditText(this);
        timeInput.setHint("e.g. 5:00 PM");
        timeInput.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_TIME);

        // Pack them into a vertical LinearLayout
        LinearLayout dialogLayout = new LinearLayout(this);
        dialogLayout.setOrientation(LinearLayout.VERTICAL);
        dialogLayout.setPadding(50, 20, 50, 10);
        dialogLayout.addView(titleInput);
        dialogLayout.addView(timeInput);

        new AlertDialog.Builder(this)
                .setTitle("New Task")
                .setView(dialogLayout)
                .setPositiveButton("Add", (dialog, which) -> {
                    String title = titleInput.getText().toString().trim();
                    String time = timeInput.getText().toString().trim();
                    if (!title.isEmpty()) {
                        addTaskView(title, time, selectedDateMillis);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void addTaskView(String title, String time, long dateMillis) {
        // 1) Create the horizontal container
        LinearLayout taskRow = new LinearLayout(this);
        taskRow.setOrientation(LinearLayout.HORIZONTAL);
        taskRow.setPadding(16, 16, 16, 16);
        taskRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // 2) Checkbox
        CheckBox checkBox = new CheckBox(this);
        taskRow.addView(checkBox);

        // 3) Title TextView
        TextView titleView = new TextView(this);
        titleView.setText(title);
        titleView.setTextSize(18);
        titleView.setTextColor(getResources().getColor(android.R.color.black));
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        titleView.setLayoutParams(titleParams);
        taskRow.addView(titleView);

        // 4) Date + Time TextView
        TextView dtView = new TextView(this);
        dtView.setText(formatDateTime(dateMillis, time));
        dtView.setTextSize(14);
        dtView.setTextColor(getResources().getColor(android.R.color.darker_gray));
        taskRow.addView(dtView);

        // 5) Checkbox listener for strike-through
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                titleView.setPaintFlags(
                        titleView.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                titleView.setPaintFlags(
                        titleView.getPaintFlags() & (~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG));
            }
        });

        // 6) Add the row to the container
        taskListContainer.addView(taskRow);
    }

    private String formatDateTime(long dateMillis, String time) {
        // Format date as "Saturday, Apr 26"
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d", Locale.getDefault());
        String dateStr = sdf.format(dateMillis);
        if (time != null && !time.isEmpty()) {
            return dateStr + " • " + time;
        } else {
            return dateStr;
        }
    }
}

