package com.example.expensemate.databse.queryResults;

public class YearlyAnalysis {
    private int year;
    private int income;
    private int expense;

    public YearlyAnalysis(int year, int income, int expense) {
        this.year = year;
        this.income = income;
        this.expense = expense;
    }

    public String getYear() {
        return String.valueOf(year);
    }

    public int getIncome() {
        return income;
    }

    public int getExpense() {
        return expense;
    }

    @Override
    public String toString() {
        return "YearlyAnalysis{" +
                "year=" + year +
                ", income=" + income +
                ", expense=" + expense +
                '}';
    }
}
