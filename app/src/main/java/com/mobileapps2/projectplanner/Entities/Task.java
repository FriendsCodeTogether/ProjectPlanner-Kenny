package com.mobileapps2.projectplanner.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.intellij.lang.annotations.JdkConstants;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "task_id")
    @NonNull public String taskId;

    @ColumnInfo(name = "board_id")
    @NonNull public String boardId;

    @ColumnInfo(name = "task_name")
    @NonNull public String taskName;

    @ColumnInfo(name = "business_value")
    @NonNull public int businessValue;

    @ColumnInfo(name = "description")
    @NonNull public String description;

    @ColumnInfo(name = "epic")
    @NonNull public String epic;

    @ColumnInfo(name = "sprint")
    @NonNull public int sprint;

    @ColumnInfo(name = "status")
    @NonNull public String status;

    @ColumnInfo(name = "story_points")
    @NonNull public int storyPoints;

    @ColumnInfo(name = "progress")
    @NonNull public int progress;

    public Task() {this.taskId = UUID.randomUUID().toString();}

}
