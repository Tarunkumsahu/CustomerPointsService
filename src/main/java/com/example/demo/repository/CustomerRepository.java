package com.example.demo.repository;

import com.example.demo.model.Customer;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(Long id);
    // Other methods can be added as needed, like findAll, save, delete, etc.
}
