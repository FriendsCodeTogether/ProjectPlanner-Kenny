package com.mobileapps2.projectplanner.Entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    @NonNull public String userId;

    @ColumnInfo(name = "user_name")
    @NonNull public String userName;

    @ColumnInfo(name = "team_id")
    @Nullable public String teamID;

    @ColumnInfo(name = "user_password")
    @NonNull public String password;

    @ColumnInfo(name = "email")
    @NonNull public String email;

    public User() {this.userId = UUID.randomUUID().toString();}
}
