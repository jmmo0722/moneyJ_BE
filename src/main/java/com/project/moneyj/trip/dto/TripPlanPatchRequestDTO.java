package com.project.moneyj.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripPlanPatchRequestDTO {

    /**
     * 여행 플랜 수정을 위한 DTO
     * 모든 필드가 null 일 수 있으므로 @NotNull 사용 X.
     */

    private String country;
    private String city;

    private Integer flight_cost;
    private Integer accommodation_cost;
    private Integer food_cost;
    private Integer other_cost;

    private LocalDate tripStartDate;
    private LocalDate tripEndDate;

    private Integer totalBudget;
    private LocalDate targetDate;

    private List<String> tripMembersList;
}
