package com.example.expensemate.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expensemate.databse.entities.UserTransaction;
import com.example.expensemate.databse.repository.UserTransactionRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserTransactionModel extends AndroidViewModel {

    private UserTransactionRepository repository;
    private int userId;
    private LiveData<Integer> userBalance;
    private LiveData<Integer> userIncome;
    private LiveData<Integer> userExpense;
    private LiveData<List<UserTransaction>> userTransactions;

    public UserTransactionModel(@NonNull Application application, int userId) {
        super(application);
        repository = new UserTransactionRepository(application);
        this.userId = userId;
        userBalance = repository.getUserBalance(this.userId);
        userIncome = repository.getUserIncome(this.userId);
        userExpense = repository.getUserExpense(this.userId);
        userTransactions = repository.getUserTransactions(this.userId);
    }

    public void insertTransaction(UserTransaction transaction) {
        repository.insertTransaction(transaction);
    }

    public void updateTransaction(UserTransaction transaction) {
        repository.updateTransaction(transaction);
    }

    public void deleteTransaction(UserTransaction transaction) {
        repository.deleteTransaction(transaction);
    }

    public UserTransaction getTransaction(int id) throws ExecutionException, InterruptedException {
        return repository.getTransaction(id);
    }

    public LiveData<List<UserTransaction>> getUserTransactions() {
        return userTransactions;
    }

    public LiveData<Integer> getUserBalance() {
        return userBalance;
    }

    public LiveData<Integer> getUserIncome() {
        return userIncome;
    }

    public LiveData<Integer> getUserExpense() {
        return userExpense;
    }
}
