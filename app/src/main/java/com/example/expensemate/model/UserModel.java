package com.example.expensemate.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expensemate.databse.entities.User;
import com.example.expensemate.databse.repository.UserRepository;

import java.util.concurrent.ExecutionException;

public class UserModel extends AndroidViewModel {

    // creating a new variable for course repository.
    private UserRepository repository;

    // constructor for our view modal.
    public UserModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    // below method is use to insert the data to our repository.
    public void insert(User user) {
        repository.insertUser(user);
    }

    // below line is to update data in our repository.
    public void update(User user) {
        repository.updateUser(user);
    }

    // below line is to delete the data in our repository.
    public void delete(User user) {
        repository.deleteUser(user);
    }

    public LiveData<User> findUserByUsernameAndPassword(String username, String password) {
        return repository.findUserByUsernameAndPassword(username, password);
    }

    public Boolean isUsernameTaken(String username) throws ExecutionException, InterruptedException {
        return repository.isUsernameTaken(username);
    }
}