package com.example.demo;

import com.example.demo.controller.RewardController;
import com.example.demo.service.RewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RewardController.class)
public class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @BeforeEach
    public void setUp() {
        Map<YearMonth, Integer> rewards = new HashMap<>();
        rewards.put(YearMonth.of(2023, 3), 195);

        when(rewardService.calculateMonthlyRewards(1L)).thenReturn(rewards);
    }

    @Test
    public void getMonthlyRewards() throws Exception {
        mockMvc.perform(get("/customers/1/rewards/2023/03"))
                .andExpect(status().isOk())
                .andExpect(content().string("195"));
    }
}
