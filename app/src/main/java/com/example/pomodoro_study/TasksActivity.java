package com.example.pomodoro_study;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;


//task_layout.xml
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

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_layout);

        etTaskName    = findViewById(R.id.et_task_name);
        etTaskDate    = findViewById(R.id.et_task_date);
        etStartTime   = findViewById(R.id.et_start_time);
        etEndTime     = findViewById(R.id.et_end_time);
        closeButton   = findViewById(R.id.closeButton);
        btnCreateTask = findViewById(R.id.btn_create_task);

        closeButton.setOnClickListener(v -> finish());

        btnCreateTask.setOnClickListener(v -> {
            String name  = etTaskName.getText().toString().trim();
            String date  = etTaskDate.getText().toString().trim();
            String start = etStartTime.getText().toString().trim();
            String end   = etEndTime.getText().toString().trim();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null){
                String uid = user.getUid();

                Map<String, Object> task = new HashMap<>();
                task.put("taskName", name);
                task.put("date", date);
                task.put("startTime", start);
                task.put("endTime", end);

                FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(uid)
                        .collection("tasks")
                        .add(task)
                        .addOnSuccessListener(documentReference -> {
                            Log.d("Firestore","Task added with ID: " + documentReference.getId());
                            Toast.makeText(TasksActivity.this,"Task saved!", Toast.LENGTH_LONG).show();
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Log.e("Firestore", "Error saving task", e);
                            Toast.makeText(TasksActivity.this, "Error saving task", Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}


