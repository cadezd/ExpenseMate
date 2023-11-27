package com.example.expensemate.databse.entities;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

import java.util.List;


public class UserWithTransactions {
    @Embedded
    private User user;
    @Relation(
            entity = UserTransaction.class,
            parentColumn = "id",
            entityColumn = "userId"
    )
    private List<UserTransaction> transactions;

    public UserWithTransactions(User user, List<UserTransaction> transactions) {
        this.user = user;
        this.transactions = transactions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<UserTransaction> transactions) {
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
