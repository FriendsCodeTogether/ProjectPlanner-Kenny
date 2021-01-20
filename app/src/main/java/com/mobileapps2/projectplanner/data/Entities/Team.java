package com.mobileapps2.projectplanner.data.Entities;

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

    @ColumnInfo(name = "team_name")
    @NonNull public String teamName;

    @ColumnInfo(name = "user_id")
    @NonNull public String userId;

    public Team() {this.teamId = UUID.randomUUID().toString();}
}
