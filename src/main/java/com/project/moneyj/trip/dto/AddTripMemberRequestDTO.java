package com.project.moneyj.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AddTripMemberRequestDTO {

    private List<String> email;
}
