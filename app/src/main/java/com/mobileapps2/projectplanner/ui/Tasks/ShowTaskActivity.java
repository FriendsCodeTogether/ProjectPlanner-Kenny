package com.mobileapps2.projectplanner.ui.Tasks;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileapps2.projectplanner.ProjectPlannerDb;
import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.data.DAOs.TaskDAO;
import com.mobileapps2.projectplanner.data.DAOs.TeamDAO;
import com.mobileapps2.projectplanner.data.Entities.Task;
import com.mobileapps2.projectplanner.ui.boards.BoardListActivity;
import com.mobileapps2.projectplanner.ui.boards.EditBoardActivity;

public class ShowTaskActivity extends AppCompatActivity {
    private static final int REQUEST_EDIT_TASK = 2;
    private ProjectPlannerDb db;
    private TaskDAO taskDAO;
    private Task task;
    private TextView taskName;
    private TextView taskDescription;
    private TextView taskStatus;
    private TextView taskSprint;
    private TextView taskEpic;
    private TextView taskProgress;
    private TextView taskBusinessValue;
    private TextView taskStorypoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);

        addToolbar();
        initializeDatabase();
        initializeItems();
        FillInFields();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private void initializeItems() {
        Intent incomingIntent = getIntent();
        task =(Task) incomingIntent.getSerializableExtra("task");
        taskName = findViewById(R.id.TaskNameTextView);
        taskDescription = findViewById(R.id.TaskDescriptionTextView);
        taskEpic = findViewById(R.id.TaskEpicTextView);
        taskProgress = findViewById(R.id.TaskProgressTextView);
        taskSprint = findViewById(R.id.TaskSprintTextView);
        taskStatus = findViewById(R.id.TaskStatusTextView);
        taskBusinessValue = findViewById(R.id.TaskBValueTextView);
        taskStorypoints = findViewById(R.id.TaskStorypointsTextView);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_Edit:
                Intent intent = new Intent(this, EditTaskActivity.class);
                intent.putExtra("task",task);
                startActivityForResult(intent, REQUEST_EDIT_TASK);
                return true;
            case R.id.action_Delete:
                AlertDialog alertDialog = new AlertDialog.Builder(ShowTaskActivity.this).create();
                alertDialog.setTitle("Oh No");
                alertDialog.setMessage("Are you sure you want to delete this task: " + task.taskName + "?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", (dialog, which) -> {
                });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", (dialog, which) -> {
                    db.getTaskDAO().deleteTask(this.task);
                    Intent intentDelete = new Intent(this, TaskListActivity.class);
                    intentDelete.putExtra("deletedTaskName", this.task.taskName);
                    setResult(RESULT_OK, intentDelete);
                    finish();
                });
                alertDialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //switch for the requests
        switch (requestCode) {
            case REQUEST_EDIT_TASK:
                switch (resultCode) {
                    case RESULT_OK:
                        String editedTaskName = data.getStringExtra("editedTaskName");
                        Toast.makeText(this, editedTaskName + " Edited", Toast.LENGTH_SHORT).show();
                        task  = taskDAO.getTaskById(this.task.taskId);
                        FillInFields();
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
        }
    }

    private void FillInFields() {
        taskName.setText(task.taskName);
        taskDescription.setText(task.description);
        taskEpic.setText(task.epic);
        taskStatus.setText(task.status);
        taskProgress.setText(task.progress+"");
        taskSprint.setText(task.sprint+"");
        taskBusinessValue.setText(task.businessValue+"");
        taskStorypoints.setText(task.storyPoints+"");
    }

    private void initializeDatabase() {
        db = ProjectPlannerDb.getInstance(this);
        taskDAO = db.getTaskDAO();
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}