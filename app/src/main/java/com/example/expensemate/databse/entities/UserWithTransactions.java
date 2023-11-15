package com.example.expensemate.databse.entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Relation;

import java.util.List;


public class UserWithTransactions {
    @Embedded
    private User user;
    @Relation(
            entity = Transaction.class,
            parentColumn = "id",
            entityColumn = "userId"
    )
    private List<Transaction> transactions;

    public UserWithTransactions(User user, List<Transaction> transactions) {
        this.user = user;
        this.transactions = transactions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Ignore
    @Override
    public String toString() {
        return "UserWithTransactions{" +
                "user=" + user +
                ", transactions=" + transactions +
                '}';
    }
}
