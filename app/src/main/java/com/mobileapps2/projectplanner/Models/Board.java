package com.mobileapps2.projectplanner.Models;

import java.util.ArrayList;

public class Board {
    private Integer Id = null;
    private String Name = null;
    private ArrayList<Task> Tasks = null;

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name){
        this.Name = name;
    }

    public ArrayList<Task> getTasks() {
        return Tasks;
    }
    public void setTasks(ArrayList<Task> tasks){
        this.Tasks = tasks;
    }
}
