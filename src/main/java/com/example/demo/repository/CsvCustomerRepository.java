package com.example.demo.repository;

import com.example.demo.model.Customer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class CsvCustomerRepository implements CustomerRepository {

    private final Resource csvResource;

    public CsvCustomerRepository(@Value("classpath:data.csv") Resource csvResource) {
        this.csvResource = csvResource;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        Set<Customer> customers = readCustomersFromCsv();
        return customers.stream().filter(customer -> customer.getId().equals(id)).findFirst();
    }

    private Set<Customer> readCustomersFromCsv() {
        Set<Customer> customers = new HashSet<>();
        try (Reader reader = new InputStreamReader(csvResource.getInputStream(), StandardCharsets.UTF_8);
             CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : parser) {
                Long customerId = Long.parseLong(record.get("CustomerID")); // Replace with your actual header name
                customers.add(new Customer(customerId));
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Proper exception handling should be implemented
        }
        return customers;
    }
    // Additional methods as needed
}
