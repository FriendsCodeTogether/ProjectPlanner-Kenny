package com.mobileapps2.projectplanner.ui.teams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobileapps2.projectplanner.data.Entities.Team;
import com.mobileapps2.projectplanner.ProjectPlannerDb;
import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.data.DAOs.TeamDAO;

import java.util.ArrayList;

public class EditTeamActivity extends AppCompatActivity {
    private ProjectPlannerDb db;
    private TeamDAO teamDAO;
    private Button saveButton;
    private Button cancelButton;
    private EditText teamNameEditText;
    private ArrayList<Team> teamList = new ArrayList<>();
    private Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        initializeDatabase();
        initializeItems();
        addToolbar();
        setListeners();
    }

    private void initializeItems() {
        saveButton = findViewById(R.id.EditTeamSaveButton);
        cancelButton = findViewById(R.id.EditTeamCancelButton);
        teamNameEditText = findViewById(R.id.EditTeamNameEditText);
        Intent incommingIntent = getIntent();
        team = (Team)incommingIntent.getSerializableExtra("team");
        fillInCurrentFields();
    }
    private void fillInCurrentFields() {
        teamNameEditText.setText(team.teamName);
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
        String teamName = teamNameEditText.getText().toString();
        teamList.addAll(teamDAO.getAllTeams());

        if (teamName.isEmpty()) {
            Toast.makeText(this, "Fill in team name", Toast.LENGTH_SHORT).show();
        }
        else if(verifyIfUnique(teamName) == false){
            Toast.makeText(this, teamName + " already exists", Toast.LENGTH_SHORT).show();
        }
        else{
            team.teamName = teamName;
            teamDAO.updateTeam(team);
            Intent intent = new Intent();
            intent.putExtra("updatedTeamName", team.teamName);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private boolean verifyIfUnique(String name) {
        for (Team team : teamList) {
            if (team.teamName.equals(name)) return false;
        }
        return true;
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
        teamDAO = db.getTeamDAO();
    }
}