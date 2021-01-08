package com.mobileapps2.projectplanner.ui.boards;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileapps2.projectplanner.Entities.Board;
import com.mobileapps2.projectplanner.Entities.Team;
import com.mobileapps2.projectplanner.ProjectPlannerDb;
import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.adapters.BoardListAdapter;
import com.mobileapps2.projectplanner.adapters.TeamListAdapter;
import com.mobileapps2.projectplanner.data.DAOs.BoardDAO;
import com.mobileapps2.projectplanner.data.DAOs.TeamDAO;
import com.mobileapps2.projectplanner.ui.teams.AddTeamActivity;

import java.util.ArrayList;

public class BoardListActivity extends AppCompatActivity {

    private final static int REQUEST_ADD_BOARD = 1;
    private final static int REQUEST_DELETE_BOARD = 2;

    private ProjectPlannerDb db;
    private TeamDAO teamDAO;
    private BoardDAO boardDAO;
    private ArrayList<Team> teamList = new ArrayList<>();
    private ArrayList<Board> boardList = new ArrayList<>();
    private TextView noBoardsLabel;
    private ListView boardListView;
    private ImageButton createBoardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        //TODO: GET TEAM FROM PREVIOUS PAGE
        initializeDatabase();
        initializeViewElements();
        addToolbar();
        getListItems();
        setListeners();
    }

    private void getListItems() {
        boardList.clear();
        boardList.addAll(boardDAO.getAllBoards());
        if (boardList.size()==0)
        {
            noBoardsLabel.setVisibility(View.VISIBLE);
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
            startActivityForResult(intent,REQUEST_ADD_BOARD);
        });
    }

    private void initializeViewElements() {
        noBoardsLabel = findViewById(R.id.noBoardsLabel);
        boardListView = findViewById(R.id.BoardsList);
        createBoardButton = findViewById(R.id.AddBoardButton);
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
        }
        return super.onOptionsItemSelected(item);
    }
}