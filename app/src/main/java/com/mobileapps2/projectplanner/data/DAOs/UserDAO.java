package com.mobileapps2.projectplanner.data.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mobileapps2.projectplanner.Entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT * FROM user WHERE id = :id")
    User getUserById(int id);

    @Query("SELECT * FROM user WHERE user_name = :userName")
    User getUserByUserName(String userName);
}
