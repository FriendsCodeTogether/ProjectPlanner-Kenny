package com.mobileapps2.projectplanner.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class Team implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "team_id")
    @NonNull public String teamId;

    @ColumnInfo(name = "user_name")
    @NonNull public String teamName;

    public Team() {this.teamId = UUID.randomUUID().toString();}
}
