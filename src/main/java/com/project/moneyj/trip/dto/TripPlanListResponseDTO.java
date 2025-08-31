package com.project.moneyj.trip.dto;

import com.project.moneyj.trip.domain.TripPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class TripPlanListResponseDTO {

    private Long planId;
    private String country;
    private String city;
    private LocalDate tripStartDate;
    private LocalDate tripEndDate;
    private Integer totalBudget;
    private Integer currentSavings;

    public static TripPlanListResponseDTO fromEntity(TripPlan entity){
        return new TripPlanListResponseDTO(
                entity.getTrip_plan_id(),
                entity.getCountry(),
                entity.getCity(),
                entity.getTripStartDate(),
                entity.getTripEndDate(),
                entity.getTotalBudget(),
                entity.getCurrentSavings()
        );
    }
}
