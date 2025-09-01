package com.project.moneyj.trip.dto;

import com.project.moneyj.trip.domain.TripPlan;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TripPlanRequestDTO {

    /**
     * 여행 플랜 생성 요청 DTO
     */

    @NotNull
    private String country;
    @NotNull
    private String countryCode;
    @NotNull
    private String city;

    @NotNull
    private Integer flightCost;
    @NotNull
    private Integer accommodationCost;
    @NotNull
    private Integer foodCost;
    @NotNull
    private Integer otherCost;

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
