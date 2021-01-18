package com.mobileapps2.projectplanner.ui.Tasks;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileapps2.projectplanner.Entities.Board;
import com.mobileapps2.projectplanner.Entities.Team;
import com.mobileapps2.projectplanner.ProjectPlannerDb;
import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.data.DAOs.BoardDAO;
import com.mobileapps2.projectplanner.data.DAOs.TeamDAO;
import com.mobileapps2.projectplanner.ui.boards.AddBoardActivity;
import com.mobileapps2.projectplanner.ui.boards.BoardListActivity;
import com.mobileapps2.projectplanner.ui.boards.EditBoardActivity;
import com.mobileapps2.projectplanner.ui.teams.EditTeamActivity;
import com.mobileapps2.projectplanner.ui.teams.TeamListActivity;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    private static final int REQUEST_ADD_TASK = 1;
    private static final int REQUEST_DELETE_TASK = 2;
    private static final int REQUEST_EDIT_BOARD = 3;
    private ProjectPlannerDb db;
    private TeamDAO teamDAO;
    private BoardDAO boardDAO;
    private ArrayList<Team> teamList = new ArrayList<>();
    private ArrayList<Board> boardList = new ArrayList<>();
    private TextView noBoardsLabel;
    private ListView boardListView;
    private TextView boardName;
    private ImageButton createTaskButton;
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        initializeDatabase();
        initializeElements();
        addToolbar();
        setListeners();
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
                break;
            case REQUEST_DELETE_TASK:
                switch (resultCode) {
                    case RESULT_OK:
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
            case REQUEST_EDIT_BOARD:
                switch (resultCode) {
                    case RESULT_OK:
                        String editedBoardName = data.getStringExtra("updatedBoardName");
                        Toast.makeText(this, editedBoardName + " Edited", Toast.LENGTH_SHORT).show();
                        boardName.setText(editedBoardName);
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
        }
    }
    private void initializeElements() {
        noBoardsLabel = findViewById(R.id.noBoardsLabel);
        boardListView = findViewById(R.id.BoardsList);
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
    }

    private void initializeDatabase() {
        db = ProjectPlannerDb.getInstance(this);
        teamDAO = db.getTeamDAO();
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