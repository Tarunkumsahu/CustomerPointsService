package com.example.demo.controller;

import com.example.demo.service.RewardService;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class RewardController {

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("/{customerId}/rewards/{year}/{month}")
    public Integer getMonthlyRewards(@PathVariable Long customerId, @PathVariable int year, @PathVariable int month) {
        Map<YearMonth, Integer> rewards = rewardService.calculateMonthlyRewards(customerId);
        return rewards.getOrDefault(YearMonth.of(year, month), 0);
    }

    @GetMapping("/{customerId}/rewards/total")
    public Integer getTotalRewards(@PathVariable Long customerId) {
        return rewardService.calculateTotalRewards(customerId);
    }

    // Implement other endpoints here
}
