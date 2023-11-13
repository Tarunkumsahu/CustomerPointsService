package com.example.demo.repository;

import com.example.demo.model.Transaction;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository {
    List<Transaction> findAll();
    List<Transaction> findByCustomerId(Long customerId);
    List<Transaction> findByDate(LocalDate date);
    // Additional methods as needed can be included here.
}
