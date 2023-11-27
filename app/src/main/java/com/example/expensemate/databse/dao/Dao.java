package com.example.expensemate.databse.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensemate.databse.entities.User;
import com.example.expensemate.databse.entities.UserTransaction;
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
    User findUserByUsernameAndPassword(String username, String password);

    @Query("SELECT EXISTS( SELECT * FROM User WHERE User.username = :username )")
    Boolean isUsernameTaken(String username);


    /*TRANSACTION*/

    @Insert
    void insertTransaction(UserTransaction transaction);

    @Update
    void updateTransaction(UserTransaction transaction);

    @Delete
    void deleteTransaction(UserTransaction transaction);

    @Query("SELECT * FROM UserTransaction WHERE id = :id")
    UserTransaction getTransaction(int id);

    @Query("SELECT * FROM UserTransaction WHERE userId = :userId ORDER BY date DESC")
    LiveData<List<UserTransaction>> getUserTransactions(int userId);

    @Query("SELECT SUM(amount) FROM UserTransaction WHERE UserTransaction.userId = :userId")
    LiveData<Integer> getUserBalance(int userId);

    @Query("SELECT SUM(amount) FROM UserTransaction WHERE UserTransaction.userId = :userId AND UserTransaction.amount >= 0")
    LiveData<Integer> getUserIncome(int userId);

    @Query("SELECT SUM(amount) * -1 FROM UserTransaction WHERE UserTransaction.userId = :userId AND UserTransaction.amount < 0")
    LiveData<Integer> getUserExpense(int userId);
}
