package com.project.moneyj.trip.dto;

import com.project.moneyj.trip.domain.TripPlans;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TripPlansListResponse {

    private Long planId;
    private String destination;
    private LocalDate tripStartDate;
    private LocalDate tripEndDate;
    private Integer totalBudget;
    private Integer currentSavings;

    public static TripPlansListResponse fromEntity(TripPlans entity){
        return new TripPlansListResponse(
                entity.getTrip_plans_id(),
                entity.getDestination(),
                entity.getTripStartDate(),
                entity.getTripEndDate(),
                entity.getTotalBudget(),
                entity.getCurrentSavings()
        );
    }
}
