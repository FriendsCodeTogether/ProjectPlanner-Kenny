package com.mobileapps2.projectplanner.ui.teams;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileapps2.projectplanner.Entities.Team;
import com.mobileapps2.projectplanner.ProjectPlannerDb;
import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.adapters.TeamListAdapter;
import com.mobileapps2.projectplanner.data.DAOs.TeamDAO;
import com.mobileapps2.projectplanner.data.DAOs.TeamDAO_Impl;

import java.util.ArrayList;
import java.util.List;

public class TeamListActivity extends AppCompatActivity {
    private final static int REQUEST_ADD_TEAM = 1;
    private final static int REQUEST_DELETE_TEAM = 2;

    private ProjectPlannerDb db;
    private TeamDAO teamDAO;
    private List<Team> teamList;
    private TextView noTeamsLabel;
    private ListView teamListView;
    private ImageButton createTeamButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        //TODO: check if logged in
        initializeDatabase();
        initializeViewElements();
        addToolbar();
        getListItems();
        setListeners();
    }

    private void setListeners() {
        createTeamButton.setOnClickListener(v -> {
            Intent intent = new Intent(this,AddTeamActivity.class);
            startActivityForResult(intent,REQUEST_ADD_TEAM);
        });
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

    private void initializeViewElements() {
        noTeamsLabel = findViewById(R.id.noTeamsLabel);
        teamListView = findViewById(R.id.TeamList);
        createTeamButton = findViewById(R.id.AddTeamButton);
    }

    private void getListItems() {
        teamList = new ArrayList<>();
        teamList = teamDAO.getAllTeams();
        if (teamList.size()==0)
        {
            noTeamsLabel.setVisibility(View.VISIBLE);
        }
        else
        {
            TeamListAdapter adapter = new TeamListAdapter(this, R.layout.team_list_item, (ArrayList<Team>) teamList);
            teamListView.setAdapter(adapter);
        }
    }

    private void initializeDatabase() {
        db = ProjectPlannerDb.getInstance(this);
        teamDAO = db.getTeamDAO();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //switch for the requests
        switch (requestCode) {
            case REQUEST_ADD_TEAM:
                //switch for the results from add team
                switch (resultCode) {
                    case RESULT_OK:
                        String addedTeamName = data.getStringExtra("addedTeamName");
                        Toast.makeText(this, addedTeamName + " Added", Toast.LENGTH_SHORT).show();
                        getListItems();
                        break;
                    case RESULT_CANCELED:
                        Toast.makeText(this, "Team Canceled", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case REQUEST_DELETE_TEAM:
                switch (resultCode){
                    case RESULT_OK:
                        String deletedTeamName = data.getStringExtra("deletedTeamName");
                        Toast.makeText(this,deletedTeamName+ " Deleted",Toast.LENGTH_SHORT).show();
                        getListItems();
                        break;
                    case RESULT_CANCELED:
                        getListItems();
                        break;
                }
                break;
        }
    }
}