package com.example.expensemate.databse.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithTransactions {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "userId"
    )
    public List<Transaction> transactions;

    public UserWithTransactions(User user, List<Transaction> transactions) {
        this.user = user;
        this.transactions = transactions;
    }
}
