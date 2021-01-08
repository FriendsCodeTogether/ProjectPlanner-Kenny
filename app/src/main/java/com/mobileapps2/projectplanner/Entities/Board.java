package com.mobileapps2.projectplanner.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class Board implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "board_id")
    @NonNull public String boardId;

    @ColumnInfo(name = "board_name")
    @NonNull public String boardName;

    @ColumnInfo(name = "board_description")
    @NonNull public  String boardDescription;

    @ColumnInfo(name = "team_id")
    @NonNull public String teamId;

    public Board() {this.boardId = UUID.randomUUID().toString();}
}
