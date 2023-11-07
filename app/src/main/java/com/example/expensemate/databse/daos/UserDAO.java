package com.example.expensemate.databse.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensemate.databse.entities.Transaction;
import com.example.expensemate.databse.entities.User;
import com.example.expensemate.databse.entities.UserWithTransactions;

import java.util.List;

@Dao
public interface UserDAO {
    @androidx.room.Transaction
    @Insert
    void insertUser(User user);

    @androidx.room.Transaction
    @Delete
    void deleteUser(User user);

    @androidx.room.Transaction
    @Update
    void updateUser(User user);

    @androidx.room.Transaction
    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    User findByUsernameAndPassword(String username, String password);

    @androidx.room.Transaction
    @Insert
    void insertTransactions(List<Transaction> transactions);

    @androidx.room.Transaction
    @Query("SELECT * FROM User WHERE id = :userId")
    List<UserWithTransactions> getUserTransactions(int userId);
}
