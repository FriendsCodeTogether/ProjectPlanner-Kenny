package com.mobileapps2.projectplanner.Models;

import java.util.ArrayList;

public class User {
    private Integer UserId = null;
    private String UserName = null;
    private String Password = null;
    private ArrayList<Team> Teams = null;

    public Integer getUserId() {
        return UserId;
    }

    public String getUserName() {
        return UserName;
    }
    public void setUserName(String userName){
        this.UserName = userName;
    }

    public String getPassword() {
        return Password;
    }
    public void setPassword(String password){
        this.Password = password;
    }


    public ArrayList<Team> getTeams() {
        return Teams;
    }
    public void setTeams(ArrayList<Team> teams){
        this.Teams = teams;
    }
}
