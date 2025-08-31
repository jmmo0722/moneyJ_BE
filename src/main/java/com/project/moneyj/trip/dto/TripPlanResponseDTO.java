package com.project.moneyj.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TripPlanResponseDTO {

    private Long planId;
    private String message;
}
