package com.mobileapps2.projectplanner.ui.boards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobileapps2.projectplanner.data.Entities.Board;
import com.mobileapps2.projectplanner.data.Entities.Team;
import com.mobileapps2.projectplanner.ProjectPlannerDb;
import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.data.DAOs.BoardDAO;

public class AddBoardActivity extends AppCompatActivity {
    private ProjectPlannerDb db;
    private BoardDAO boardDAO;
    private Button saveButton;
    private Button cancelButton;
    private EditText boardNameEditText;
    private EditText boardDescriptionEditText;
    private Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_board);

        initializeDatabase();
        initializeItems();
        addToolbar();
        setListeners();
    }

    private void initializeItems() {
        saveButton = findViewById(R.id.CreateBoardSaveButton);
        cancelButton = findViewById(R.id.CreateBoardCancelButton);
        boardNameEditText = findViewById(R.id.boardNameEditText);
        boardDescriptionEditText = findViewById(R.id.boardDescriptionEditText);
        Intent incomingIntent = getIntent();
        team = (Team) incomingIntent.getSerializableExtra("team");
    }

    private void setListeners() {
        saveButton.setOnClickListener(v->{
            verifyFieldsAndSave();
        });
        cancelButton.setOnClickListener(v->{
            onBackPressed();
        });
    }

    private void verifyFieldsAndSave() {
        String boardName = boardNameEditText.getText().toString();
        String boardDescription = boardDescriptionEditText.getText().toString();

        if (boardDescription.isEmpty()&&boardName.isEmpty()) {
            Toast.makeText(this, "All fields must be filled in", Toast.LENGTH_SHORT);
        }
        else{
            Board newBoard = new Board();
            newBoard.boardName = boardName;
            newBoard.boardDescription = boardDescription;
            newBoard.teamId = team.teamId;
            boardDAO.insertBoard(newBoard);

            Intent intent = new Intent();
            intent.putExtra("addedBoardName", newBoard.boardName);
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
        boardDAO = db.getBoardDAO();
    }
}