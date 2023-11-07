package com.example.expensemate.databse.connection;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.expensemate.databse.daos.UserDAO;
import com.example.expensemate.databse.entities.User;

// START source: https://www.youtube.com/watch?v=qO56SL856xc&list=PLdHg5T0SNpN3CMNtsd5KGaiBtzhTGIwtC
@Database(entities = {User.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();

    private static volatile MyDatabase INSTANCE;

    static MyDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), MyDatabase.class, "EXPENSE_MATE_DB")
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}

// END source: https://www.youtube.com/watch?v=qO56SL856xc&list=PLdHg5T0SNpN3CMNtsd5KGaiBtzhTGIwtC