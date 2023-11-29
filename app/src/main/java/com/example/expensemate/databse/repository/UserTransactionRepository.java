package com.example.expensemate.databse.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.expensemate.databse.connection.MyDatabase;
import com.example.expensemate.databse.dao.Dao;
import com.example.expensemate.databse.entities.UserTransaction;
import com.example.expensemate.databse.queryResults.DailyAnalysis;
import com.example.expensemate.databse.queryResults.MonthlyAnalysis;
import com.example.expensemate.databse.queryResults.WeeklyAnalysis;
import com.example.expensemate.databse.queryResults.YearlyAnalysis;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserTransactionRepository {

    private Dao dao;

    public UserTransactionRepository(Application application) {
        MyDatabase database = MyDatabase.getInstance(application);
        dao = database.dao();
    }

    public void insertTransaction(UserTransaction transaction) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            dao.insertTransaction(transaction);
        });
    }

    public void updateTransaction(UserTransaction transaction) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            dao.updateTransaction(transaction);
        });
    }

    public void deleteTransaction(UserTransaction transaction) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            dao.deleteTransaction(transaction);
        });
    }

    public UserTransaction getTransaction(int id) throws ExecutionException, InterruptedException {
        UserTransaction transaction = Executors.newSingleThreadExecutor().submit(() -> {
            return dao.getTransaction(id);
        }).get();

        return transaction;
    }

    public LiveData<List<UserTransaction>> getUserTransactions(int userId) {
        return dao.getUserTransactions(userId);
    }

    public LiveData<Integer> getUserBalance(int userId) {
        return dao.getUserBalance(userId);
    }

    public LiveData<Integer> getUserIncome(int userId) {
        return dao.getUserIncome(userId);
    }

    public LiveData<Integer> getUserExpense(int userId) {
        return dao.getUserExpense(userId);
    }

    public LiveData<List<UserTransaction>> getTodaysUserTransactions(int userId, long date) {
        return dao.getTodaysUserTransactions(userId, date);
    }

    public LiveData<List<UserTransaction>> getThisWeeksUserTransactions(int userId, long date) {
        return dao.getThisWeeksUserTransactions(userId, date);
    }

    public LiveData<List<UserTransaction>> getThisMonthsUserTransactions(int userId, long date) {
        return dao.getThisMonthsUserTransactions(userId, date);
    }

    public LiveData<List<DailyAnalysis>> getWeeklyIncomeAndExpenses(int userId, long date) {
        return dao.getWeeklyIncomeAndExpenses(userId, date);
    }

    public LiveData<List<WeeklyAnalysis>> getMonthlyIncomeAndExpenses(int userId, long date) {
        return dao.getMonthlyIncomeAndExpenses(userId, date);
    }

    public LiveData<List<MonthlyAnalysis>> getYearlyIncomeAndExpenses(int userId, long date) {
        return dao.getYearlyIncomeAndExpenses(userId, date);
    }

    public LiveData<List<YearlyAnalysis>> getDecadeIncomeAndExpenses(int userId, long date) {
        return dao.getDecadeIncomeAndExpenses(userId, date);
    }
}
