package com.mobileapps2.projectplanner.data.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.mobileapps2.projectplanner.data.Entities.Team;

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

    @Transaction
    @Query("SELECT*FROM Team WHERE user_id=:id")
    List<Team> getAllMyTeams(String id);
}
