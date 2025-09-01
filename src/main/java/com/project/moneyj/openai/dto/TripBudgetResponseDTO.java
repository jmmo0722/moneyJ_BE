package com.project.moneyj.openai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TripBudgetResponseDTO {
    private int flightCost;
    private int accommodationCost;
    private int foodCost;
    private int otherCost;
    private int totalCost;
}
