package com.project.moneyj.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class TripPlanDetailResponseDTO {

    private Long planId;
    private String destination;
    private Integer duration;
    private LocalDate tripStartDate;
    private LocalDate tripEndDate;
    private Integer totalBudget;
    private Integer currentSavings;
    private LocalDate startDate;
    private LocalDate targetDate;
    private List<String> savingsPhrase;
    private List<String> tripTip;
    private List<MemberDto> members;

    @Data
    @AllArgsConstructor
    public static class MemberDto {
        private Long userId;
        private String nickname;
        private String email;
        private String image_url;
    }
}
