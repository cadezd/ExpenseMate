package com.example.expensemate.databse.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensemate.databse.entities.User;
import com.example.expensemate.databse.entities.UserTransaction;
import com.example.expensemate.databse.queryResults.DailyAnalysis;
import com.example.expensemate.databse.queryResults.MonthlyAnalysis;
import com.example.expensemate.databse.queryResults.WeeklyAnalysis;
import com.example.expensemate.databse.queryResults.YearlyAnalysis;

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

    @Query("SELECT * FROM UserTransaction WHERE userId = :userId AND date / (1000 * 60 * 60 * 24)= (:date / (1000 * 60 * 60 * 24)) ORDER BY date DESC")
    LiveData<List<UserTransaction>> getTodaysUserTransactions(int userId, long date);

    @Query("SELECT * FROM UserTransaction WHERE userId = :userId AND date / (1000 * 60 * 60 * 24) BETWEEN (:date / (1000 * 60 * 60 * 24)) - (:date / (1000 * 60 * 60 * 24)) % 7 AND (:date / (1000 * 60 * 60 * 24)) + 6 - (:date / (1000 * 60 * 60 * 24)) % 7 ORDER BY date DESC")
    LiveData<List<UserTransaction>> getThisWeeksUserTransactions(int userId, long date);

    @Query("SELECT * FROM UserTransaction WHERE userId = :userId AND date / (1000 * 60 * 60 * 24) BETWEEN (:date / (1000 * 60 * 60 * 24)) - (:date / (1000 * 60 * 60 * 24)) % 30 AND (:date / (1000 * 60 * 60 * 24)) + 29 - (:date / (1000 * 60 * 60 * 24)) % 30 ORDER BY date DESC")
    LiveData<List<UserTransaction>> getThisMonthsUserTransactions(int userId, long date);

    @Query("SELECT * FROM UserTransaction WHERE userId = :userId ORDER BY date DESC")
    LiveData<List<UserTransaction>> getUserTransactions(int userId);

    @Query("SELECT SUM(amount) FROM UserTransaction WHERE UserTransaction.userId = :userId")
    LiveData<Integer> getUserBalance(int userId);

    @Query("SELECT SUM(amount) FROM UserTransaction WHERE UserTransaction.userId = :userId AND UserTransaction.amount >= 0")
    LiveData<Integer> getUserIncome(int userId);

    @Query("SELECT SUM(amount) * -1 FROM UserTransaction WHERE UserTransaction.userId = :userId AND UserTransaction.amount < 0")
    LiveData<Integer> getUserExpense(int userId);

    // Query to get the daily income and expenses for the current week (grouped by day)
    @Query("SELECT strftime('%w', date / 1000, 'unixepoch') AS dayIndex, " +
            "SUM(CASE WHEN amount >= 0 THEN amount ELSE 0 END) AS income, " +
            "SUM(CASE WHEN amount < 0 THEN amount ELSE 0 END) * -1 AS expense " +
            "FROM UserTransaction " +
            "WHERE UserTransaction.userId = :userId " +
            "AND UserTransaction.date / (1000 * 60 * 60 * 24) BETWEEN (:date / (1000 * 60 * 60 * 24)) - (:date / (1000 * 60 * 60 * 24)) % 7 AND (:date / (1000 * 60 * 60 * 24)) + 6 - (:date / (1000 * 60 * 60 * 24)) % 7 " +
            "GROUP BY dayIndex " +
            "ORDER BY dayIndex ASC")
    LiveData<List<DailyAnalysis>> getWeeklyIncomeAndExpenses(int userId, long date);

    // Query to get the weekly income and expenses for the current month (grouped by week)
    @Query("SELECT strftime('%W', date / 1000, 'unixepoch') AS weekIndex, " +
            "SUM(CASE WHEN amount >= 0 THEN amount ELSE 0 END) AS income, " +
            "SUM(CASE WHEN amount < 0 THEN amount ELSE 0 END) * -1 AS expense " +
            "FROM UserTransaction " +
            "WHERE UserTransaction.userId = :userId " +
            "AND UserTransaction.date / (1000 * 60 * 60 * 24 * 7) BETWEEN (:date / (1000 * 60 * 60 * 24 * 7)) - (:date / (1000 * 60 * 60 * 24 * 7)) % 30 AND (:date / (1000 * 60 * 60 * 24 * 7)) + 29 - (:date / (1000 * 60 * 60 * 24 * 7)) % 30 " +
            "GROUP BY weekIndex " +
            "ORDER BY weekIndex ASC")
    LiveData<List<WeeklyAnalysis>> getMonthlyIncomeAndExpenses(int userId, long date);

    // Query to get the monthly income and expenses for the current year (grouped by month)
    @Query("SELECT strftime('%m', date / 1000, 'unixepoch') AS monthIndex, " +
            "SUM(CASE WHEN amount >= 0 THEN amount ELSE 0 END) AS income, " +
            "SUM(CASE WHEN amount < 0 THEN amount ELSE 0 END) * -1 AS expense " +
            "FROM UserTransaction " +
            "WHERE UserTransaction.userId = :userId " +
            "AND UserTransaction.date / (1000 * 60 * 60 * 24 * 30) BETWEEN (:date / (1000 * 60 * 60 * 24 * 30)) - (:date / (1000 * 60 * 60 * 24 * 30)) % 12 AND (:date / (1000 * 60 * 60 * 24 * 30)) + 11 - (:date / (1000 * 60 * 60 * 24 * 30)) % 12 " +
            "GROUP BY monthIndex " +
            "ORDER BY monthIndex ASC")
    LiveData<List<MonthlyAnalysis>> getYearlyIncomeAndExpenses(int userId, long date);

    // Query to get the yearly income and expenses for the current year (grouped by year)
    @Query("SELECT strftime('%Y', date / 1000, 'unixepoch') AS year, " +
            "SUM(CASE WHEN amount >= 0 THEN amount ELSE 0 END) AS income, " +
            "SUM(CASE WHEN amount < 0 THEN amount ELSE 0 END) * -1 AS expense " +
            "FROM UserTransaction " +
            "WHERE UserTransaction.userId = :userId " +
            "AND UserTransaction.date / (1000 * 60 * 60 * 24 * 365) BETWEEN (:date / (1000 * 60 * 60 * 24 * 365)) - (:date / (1000 * 60 * 60 * 24 * 365)) % 10 AND (:date / (1000 * 60 * 60 * 24 * 365)) + 9 - (:date / (1000 * 60 * 60 * 24 * 365)) % 10 " +
            "GROUP BY year " +
            "ORDER BY year ASC")
    LiveData<List<YearlyAnalysis>> getDecadeIncomeAndExpenses(int userId, long date);
}
