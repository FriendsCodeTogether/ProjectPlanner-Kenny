package com.mobileapps2.projectplanner.ui.Tasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobileapps2.projectplanner.Entities.Board;
import com.mobileapps2.projectplanner.Entities.Task;
import com.mobileapps2.projectplanner.Entities.Team;
import com.mobileapps2.projectplanner.ProjectPlannerDb;
import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.data.DAOs.TaskDAO;
import com.shawnlin.numberpicker.NumberPicker;

public class AddTaskActivity extends AppCompatActivity {
    private ProjectPlannerDb db;
    private TaskDAO taskDAO;
    private Button saveButton;
    private Button cancelButton;
    private Board board;
    private EditText taskNameEditText;
    private EditText taskDescriptionEditText;
    private EditText taskEpicEditText;
    private EditText taskStatusEditText;
    private EditText taskProgressEditText;
    private EditText taskSprintEditText;
    private EditText taskBusinessValueEditText;
    private EditText taskStorypointEditText;
    private int taskProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initializeDatabase();
        initializeItems();
        addToolbar();
        setListeners();
    }
    private void initializeItems() {
        saveButton = findViewById(R.id.CreateTaskSaveButton);
        cancelButton = findViewById(R.id.CreateTaskCancelButton);
        taskNameEditText = findViewById(R.id.TaskNameEditText);
        taskDescriptionEditText = findViewById(R.id.TaskDescriptionEditText);
        taskEpicEditText = findViewById(R.id.TaskEpicEditText);
        taskStatusEditText = findViewById(R.id.TaskStatusEditText);
        taskProgressEditText = findViewById(R.id.TaskProgressEditText);
        taskSprintEditText = findViewById(R.id.TaskSprintEditText);
        taskBusinessValueEditText = findViewById(R.id.TaskBusinessValueEditText);
        taskStorypointEditText = findViewById(R.id.TaskStorypointEditText);
        Intent incomingIntent = getIntent();
        board = (Board) incomingIntent.getSerializableExtra("board");
    }

    private void setListeners() {
        saveButton.setOnClickListener(v->{
            verifyFieldsAndSave();
        });
        cancelButton.setOnClickListener(v->{
            onBackPressed();
        });
        taskStatusEditText.setOnClickListener(v->onStatusEditTextClick());
        taskProgressEditText.setOnClickListener(v->onProgressEditTextClick());
        taskSprintEditText.setOnClickListener(v->onSprintEditTextClick());
        taskBusinessValueEditText.setOnClickListener(v->onBusinessValueEditTextClick());
        taskStorypointEditText.setOnClickListener(v->onStorypointEditTextClick());
    }

    private void onStorypointEditTextClick() {
    }

    private void onBusinessValueEditTextClick() {
    }

    private void onSprintEditTextClick() {
    }

    private void onProgressEditTextClick() {
        final ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.view_single_number_dialog, null);
        NumberPicker numberPicker = constraintLayout.findViewById(R.id.NumberPicker);

        numberPicker.setValue(0);

        final AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle("Total Progress made")
                .setPositiveButton("Submit", null)
                .setNegativeButton("Cancel", null)
                .setView(constraintLayout)
                .setCancelable(false)
                .create();
        builder.show();

        builder.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener((View.OnClickListener) view -> {
            taskProgress = numberPicker.getValue();
            taskProgressEditText.setText(taskProgress + "%");
            builder.dismiss();
        });
    }

    private void onStatusEditTextClick() {
    }

    private void verifyFieldsAndSave() {

        if (board==null){//TODO:CHECK IF FIELDS ARE FILLED IN
            Toast.makeText(this, "All fields must be filled in", Toast.LENGTH_SHORT);
        }
        else{
            //TODO: FILL IN TASK
            Task newTask = new Task();
            Intent intent = new Intent();
            intent.putExtra("addedTaskName",newTask.taskName);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeDatabase() {
        db = ProjectPlannerDb.getInstance(this);
        taskDAO = db.getTaskDAO();
    }
}