package com.example.expensemate.databse.entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;


public class UserWithTransactions {
    @Embedded
    public User user;
    @Relation(
            entity = Transaction.class,
            parentColumn = "id",
            entityColumn = "userId"
    )
    public List<Transaction> transactions;

    public UserWithTransactions(User user, List<Transaction> transactions) {
        this.user = user;
        this.transactions = transactions;
    }
}
