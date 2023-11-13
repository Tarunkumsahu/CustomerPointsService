package com.example.demo.controller;

import com.example.demo.service.RewardCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/report")
public class ReportGenerationController {

    private final RewardCalculationService rewardCalculationService;
    private final ResourceLoader resourceLoader;

    @Autowired
    public ReportGenerationController(RewardCalculationService rewardCalculationService, ResourceLoader resourceLoader) {
        this.rewardCalculationService = rewardCalculationService;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/generate-rewards")
    public ResponseEntity<?> generateRewardsReport() {
        // Define the file path in the 'target' directory
        String outputFilePath = "target/rewards_report.csv";
        try {
            rewardCalculationService.calculateAndWriteRewardsToFile(outputFilePath);
            Path path = Paths.get(outputFilePath);
            Resource resource = resourceLoader.getResource("file:" + path.toAbsolutePath());
            return ResponseEntity.ok().body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error generating rewards report: " + e.getMessage());
        }
    }
}
