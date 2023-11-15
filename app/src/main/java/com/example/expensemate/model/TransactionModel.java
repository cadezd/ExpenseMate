package com.example.expensemate.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.expensemate.databse.entities.Transaction;
import com.example.expensemate.databse.repository.TransactionRepository;

import java.util.concurrent.ExecutionException;

public class TransactionModel extends AndroidViewModel {

    private TransactionRepository repository;

    public TransactionModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepository(application);
    }

    public void insertTransaction(Transaction transaction) {
        repository.insertTransaction(transaction);
    }

    public void updateTransaction(Transaction transaction) {
        repository.updateTransaction(transaction);
    }

    public void deleteTransaction(Transaction transaction) {
        repository.deleteTransaction(transaction);
    }

    public Transaction getTransaction(int id) throws ExecutionException, InterruptedException {
        return repository.getTransaction(id);
    }
}
