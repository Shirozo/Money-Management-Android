package com.shirozo.simplemoneymanagement.classes;

public class Money {

    private final Float income;
    private final Float expenses;
    private final Float saved;

    private final String date;

    private final Integer id;

    public Money(Integer id, Float income, Float expenses, Float saved, String date) {
        this.id = id;
        this.income = income;
        this.expenses = expenses;
        this.date = date;
        this.saved = saved;
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
