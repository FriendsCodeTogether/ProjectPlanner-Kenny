package com.mobileapps2.projectplanner.Models;

public class Task {
    private Integer Id = null;
    private String Name= null;
    private String Description = null;
    private String Epic = null;
    private String Status = null;
    private Integer SprintNumber = null;
    private Integer BusinessValue = null;
    private Integer Storypoints = null;

    public Integer getId() {
        return Id;
    }
    //ADD Set id if shit doesn't work!!!!!!
    public String getName() {
        return Name;
    }
    public void setName(String name){
        this.Name = name;
    }

    public String getDescription() {
        return Description;
    }
    public void setDescription(String description){
        this.Description = description;
    }

    public String getEpic() {
        return Epic;
    }
    public void setEpic(String epic){
        this.Epic = epic;
    }

    public String getStatus() {
        return Status;
    }
    public void setStatus(String status){
        this.Status = status;
    }

    public Integer getSprintNumber() {
        return SprintNumber;
    }
    public void setSprintNumber(Integer sprintNumber){
        this.SprintNumber = sprintNumber;
    }

    public Integer getBusinessValue() {
        return BusinessValue;
    }
    public void setBusinessValue(Integer businessValue){
        this.BusinessValue = businessValue;
    }

    public Integer getStorypoints() {
        return Storypoints;
    }
    public void setStorypoints(Integer storypoints){
        this.Storypoints = storypoints;
    }
}