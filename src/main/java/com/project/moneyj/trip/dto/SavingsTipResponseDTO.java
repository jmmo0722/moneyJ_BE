package com.project.moneyj.trip.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SavingsTipResponseDTO {
    private List<String> messages;
}
