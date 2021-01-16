package com.mobileapps2.projectplanner.data.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mobileapps2.projectplanner.Entities.Team;

import java.util.List;

@Dao
public interface TeamDAO {
    @Insert
    void insertTeam(Team team);

    @Update
    void updateTeam(Team team);

    @Delete
    void deleteTeam(Team team);

    @Query("SELECT * FROM team")
    List<Team> getAllTeams();

    @Query("SELECT * FROM team WHERE id = :id")
    Team getTeamById(int id);
}
