package com.example.expensemate.databse.queryResults;

import androidx.room.Ignore;

public class MonthlyAnalysis {

    @Ignore
    private String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"};
    private int monthIndex;
    private int income;
    private int expense;

    public MonthlyAnalysis(int monthIndex, int income, int expense) {
        this.monthIndex = monthIndex - 1;
        this.income = income;
        this.expense = expense;
    }

    public String getMonth() {
        return months[monthIndex % months.length];
    }

    public int getIncome() {
        return income;
    }

    public int getExpense() {
        return expense;
    }

    @Override
    public String toString() {
        return "MonthlyAnalysis{" +
                "month=" + months[monthIndex % months.length] +
                ", income=" + income +
                ", expense=" + expense +
                '}';
    }
}
