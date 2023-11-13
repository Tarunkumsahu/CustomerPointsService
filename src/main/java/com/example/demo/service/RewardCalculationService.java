package com.example.demo.service;

import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardCalculationService {

    private final TransactionRepository transactionRepository;
    private final RewardService rewardService;

    @Autowired
    public RewardCalculationService(TransactionRepository transactionRepository, RewardService rewardService) {
        this.transactionRepository = transactionRepository;
        this.rewardService = rewardService;
    }

    public void calculateAndWriteRewardsToFile(String outputFilePath) throws IOException {
        Map<Long, Map<YearMonth, Integer>> rewardsByCustomer = calculateRewards();

        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("CustomerID", "YearMonth", "MonthlyPoints", "TotalPoints"))
        ) {
            for (Map.Entry<Long, Map<YearMonth, Integer>> customerEntry : rewardsByCustomer.entrySet()) {
                Long customerId = customerEntry.getKey();
                Map<YearMonth, Integer> monthlyRewards = customerEntry.getValue();
                int totalPoints = monthlyRewards.values().stream().mapToInt(Integer::intValue).sum();

                for (Map.Entry<YearMonth, Integer> monthEntry : monthlyRewards.entrySet()) {
                    YearMonth yearMonth = monthEntry.getKey();
                    int monthlyPoints = monthEntry.getValue();

                    csvPrinter.printRecord(customerId, yearMonth.toString(), monthlyPoints, totalPoints);
                }
            }
            csvPrinter.flush();
        }
    }

    private Map<Long, Map<YearMonth, Integer>> calculateRewards() {
        List<Transaction> transactions = transactionRepository.findAll();
        Map<Long, Map<YearMonth, Integer>> rewardsByCustomer = new HashMap<>();

        for (Transaction transaction : transactions) {
            Long customerId = transaction.getCustomerId();
            YearMonth yearMonth = YearMonth.from(transaction.getDate());
            int points = rewardService.calculateRewardsForTransaction(transaction.getAmount()); //.calculatePointsForTransaction(transaction.getAmount());

            rewardsByCustomer.computeIfAbsent(customerId, k -> new HashMap<>())
                    .merge(yearMonth, points, Integer::sum);
        }

        return rewardsByCustomer;
    }
}
