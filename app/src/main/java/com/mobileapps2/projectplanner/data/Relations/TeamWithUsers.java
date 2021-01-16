package com.mobileapps2.projectplanner.data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mobileapps2.projectplanner.Entities.Board;
import com.mobileapps2.projectplanner.Entities.Team;
import com.mobileapps2.projectplanner.Entities.User;

import java.util.List;

public class TeamWithUsers {
    @Embedded
    public Team team;
    @Relation(
            parentColumn = "team_id",
            entityColumn = "team_id"
    )
    public List<User> teamUsers;
}
