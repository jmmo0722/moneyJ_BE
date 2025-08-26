package com.project.moneyj.trip.dto;

import com.project.moneyj.trip.domain.TripPlan;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TripPlanListResponse {

    private Long planId;
    private String destination;
    private LocalDate tripStartDate;
    private LocalDate tripEndDate;
    private Integer totalBudget;
    private Integer currentSavings;

    public static TripPlanListResponse fromEntity(TripPlan entity){
        return new TripPlanListResponse(
                entity.getTrip_plan_id(),
                entity.getDestination(),
                entity.getTripStartDate(),
                entity.getTripEndDate(),
                entity.getTotalBudget(),
                entity.getCurrentSavings()
        );
    }
}
