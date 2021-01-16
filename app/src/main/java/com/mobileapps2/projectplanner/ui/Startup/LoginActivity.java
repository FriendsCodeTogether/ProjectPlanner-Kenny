package com.mobileapps2.projectplanner.ui.Startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.mobileapps2.projectplanner.Entities.User;
import com.mobileapps2.projectplanner.ProjectPlannerDb;
import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.data.DAOs.UserDAO;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;
    private EditText passwordEditText;
    private EditText userNameEditText;
    private ProjectPlannerDb db;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeDatabase();
        initializeViewElements();
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
        User user = userDAO.getUserByUserName(userName);
    }

    private void initializeViewElements() {
        loginButton = findViewById(R.id.LoginPageLoginButton);
        registerButton = findViewById(R.id.LoginPageRegisterButton);
        passwordEditText = findViewById(R.id.LoginPasswordEditText);
        userNameEditText = findViewById(R.id.LoginNameEditText);
    }

    private void initializeDatabase() {
        db = ProjectPlannerDb.getInstance(this);
        userDAO = db.getUserDAO();
    }
}