package com.mobileapps2.projectplanner.ui.Startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.ui.teams.TeamListActivity;

public class StartupActivity extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        initializeItems();
        setEventListeners();
        tryAutologin();
    }

    private void tryAutologin() {
        try {
            String test = pref.getString("loggedInUser","");
            if (!test.isEmpty()){
                Intent intent = new Intent(this,TeamListActivity.class);
                intent.putExtra("userName",pref.getString("loggedInUser",""));
                startActivity(intent);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setEventListeners() {
        loginButton.setOnClickListener((v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }));
        registerButton.setOnClickListener((v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }));
    }

    private void initializeItems() {
        loginButton = findViewById(R.id.LoginButton);
        registerButton = findViewById(R.id.RegisterButton);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
    }
}