package com.mobileapps2.projectplanner.data.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mobileapps2.projectplanner.data.Entities.Task;

import java.util.List;

@Dao
public interface TaskDAO {
    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM task")
    List<Task> getAllTasks();

    @Query("SELECT * FROM task WHERE task_id = :id")
    Task getTaskById(String id);

    @Query("SELECT * FROM task WHERE board_id = :id ORDER BY status ASC")
    List<Task> getAllTasksByBoardId(String id);
}
