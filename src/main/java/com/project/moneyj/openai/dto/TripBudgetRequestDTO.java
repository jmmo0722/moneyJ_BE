package com.project.moneyj.openai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TripBudgetRequestDTO {
    private String destination;
    private int days;
    private LocalDate startDate;
    private LocalDate endDate;
}
