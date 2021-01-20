package com.mobileapps2.projectplanner.ui.Tasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobileapps2.projectplanner.data.Entities.Board;
import com.mobileapps2.projectplanner.data.Entities.Task;
import com.mobileapps2.projectplanner.ProjectPlannerDb;
import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.data.DAOs.TaskDAO;
import com.shawnlin.numberpicker.NumberPicker;

public class EditTaskActivity extends AppCompatActivity {
    private ProjectPlannerDb db;
    private TaskDAO taskDAO;
    private Button saveButton;
    private Button cancelButton;
    private Task task;
    private EditText taskNameEditText;
    private EditText taskDescriptionEditText;
    private EditText taskEpicEditText;
    private EditText taskStatusEditText;
    private EditText taskProgressEditText;
    private EditText taskSprintEditText;
    private EditText taskBusinessValueEditText;
    private EditText taskStorypointEditText;
    private int taskProgress;
    private int taskSprintNumber;
    private String taskStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        initializeDatabase();
        initializeItems();
        addToolbar();
        setListeners();

    }

    private void fillInFields() {
        taskNameEditText.setText(task.taskName);
        taskDescriptionEditText.setText(task.description);
        taskEpicEditText.setText(task.epic);
        taskStatusEditText.setText(task.status);
        taskProgressEditText.setText(task.progress+" %");
        taskSprintEditText.setText(task.sprint+"");
        taskBusinessValueEditText.setText(task.businessValue+"");
        taskStorypointEditText.setText(task.storyPoints+"");
    }


    private void initializeItems() {
        saveButton = findViewById(R.id.EditTaskSaveButton);
        cancelButton = findViewById(R.id.EditTaskCancelButton);
        taskNameEditText = findViewById(R.id.EditTaskNameEditText);
        taskDescriptionEditText = findViewById(R.id.EditTaskDescriptionEditText);
        taskEpicEditText = findViewById(R.id.EditTaskEpicEditText);
        taskStatusEditText = findViewById(R.id.EditTaskStatusEditText);
        taskProgressEditText = findViewById(R.id.EditTaskProgressEditText);
        taskSprintEditText = findViewById(R.id.EditTaskSprintEditText);
        taskBusinessValueEditText = findViewById(R.id.EditTaskBusinessValueEditText);
        taskStorypointEditText = findViewById(R.id.EditTaskStorypointEditText);
        Intent incomingIntent = getIntent();
        task = (Task) incomingIntent.getSerializableExtra("task");
        fillInFields();
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
        final ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.view_single_number_dialog, null);
        NumberPicker numberPicker = constraintLayout.findViewById(R.id.NumberPicker);
        String[] storypointValues = new String[]{"1","2","3","5","8","13","21"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(storypointValues.length);
        numberPicker.setDisplayedValues(storypointValues);

        final AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle("StoryPoints")
                .setPositiveButton("Submit", null)
                .setNegativeButton("Cancel", null)
                .setView(constraintLayout)
                .setCancelable(false)
                .create();
        builder.show();

        builder.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view -> {
            int pickerValue = numberPicker.getValue();
            taskStorypointEditText.setText(storypointValues[pickerValue-1]);
            builder.dismiss();
        });
    }

    private void onBusinessValueEditTextClick() {
        final ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.view_single_number_dialog, null);
        NumberPicker numberPicker = constraintLayout.findViewById(R.id.NumberPicker);
        String[] BusinessValues = new String[]{"1","2","3","5","8","13","21"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(BusinessValues.length);
        numberPicker.setDisplayedValues(BusinessValues);

        final AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle("BusinessValue")
                .setPositiveButton("Submit", null)
                .setNegativeButton("Cancel", null)
                .setView(constraintLayout)
                .setCancelable(false)
                .create();
        builder.show();

        builder.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view -> {
            int pickerValue = numberPicker.getValue();
            taskBusinessValueEditText.setText(BusinessValues[pickerValue-1]);
            builder.dismiss();
        });
    }

    private void onSprintEditTextClick() {
        final ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.view_single_number_dialog, null);
        NumberPicker numberPicker = constraintLayout.findViewById(R.id.NumberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(50);
        numberPicker.setValue(0);

        final AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle("Sprint numbere")
                .setPositiveButton("Submit", null)
                .setNegativeButton("Cancel", null)
                .setView(constraintLayout)
                .setCancelable(false)
                .create();
        builder.show();

        builder.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view -> {
            taskSprintNumber = numberPicker.getValue();
            taskSprintEditText.setText(taskSprintNumber+" ");
            builder.dismiss();
        });
    }

    private void onProgressEditTextClick() {
        final ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.view_single_number_dialog, null);
        NumberPicker numberPicker = constraintLayout.findViewById(R.id.NumberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(100);
        numberPicker.setValue(0);

        final AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle("Total Progress made")
                .setPositiveButton("Submit", null)
                .setNegativeButton("Cancel", null)
                .setView(constraintLayout)
                .setCancelable(false)
                .create();
        builder.show();

        builder.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view -> {
            taskProgress = numberPicker.getValue();
            taskProgressEditText.setText(taskProgress + "%");
            builder.dismiss();
        });
    }

    private void onStatusEditTextClick() {
        final ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.view_single_number_dialog, null);
        NumberPicker numberPicker = constraintLayout.findViewById(R.id.NumberPicker);
        String[] statusValues = new String[]{"Product Backlog","Sprint Backlog","Busy","Done","Buggs & Defects"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(statusValues.length);
        numberPicker.setDisplayedValues(statusValues);

        final AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle("Statussen")
                .setPositiveButton("Submit", null)
                .setNegativeButton("Cancel", null)
                .setView(constraintLayout)
                .setCancelable(false)
                .create();
        builder.show();

        builder.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view -> {
            int pickerValue = numberPicker.getValue();
            taskStatus = statusValues[pickerValue-1];
            taskStatusEditText.setText(taskStatus);
            builder.dismiss();
        });
    }

    private void verifyFieldsAndSave() {
        String taskName = taskNameEditText.getText().toString();
        String taskDescription = taskDescriptionEditText.getText().toString();
        String taskEpic = taskEpicEditText.getText().toString();
        String taskStatus = taskStatusEditText.getText().toString();
        String taskProgress = taskProgressEditText.getText().toString();
        String taskSprint = taskSprintEditText.getText().toString();
        String taskBusinessValue = taskBusinessValueEditText.getText().toString();
        String taskStorypoints = taskStorypointEditText.getText().toString();
        if (taskName.isEmpty()||taskDescription.isEmpty()||taskEpic.isEmpty()||taskStatus.isEmpty()||taskProgress.isEmpty()||taskSprint.isEmpty()||taskBusinessValue.isEmpty()||taskStorypoints.isEmpty()){//TODO:CHECK IF FIELDS ARE FILLED IN
            Toast.makeText(this, "All fields must be filled in", Toast.LENGTH_SHORT);
        }
        else{
            task.taskName = taskName;
            task.description = taskDescription;
            task.epic = taskEpic;
            task.status = taskStatus;
            task.progress = this.taskProgress;
            task.sprint = taskSprintNumber;
            task.businessValue = Integer.parseInt(taskBusinessValue);
            task.storyPoints = Integer.parseInt(taskStorypoints);
            taskDAO.updateTask(task);
            Intent intent = new Intent();
            intent.putExtra("editedTaskName",task.taskName);
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