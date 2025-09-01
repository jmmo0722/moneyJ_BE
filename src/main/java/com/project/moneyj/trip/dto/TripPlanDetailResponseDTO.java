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

    /**
     * 여행 플랜 상세 조회를 위한 응답 DTO
     */

    private Long planId;
    private String country;
    private String city;

    private Integer flightCost;
    private Integer accommodationCost;
    private Integer foodCost;
    private Integer otherCost;

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
                .planId(tripPlan.getTripPlanId())
                .country(tripPlan.getCountry())
                .city(tripPlan.getCity())
                .flightCost(tripPlan.getFlightCost())
                .accommodationCost(tripPlan.getAccommodationCost())
                .flightCost(tripPlan.getFoodCost())
                .otherCost(tripPlan.getOtherCost())
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
