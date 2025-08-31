package com.project.moneyj.trip.dto;

import com.project.moneyj.trip.domain.TripMember;
import com.project.moneyj.trip.domain.TripPlan;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TripPlanDetailResponseDTO {

    private Long planId;
    private String country;
    private String city;

    private Integer flight_cost;
    private Integer accommodation_cost;
    private Integer food_cost;
    private Integer other_cost;

    private Integer duration;
    private LocalDate tripStartDate;
    private LocalDate tripEndDate;

    private Integer totalBudget;
    private Integer currentSavings;

    private LocalDate startDate;
    private LocalDate targetDate;

    private List<String> savingsPhrase;
    private List<String> tripTip;

    private List<TripMemberDTO> tripMemberDTOList;

    public static TripPlanDetailResponseDTO fromEntity(TripPlan tripPlan, List<String> savingsPhrase, List<String> tripTip, List<TripMemberDTO> tripMemberDTOList){
        return TripPlanDetailResponseDTO.builder()
                .planId(tripPlan.getTrip_plan_id())
                .country(tripPlan.getCountry())
                .city(tripPlan.getCity())
                .flight_cost(tripPlan.getFlight_cost())
                .accommodation_cost(tripPlan.getAccommodation_cost())
                .food_cost(tripPlan.getFood_cost())
                .other_cost(tripPlan.getOther_cost())
                .duration(tripPlan.getDuration())
                .tripStartDate(tripPlan.getTripStartDate())
                .tripEndDate(tripPlan.getTripEndDate())
                .totalBudget(tripPlan.getTotalBudget())
                .currentSavings(tripPlan.getCurrentSavings())
                .startDate(tripPlan.getStartDate())
                .targetDate(tripPlan.getTargetDate())
                .savingsPhrase(savingsPhrase)
                .tripTip(tripTip)
                .tripMemberDTOList(tripMemberDTOList)
                .build();
    }

}
