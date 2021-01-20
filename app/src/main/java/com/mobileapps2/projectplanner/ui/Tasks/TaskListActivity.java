package com.mobileapps2.projectplanner.ui.Tasks;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileapps2.projectplanner.adapters.BoardListAdapter;
import com.mobileapps2.projectplanner.adapters.TaskListAdapter;
import com.mobileapps2.projectplanner.data.DAOs.TaskDAO;
import com.mobileapps2.projectplanner.data.Entities.Board;
import com.mobileapps2.projectplanner.data.Entities.Task;
import com.mobileapps2.projectplanner.data.Entities.Team;
import com.mobileapps2.projectplanner.ProjectPlannerDb;
import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.data.DAOs.BoardDAO;
import com.mobileapps2.projectplanner.data.DAOs.TeamDAO;
import com.mobileapps2.projectplanner.ui.boards.BoardListActivity;
import com.mobileapps2.projectplanner.ui.boards.EditBoardActivity;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    private static final int REQUEST_ADD_TASK = 1;
    private static final int REQUEST_DELETE_TASK = 2;
    private static final int REQUEST_EDIT_BOARD = 3;
    private ProjectPlannerDb db;
    private TaskDAO taskDAO;
    private BoardDAO boardDAO;
    private ArrayList<Task> taskList = new ArrayList<>();
    private ArrayList<Board> boardList = new ArrayList<>();
    private TextView noTasksLabel;
    private ListView taskListView;
    private TextView boardName;
    private ImageButton createTaskButton;
    private Board board;
    private Task task;
    private String editedBoardName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        initializeDatabase();
        initializeElements();
        addToolbar();
        setListeners();
        getListItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //switch for the requests
        switch (requestCode) {
            case REQUEST_ADD_TASK:
                switch (resultCode) {
                    case RESULT_OK:
                        String addedTaskName = data.getStringExtra("addedTaskName");
                        Toast.makeText(this, addedTaskName + " Added", Toast.LENGTH_SHORT).show();
                        getListItems();
                        break;
                    case RESULT_CANCELED:
                        Toast.makeText(this, "Board Canceled", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case REQUEST_DELETE_TASK:
                switch (resultCode) {
                    case RESULT_OK:
                        String deletedTaskName = data.getStringExtra("deletedTaskName");
                        Toast.makeText(this, deletedTaskName + " Deleted", Toast.LENGTH_SHORT).show();
                        getListItems();
                        break;
                    case RESULT_CANCELED:
                        getListItems();
                        break;
                }
                break;
            case REQUEST_EDIT_BOARD:
                switch (resultCode) {
                    case RESULT_OK:
                        editedBoardName = data.getStringExtra("updatedBoardName");
                        Toast.makeText(this, editedBoardName + " Edited", Toast.LENGTH_SHORT).show();
                        board  = boardDAO.getBoardById(this.board.id);
                        getListItems();
                        break;
                    case RESULT_CANCELED:
                        getListItems();
                        break;
                }
                break;
        }
    }

    private void getListItems() {
        board = boardDAO.getBoardById(this.board.id);
        boardName.setText(board.boardName);
        taskList.clear();
        taskList.addAll(taskDAO.getAllTasksByBoardId(board.boardId));
        if (taskList.size()==0)
        {
            noTasksLabel.setVisibility(View.VISIBLE);
            taskListView.setAdapter(null);
        }
        else
        {
            noTasksLabel.setVisibility(View.INVISIBLE);
            TaskListAdapter adapter = new TaskListAdapter(this, R.layout.task_list_item, taskList);
            taskListView.setAdapter(adapter);
        }
    }

    private void initializeElements() {
        noTasksLabel = findViewById(R.id.noTasksLabel);
        taskListView = findViewById(R.id.TaskList);
        createTaskButton = findViewById(R.id.AddTaskButton);
        boardName = findViewById(R.id.BoardNameTitle);
        Intent incomingIntent = getIntent();
        board = (Board) incomingIntent.getSerializableExtra("board");
        boardName.setText(board.boardName);
    }

    private void setListeners() {
        createTaskButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddTaskActivity.class);
            intent.putExtra("board",board);
            startActivityForResult(intent,REQUEST_ADD_TASK);
        });
        taskListView.setOnItemClickListener(((parent, view, position, id) -> {
            Task task = taskList.get(position);
            Intent intent = new Intent(this,ShowTaskActivity.class);
            intent.putExtra("task",task);
            startActivityForResult(intent, REQUEST_DELETE_TASK);
        }));
    }

    private void initializeDatabase() {
        db = ProjectPlannerDb.getInstance(this);
        taskDAO = db.getTaskDAO();
        boardDAO = db.getBoardDAO();
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
            case R.id.action_Edit:
                Intent intent = new Intent(this, EditBoardActivity.class);
                intent.putExtra("board",board);
                startActivityForResult(intent, REQUEST_EDIT_BOARD);
                return true;
            case R.id.action_Delete:
                AlertDialog alertDialog = new AlertDialog.Builder(TaskListActivity.this).create();
                alertDialog.setTitle("Oh No");
                alertDialog.setMessage("Are you sure you want to delete this board: " + board.boardName + "?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", (dialog, which) -> {
                });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", (dialog, which) -> {
                    db.getBoardDAO().deleteBoard(this.board);
                    Intent intentDelete = new Intent(this, BoardListActivity.class);
                    intentDelete.putExtra("deletedBoardName", this.board.boardName);
                    setResult(RESULT_OK, intentDelete);
                    finish();
                });
                alertDialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}