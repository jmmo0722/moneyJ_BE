package com.project.moneyj.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


@Data
@AllArgsConstructor
public class TripBudgetRequestDTO {
    private String country;
    private String city;
    private int nights;
    private int days;
    private LocalDate startDate;
    private LocalDate endDate;
}
