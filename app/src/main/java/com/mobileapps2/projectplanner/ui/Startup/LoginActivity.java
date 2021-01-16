package com.mobileapps2.projectplanner.ui.Startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobileapps2.projectplanner.Entities.User;
import com.mobileapps2.projectplanner.ProjectPlannerDb;
import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.data.DAOs.UserDAO;
import com.mobileapps2.projectplanner.ui.MyGlobals;
import com.mobileapps2.projectplanner.ui.teams.TeamListActivity;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;
    private EditText passwordEditText;
    private EditText userNameEditText;
    private ProjectPlannerDb db;
    private UserDAO userDAO;
    private MyGlobals myGlobals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeDatabase();
        initializeElements();
        setListeners();
    }
    private void setListeners() {
        registerButton.setOnClickListener(v->{
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
        });
        loginButton.setOnClickListener(v->{
            VerifyFieldsAndLogin();
        });
    }

    private void VerifyFieldsAndLogin() {
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String hashedPassword = myGlobals.getSecurePassword(password);
        User user = userDAO.getUserByUserName(userName);

        if (user == null){
            Toast.makeText(this, "This username does not exist", Toast.LENGTH_SHORT).show();
        }
        else if(!user.password.equals(hashedPassword)){
            Toast.makeText(this, "This password is incorrect", Toast.LENGTH_SHORT).show();
        }
        else{
            //TODO: USER LOGGED IN PREFERENCE
            Intent intent = new Intent(this, TeamListActivity.class);
            intent.putExtra("userName",user.userName);
            startActivity(intent);
        }
    }

    private void initializeElements() {
        loginButton = findViewById(R.id.LoginPageLoginButton);
        registerButton = findViewById(R.id.LoginPageRegisterButton);
        passwordEditText = findViewById(R.id.LoginPasswordEditText);
        userNameEditText = findViewById(R.id.LoginNameEditText);
        myGlobals = new MyGlobals(getApplicationContext());
    }

    private void initializeDatabase() {
        db = ProjectPlannerDb.getInstance(this);
        userDAO = db.getUserDAO();
    }
}