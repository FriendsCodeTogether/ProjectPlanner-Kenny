package com.mobileapps2.projectplanner.ui.Startup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mobileapps2.projectplanner.Entities.User;
import com.mobileapps2.projectplanner.ProjectPlannerDb;
import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.data.DAOs.UserDAO;
import com.mobileapps2.projectplanner.ui.teams.TeamListActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;
    private ProjectPlannerDb db;
    private UserDAO userDAO;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText verifyPasswordEditText;
    private EditText emailEditText;
    private ArrayList<User>userList = new ArrayList<>();
    private Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
    private Pattern passwordPattern = Pattern.compile("^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeDatabase();
        initializeViewElements();
        setListeners();
    }

    private void setListeners() {
        registerButton.setOnClickListener(v->{
            try {
                verifyFieldsAndSave();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        loginButton.setOnClickListener(v->{
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        });
    }

    private void initializeViewElements() {
        loginButton = findViewById(R.id.RegisterPageLoginButton);
        registerButton = findViewById(R.id.RegisterPageRegisterButton);
        userNameEditText = findViewById(R.id.RegisterNameEditText);
        emailEditText = findViewById(R.id.EmailAddressEditText);
        passwordEditText = findViewById(R.id.PasswordEditText);
        verifyPasswordEditText = findViewById(R.id.VerifyPasswordEditText);
    }

    private void verifyFieldsAndSave() throws NoSuchProviderException, NoSuchAlgorithmException {
        String userName = userNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String errors = VerifyPassword(password);
        userList.addAll(userDAO.getAllUsers());

        if (userName.isEmpty() && password.isEmpty() && email.isEmpty()) {
            Toast.makeText(this, "All fields must be filled in", Toast.LENGTH_SHORT).show();
        }
        else if (!emailPattern.matcher(email).matches()){
            Toast.makeText(this, "This is not a valid E-mail",Toast.LENGTH_SHORT).show();
        }
        else if(verifyIfUnique(userName) == false){
            Toast.makeText(this,"this username already exists", Toast.LENGTH_SHORT ).show();
        }
        else if (errors!=""){
            new AlertDialog.Builder(this).setTitle("Password Incorrect").setMessage(errors).setPositiveButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert).show();
        }
        else{
            User newUser = new User();
            byte[] salt = getSalt();
            String securePassword = getSecurePassword(password, salt);

            newUser.userName = userName;
            newUser.email = email;
            newUser.password = securePassword;
            userDAO.insertUser(newUser);

            Intent intent = new Intent(this, TeamListActivity.class);
            intent.putExtra("userName",newUser.userName);
            //TODO: Store last logged in user => maybe using Preferences?
            startActivity(intent);
        }
    }

    private String VerifyPassword(String password) {
        String verifyPassword = verifyPasswordEditText.getText().toString();
          if (!password.equals(verifyPassword)) {
            return "Passwords are not the same";
        } else if (passwordPattern.matcher(password).matches())
            return "The password must contain: Atleast 8 characters, 1 number, 1 uppercase and lowercase, 1 special character";
        return "";
    }

    private boolean verifyIfUnique(String name) {
        for (User user : userList) {
            if (user.userName.equals(name)) return false;
        }
        return true;
    }

    private void initializeDatabase() {
        db = ProjectPlannerDb.getInstance(this);
        userDAO = db.getUserDAO();
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchProviderException {
        //Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        //Create array for salt
        byte[] salt = new byte[16];
        //Get a random salt
        sr.nextBytes(salt);
        //return salt
        return salt;
    }

    private static String getSecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(salt);
            //Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}