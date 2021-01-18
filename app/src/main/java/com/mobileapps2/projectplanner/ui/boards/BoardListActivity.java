package com.mobileapps2.projectplanner.ui.boards;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileapps2.projectplanner.Entities.Board;
import com.mobileapps2.projectplanner.Entities.Team;
import com.mobileapps2.projectplanner.ProjectPlannerDb;
import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.adapters.BoardListAdapter;
import com.mobileapps2.projectplanner.data.DAOs.BoardDAO;
import com.mobileapps2.projectplanner.data.DAOs.TeamDAO;
import com.mobileapps2.projectplanner.ui.Tasks.TaskListActivity;
import com.mobileapps2.projectplanner.ui.teams.EditTeamActivity;
import com.mobileapps2.projectplanner.ui.teams.TeamListActivity;

import java.util.ArrayList;

public class BoardListActivity extends AppCompatActivity {

    private final static int REQUEST_ADD_BOARD = 1;
    private final static int REQUEST_DELETE_BOARD = 2;
    private static final int REQUEST_EDIT_TEAM = 3;

    private ProjectPlannerDb db;
    private TeamDAO teamDAO;
    private BoardDAO boardDAO;
    private ArrayList<Team> teamList = new ArrayList<>();
    private ArrayList<Board> boardList = new ArrayList<>();
    private TextView noBoardsLabel;
    private ListView boardListView;
    private TextView teamName;
    private ImageButton createBoardButton;
    private Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        initializeDatabase();
        initializeElements();
        addToolbar();
        getListItems();
        setListeners();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void getListItems() {
        Team team = teamDAO.getTeamById(this.team.id);
        teamName.setText(team.teamName);
        boardList.clear();
        boardList.addAll(boardDAO.getAllBoardsForTeam(team.teamId));
        if (boardList.size()==0)
        {
            noBoardsLabel.setVisibility(View.VISIBLE);
            boardListView.setAdapter(null);
        }
        else
        {
            noBoardsLabel.setVisibility(View.INVISIBLE);
            BoardListAdapter adapter = new BoardListAdapter(this, R.layout.board_list_item, boardList);
            boardListView.setAdapter(adapter);
        }
    }

    private void setListeners() {
        createBoardButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddBoardActivity.class);
            intent.putExtra("team",team);
            startActivityForResult(intent,REQUEST_ADD_BOARD);
        });
        boardListView.setOnItemClickListener((parent, view, position, id) -> {
            Board board = boardList.get(position);
            Intent intent = new Intent(this, TaskListActivity.class);
            intent.putExtra("board",board);
            startActivityForResult(intent,REQUEST_DELETE_BOARD);
        });

    }

    private void initializeElements() {
        noBoardsLabel = findViewById(R.id.noBoardsLabel);
        boardListView = findViewById(R.id.BoardsList);
        createBoardButton = findViewById(R.id.AddBoardButton);
        teamName = findViewById(R.id.TeamName);
        Intent incomingIntent = getIntent();
        team = (Team) incomingIntent.getSerializableExtra("team");
        teamName.setText(team.teamName);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //switch for the requests
        switch (requestCode) {
            case REQUEST_ADD_BOARD:
                //switch for the results from add board
                switch (resultCode) {
                    case RESULT_OK:
                        String addedBoardName = data.getStringExtra("addedBoardName");
                        Toast.makeText(this, addedBoardName + " Added", Toast.LENGTH_SHORT).show();
                        getListItems();
                        break;
                    case RESULT_CANCELED:
                        Toast.makeText(this, "Board Canceled", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case REQUEST_DELETE_BOARD:
                switch (resultCode) {
                    case RESULT_OK:
                        String deletedBoardName = data.getStringExtra("deletedBoardName");
                        Toast.makeText(this, deletedBoardName + " Deleted", Toast.LENGTH_SHORT).show();
                        getListItems();
                        break;
                    case RESULT_CANCELED:
                        getListItems();
                        break;
                }
                break;
        case REQUEST_EDIT_TEAM:
                switch (resultCode) {
                    case RESULT_OK:
                        String editedTeamName = data.getStringExtra("updatedTeamName");
                        Toast.makeText(this, editedTeamName + " Edited", Toast.LENGTH_SHORT).show();
                        getListItems();
                        break;
                    case RESULT_CANCELED:
                        getListItems();
                        break;
                }
                break;
        }
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
                Intent intent = new Intent(this, EditTeamActivity.class);
                intent.putExtra("team",team);
                startActivityForResult(intent, REQUEST_EDIT_TEAM);
                return true;
            case R.id.action_Delete:
                AlertDialog alertDialog = new AlertDialog.Builder(BoardListActivity.this).create();
                alertDialog.setTitle("Oh No");
                alertDialog.setMessage("Are you sure you want to delete this team: " + team.teamName + "?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", (dialog, which) -> {
                });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", (dialog, which) -> {
                    db.getTeamDAO().deleteTeam(this.team);
                    Intent intentDelete = new Intent(this, TeamListActivity.class);
                    intentDelete.putExtra("deletedTeamName", this.team.teamName);
                    setResult(RESULT_OK, intentDelete);
                    finish();
                });
                alertDialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}