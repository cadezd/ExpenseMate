package com.example.expensemate.databse.queryResults;

import androidx.room.Ignore;

public class DailyAnalysis {

    @Ignore
    private String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private int dayIndex;
    private int income;
    private int expense;

    public DailyAnalysis(int dayIndex, int income, int expense) {
        this.dayIndex = dayIndex;
        this.income = income;
        this.expense = expense;
    }

    public String getDay() {
        return days[dayIndex % days.length];
    }

    public int getIncome() {
        return income;
    }

    public int getExpense() {
        return expense;
    }

    @Override
    public String toString() {
        return "DailyAnalysis{" +
                "day=" + days[dayIndex %  days.length] +
                ", income=" + income +
                ", expense=" + expense +
                '}';
    }
}
