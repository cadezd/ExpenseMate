package com.example.expensemate.databse.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.view.View;

import androidx.lifecycle.LiveData;

import com.example.expensemate.databse.connection.MyDatabase;
import com.example.expensemate.databse.dao.Dao;
import com.example.expensemate.databse.entities.User;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class UserRepository {
    private Dao dao;

    public UserRepository(Application application) {
        MyDatabase database = MyDatabase.getInstance(application);
        dao = database.dao();
    }

    public void insertUser(User user) {
        new InsertUserAsyncTask(dao).execute(user);
    }

    public void deleteUser(User user) {
        new DeleteUserAsyncTask(dao).execute(user);
    }

    public void updateUser(User user) {
        new UpdateUserAsyncTask(dao).execute(user);
    }

    public LiveData<User> findUserByUsernameAndPassword(String username, String password) {
        return dao.findUserByUsernameAndPassword(username, password);
    }

    public Boolean isUsernameTaken(String username) {
        return dao.isUsernameTaken(username);
    }

    private static class InsertUserAsyncTask extends android.os.AsyncTask<User, Void, Void> {
        private Dao dao;

        private InsertUserAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            dao.insertUser(users[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends android.os.AsyncTask<User, Void, Void> {
        private Dao dao;

        private DeleteUserAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            dao.deleteUser(users[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends android.os.AsyncTask<User, Void, Void> {
        private Dao dao;

        private UpdateUserAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            dao.updateUser(users[0]);
            return null;
        }
    }

}


