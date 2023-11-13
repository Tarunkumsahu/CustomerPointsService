package com.example.demo.repository;

import com.example.demo.model.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CsvTransactionRepository implements TransactionRepository {

    private final Resource csvResource;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public CsvTransactionRepository(@Value("classpath:data.csv") Resource csvResource) {
        this.csvResource = csvResource;
    }

    @Override
    public List<Transaction> findByCustomerId(Long customerId) {
        List<Transaction> transactions = new ArrayList<>();
        try (Reader reader = new InputStreamReader(csvResource.getInputStream(), StandardCharsets.UTF_8);
             CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : parser) {
                if (Long.parseLong(record.get("CustomerID")) == customerId) {
                    Transaction transaction = new Transaction(
                            Long.parseLong(record.get("TransactionID")),
                            customerId,
                            Double.parseDouble(record.get("Amount")),
                            LocalDate.parse(record.get("Date"), dateFormatter)
                    );
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Proper exception handling should be implemented
        }
        return transactions;
    }

    @Override
    public List<Transaction> findByDate(LocalDate date) {
        List<Transaction> transactions = new ArrayList<>();
        try (Reader reader = new InputStreamReader(csvResource.getInputStream(), StandardCharsets.UTF_8);
             CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : parser) {
                LocalDate recordDate = LocalDate.parse(record.get("Date"), dateFormatter);
                if (recordDate.equals(date)) {
                    Transaction transaction = new Transaction(
                            Long.parseLong(record.get("TransactionID")),
                            Long.parseLong(record.get("CustomerID")),
                            Double.parseDouble(record.get("Amount")),
                            date
                    );
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Proper exception handling should be implemented
        }
        return transactions;
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        try (Reader reader = new InputStreamReader(csvResource.getInputStream(), StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                Transaction transaction = new Transaction(
                        Long.parseLong(record.get("TransactionID")),
                        Long.parseLong(record.get("CustomerID")),
                        Double.parseDouble(record.get("Amount")),
                        LocalDate.parse(record.get("Date"), dateFormatter));
                transactions.add(transaction);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Proper exception handling should be implemented
        }
        return transactions;
    }


    // Additional methods to be implemented as needed.
}
