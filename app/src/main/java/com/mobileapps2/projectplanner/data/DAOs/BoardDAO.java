package com.mobileapps2.projectplanner.data.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mobileapps2.projectplanner.Entities.Board;

import java.util.List;

@Dao
public interface BoardDAO {
    @Insert
    void insertBoard(Board board);

    @Update
    void updateBoard(Board board);

    @Delete
    void deleteBoard(Board board);

    @Query("SELECT * FROM board")
    List<Board> getAllBoards();

    @Query("SELECT * FROM board WHERE id = :id")
    Board getBoardById(int id);

    @Query("SELECT * FROM board WHERE team_id = :teamId")
    List<Board> getAllBoardsForTeam(String teamId);
}
