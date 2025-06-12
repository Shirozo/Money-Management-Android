package com.shirozo.simplemoneymanagement.classes;

public class Monthly {

    private final Float income;
    private final Float expenses;
    private final Float saved;

    private final String date;

    private final Integer id;

    public Monthly (Integer id, Float income, Float expenses, String date) {
        this.id = id;
        this.income = income;
        this.expenses = expenses;
        this.date = date;
        this.saved = income - expenses;
    }

    public Integer getId() {
        return id;
    }

    public Float getIncome() {
        return income;
    }

    public Float getExpenses() {
        return expenses;
    }

    public Float getSaved() {
        return saved;
    }

    public String getDate() {
        return date;
    }
}
