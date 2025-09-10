package com.project.moneyj.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class isConsumedResponseDTO {

    private String message;

    @NotNull
    private boolean isConsumed;
}
