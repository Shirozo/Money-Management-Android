package com.shirozo.simplemoneymanagement.classes;

public class Transaction {

    private final int id;

    private final String transaction;

    private final String transaction_date;

    private final String transaction_amount;

    private final Integer type;

    public Transaction(int id, String transaction, String transaction_amount, Integer type, String transaction_date) {
        this.id = id;
        this.transaction = transaction;
        this.transaction_date = transaction_date;
        this.transaction_amount = transaction_amount;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getTransaction() {
        return transaction;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public String getTransaction_amount() {
        return transaction_amount;
    }

    public Integer getType() {
        return type;
    }
}
