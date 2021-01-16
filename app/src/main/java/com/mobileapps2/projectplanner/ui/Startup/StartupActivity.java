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
    private SharedPreferences.Editor editor;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        loginButton = findViewById(R.id.LoginButton);
        registerButton = findViewById(R.id.RegisterButton);

        loginButton.setOnClickListener((v -> {
            // TODO: Remove next comment before presentation
            // Intent intent = new Intent(this, TeamListActivity.class);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }));
        registerButton.setOnClickListener((v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }));

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        editor.putString("loggedInUser", "init");
        editor.commit();
    }
}