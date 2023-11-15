package com.example.expensemate.databse.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.expensemate.databse.connection.MyDatabase;
import com.example.expensemate.databse.dao.Dao;
import com.example.expensemate.databse.entities.User;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class UserRepository {
    private Dao dao;

    public UserRepository(Application application) {
        MyDatabase database = MyDatabase.getInstance(application);
        dao = database.dao();
    }

    public void insertUser(User user) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            dao.insertUser(user);
        });
    }

    public void deleteUser(User user) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            dao.deleteUser(user);
        });
    }

    public void updateUser(User user) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            dao.updateUser(user);
        });
    }

    public Boolean isUsernameTaken(String username) throws ExecutionException, InterruptedException {
        Boolean usernameTaken = Executors.newSingleThreadExecutor().submit(() -> {
            return dao.isUsernameTaken(username);
        }).get();

        return usernameTaken;
    }

    public LiveData<User> findUserByUsernameAndPassword(String username, String password) {
        return dao.findUserByUsernameAndPassword(username, password);
    }

}


