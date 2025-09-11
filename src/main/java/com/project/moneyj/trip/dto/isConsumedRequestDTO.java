package com.project.moneyj.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class isConsumedRequestDTO {

    @NotNull
    private Long tripPlanId;

    @NotNull
    private String categoryName;

    @NotNull
    private boolean isConsumed;
}
