package com.example.demo.model;

public class Customer {
    private Long id; // This corresponds to the CustomerID from the transaction data

    // Constructor
    public Customer(Long id) {
        this.id = id;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
