package com.mobileapps2.projectplanner.ui.Tasks;

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

import com.mobileapps2.projectplanner.Entities.Board;
import com.mobileapps2.projectplanner.Entities.Team;
import com.mobileapps2.projectplanner.ProjectPlannerDb;
import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.data.DAOs.BoardDAO;
import com.mobileapps2.projectplanner.data.DAOs.TeamDAO;
import com.mobileapps2.projectplanner.ui.boards.AddBoardActivity;
import com.mobileapps2.projectplanner.ui.boards.BoardListActivity;
import com.mobileapps2.projectplanner.ui.teams.EditTeamActivity;
import com.mobileapps2.projectplanner.ui.teams.TeamListActivity;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    private static final int REQUEST_ADD_TASK = 1;
    private static final int REQUEST_DELETE_TASK = 2;
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
            Intent intent = new Intent(this, AddBoardActivity.class);
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
                return true;
            case R.id.action_Delete:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}