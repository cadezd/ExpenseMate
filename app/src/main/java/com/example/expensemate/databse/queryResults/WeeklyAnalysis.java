package com.example.expensemate.databse.queryResults;

public class WeeklyAnalysis {
    private int weekIndex;
    private int income;
    private int expense;

    public WeeklyAnalysis(int weekIndex, int income, int expense) {
        this.weekIndex = weekIndex;
        this.income = income;
        this.expense = expense;
    }

    public String getWeek() {
        return "Week " + (weekIndex + 1);
    }

    public int getIncome() {
        return income;
    }

    public int getExpense() {
        return expense;
    }

    @Override
    public String toString() {
        return "WeeklyAnalysis{" +
                "week=" + "Week " + (weekIndex + 1) +
                ", income=" + income +
                ", expense=" + expense +
                '}';
    }
}
