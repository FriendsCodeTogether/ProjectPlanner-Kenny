package com.mobileapps2.projectplanner.data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mobileapps2.projectplanner.Entities.Board;
import com.mobileapps2.projectplanner.Entities.Task;
import com.mobileapps2.projectplanner.Entities.Team;

import java.util.List;

public class TeamWithBoards {
    @Embedded
    public Team team;
    @Relation(
            parentColumn = "team_id",
            entityColumn = "team_id"
    )
    public List<Board> teamBoards;
}
