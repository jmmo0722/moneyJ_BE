package com.project.moneyj.trip.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class TripPlanRequestDTO {

    @NotNull
    private String destination;

    @Min(1)
    private Integer duration;

    @NotNull
    private LocalDate tripStartDate;

    @NotNull
    private LocalDate tripEndDate;

    @NotNull @Positive
    private Integer totalBudget;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate targetDate;

    @NotNull
    private List<String> tripMemberList;
}
