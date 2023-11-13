package com.example.demo.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Reward {
    private Long customerId;
    private Map<LocalDate, Integer> monthlyPoints;
    private Integer totalPoints;

    public Reward(Long customerId) {
        this.customerId = customerId;
        this.monthlyPoints = new HashMap<>();
        this.totalPoints = 0;
    }

    // Method to add points for a specific month
    public void addMonthlyPoints(LocalDate date, int points) {
        monthlyPoints.merge(date.withDayOfMonth(1), points, Integer::sum);
        totalPoints += points;
    }

    // Getters and Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Map<LocalDate, Integer> getMonthlyPoints() {
        return monthlyPoints;
    }

    public void setMonthlyPoints(Map<LocalDate, Integer> monthlyPoints) {
        this.monthlyPoints = monthlyPoints;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }
}
