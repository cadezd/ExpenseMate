package com.example.expensemate.databse.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensemate.databse.entities.Transaction;
import com.example.expensemate.databse.entities.User;
import com.example.expensemate.databse.entities.UserWithTransactions;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    /*USER*/

    @androidx.room.Transaction
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM User WHERE User.username = :username AND User.password = :password")
    LiveData<User> findUserByUsernameAndPassword(String username, String password);

    @Query("SELECT EXISTS( SELECT * FROM User WHERE User.username = :username )")
    Boolean isUsernameTaken(String username);



    /*TRANSACTION

    @Insert
    void insertTransaction(Transaction transaction);

    @Insert
    void insertTransactions(List<Transaction> transactions);

    @Update
    void updateTransactions(Transaction transaction);

    @Delete
    void deleteTransaction(Transaction transaction);

    @Query("SELECT * FROM [Transaction] WHERE id = :id")
    Transaction getTransaction(int id);

    /* USER WITH TRANSACTIONS

    @Query("SELECT * FROM User JOIN [Transaction] ON User.id = [Transaction].userId WHERE User.id = :userId")
    LiveData<UserWithTransactions> getUserTransactions(int userId);

    */
}
