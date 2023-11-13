package com.example.demo.model;

import java.time.LocalDate;

public class Transaction {
    private Long transactionId;
    private Long customerId;
    private Double amount;
    private LocalDate date;

    // Constructor
    public Transaction(Long transactionId, Long customerId, Double amount, LocalDate date) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.amount = amount;
        this.date = date;
    }

    // Getters and Setters
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Override hashCode, equals, and toString methods as appropriate
}
