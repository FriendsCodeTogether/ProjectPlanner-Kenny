package com.mobileapps2.projectplanner.data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mobileapps2.projectplanner.data.Entities.Board;
import com.mobileapps2.projectplanner.data.Entities.Task;

import java.util.List;

public class BoardWithTasks {
    @Embedded
    public Board board;
    @Relation(
            parentColumn = "board_id",
            entityColumn = "board_id"
    )
    public List<Task> boardTasks;
}
