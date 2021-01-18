package com.mobileapps2.projectplanner.ui.boards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobileapps2.projectplanner.Entities.Board;
import com.mobileapps2.projectplanner.Entities.Team;
import com.mobileapps2.projectplanner.ProjectPlannerDb;
import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.data.DAOs.BoardDAO;
import com.mobileapps2.projectplanner.data.DAOs.TeamDAO;
import com.mobileapps2.projectplanner.data.DAOs.UserDAO;

import java.util.ArrayList;

public class EditBoardActivity extends AppCompatActivity {
    private ProjectPlannerDb db;
    private BoardDAO boardDAO;
    private Button saveButton;
    private Button cancelButton;
    private EditText boardNameEditText;
    private EditText boardDescriptionEditText;
    private ArrayList<Board> boardList = new ArrayList<>();
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_board);

        initializeDatabase();
        initializeItems();
        addToolbar();
        setListeners();
    }

    private void initializeItems() {
        saveButton = findViewById(R.id.EditBoardSaveButton);
        cancelButton = findViewById(R.id.EditBoardCancelButton);
        boardNameEditText = findViewById(R.id.EditBoardNameEditText);
        boardDescriptionEditText = findViewById(R.id.EditBoardDescriptionEditText);
        Intent incommingIntent = getIntent();
        board = (Board) incommingIntent.getSerializableExtra("board");
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

        boardList.addAll(boardDAO.getAllBoards());

        if (boardDescription.isEmpty()&&boardName.isEmpty()) {
            Toast.makeText(this, "All fields must be filled in", Toast.LENGTH_SHORT);
        }
        else{

            board.boardName = boardName;
            board.boardDescription = boardDescription;
            boardDAO.updateBoard(board);

            Intent intent = new Intent();
            intent.putExtra("updatedBoardName", board.boardName);
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