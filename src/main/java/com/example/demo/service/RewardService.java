package com.example.demo.service;

import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardService {

    private final TransactionRepository transactionRepository;

    public RewardService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public int calculateRewardsForTransaction(double amount) {
        if (amount <= 50) return 0;
        int points = (int) (amount - 50);
        if (amount > 100) points += (int) (amount - 100);
        return points;
    }

    public Map<YearMonth, Integer> calculateMonthlyRewards(Long customerId) {
        List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);
        Map<YearMonth, Integer> monthlyRewards = new HashMap<>();

        for (Transaction transaction : transactions) {
            YearMonth yearMonth = YearMonth.from(transaction.getDate());
            int points = calculateRewardsForTransaction(transaction.getAmount());
            monthlyRewards.merge(yearMonth, points, Integer::sum);
        }

        return monthlyRewards;
    }

    public int calculateTotalRewards(Long customerId) {
        return calculateMonthlyRewards(customerId).values().stream().mapToInt(Integer::intValue).sum();
    }

    // Methods for other endpoints can be added here.
}
