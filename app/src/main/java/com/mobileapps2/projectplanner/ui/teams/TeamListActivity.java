package com.mobileapps2.projectplanner.ui.teams;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileapps2.projectplanner.data.Entities.Team;
import com.mobileapps2.projectplanner.data.Entities.User;
import com.mobileapps2.projectplanner.ProjectPlannerDb;
import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.adapters.TeamListAdapter;
import com.mobileapps2.projectplanner.data.DAOs.TeamDAO;
import com.mobileapps2.projectplanner.data.DAOs.UserDAO;
import com.mobileapps2.projectplanner.ui.Startup.StartupActivity;
import com.mobileapps2.projectplanner.ui.boards.BoardListActivity;

import java.util.ArrayList;

public class TeamListActivity extends AppCompatActivity {
    private final static int REQUEST_ADD_TEAM = 1;
    private final static int REQUEST_DELETE_TEAM = 2;
    private User user;
    private ProjectPlannerDb db;
    private TeamDAO teamDAO;
    private UserDAO userDAO;
    private ArrayList<Team> teamList = new ArrayList<>();
    private TextView noTeamsLabel;
    private ListView teamListView;
    private ImageButton createTeamButton;
    private Button logoutButton;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        initializeDatabase();
        initializeElements();
        addToolbar();
        getListItems();
        setListeners();
    }

    private void setListeners() {
        createTeamButton.setOnClickListener(v -> {
            Intent intent = new Intent(this,AddTeamActivity.class);
            intent.putExtra("user",user);
            startActivityForResult(intent,REQUEST_ADD_TEAM);
        });
        teamListView.setOnItemClickListener((parent, view, position, id) -> {
            Team team = teamList.get(position);
            Intent intent = new Intent(this, BoardListActivity.class);
            intent.putExtra("team", team);
            startActivityForResult(intent,REQUEST_DELETE_TEAM);
        });
        logoutButton.setOnClickListener(v->{
            pref.edit().clear().commit();
            Intent intent = new Intent(this, StartupActivity.class);
            startActivity(intent);
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

    private void initializeElements() {
        noTeamsLabel = findViewById(R.id.noTeamsLabel);
        teamListView = findViewById(R.id.TeamList);
        createTeamButton = findViewById(R.id.AddTeamButton);
        logoutButton = findViewById(R.id.LogoutButton);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String test = pref.getString("loggedInUser","");
        user = userDAO.getUserByUserName(test);
    }

    private void getListItems() {
        teamList.clear();
        try {
            teamList.addAll(teamDAO.getAllMyTeams(user.userId));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if (teamList.size()==0)
        {
            noTeamsLabel.setVisibility(View.VISIBLE);
            teamListView.setAdapter(null);
        }
        else
        {
            noTeamsLabel.setVisibility(View.INVISIBLE);
            TeamListAdapter adapter = new TeamListAdapter(this, R.layout.team_list_item, teamList);
            teamListView.setAdapter(adapter);
        }
    }

    private void initializeDatabase() {
        db = ProjectPlannerDb.getInstance(this);
        teamDAO = db.getTeamDAO();
        userDAO = db.getUserDAO();
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