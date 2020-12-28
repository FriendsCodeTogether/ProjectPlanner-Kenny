package com.mobileapps2.projectplanner.Models;

import java.util.ArrayList;

public class Team {
    private Integer Id = null;
    private String Name = null;
    private ArrayList<User> Users = null;
    private ArrayList<Board> Boards = null;

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name){
        this.Name = name;
    }

    public ArrayList<User> getUsers() {
        return Users;
    }
    public void setUsers(ArrayList<User> users){
        this.Users = users;
    }

    public ArrayList<Board> getBoards() {
        return Boards;
    }
    public void setBoards(ArrayList<Board> board){
        this.Boards = board;
    }
}
