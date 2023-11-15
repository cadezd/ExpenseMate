package com.example.expensemate.databse.repository;

import android.app.Application;

import com.example.expensemate.databse.connection.MyDatabase;
import com.example.expensemate.databse.dao.Dao;
import com.example.expensemate.databse.entities.Transaction;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionRepository {

    private Dao dao;

    public TransactionRepository(Application application) {
        MyDatabase database = MyDatabase.getInstance(application);
        dao = database.dao();
    }

    public void insertTransaction(Transaction transaction) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            dao.insertTransaction(transaction);
        });
    }

    public void updateTransaction(Transaction transaction) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            dao.updateTransaction(transaction);
        });
    }

    public void deleteTransaction(Transaction transaction) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            dao.deleteTransaction(transaction);
        });
    }

    public Transaction getTransaction(int id) throws ExecutionException, InterruptedException {
        Transaction transaction = Executors.newSingleThreadExecutor().submit(() -> {
            return dao.getTransaction(id);
        }).get();

        return transaction;
    }
}
