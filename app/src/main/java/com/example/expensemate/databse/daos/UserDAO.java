package com.example.expensemate.databse.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensemate.databse.entities.User;

@Dao
public interface UserDAO {
    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    User findByUsernameAndPassword(String username, String password);
}
